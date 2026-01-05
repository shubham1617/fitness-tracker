package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.AiRecommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
//@NoArgsConstructor(force = true)
public class ActivityAiService
{
    private final GeminiService geminiService;

    public AiRecommendation generateRecommendation(Activity activity)
    {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAiRecommendation(prompt);
        log.info("prompt: {}", aiResponse);
        return processAIResponse(activity,aiResponse);
    }

    private AiRecommendation processAIResponse(Activity activity, String aiResponse) {
        try
            {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(aiResponse);
                JsonNode textNode = rootNode.path("candidates").get(0)
                        .path("content")
                        .path("parts").get(0)
                        .get("text");

                String jsonResponse = textNode.asText().replaceAll("```json\\n", "")
                        .replaceAll("\\n```", "")
                        .replaceAll("```json", "")
                        .trim();
                log.info("text: {}", jsonResponse);
                JsonNode analysisJson = mapper.readTree(jsonResponse);
                JsonNode analysisNode = analysisJson.get("analysis");
                StringBuilder  fullAnalysis = new StringBuilder();
                addAnalysisSection(fullAnalysis,analysisNode,"overall","Overall: ");
                addAnalysisSection(fullAnalysis,analysisNode,"pace","Pace: ");
                addAnalysisSection(fullAnalysis,analysisNode,"heartRate","Heartrate : ");
                addAnalysisSection(fullAnalysis,analysisNode,"caloriesBurned","Burned Chalories: ");

                List<String> improvements = extractImprovement(analysisJson.path("improvements"));
                List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
                List<String> safetyList = extractSafetyList(analysisJson.path("safety"));

                return AiRecommendation.builder()
                        .activityId(activity.getActivityId())
                        .userId(activity.getUserId())
                        .type(activity.getType().toString())
                        .recommendation(fullAnalysis.toString().trim())
                        .improvements(improvements)
                        .suggestions(suggestions)
                        .safety(safetyList)
                        .createDt(LocalDateTime.now())
                        .build();
            }
        catch (Exception e)
        {
            e.printStackTrace();
            return defaultRecommendation(activity);
        }
    }

    private AiRecommendation defaultRecommendation(Activity activity) {
        return AiRecommendation.builder()
                .activityId(activity.getActivityId())
                .userId(activity.getUserId())
                .type(activity.getType().toString())
                .recommendation("Invalid")
                .improvements(Collections.singletonList("Invalid"))
                .suggestions(Collections.singletonList("Invalid"))
                .safety(Collections.singletonList("Invalid"))
                .createDt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyList(JsonNode safety) {
        List<String> safetyList = new ArrayList<>();
        if(safety.isArray()){
            safety.forEach(jsonNode -> {
                String value = jsonNode.asText();
                safetyList.add(value);
            });
        }
        return safetyList;
    }

    private List<String> extractSuggestions(JsonNode suggestions) {
        List<String> suggestionList = new ArrayList<>();
        if(suggestions.isArray()){
            suggestions.forEach(jsonNode -> {
                String workout = jsonNode.path("workout").asText();
                String description = jsonNode.path("description").asText();
                suggestionList.add(String.format("%s: %s",workout,description));
            });
        }
        return suggestionList.isEmpty() ? Collections.singletonList("No Specific Suggestions Provided") : suggestionList;
    }

    private List<String> extractImprovement(JsonNode improvements) {
        List<String> improvementList = new ArrayList<>();
        if(improvements.isArray()){
            improvements.forEach(improvement->{
                String area = improvement.path("area").asText();
                String recommendation =improvement.path("recommendation").asText();
                improvementList.add(String.format("%s: %s",area,recommendation));
            });
        }

        return improvementList;

    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
    if(!analysisNode.path(key).isMissingNode())
    {
        fullAnalysis.append(prefix).append(analysisNode.path(key).asText()).append("\n\n");
    }

    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCalorieBurn(),
                activity.getType(),
                activity.getDuration(),
                activity.getCalorieBurn()
        );
    }
}
