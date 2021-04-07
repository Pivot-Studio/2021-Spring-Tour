package com.nick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JumpController {
    @RequestMapping("/goToQueryAllBlogs")
    public String goToQueryAllBlogs()
    {
        return "redirect:/blog/queryAllBlogs";
    }


}
