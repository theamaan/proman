package com.upgrad.proman.service.business;

import com.upgrad.proman.service.common.JwtTokenProvider;
import com.upgrad.proman.service.dao.UserAuthDao;
import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;
    @Autowired
    private UserAuthDao userAuthDao;

    public UserAuthTokenEntity authenticate( String email,  String password) throws AuthenticationFailedException {
        UserEntity user = userDao.findByemail(email);

        if(user == null){
            throw new AuthenticationFailedException("ATH-001","User with email not found ");
        }else{
            String encryptedPassword = cryptographyProvider.encrypt(password, user.getSalt());

            if(encryptedPassword.equals(user.getPassword())){
                JwtTokenProvider jwtTokenProvider=new JwtTokenProvider(encryptedPassword);
                UserAuthTokenEntity userAuthToken=new UserAuthTokenEntity();
                userAuthToken.setUser(user);
                ZonedDateTime now=ZonedDateTime.now();
                ZonedDateTime expiresAt=now.plusHours(4);
                userAuthToken.setAccessToken(jwtTokenProvider.generateToken(user.getUuid(), now,expiresAt));

                userAuthToken.setLoginAt(now);
                userAuthToken.setExpiresAt(expiresAt);
                userAuthToken.setCreatedBy("api-backend");
                userAuthToken.setCreatedAt(now);

                userAuthDao.save(userAuthToken);
                user.setLastLoginAt(now);
                return userAuthToken;

            }else{
                throw new AuthenticationFailedException("ATH-002","Password Failed" );
            }

        }
    }
}