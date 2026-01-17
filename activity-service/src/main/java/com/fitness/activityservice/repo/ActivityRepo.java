package com.fitness.activityservice.repo;

import com.fitness.activityservice.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface ActivityRepo extends JpaRepository<Activity,String>
{
}
