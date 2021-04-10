package org.cjw.controller;

import org.cjw.pojo.User;
import org.cjw.service.UserService;

public class UserController {
    private UserService userService;
    public void setUserService(UserService userService){
        this.userService=userService;
    }
    public void insert(){
        System.out.println("Controller层方法执行了");
        User user=new User();
        userService.insert(user);
    }
}
