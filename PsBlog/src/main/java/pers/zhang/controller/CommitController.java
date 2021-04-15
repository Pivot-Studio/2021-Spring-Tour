package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.zhang.pojo.Blog;
import pers.zhang.pojo.Commit;
import pers.zhang.service.CommitService;
import pers.zhang.utils.JudgementUtile;

import java.util.List;

@RestController
@RequestMapping("/commit")
public class CommitController {
    @Autowired
    private  CommitService commitService;

    @PostMapping
    public JudgementUtile addCommit(String committerId,String ccontent,String target){
        int judgement = commitService.addCommit(Integer.parseInt(committerId),ccontent,Integer.parseInt(target));
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }
    @DeleteMapping("/{cid}")
    public JudgementUtile deleteCommit(@PathVariable String cid){
        int judgement = commitService.deleteCommit(Integer.parseInt(cid));
        JudgementUtile judgementUtile = new JudgementUtile();
        judgementUtile.setStatus(judgement);
        return judgementUtile;
    }

    @GetMapping("/{cid}")
    public Commit getCommitByCid(@PathVariable String cid){
        return commitService.getCommitByCid(Integer.parseInt(cid));
    }

    @GetMapping("/blog/{id}")
    public List<Commit> getCommitOfBlog(@PathVariable String id){
        return commitService.getCommitOfBlog(Integer.parseInt(id));
    }


}
