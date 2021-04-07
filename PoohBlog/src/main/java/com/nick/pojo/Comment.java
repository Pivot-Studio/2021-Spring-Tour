package com.nick.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int topicId;
    private String content;
    private int fromId;
    private Timestamp releaseDateTime;
    private int likeCount;
}
