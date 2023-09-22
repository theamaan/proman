package com.upgrad.proman.service.business;

import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessService {
    @Autowired
    private UserAdminBusinessService adminBusinessService;
    public UserEntity signup(UserEntity user){
        return  adminBusinessService.createUser(user);
    }
}
