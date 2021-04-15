package part4;

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
import part1.JSONUtil;
import part1.User;

import java.io.*;
import java.util.Scanner;

public class Request {
    public static void main(String[] args) throws IOException {
        int sentry[]=new int[10000];
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
                File f=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part4","starred.txt");
                FileWriter writefile=new FileWriter(f);
                writefile.write("返回的所有json数据\n");
                writefile.write(result);
                System.out.println("成功将所有json数据写入starred.txt");
            }
            //解析json数组，筛选出想要的信息，并输出到repos.txt
            JSONArray jsonArray;
            jsonArray = (JSONArray) JSONArray.parse(result);
            File f2=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part4","repos.json");
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
            boolean continue_or_break=true;
            Scanner reader = new Scanner(System.in);
            JSONArray jsonArray6=new JSONArray();
            JSONArray jsonArray8=new JSONArray();
            int k=1;
            OperationOfFavorite test = new OperationOfFavorite();
            while(continue_or_break) {
                System.out.println("根据提示输入你想进行的操作的序号");
                System.out.println("如果想创建一个新的收藏夹请输入1");
                System.out.println("如果想将一个仓库添加进某个收藏夹中请输入2，前提是你已经创建好了该收藏夹");
                System.out.println("如果想将一个仓库从某个收藏夹中移除请输入3，前提是该仓库确实在该收藏夹中");
                System.out.println("如果想对star仓库列表中的仓库进行排序请输入4");
                System.out.println("如果想查看star中所有仓库列表请输入5");
                System.out.println("如果想查看收藏夹列表请输入6");
                System.out.println("如果想终止程序，请输入7");
                String n=reader.next();
                switch (n) {
                    case "1":
                        System.out.println("请输入你想创建的收藏夹的名称");
                        String favorite_name1 = reader.next();
                        JSONArray jsonArray2=new JSONArray();
                        JSONObject jsonObject1=new JSONObject();
                        jsonObject1.put("favorite_name",favorite_name1);
                        test.create_favorite(favorite_name1);
                        jsonArray6.add(k-1,jsonObject1);
                        jsonArray8.add(k-1,jsonArray2);
                        k++;
                        break;
                    case "2":
                        System.out.println("你是否记得你想添加的仓库在star列表中的序号，如果你不记得序号，请在star仓库列表中查看序号后再来进行此操作");
                        System.out.println("记得序号请输入1，将继续此操作。不记得序号请输入2，将返回至菜单,你可以在菜单中查看仓库列表以查看该仓库的序号");
                        String m=reader.next();
                        if(!m.equals("1"))
                        {
                            if(!m.equals("2"))
                                System.out.println("用户输入了意料之外的值，返回到菜单");
                            break;
                        }
                        System.out.println("请继续输入仓库在列表中的序号");
                        int i = reader.nextInt();
                        System.out.println("你是否记得你想添加的收藏夹的名称，如果你不记得，请查看收藏夹列表后再来进行此操作");
                        System.out.println("记得收藏夹名称请输入1，将继续此操作。不记得序号请输入2，将返回至菜单,你可以在菜单中查看收藏夹列表以确认你想把该仓库添加至哪个收藏夹");
                        m=reader.next();
                        if(!m.equals("1"))
                        {
                            if(!m.equals("2"))
                                System.out.println("用户输入了意料之外的值，返回到菜单");
                            break;
                        }
                        System.out.println("请继续输入收藏夹的名称");
                        String favorite_name = reader.next();
                        JSONObject jsonObject = (JSONObject) jsonArray7.get(i-1);
                        test.add_to_favorite(favorite_name, jsonObject, sentry, i-1,jsonArray6,jsonArray8);
                        System.out.println(sentry[i-1]);
                        break;
                    case "3":
                        System.out.println("你是否记得你想添加的仓库在star列表中的序号，如果你不记得序号，请在star仓库列表中查看序号后再来进行此操作");
                        System.out.println("记得序号请输入1，将继续此操作。不记得序号请输入2，将返回至菜单,你可以在菜单中查看仓库列表以查看该仓库的序号");
                        m=reader.next();
                        if(!m.equals("1"))
                        {
                            if(!m.equals("2"))
                                System.out.println("用户输入了意料之外的值，返回到菜单");
                            break;
                        }
                        System.out.println("请继续输入仓库在列表中的序号");
                        i = reader.nextInt();
                        System.out.println("你是否记得此仓库所在的收藏夹的名称。如果你不记得该仓库位于哪个收藏夹，请查看收藏夹列表后再来进行此操作");
                        System.out.println("记得收藏夹名称请输入1，将继续此操作。不记得序号请输入2，将返回至菜单,你可以在菜单中查看收藏夹列表以确认该仓库位于哪个收藏夹");
                        m=reader.next();
                        if(!m.equals("1"))
                        {
                            if(!m.equals("2"))
                                System.out.println("用户输入了意料之外的值，返回到菜单");
                            break;
                        }
                        System.out.println("请输入该仓库所在的收藏夹的名称");
                        String favorite_name2 = reader.next();
                        JSONObject jsonObject2 = (JSONObject) jsonArray7.get(i-1);
                        test.remove_from_favorite(jsonObject2,i,sentry,favorite_name2,jsonArray6,jsonArray8);
                        break;
                    case "4":
                        System.out.println("请问你想依据什么进行排序");
                        String key=reader.next();
                        Sort Sort=new Sort();
                        Sort.sort(jsonArray7,key);
                        break;
                    case "5":
                        test.check_star(jsonArray7);
                        break;
                    case "6":
                        test.check_favorite(jsonArray6,jsonArray8);
                        break;
                    case "7":
                        continue_or_break=false;
                        break;
                    default:
                        System.out.println("用户输入了意料之外的值，返回到菜单");
                        break;
                }
            }
        }
    }

}
