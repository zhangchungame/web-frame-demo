package com.dandinglong.webframedemo.service;

import com.dandinglong.webframedemo.dao.master.UserMapper;
import com.dandinglong.webframedemo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Cacheable(value = "allUser",key = "'info'")
    public List<UserEntity> selectAllUser(){
        return userMapper.selectAllUser();
    }

    @Cacheable(value = "user", key = "#root.args[0]", unless = "#result eq null ")
    @Transactional(transactionManager = "masterTransactionManager")
    public UserEntity select(int userId) {
        UserEntity userEntity = userMapper.myselectById(userId);
//        throw new RuntimeException("错误");
        return userEntity;
    }
    @CachePut(value = "user",key = "#result.id")
//    @Transactional
    public UserEntity insert(UserEntity userEntity) {
        userMapper.myinsertOne(userEntity);
        return userEntity;
    }


    @Caching(evict = {
            @CacheEvict(value = "user",key = "#root.args[0]"),
            @CacheEvict(value = "allUser",key = "'info'")
    })
//    @Transactional
    public UserEntity delete(int userId) {
        UserEntity userEntity = new UserEntity();
        userMapper.deleteById(userId);
        return userEntity;
    }
}
