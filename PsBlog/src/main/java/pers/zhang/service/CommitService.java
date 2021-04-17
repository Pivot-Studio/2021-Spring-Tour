package pers.zhang.service;

import pers.zhang.pojo.Commit;

import java.util.List;

public interface CommitService {
    Commit addCommit(int committerId,String ccontent,int target);
    int deleteCommit(int cid);
    Commit getCommitByCid(int cid);
    List<Commit> getCommitOfBlog(int target);
}
