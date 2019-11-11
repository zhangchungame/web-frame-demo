package com.dandinglong.webframedemo.controller;

import com.dandinglong.webframedemo.dao.master.UserMapper;
import com.dandinglong.webframedemo.dao.slaver.UserSlaverMapper;
import com.dandinglong.webframedemo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserSlaverMapper userSlaverMapper;

    @RequestMapping("helloworld")
    @Transactional(transactionManager = "masterTransactionManager")
    public UserEntity helloWorld(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        UserEntity userEntity = userMapper.myselectById(7);
//        String id = request.getParameter("id");
//        return userEntity;
        UserEntity userEntity = userMapper.myselectById(Integer.valueOf(request.getParameter("id")));
        String uName = userEntity.getuName();
        for (int i = 0; i < 4; i++) {
            userEntity.setuName(uName + String.valueOf(i));
//            userSlaverMapper.myinsertOne(userEntity);
            userMapper.myinsertOne(userEntity);
        }
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚事务
        if (request.getParameter("transact").equals("t")) {
            throw new RuntimeException("看看事务回滚");
        }
        return userEntity;
//        Example example=new Example(UserEntity.class);
//        example.createCriteria().andEqualTo("uName","张春");
////        List<UserEntity> userEntities = userMapper.selectAll();
//        List<UserEntity> userEntities = userMapper.selectAll();
//        List<UserEntity> userslaverEntities = userSlaverMapper.selectAll();
//        for(UserEntity userEntityTmp:userslaverEntities){
//            userEntities.add(userEntityTmp);
//        }
//        return userEntities;
    }

    @RequestMapping("doget")
    @Transactional(transactionManager = "slaverTransactionManager")
    public UserEntity doget(@RequestParam("id") int id) {
        UserEntity userEntity = userMapper.myselectById(id);
        userSlaverMapper.myinsertOne(userEntity);
        return userEntity;

    }
}
