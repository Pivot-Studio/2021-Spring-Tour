package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.zhang.pojo.Commit;
import pers.zhang.service.CommitService;

import java.util.List;

@Controller
@RequestMapping({"/commits"})
public class CommitController {
    @Autowired
    private  CommitService commitService;

    //作用：发表一条评论
    //参数：评论者id号=committerId，评论内容=ccontent，所评论博客id号=id
    @PostMapping
    public ResponseEntity addCommit(String committerId,String ccontent,String target){
        Commit commit = commitService.addCommit(Integer.parseInt(committerId),ccontent,Integer.parseInt(target));
        //内容为空或所评论博客不存在，返回状态码422
        if(commit==null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        //发表成功，返回刚创建的Commit实例转换成的Json
        return ResponseEntity.status(HttpStatus.CREATED).body(commit);
    }

    //作用：删除一条评论
    //参数：评论id号=cid
    @DeleteMapping("/{cid}")
    public ResponseEntity deleteCommit(@PathVariable String cid){
        //所删除评论不存在，返回404
        if(commitService.deleteCommit(Integer.parseInt(cid))==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //删除成功，返回204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //作用：获取一条评论
    //参数：评论id号=cid
    //返回值：commit类对象转换成的Json
    @GetMapping("/{cid}")
    public Commit getCommitByCid(@PathVariable String cid){
        return commitService.getCommitByCid(Integer.parseInt(cid));
    }

    //作用：获取一篇博客的所有评论
    //参数：博客id号=id
    //返回值：commit类对象集合转换成的Json
    @GetMapping
    public List<Commit> getCommitOfBlog(String id){
        return commitService.getCommitOfBlog(Integer.parseInt(id));
    }


}
