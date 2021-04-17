package pers.zhang.service;

import pers.zhang.pojo.User;

public interface UserService {
    User login(String name,String pwd);
    User register(String name,String pwd);
    User updatePwd(String name,String oldPwd,String newPwd);
}
