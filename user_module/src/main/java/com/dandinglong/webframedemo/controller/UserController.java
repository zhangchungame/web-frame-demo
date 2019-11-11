package com.dandinglong.webframedemo.controller;

import com.dandinglong.webframedemo.entity.UserEntity;
import com.dandinglong.webframedemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("selectall")
    public List<UserEntity> selectAll(){
        return userService.selectAllUser();
    }

    @RequestMapping("select")
    public UserEntity select() {
        int userId=Integer.valueOf(httpServletRequest.getParameter("id"));
        return userService.select(userId);
    }
    @RequestMapping("insert")
    public UserEntity insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setuName("王八");
        userEntity.setMobile("123456");
        userEntity.setAddr("shanghai");
        userEntity.setAge(39);
        return userService.insert(userEntity);
    }

    @RequestMapping("delete")
    public UserEntity delete() {
        Integer userId=Integer.valueOf(httpServletRequest.getParameter("id"));

        return userService.delete(userId);
    }
    @RequestMapping("uuid")
    public long getUUid(){
        return userService.getUUid();
    }

    @RequestMapping("register")
    public UserEntity register(@RequestParam("name")String name){
        UserEntity userEntity=new UserEntity();
        userEntity.setuName(name);
        userEntity.setAddr("shanghai");
        userEntity.setMobile("13768481738");
        userEntity.setAge(29);
        return userService.register(userEntity);
    }
}
