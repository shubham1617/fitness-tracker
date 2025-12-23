package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.AiRecommendation;
import com.fitness.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendation")
public class RecommendationController
{
    private final RecommendationService aiRecommendationServive;

    @GetMapping("/activiy/{id}")
    public ResponseEntity<List<AiRecommendation>> getUserRecommendation(@PathVariable String id)
    {
       return ResponseEntity.ok(aiRecommendationServive.getUserRecommendation(id));
    }


    @GetMapping("/activity/{activityId}")
    public ResponseEntity<AiRecommendation> getActivityRecommendation(@PathVariable String activityId)
    {
        return ResponseEntity.ok(aiRecommendationServive.getRecommendationByActivityId(activityId));
    }
}
