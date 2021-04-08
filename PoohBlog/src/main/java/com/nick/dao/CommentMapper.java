package com.nick.dao;

import com.nick.pojo.Comment;
import com.nick.utilObjects.AddCommentObject;

import java.util.List;

public interface CommentMapper {
    public List<Comment> queryCommentByPagingDesc(int beginIndex,int stepSize ,int topicId);

    public int addComment(AddCommentObject addCommentObject);

    public Comment queryComment(int id);
}
