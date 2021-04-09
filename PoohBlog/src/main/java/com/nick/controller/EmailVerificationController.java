package com.nick.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("emailVerificationController")
public class EmailVerificationController {
    @Autowired
    @Qualifier("UIDMaiVerification")
    private HashMap<String ,String> UIDMaiVerification;

    @RequestMapping("/{UUID}")
    public String EmailVerificationController(String UUID)
    {
        return null;
    }
}
