package com.nick.utilObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBlogObject {
    private String content;
    private String title;
    private int writerId;
    private int typeId;
}
