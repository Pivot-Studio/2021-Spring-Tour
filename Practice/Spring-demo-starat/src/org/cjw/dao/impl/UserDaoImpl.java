package org.cjw.dao.impl;

import org.cjw.dao.UserDao;
import org.cjw.pojo.User;

public class UserDaoImpl implements UserDao {
    @Override
    public void insert(User user) {
        System.out.println("Dao层方法执行了");
    }
}
