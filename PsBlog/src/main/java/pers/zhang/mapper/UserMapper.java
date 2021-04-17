package pers.zhang.mapper;

import pers.zhang.pojo.User;

public interface UserMapper {
    User login(User user);
    int register(User user);
    int updatePwd(User user);
}
