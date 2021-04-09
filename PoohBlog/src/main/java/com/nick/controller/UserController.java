package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.User;
import com.nick.service.UserService;
import com.nick.util.JudgmentToJson;
import com.nick.utilObjects.AddUserObject;
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

    @ResponseBody
    @PostMapping(value = "/signIn",produces = "text/html;charset=UTF-8")
    public String signIn(@RequestBody AddUserObject addUserObject) throws Exception {
        System.out.println(addUserObject);
        int res=userService.signIn(addUserObject);
        return JudgmentToJson.judgmentToJson(res);
    }

    @ResponseBody
    @GetMapping(value = "/verification/{mail}/{uid}",produces = "text/html;charset=UTF-8")
    public String emailVerification(@PathVariable String mail,@PathVariable String uid) throws JsonProcessingException {

        int res=userService.emailVerification(mail,uid);
        return JudgmentToJson.judgmentToJson(res);
    }
}
