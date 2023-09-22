package com.upgrad.proman.service.dao;

import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthDao extends JpaRepository<UserAuthTokenEntity,Integer> {

    UserAuthTokenEntity findByaccessToken(String accessToken);
}
