package com.fitness.userservice.dto;

import com.fitness.userservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private UserRole role;
    private String about;
    private LocalDateTime createdDt;
    private LocalDateTime updateDt;
}
