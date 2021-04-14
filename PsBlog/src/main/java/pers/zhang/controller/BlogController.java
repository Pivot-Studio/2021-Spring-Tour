package pers.zhang.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.zhang.pojo.Blog;
import pers.zhang.service.BlogService;
import pers.zhang.utils.JudgementUtile;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;


    @PostMapping
    @ResponseBody
//    public JudgementUtile createBlog(String authorId,String content){
//        int judgement = blogService.createBlog(Integer.parseInt(authorId),content);
//        return new JudgementUtile(judgement);
//    }
    public JudgementUtile createBlog(){
        int judgement = blogService.createBlog(1,"another test");
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Blog getBlog(@PathVariable String id){
        return blogService.getBlog((Integer.parseInt(id)));
    }
}
