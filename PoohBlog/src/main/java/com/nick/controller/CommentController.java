package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.Comment;
import com.nick.service.BlogService;
import com.nick.service.CommentService;
import com.nick.util.JudgmentToJson;
import com.nick.util.ObjectToJson;
import com.nick.utilObjects.AddCommentObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

    @ResponseBody
    @GetMapping(value = "/queryCommentByPagingDesc/{pageIndex}/{stepSize}/{topicId}",produces = "text/html;charset=UTF-8")
    public String queryCommentByPagingDesc(@PathVariable int pageIndex,@PathVariable int stepSize,@PathVariable int topicId) throws JsonProcessingException {
        List<Comment> res=commentService.queryCommentByPagingDesc(pageIndex,stepSize,topicId);

        if (res==null||res.isEmpty()){
            return JudgmentToJson.judgmentToJson(0);
        }
        else {//若查询结果不为空
            return ObjectToJson.objectToJson(res);
        }
    }

    @ResponseBody
    @PostMapping("/addComment")
    public String addComment(@RequestBody AddCommentObject addCommentObject) throws JsonProcessingException {
        int res = commentService.addComment(addCommentObject);
        return JudgmentToJson.judgmentToJson(res);
    }
}
