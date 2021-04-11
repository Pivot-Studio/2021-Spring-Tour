package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/User")
public class UserController {
    @RequestMapping(value = "/login",method = RequestMethod.POST,params = {"uname","pwd"})
    public void login(){

    }
}
