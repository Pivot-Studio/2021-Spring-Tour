package com.nick.service;

import com.nick.pojo.ReplyComment;
import com.nick.utilObjects.AddReplyCommentObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyCommentService {
    public List<ReplyComment> queryReplyCommentByPagingDesc(int beginIndex,int stepSize ,int id);
    //增加回复
    public int addReplyComment(AddReplyCommentObject addReplyCommentObject);
}
