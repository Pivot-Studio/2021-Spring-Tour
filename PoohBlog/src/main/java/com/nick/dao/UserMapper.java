package com.nick.dao;

import com.nick.pojo.User;
import com.nick.utilObjects.AddUserObject;
import com.nick.utilObjects.LogInUserObject;
import com.nick.utilObjects.ModifyPassWordObject;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    //通过id查找user
    public User queryUser(int id);
    //增加user
    public int addUser(User user);
    //通过账号密码查找user
    public User queryUserByAccountAndPassWord(LogInUserObject logInUserObject);
    //通过账号查找user
    public User queryUserByAccount(@Param("account") String account);
    //通过邮箱查找user
    public User queryUserByMail(@Param("mail") String mail);
    //修改user
    public int updateUserPassWord(ModifyPassWordObject modifyPassWordObject);
}
