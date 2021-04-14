package part2;

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
import part1.User;

import java.io.*;

public class Preparation {
    public static void main(String[] args) throws IOException {
        File f1=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part1","user.json");
        byte[] bytes = new byte[]{};
        try {
            FileInputStream stream=new FileInputStream(f1);//创建一个输入流
            bytes=stream.readAllBytes();//读取输入流中所有字节
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = new String(bytes);
        User user = JSONObject.parseObject(jsonString, User.class);//将json数据转换成了User对象
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
                File f=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part2","starred.txt");
                FileWriter writefile=new FileWriter(f);
                writefile.write("返回的所有json数据\n");
                writefile.write(result);
                System.out.println("成功将所有json数据写入starred.txt");
            }
            //解析json数组，筛选出想要的信息，并输出到repos.txt
            JSONArray jsonArray;
            jsonArray = (JSONArray) JSONArray.parse(result);
            File f2=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part2","repos.txt");
            try (FileWriter writefile = new FileWriter(f2)) {
                for (int j = 1; j < jsonArray.size()+1; j++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(j-1);
                    writefile.write("star中第" + j + "个仓库的全名:   " + jsonObject.getString("full_name") + "\n");
                    writefile.write("star中第" + j + "个仓库的名字:   " + jsonObject.getString("name") + "\n");
                    writefile.write("star中第" + j + "个仓库的ID:    " + jsonObject.getString("id") + "\n");
                    writefile.write("star中第" + j + "个仓库的HTML的URL:    " + jsonObject.getString("html_url") + "\n");
                    writefile.write("star中第" + j + "个仓库的创建时间:   " + jsonObject.getString("created_at") + "\n");
                }
                System.out.println("成功将筛选出来的信息写入repos.txt");
            }
        }
    }

}
