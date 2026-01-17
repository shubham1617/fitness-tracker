package com.fitness.userservice.dto;

import com.fitness.userservice.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private String about;
}
