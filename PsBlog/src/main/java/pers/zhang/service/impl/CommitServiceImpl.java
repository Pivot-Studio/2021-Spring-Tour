package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.zhang.mapper.BlogMapper;
import pers.zhang.mapper.CommitMapper;
import pers.zhang.pojo.Blog;
import pers.zhang.pojo.Commit;
import pers.zhang.service.CommitService;

import java.util.List;

@Service("commitService")
public class CommitServiceImpl implements CommitService {
    @Autowired
    private CommitMapper commitMapper;
    @Autowired
    private BlogMapper blogMapper;

    //作用：发表一条评论
    //参数：评论者id号=committerId，评论内容=content，所评论博客id号=id
    //返回值：所发表Commit实例，可能为null
    @Override
    public Commit addCommit(int committerId, String ccontent, int target){
        Blog blog = new Blog();
        blog.setId(target);
        //内容为空字符串或所评论博客不存在，返回null
        if(ccontent==""||blogMapper.getBlog(blog)==null) return null;
        Commit commit = new Commit();
        commit.setCommitterId(committerId);
        commit.setCcontent(ccontent);
        commit.setTarget(target);
        //将刚发表的评论的自增主键赋给实例的相应字段cid
        commitMapper.addCommit(commit);
        return commit;
    }
//    public int addCommit(int committerId, String ccontent, int target) {
//        Blog blog = new Blog();
//        blog.setId(target);
//        if(ccontent==null||blogMapper.getBlog(blog)==null) return 0;
//        Commit commit = new Commit();
//        commit.setCommitterId(committerId);
//        commit.setCcontent(ccontent);
//        commit.setTarget(target);
//        return commitMapper.addCommit(commit);
//    }

    //作用：删除一条评论
    //参数：评论id号=cid
    //返回值：删除记录数（成功为1，因为评论不存在失败为0）
    @Override
    public int deleteCommit(int cid) {
        Commit commit = new Commit();
        commit.setCid(cid);
        return commitMapper.deleteCommit(commit);
    }

    //作用：获取一条评论
    //参数：评论id号=cid
    //返回值：commit类对象
    @Override
    public Commit getCommitByCid(int cid) {
        Commit commit = new Commit();
        commit.setCid(cid);
        return commitMapper.getCommitByCid(commit);
    }

    //作用：获取一篇博客的所有评论
    //参数：博客id号=id
    //返回值：commit类对象集合
    @Override
    public List<Commit> getCommitOfBlog(int target) {
        Blog blog = new Blog();
        blog.setId(target);
        return commitMapper.getCommitOfBlog(blog);
    }
}
