package com.nick.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    public static void snedEmail(String code,String mail) throws Exception {
        Properties properties=new Properties();
        properties.setProperty("mail.host","smtp.qq.com");//设置QQ邮件服务器
        properties.setProperty("mail.transport.protocol","smtp");//邮件发送协议
        properties.setProperty("mail.smtp.auth","true");//需要用户名与密码

        //设置ssl加密
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.ssl.socketFactory",sf);

        System.out.println("================0===========================");
        //使用JavaMail发送邮件五个步骤
        //1.创建需要的session对象
        Session session=Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2975684744@qq.com","*************");
            }
        });
        session.setDebug(true);

        System.out.println("===============1.ok========================");
        //2.通过session得到transport对象
        Transport ts=session.getTransport();
        //3.连接服务器
        ts.connect("smtp.qq.com","2975684744@qq.com","************");
        //4.创建邮件
        //创建邮件对象
        MimeMessage message=new MimeMessage(session);
        //邮件标题
        message.setSubject("验证注册");
        //邮件文本内容
        message.setContent("<a href='http://localhost:8080/user/verification/"+mail+"/"+code+"'>click me<a/>","text/html;charset=UTF-8");
        //指明邮件发件人
        message.setFrom(new InternetAddress("2975684744@qq.com"));
        //指明邮件收件人
        message.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(mail)));
        //5.发送
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
    }
}
