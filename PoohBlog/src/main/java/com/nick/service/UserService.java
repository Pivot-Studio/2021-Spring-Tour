package com.nick.service;

import com.nick.pojo.User;

public interface UserService {
    //通过id查找user
    public User queryUser(int id);
}
