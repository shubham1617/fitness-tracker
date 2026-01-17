package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activity")
@AllArgsConstructor
@Slf4j
public class ActivityController
{
    private final ActivityService service;

    //create activity
    @PostMapping("/")
    public ResponseEntity<ActivityResponse> createActivity(@RequestBody ActivityRequest activityRequest)
    {
        log.info("Received request to create activity: {}", activityRequest);
        ActivityResponse activity = service.createActivity(activityRequest);
        log.info("Created activity: {}", activity);
        return ResponseEntity.ok().body(activity);
    }
}
