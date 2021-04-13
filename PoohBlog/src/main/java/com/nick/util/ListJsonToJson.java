package com.nick.util;

import java.util.LinkedList;
import java.util.List;

public class ListJsonToJson {
    public static String listJsonToJson(List<String> list)
    {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("[");
        while (!list.isEmpty())
        {
            stringBuffer.append(list.remove(0));
            stringBuffer.append(",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        List<String>list=new LinkedList<>();
        list.add("askfkjasd");
        list.add("sajkdfnjaksdfnjdkas");
        System.out.println(listJsonToJson(list));
    }
}
