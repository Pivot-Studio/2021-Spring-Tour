package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.zhang.mapper.UserMapper;
import pers.zhang.pojo.User;
import pers.zhang.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String name, String pwd) {
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        return userMapper.login(user);
    }

    @Override
    public User register(String name, String pwd) {
        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        user.setUid(userMapper.register(user));
        return user;
    }

    @Override
    public User updatePwd(String name, String oldPwd, String newPwd) {
        User user = new User();
        user.setName(name);
        user.setPwd(oldPwd);
        user = userMapper.login(user);
        if (user!=null){
            user.setPwd(newPwd);
            userMapper.updatePwd(user);
            return userMapper.login(user);
        }else return null;
    }
}
