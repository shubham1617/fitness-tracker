package com.fitness.aiservice.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "ai_recommendation")
public class AiRecommendation
{
    @Id
    private String id;
    private String activityId;
    private String userId;
    private String recommendation;
    private List<String> suggestions;
    private List<String> safety;
    @CreatedDate
    private LocalDateTime createDt;
}
