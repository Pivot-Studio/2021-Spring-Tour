package com.nick.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private int id;
    private String content;
    private String title;
    private String writer;
    private int writerId;
    private int isDeleted;
    private int typeId;
    private int readCount;
    private String keyWord;
    private int commentCount;
    private Timestamp releaseDateTime;
    private Timestamp modifiedDateTime;
    private int likeCount;

}
