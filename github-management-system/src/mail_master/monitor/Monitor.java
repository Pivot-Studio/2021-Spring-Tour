package part3.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import privacy.JSONUtil;
import privacy.User;
import part3.smtp.Smtp;

import java.io.*;

import static java.lang.Thread.sleep;

public class Monitor extends  Thread{

    public  void run() {
        String a=null;//用来记录最新的PushEvent的id
        String b;
        JSONUtil jsonUtil=new JSONUtil();
        User user=jsonUtil.get_key();
        String access_token = user.getAccess_token();
        //第一次请求
        HttpGet request = new HttpGet("https://api.github.com/users/machuanhu/events?access_token=" + access_token);

        CredentialsProvider provider = new BasicCredentialsProvider();

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
                File f=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part3","events.txt");//将获取的所有events信息存入events.txt
                FileWriter writefile = new FileWriter(f);
                writefile.write(result);
            }
            JSONArray jsonArray;
            jsonArray = (JSONArray) JSONArray.parse(result);
            File f2=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part3","PushEvents.txt");
            try (FileWriter writefile = new FileWriter(f2)) {
                //检索events列表
                for (int j = 1; j < jsonArray.size() + 1; j++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(j-1);
                    writefile.write("事件类型:   " + jsonObject.getString("type")+"      "+"id:"+jsonObject.getString("id")+"\n");//将检索到的events的type和id存入PushEvents.txt中
                    if (jsonObject.getString("type").equals("PushEvent"))//用a记录第一次请求时最新的PushEvent的id
                    {
                        a=jsonObject.getString("id");
                        break;//检索到最新的PushEvent，停止检索
                    }
                }
            }
            //System.out.println(a+"    "+b);
            try {
                sleep(10000);//设置轮询的时间间隔为30秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开始不断地轮询
        while(true) {
            HttpGet request1 = new HttpGet("https://api.github.com/users/machuanhu/events?access_token=" + access_token);

            CredentialsProvider provider1 = new BasicCredentialsProvider();

            try (CloseableHttpClient httpClient1 = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider1)
                    .build();
                 CloseableHttpResponse response1 = httpClient1.execute(request1)) {


                HttpEntity entity1 = response1.getEntity();
                String result1 = null;
                if (entity1 != null) {
                    // return it as a String
                    result1 = EntityUtils.toString(entity1);
                    File f = new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part3", "events.txt");
                    FileWriter writefile = new FileWriter(f);
                    writefile.write(result1);
                }
                JSONArray jsonArray1;
                jsonArray1 = (JSONArray) JSONArray.parse(result1);
                File f3 = new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part3", "PushEvents.txt");
                b=a;//用b记录上一次请求中最新的PushEvent的id
                try (FileWriter writefile = new FileWriter(f3)) {
                    for (int j = 1; j < jsonArray1.size() + 1; j++) {
                        JSONObject jsonObject = (JSONObject) jsonArray1.get(j-1);
                        writefile.write("事件类型:   " + jsonObject.getString("type")+"      "+"id:"+jsonObject.getString("id")+ "\n");
                        if (jsonObject.getString("type").equals("PushEvent"))
                        {
                            a=jsonObject.getString("id");//用a记录本次请求中最新的PushEvent的id
                            break;
                        }
                    }
                }
                //System.out.println(a+"    "+b);
                if (!a.equals(b))
                {
                    System.out.println("新的PushEvent");//当本次请求中最新的PushEvent的id与上次请求中最新的PushEvent的id不相等时，证明发生了PushEvent,立刻发送邮件
                    Smtp smtp=new Smtp();
                    try {
                        smtp.sendmessage(user.getMyEmailAccount(),user.getMyEmailPassword());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}