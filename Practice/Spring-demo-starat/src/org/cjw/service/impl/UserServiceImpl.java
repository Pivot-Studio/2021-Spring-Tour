package org.cjw.service.impl;

import org.cjw.dao.UserDao;
import org.cjw.pojo.User;
import org.cjw.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    public void setUserDao(UserDao userDao){
        this.userDao=userDao;
    }
    @Override
    public void insert(User user) {
        System.out.println("Service层方法执行了");
        userDao.insert(user);
    }
}
