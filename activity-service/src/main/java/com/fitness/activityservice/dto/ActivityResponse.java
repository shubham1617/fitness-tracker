package com.fitness.activityservice.dto;

import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityResponse
{
    private String activityId;
    private String userId;
    private ActivityType type;
    private Integer calorieBurn;
    private Integer duration;
    private Integer startTime;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    public static  ActivityResponse modelToResponse (Activity activity)
    {
        ActivityResponse activityResponse = ActivityResponse.builder()
                .activityId(activity.getActivityId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .startTime(activity.getStartTime())
                .duration(activity.getDuration())
                .calorieBurn(activity.getCalorieBurn())
                .createdDt(activity.getCreatedDt())
                .updatedDt(activity.getUpdatedDt())
                .build();

        return activityResponse;
    }
}
