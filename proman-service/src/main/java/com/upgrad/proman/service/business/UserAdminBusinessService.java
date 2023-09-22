package com.upgrad.proman.service.business;

import com.upgrad.proman.service.dao.UserAuthDao;
import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.RoleEntity;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.ResourceNotFoundException;
import com.upgrad.proman.service.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class UserAdminBusinessService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    public UserEntity getUser(String uuid,String authorization) throws ResourceNotFoundException, UnAuthorizedException {

        UserAuthTokenEntity userAuthToken=userAuthDao.findByaccessToken(authorization);
        if(userAuthToken.getUser().getRole()!=null && userAuthToken.getUser().getRole().getUuid()==101){
            UserEntity user = userDao.findByuuid(uuid);

            if(user ==null){
                //throw an Exception
                throw new ResourceNotFoundException("USR-001","User Not Found");
            }
            else{
                return user;
            }
        }
        else throw new UnAuthorizedException("ATH-003","Unauthorized user");
    }

    public UserEntity createUser(UserEntity user){
        String password=user.getPassword();
        if(password==null){
            password ="admin123";
        }
        String[] encrypt = cryptographyProvider.encrypt(password);
        user.setSalt(encrypt[0]);
        user.setPassword(encrypt[1]);
        return userDao.save(user);
    }
}
