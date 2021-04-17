package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zhang.pojo.User;
import pers.zhang.service.UserService;
import pers.zhang.utils.JwtUtil;


@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public String login(String name, String pwd){
        User user = userService.login(name,pwd);
        String token = JwtUtil.getToken(Integer.toString(user.getUid()),user.getName());
        return token;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity register(String name, String pwd){
        User user = userService.register(name,pwd);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/updatePwd")
    @ResponseBody
    public ResponseEntity updatePwd(String name,String oldPwd,String newPwd){
        User user = userService.updatePwd(name,oldPwd,newPwd);
        if(user!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
