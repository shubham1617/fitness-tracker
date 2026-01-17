package com.fitness.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private String id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String about;
    @CreatedDate
    private LocalDateTime createdDt;
    @LastModifiedDate
    private LocalDateTime updateDt;


}

