package org.cjw.controller;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserControllerTest {
    @Test
    public void test(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserController userController = applicationContext.getBean("userController",UserController.class);
        userController.insert();
    }
}
