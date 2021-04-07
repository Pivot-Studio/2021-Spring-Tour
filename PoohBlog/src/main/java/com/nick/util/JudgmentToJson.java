package com.nick.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.HashMap;
import java.util.Map;

//接受一个int参数，返回一个形如{success:true}的json
public class JudgmentToJson {
    public static String judgmentToJson(int judgement) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String ,String> resMap=new HashMap<>();
        String res;
        if(judgement==1)
        {
            res="true";
        }
        else
        {
            res="false";
        }
        resMap.put("success",res);
        return objectMapper.writeValueAsString(resMap);
    }

}
