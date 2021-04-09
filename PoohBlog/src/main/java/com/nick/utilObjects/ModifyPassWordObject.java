package com.nick.utilObjects;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPassWordObject {
    private String account;
    private String passWord;
}
