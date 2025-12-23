package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repo.ActivityRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ActivityService
{
    private final ActivityRepo repo;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResponse createActivity(ActivityRequest activity)
    {
       Boolean isExist = restTemplate.getForObject("http://USER-SERVICE/api/v1/user/checkUser/" + activity.getUserId(), Boolean.class);
        if(!isExist){
            throw new RuntimeException("Id not match with the exact user !!");
        }
        Activity activityRequest = Activity.builder()
                .userId(activity.getUserId())
                .calorieBurn(activity.getCalorieBurn())
                .duration(activity.getDuration())
                .startTime(activity.getStartTime())
                .type(activity.getType())
                .build();
        Activity save = repo.save(activityRequest);
        try {
            kafkaTemplate.send(topicName,save.getUserId(),save);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ActivityResponse response = ActivityResponse.modelToResponse(save);
        return response;
    }
}
