package com.nick.service;

import com.nick.pojo.User;
import com.nick.utilObjects.AddUserObject;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {
    //通过id查找user
    public User queryUser(int id);

    public int signIn(AddUserObject addUserObject) throws Exception;
    public int emailVerification(String mail,String uid);
}
