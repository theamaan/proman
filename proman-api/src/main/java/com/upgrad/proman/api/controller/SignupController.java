package com.upgrad.proman.api.controller;

import com.upgrad.proman.api.model.SignupUserRequest;
import com.upgrad.proman.api.model.SignupUserResponse;
import com.upgrad.proman.service.business.UserBusinessService;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
public class SignupController {
    @Autowired
    private UserBusinessService userService;
    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponse> signup(@RequestBody SignupUserRequest userRequest){
        UserEntity user = new UserEntity();
        user.setUuid(UUID.randomUUID().toString());
        System.out.println(user.getUuid());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmailAddress());
        user.setPassword(userRequest.getPassword());
        user.setMobilePhone(userRequest.getMobileNumber());
        user.setStatus(4);
        user.setSalt("Fast@123");
        user.setCreatedBy("Amaan");
        user.setCreatedAt(ZonedDateTime.now());
        userService.signup(user);
        SignupUserResponse response=new SignupUserResponse();
        response.setId(UUID.randomUUID().toString());
        response.setStatus(UserStatus.REGISTERED.toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}