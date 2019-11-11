package com.dandinglong.webframedemo.dao.master;


import com.dandinglong.webframedemo.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper{
    public int myinsertOne(UserEntity userEntity);
    public UserEntity myselectById(int id);
    public int updateUser(UserEntity userEntity);
    public List<UserEntity> selectFrontFive();
    public List<UserEntity> selectAllUser();
    public int deleteById(int id);
}
