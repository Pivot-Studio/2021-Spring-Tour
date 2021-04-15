package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.User;
import com.nick.service.UserService;
import com.nick.util.JudgmentToJson;
import com.nick.util.ObjectToJson;
import com.nick.util.jwtUtil.Constant;
import com.nick.util.jwtUtil.JwtUtils;
import com.nick.utilObjects.AddUserObject;
import com.nick.utilObjects.LogInUserObject;
import com.nick.utilObjects.ModifyPassWordObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("user/")
@Api(value = "用户")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    //done
    @ResponseBody
    @PostMapping(value = "/signIn",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "注册")
    public String signIn(@ApiParam(value = "增加用户信息",required = true) @RequestBody AddUserObject addUserObject) throws Exception {
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
    @ApiOperation(value = "登陆")
    public String LogIn(@ApiParam(value = "注册信息",required = true) @RequestBody LogInUserObject logInUserObject) throws Exception {
        System.out.println(logInUserObject);
        User user = userService.logIn(logInUserObject);
        if(user==null)
        {
            return JudgmentToJson.judgmentToJson(0);
        }
        else {
            Map<String ,String> map=new HashMap<>();
            String jwt=null;
            String userJson=ObjectToJson.objectToJson(user);
                System.out.println(Constant.JWT_TTL);
                System.out.println(userJson);
                jwt = JwtUtils.createJWT(userJson, Constant.JWT_TTL);
                System.out.println(jwt);
            map.put("jwt_token",jwt);
            map.put("success","true");
            return ObjectToJson.objectToJson(map);
        }
    }

    @ResponseBody
    @PostMapping(value = "/modifyPassWord",produces = "text/html;charset=UTF-8")
    @ApiOperation(value = "修改密码")
    public String modifyPassWord(@ApiParam(value = "修改信息") @RequestBody ModifyPassWordObject modifyPassWordObject) throws Exception {
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
