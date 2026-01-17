package com.fitness.aiservice.repo;

import com.fitness.aiservice.model.AiRecommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepo extends MongoRepository<AiRecommendation,String>
{
    public List<AiRecommendation> getAllByUserId(String userId);
    public AiRecommendation getByActivityId(String activityId);
}
