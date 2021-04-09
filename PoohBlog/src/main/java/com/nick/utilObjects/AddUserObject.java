package com.nick.utilObjects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserObject {
    private String name;
    private String mail;
    private String passWord;
    private String account;
}
