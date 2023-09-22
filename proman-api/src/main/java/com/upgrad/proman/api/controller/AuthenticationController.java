package com.upgrad.proman.api.controller;
import com.upgrad.proman.api.model.AuthorizedUserResponse;
import com.upgrad.proman.service.business.AuthenticationService;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;
import java.util.UUID;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("auth/login")
    public ResponseEntity<AuthorizedUserResponse> login(@RequestHeader("authorization") String authorization) throws AuthenticationFailedException {
        AuthorizedUserResponse response=new AuthorizedUserResponse();
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");
        UserAuthTokenEntity userAuthToken = authenticationService.authenticate(decodedArray[0], decodedArray[1]);
        UserEntity user=userAuthToken.getUser();
        response.setId(UUID.fromString(user.getUuid()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmailAddress(user.getEmail());
        response.setMobilePhone(user.getMobilePhone());
        response.setLastLoginTime(user.getLastLoginAt());
        HttpHeaders headers=new HttpHeaders();
        headers.set("access-token",userAuthToken.getAccessToken());
        return new ResponseEntity<>(response,headers, HttpStatus.OK);
    }
}
