import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendEmail {
    public static void main(String[] args) throws Exception {
        Properties properties=new Properties();
        properties.setProperty("mail.host","smtp.qq.com");//设置QQ邮件服务器
        properties.setProperty("mail.transport.protocol","smtp");//邮件发送协议
        properties.setProperty("mail.smtp.auth","true");//需要用户名与密码

        //设置ssl加密
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.ssl.socketFactory",sf);

        //使用JavaMail发送邮件五个步骤
        //1.创建需要的session对象
        Session session=Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2975684744@qq.com","hruhtiztgxtndche");
            }
        });
        session.setDebug(true);
        //2.通过session得到transport对象
        Transport ts=session.getTransport();
        //3.连接服务器
        ts.connect("smtp.qq.com","2975684744@qq.com","hruhtiztgxtndche");
        //4.创建邮件
        //创建邮件对象
        MimeMessage message=new MimeMessage(session);
        //邮件标题
        message.setSubject("验证注册");
        //邮件文本内容
        message.setContent("<a href='http://www.baidu.com'>click me<a/>","text/html;charset=UTF-8");
        //指明邮件发件人
        message.setFrom(new InternetAddress("2975684744@qq.com"));
        //指明邮件收件人
        message.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress("2975684744@qq.com")));
        //5.发送
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
    }
}
