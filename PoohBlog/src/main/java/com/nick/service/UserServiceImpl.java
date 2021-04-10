package com.nick.service;

import com.nick.dao.UserMapper;
import com.nick.pojo.User;
import com.nick.util.IsEmail;
import com.nick.util.JudgmentToJson;
import com.nick.util.SendEmail;
import com.nick.util.SendEmailModifyPassWord;
import com.nick.utilObjects.AddUserObject;
import com.nick.utilObjects.LogInUserObject;
import com.nick.utilObjects.ModifyPassWordObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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
    Map<String , AddUserObject> UserMap;//key为邮箱，value为待验证用户信息

    @Autowired
    @Qualifier("UIDMaiVerification")//key为邮箱，value为验证码
    Map<String ,String> UIDMaiVerification;

    @Autowired
    @Qualifier("UserModifyInfo")
    Map<String,ModifyPassWordObject> UserModifyInfo;


    //done
    //在hash表中存储了user与uid，若重复提交直接覆盖原来的内容
    @Override
    public int signIn(AddUserObject addUserObject) throws Exception {
        if(IsEmail.isEmail(addUserObject.getMail())==false||addUserObject.getAccount()==null
                ||addUserObject.getPassWord()==null
                ||addUserObject.getAccount().length()<6
                ||addUserObject.getName()==null||addUserObject.getPassWord().length()<6)
        {
            return 0;
        }
        //若已存在相同的账户或者邮箱则返回0
        if(userMapper.queryUserByAccount(addUserObject.getAccount())!=null||userMapper.queryUserByMail(addUserObject.getMail())!=null)
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

    //done
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

    //done
    //为了安全把passWord设为null
    @Override
    public User logIn(LogInUserObject logInUserObject) {
        User user=userMapper.queryUserByAccountAndPassWord(logInUserObject);
        if(user==null){
            return null;
        }
        user.setPassWord(null);
        return user;
    }

    @Override
    public int modifyPassWord(ModifyPassWordObject modifyPassWordObject) throws Exception {
        User user=userMapper.queryUserByAccount(modifyPassWordObject.getAccount());
        if(user==null)
        {
            return 0;
        }
        UserModifyInfo.put(user.getMail(),modifyPassWordObject);
        String uid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong()+user.getMail();
        //此处UID容器可以复用，因为在注册操作中必须满足User容器中有该email
        UIDMaiVerification.put(user.getMail(),uid);
        SendEmailModifyPassWord.snedEmail(uid,user.getMail());
        return 1;
    }

    @Override
    public int emailVerificationModifyPassWord(String mail, String uid) {
        //更改信息要有此邮箱
        if(UserModifyInfo.containsKey(mail)&&UIDMaiVerification.containsKey(mail)&&UIDMaiVerification.get(mail).equals(uid))
        {
            UIDMaiVerification.remove(mail);
            ModifyPassWordObject modifyPassWordObject=UserModifyInfo.get(mail);
            UserModifyInfo.remove(mail);
            return userMapper.updateUserPassWord(modifyPassWordObject);
        }
        else
        {
            return 0;
        }
    }
}
