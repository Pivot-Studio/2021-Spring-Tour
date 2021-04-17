package star.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import star.favorite.menu.Menu;
import privacy.JSONUtil;
import privacy.User;

import java.io.*;

public class Request {
    public  void request() throws IOException {

        JSONUtil jsonUtil=new JSONUtil();
        User user=jsonUtil.get_key();
        String access_token=user.getAccess_token();//获取User对象中的access_token值
        HttpGet request = new HttpGet("https://api.github.com/users/machuanhu/starred?access_token="+access_token);//向github发起请求，获取starred列表

        CredentialsProvider provider = new BasicCredentialsProvider();
//以下开始获取json数据并且进行解析
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            // 401 if wrong
            System.out.println(response.getStatusLine().getStatusCode());//打印状态码
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                //将json数据转换成字符串并输出到starred.txt
                result = EntityUtils.toString(entity);
                File f=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\star","starred.json");
                try(FileWriter writefile=new FileWriter(f))
                {
                    writefile.write(result);
                }

                System.out.println("成功将所有json数据写入starred.json");
            }
            //解析json数组，筛选出想要的信息，并输出到repos.txt
            JSONArray jsonArray;
            jsonArray = (JSONArray) JSONArray.parse(result);
            File f2=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\star","repos.json");
            JSONArray jsonArray7=new JSONArray();
            try (FileWriter writefile = new FileWriter(f2)) {
                for (int j = 1; j < jsonArray.size()+1; j++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(j-1);
                    JSONObject jsonObject1= new JSONObject();
                    jsonObject1.put("name", jsonObject.getString("name"));
                    jsonObject1.put("full_name", jsonObject.getString("full_name"));
                    jsonObject1.put("id", jsonObject.getString("id"));
                    jsonObject1.put("html_url", jsonObject.getString("html_url"));
                    jsonObject1.put("created_at", jsonObject.getString("created_at"));
                    jsonObject1.put("updated_at", jsonObject.getString("updated_at"));
                    jsonObject1.put("pushed_at", jsonObject.getString("pushed_at"));
                    jsonObject1.put("description", jsonObject.getString("description"));
                    jsonObject1.put("language", jsonObject.getString("language"));
                    jsonObject1.put("stargazers_count", jsonObject.getString("stargazers_count"));
                    jsonObject1.put("size", jsonObject.getString("size"));
                    jsonObject1.put("index_in_star",j);
                    jsonArray7.add(j-1,jsonObject1);
                }
                writefile.write(jsonArray7.toJSONString());
                System.out.println("成功将筛选出来的信息写入repos.txt");
            }
            //Favorite favorite=new Favorite("java");
            Menu menu =new Menu();
            menu.menu(jsonArray7);

        }
    }

}
