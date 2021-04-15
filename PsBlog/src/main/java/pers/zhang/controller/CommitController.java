package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.zhang.pojo.Blog;
import pers.zhang.pojo.Commit;
import pers.zhang.service.CommitService;
import pers.zhang.utils.JudgementUtile;

import java.util.List;

@RestController
@RequestMapping({"/blogs/{id}/commits"})
public class CommitController {
    @Autowired
    private  CommitService commitService;

    //作用：发表一条评论
    //参数：评论者id号=committerId，评论内容=content，所评论博客id号=id
    //返回值：status=OK/FALSE的Json
    @PostMapping
    public JudgementUtile addCommit(String committerId,String ccontent,@PathVariable("id") String target){
        int judgement = commitService.addCommit(Integer.parseInt(committerId),ccontent,Integer.parseInt(target));
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }

    //作用：删除一条评论
    //参数：评论id号=cid
    //返回值：status=OK/FALSE的Json
    @DeleteMapping("/{cid}")
    public JudgementUtile deleteCommit(@PathVariable String cid){
        int judgement = commitService.deleteCommit(Integer.parseInt(cid));
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
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
    @GetMapping("")
    public List<Commit> getCommitOfBlog(@PathVariable String id){
        return commitService.getCommitOfBlog(Integer.parseInt(id));
    }



}
