package com.fitness.aiservice.service;

import com.fitness.aiservice.model.AiRecommendation;
import com.fitness.aiservice.repo.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService
{
    private final RecommendationRepo recommendationRepo;

    public List<AiRecommendation> getUserRecommendation(String id)
    {
        List<AiRecommendation> allByUserId = recommendationRepo.getAllByUserId(id);
        return allByUserId;
    }

    public AiRecommendation getRecommendationByActivityId(String id)
    {
        AiRecommendation byActivityId = recommendationRepo.getByActivityId(id);
        return byActivityId;
    }
}
