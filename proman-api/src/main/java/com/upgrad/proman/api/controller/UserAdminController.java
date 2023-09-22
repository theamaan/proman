package com.upgrad.proman.api.controller;


import com.upgrad.proman.api.model.CreateUserRequest;
import com.upgrad.proman.api.model.CreateUserResponse;
import com.upgrad.proman.api.model.UserDetailsResponse;
import com.upgrad.proman.api.model.UserStatusType;
import com.upgrad.proman.service.business.UserAdminBusinessService;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.exception.UnAuthorizedException;
import com.upgrad.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserAdminController {
    @Autowired
    private UserAdminBusinessService businessService;


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable String id,@RequestHeader("authorization") String authorization) throws ResourceNotFoundException, UnAuthorizedException {
        UserEntity user=businessService.getUser(id,authorization);
        UserDetailsResponse response=new UserDetailsResponse();
        response.setId(user.getUuid());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmailAddress(user.getEmail());
        response.setMobileNumber(user.getMobilePhone());
        response.setStatus(UserStatusType.valueOf(UserStatus.getEnum(user.getStatus()).name()));


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request){
        UserEntity user=new UserEntity();
        user.setUuid(UUID.randomUUID().toString());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobilePhone(request.getMobileNumber());
        user.setEmail(request.getEmailAddress());
        user.setStatus(0);
        user.setCreatedBy("Admin");
        user.setCreatedAt(ZonedDateTime.now());
        businessService.createUser(user);
        CreateUserResponse response=new CreateUserResponse();
        response.setId(user.getUuid());
        response.setStatus(UserStatusType.ACTIVE);

        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }
}
