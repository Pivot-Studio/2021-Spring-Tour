package com.nick.util.jwtUtil;

import java.util.UUID;

public class Constant {
    public static final String JWT_ID= UUID.randomUUID().toString();
    public static final String JWT_SECRET="jiaranxiaojiedegou";
    public static final int JWT_TTL=3*60*60*1000;
}
