package com.nick.service;

import com.nick.dao.UserMapper;
import com.nick.pojo.User;
import com.nick.util.IsEmail;
import com.nick.util.JudgmentToJson;
import com.nick.util.SendEmail;
import com.nick.utilObjects.AddUserObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("UserMap")
    HashMap<String , AddUserObject> UserMap;//key为邮箱，value为待验证用户信息

    @Autowired
    @Qualifier("UIDMaiVerification")//key为邮箱，value为验证码
    HashMap<String ,String> UIDMaiVerification;


    //在hash表中存储了user与uid，若重复提交直接覆盖原来的内容
    @Override
    public int signIn(AddUserObject addUserObject) throws Exception {
        if(IsEmail.isEmail(addUserObject.getMail())==false||addUserObject.getAccount().length()<6||addUserObject.getName()==null||addUserObject.getPassWord().length()<6)
        {
            return 0;
        }
        UserMap.put(addUserObject.getMail(),addUserObject);
        String uid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong()+addUserObject.getMail();
        UIDMaiVerification.put(addUserObject.getMail(),uid);
        System.out.println(addUserObject.getMail());
        System.out.println(uid);
        SendEmail.snedEmail(uid,addUserObject.getMail());
        return 1;
    }
    @Override
    public User queryUser(int id) {
        return userMapper.queryUser(id);
    }

    @Override
    public int emailVerification(String mail, String uid) {
        if(UserMap.containsKey(mail)&&UIDMaiVerification.containsKey(mail)&&UIDMaiVerification.get(mail).equals(uid))
        {
            UIDMaiVerification.remove(mail);
            AddUserObject addUserObject=UserMap.get(mail);
            User user=new User();
            user.setName(addUserObject.getName());
            user.setMail(addUserObject.getMail());
            user.setAccount(addUserObject.getAccount());
            user.setPassWord(addUserObject.getPassWord());
            UserMap.remove(mail);
            userMapper.addUser(user);
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
