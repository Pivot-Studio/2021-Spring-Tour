package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.User;
import com.nick.service.UserService;
import com.nick.util.JudgmentToJson;
import com.nick.util.ObjectToJson;
import com.nick.utilObjects.AddUserObject;
import com.nick.utilObjects.LogInUserObject;
import com.nick.utilObjects.ModifyPassWordObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("user/")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    //done
    @ResponseBody
    @PostMapping(value = "/signIn",produces = "text/html;charset=UTF-8")
    public String signIn(@RequestBody AddUserObject addUserObject) throws Exception {
        System.out.println(addUserObject);
        int res=userService.signIn(addUserObject);
        return JudgmentToJson.judgmentToJson(res);
    }
    //done
    @ResponseBody
    @GetMapping(value = "/verification/{mail}/{uid}",produces = "text/html;charset=UTF-8")
    public String emailVerification(@PathVariable String mail,@PathVariable String uid) throws JsonProcessingException {
        int res=userService.emailVerification(mail,uid);
        return JudgmentToJson.judgmentToJson(res);
    }
    //done
    @ResponseBody
    @PostMapping(value = "/logIn",produces = "text/html;charset=UTF-8")
    public String LogIn(@RequestBody LogInUserObject logInUserObject) throws JsonProcessingException {
        System.out.println(logInUserObject);
        User user = userService.logIn(logInUserObject);
        if(user==null)
        {
            return JudgmentToJson.judgmentToJson(0);
        }
        return ObjectToJson.objectToJson(user);
    }

    @ResponseBody
    @PostMapping(value = "/modifyPassWord",produces = "text/html;charset=UTF-8")
    public String modifyPassWord(@RequestBody ModifyPassWordObject modifyPassWordObject) throws Exception {
        int res=userService.modifyPassWord(modifyPassWordObject);
        return JudgmentToJson.judgmentToJson(res);
    }
    @ResponseBody
    @GetMapping(value = "/verification/modifyPassWord/{mail}/{uid}",produces = "text/html;charset=UTF-8")
    public String emailVerificationModifyPassWord(@PathVariable String mail,@PathVariable String uid) throws JsonProcessingException {
        int res=userService.emailVerificationModifyPassWord(mail,uid);
        return JudgmentToJson.judgmentToJson(res);
    }

}
