package com.fitness.userservice.service;

import com.fitness.userservice.dto.UserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.model.UserRole;
import com.fitness.userservice.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public UserResponse createUser(UserRequest userRequest){

       if(userRepo.existsUserByEmail(userRequest.getEmail())){
          throw new RuntimeException("User already exists: " + userRequest.getEmail());
       }
        //changing userReponse to user
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .about(userRequest.getAbout())
                .build();
        //saving the user in db
        User save = userRepo.save(user);
        //returning user to userResponse
        UserResponse response = UserResponse.builder()
                .id(save.getId())
                .name(save.getName())
                .role(UserRole.ADMIN)
                .about(save.getAbout())
                .email(save.getEmail())
                .createdDt(save.getCreatedDt())
                .updateDt(save.getUpdateDt())
                .build();
        return response;

    }

    //get all users
    public List<UserResponse> getAllUsers()
    {
        List<User> all = userRepo.findAll();
        List<UserResponse> responseList = new ArrayList<>();
        for(User user : all){
            UserResponse response = UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .role(user.getRole())
                    .about(user.getAbout())
                    .email(user.getEmail())
                    .createdDt(user.getCreatedDt())
                    .updateDt(user.getUpdateDt())
                    .build();

            responseList.add(response);
        }
        return responseList;
    }

    //get single user by id
    public UserResponse getUserById(String id)
    {
        User user = userRepo.findById(id).orElseThrow();
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .about(user.getAbout())
                .email(user.getEmail())
                .createdDt(user.getCreatedDt())
                .updateDt(user.getUpdateDt())
                .build();

        return response;
    }

    //update user
    public UserResponse updateUser(UserRequest req, String id)
    {
        //get existing user
        User user = userRepo.findById(id).orElseThrow();
        //check for the fields
        if (req.getEmail() != null) {

            boolean emailExists = userRepo.existsUserByEmail(req.getEmail());
            if (emailExists) {
                throw new RuntimeException("Email already exists");
            }

            user.setEmail(req.getEmail());
        }

        if (req.getName() != null ) {
            user.setName(req.getName());
        }

        if (req.getPassword() != null) {
            user.setPassword(req.getPassword());
        }

        if (req.getAbout() != null ) {
            user.setAbout(req.getAbout());
        }


        User updatedUser = userRepo.save(user);

        UserResponse response = UserResponse.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .role(updatedUser.getRole())
                .about(updatedUser.getAbout())
                .email(updatedUser.getEmail())
                .createdDt(updatedUser.getCreatedDt())
                .updateDt(updatedUser.getUpdateDt())
                .build();
        return response;

    }

    //delete user by id
    public void deleteUser(String id)
    {
        userRepo.deleteById(id);
    }

    //user exists by email
    public boolean isUserExistInDB(String id)
    {
       return userRepo.existsUserById(id);
    }


}
