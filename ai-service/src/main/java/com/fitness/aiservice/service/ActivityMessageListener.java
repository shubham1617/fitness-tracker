package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.AiRecommendation;
import com.fitness.aiservice.repo.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener
{
    private final ActivityAiService activityAiService;
    private final RecommendationRepo  recommendationRepo;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivityMessage(Activity activity)
    {
        log.info("Received activity message from user id {}", activity.getUserId());
        AiRecommendation aiRecommendation = activityAiService.generateRecommendation(activity);
        AiRecommendation save = recommendationRepo.save(aiRecommendation);
        log.info("Saved recommendation {}", save);
    }
}
