package com.nick.dao;

import com.nick.pojo.ReplyComment;
import com.nick.utilObjects.AddReplyCommentObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyCommentMapper {
    //分页查询评论下所有回复
    public List<ReplyComment> queryReplyCommentByPagingDesc(@Param("beginIndex") int beginIndex,@Param("stepSize") int stepSize ,@Param("id") int id);
    //增加回复
    public int addReplyComment(ReplyComment replyComment);
}
