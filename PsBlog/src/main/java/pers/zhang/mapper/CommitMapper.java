package pers.zhang.mapper;

import pers.zhang.pojo.Blog;
import pers.zhang.pojo.Commit;

import java.util.List;

public interface CommitMapper {
    int addCommit(Commit commit);
    int deleteCommit(Commit commit);
    Commit getCommitByCid(Commit commit);
    List<Commit> getCommitOfBlog(Blog blog);
    List<Commit> getCommitPageOfBlog(Blog blog);
}
