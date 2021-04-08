package com.nick.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.ReplyComment;
import com.nick.service.ReplyCommentService;
import com.nick.util.JudgmentToJson;
import com.nick.util.ObjectToJson;
import com.nick.utilObjects.AddReplyCommentObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Controller
@RequestMapping("/replyComment")
public class ReplyCommentController {
    @Autowired
    @Qualifier("replyCommentServiceImpl")
    ReplyCommentService replyCommentService;

    @ResponseBody
    @PostMapping(value = "/addReplyComment",produces = "text/html;charset=UTF-8")
    public String addReplyComment(@RequestBody AddReplyCommentObject addReplyCommentObject) throws JsonProcessingException {
        System.out.println(addReplyCommentObject);
        int res=replyCommentService.addReplyComment(addReplyCommentObject);
        return JudgmentToJson.judgmentToJson(res);
    }

    @ResponseBody
    @GetMapping(value = "/queryReplyCommentByPagingDesc/{beginIndex}/{stepSize}/{id}",produces = "text/html;charset=UTF-8")
    public String queryReplyCommentByPagingDesc(@PathVariable int beginIndex,@PathVariable  int stepSize ,@PathVariable  int id) throws JsonProcessingException {
        List<ReplyComment> res=replyCommentService.queryReplyCommentByPagingDesc(beginIndex,stepSize,id);
        if(res==null)
        {
            return JudgmentToJson.judgmentToJson(0);
        }
        return ObjectToJson.objectToJson(res);
    }
}
