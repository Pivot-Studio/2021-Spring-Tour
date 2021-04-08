package com.nick.service;

import com.nick.pojo.Comment;
import com.nick.utilObjects.AddCommentObject;

import java.util.List;

public interface CommentService {
    public List<Comment> queryCommentByPagingDesc(int pageIndex, int stepSize , int topicId);
    public int addComment(AddCommentObject addCommentObject);
    public Comment queryComment(int id);
}
