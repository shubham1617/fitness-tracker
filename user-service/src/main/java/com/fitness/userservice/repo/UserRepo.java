package com.fitness.userservice.repo;

import com.fitness.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,String>
{
    public Boolean existsUserByEmail(String email);
    public Boolean existsUserById(String id);
}
