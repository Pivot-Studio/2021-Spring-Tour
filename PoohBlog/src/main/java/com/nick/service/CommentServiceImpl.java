package com.nick.service;

import com.nick.dao.BlogMapper;
import com.nick.dao.CommentMapper;
import com.nick.pojo.Comment;
import com.nick.utilObjects.AddCommentObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Autowired
    @Qualifier("blogServiceImpl")
    private BlogService blogService;


    //done
    //pageIndex从1开始
    @Override
    public List<Comment> queryCommentByPagingDesc(int pageIndex, int stepSize, int topicId) {
        //此处接受的为页码数，传入的为评论开始条数
        if(pageIndex<=0||stepSize<=0||topicId<=0||blogService.queryBlog(topicId)==null)
        {
            return null;
        }
        return commentMapper.queryCommentByPagingDesc((pageIndex-1)*stepSize,stepSize,topicId);
    }
    //done
    @Override
    public int addComment(AddCommentObject addCommentObject) {
        if(addCommentObject.getTopicId()==0||addCommentObject.getContent()==null||addCommentObject.getFromId()==0)
        {
            return 0;
        }
        if(userService.queryUser(addCommentObject.getFromId())==null)
        {
            return 0;
        }
        Comment comment=new Comment();
        comment.setContent(addCommentObject.getContent());
        comment.setFromId(addCommentObject.getFromId());
        comment.setTopicId(addCommentObject.getTopicId());
        return commentMapper.addComment(addCommentObject);
    }

    @Override
    public Comment queryComment(int id) {
        return commentMapper.queryComment(id);
    }
}
