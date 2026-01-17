package com.fitness.userservice.controller;

import com.fitness.userservice.dto.UserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController
{
    private final UserService userService;

    //create user
    @PostMapping("/")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest)
    {
        UserResponse user = userService.createUser(userRequest);
        return ResponseEntity.ok().body(user);
    }

    //getAllUser
    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> allUsers = userService.getAllUsers();
        return  ResponseEntity.ok().body(allUsers);
    }

    //get single user
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable String id)
    {
        UserResponse userById = userService.getUserById(id);
        return ResponseEntity.ok(userById).getBody();
    }

    //update user by id
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable String id, @RequestBody UserRequest userRequest)
    {
        UserResponse updatedUser = userService.updateUser(userRequest, id);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id)
    {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User Deleted");
    }

    @GetMapping("/checkUser/{id}")
    public Boolean isUserExistById(@PathVariable String id)
    {
        return userService.isUserExistInDB(id);

    }
}
