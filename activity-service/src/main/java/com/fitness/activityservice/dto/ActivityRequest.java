package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRequest
{
    private String userId;
    private ActivityType type;
    private Integer calorieBurn;
    private Integer duration;
    private Integer startTime;

    public static Activity requestToModel (ActivityRequest activity)
    {
        Activity activityRequest = Activity.builder()
                .userId(activity.getUserId())
                .calorieBurn(activity.getCalorieBurn())
                .duration(activity.getDuration())
                .startTime(activity.getStartTime())
                .type(activity.getType())
                .build();

        return activityRequest;
    }
}
