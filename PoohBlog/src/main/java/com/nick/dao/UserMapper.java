package com.nick.dao;

import com.nick.pojo.User;

public interface UserMapper {
    //通过id查找user
    public User queryUser(int id);
}
