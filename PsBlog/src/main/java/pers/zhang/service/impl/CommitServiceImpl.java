package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.zhang.mapper.CommitMapper;
import pers.zhang.pojo.Blog;
import pers.zhang.pojo.Commit;
import pers.zhang.service.CommitService;

import java.util.List;

@Service("commitService")
public class CommitServiceImpl implements CommitService {
    @Autowired
    private CommitMapper commitMapper;

    @Override
    public int addCommit(int committerId, String ccontent, int target) {
        if(ccontent==null||target==0) return 0;
        Commit commit = new Commit();
        commit.setCommitterId(committerId);
        commit.setCcontent(ccontent);
        commit.setTarget(target);
        return commitMapper.addCommit(commit);
    }

    @Override
    public int deleteCommit(int cid) {
        Commit commit = new Commit();
        commit.setCid(cid);
        return commitMapper.deleteCommit(commit);
    }

    @Override
    public Commit getCommitByCid(int cid) {
        Commit commit = new Commit();
        commit.setCid(cid);
        return commitMapper.getCommitByCid(commit);
    }

    @Override
    public List<Commit> getCommitOfBlog(int target) {
        Blog blog = new Blog();
        blog.setId(target);
        return commitMapper.getCommitOfBlog(blog);
    }
}
