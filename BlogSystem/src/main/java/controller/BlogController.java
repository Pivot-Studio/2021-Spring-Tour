package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pojo.Blog;
import pojo.User;
import service.BlogService;
import service.UserService;
import service.impl.BlogServiceImpl;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    @Qualifier("blogServiceImpl")
    private BlogService blogService;

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable("id") String id){
        try{
            Blog blog = this.blogService.getBlog(id);
            if(blog==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(blog);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
