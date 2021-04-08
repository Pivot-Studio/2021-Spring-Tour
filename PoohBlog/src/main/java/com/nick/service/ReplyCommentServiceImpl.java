package com.nick.service;


import com.nick.dao.ReplyCommentMapper;
import com.nick.pojo.ReplyComment;
import com.nick.utilObjects.AddReplyCommentObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCommentServiceImpl implements ReplyCommentService{
    @Autowired
    private ReplyCommentMapper replyCommentMapper;

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @Override
    public List<ReplyComment> queryReplyCommentByPagingDesc(int beginIndex, int stepSize, int id) {
        if(beginIndex<=0||stepSize<=0||id<=0||commentService.queryComment(id)==null)
        {
            return null;
        }
        return replyCommentMapper.queryReplyCommentByPagingDesc(beginIndex,stepSize,id);
    }

    @Override
    public int addReplyComment(AddReplyCommentObject addReplyCommentObject) {
        if(addReplyCommentObject.getFromId()<=0||
        addReplyCommentObject.getToId()<=0||
        addReplyCommentObject.getContent()==null||
        commentService.queryComment(addReplyCommentObject.getCommentId())==null||
                userService.queryUser(addReplyCommentObject.getFromId())==null||
                userService.queryUser(addReplyCommentObject.getToId())==null)
        {
            return 0;
        }
        ReplyComment replyComment=new ReplyComment();
        replyComment.setCommentId(addReplyCommentObject.getCommentId());
        replyComment.setContent(addReplyCommentObject.getContent());
        replyComment.setFromId(addReplyCommentObject.getFromId());
        replyComment.setToId(addReplyCommentObject.getToId());
        return replyCommentMapper.addReplyComment(replyComment);
    }
}
