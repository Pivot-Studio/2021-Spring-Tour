package com.nick.service;

import com.nick.pojo.User;
import com.nick.utilObjects.AddUserObject;
import com.nick.utilObjects.LogInUserObject;
import com.nick.utilObjects.ModifyPassWordObject;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {
    //通过id查找user
    public User queryUser(int id);

    //注册，user储存在表中，未持久化
    public int signIn(AddUserObject addUserObject) throws Exception;
    //邮箱验证，user持久化
    public int emailVerification(String mail,String uid);

    //登陆
    public User logIn(LogInUserObject logInUserObject);

    public int modifyPassWord(ModifyPassWordObject modifyPassWordObject) throws Exception;

    public int emailVerificationModifyPassWord(String mail, String uid);
}
