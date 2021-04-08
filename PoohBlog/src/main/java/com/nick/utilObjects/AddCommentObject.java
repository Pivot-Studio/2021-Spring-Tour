package com.nick.utilObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentObject {
    private int topicId;
    private String content;
    private int fromId;
}
