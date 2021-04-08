package com.nick.utilObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReplyCommentObject {
    private int commentId;
    private int fromId;
    private String content;
    private int toId;
}
