package pers.zhang.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.zhang.pojo.Blog;
import pers.zhang.service.BlogService;
import pers.zhang.utils.JudgementUtile;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping
    public JudgementUtile createBlog(String authorId,String content){
        int judgement = blogService.createBlog(Integer.parseInt(authorId),content);
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }

    @DeleteMapping("/{id}")
    public JudgementUtile deleteBlog(@PathVariable String id){
        int judgement = blogService.deleteBlog(Integer.parseInt(id));
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }

    @GetMapping("/{id}")
    public Blog getBlog(@PathVariable String id){
        return blogService.getBlog((Integer.parseInt(id)));
    }

    @PatchMapping("/{id}")
    public JudgementUtile updateBlog(@PathVariable String id,String content){
        int judgement = blogService.updateBlog(Integer.parseInt(id),content);
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }
}
