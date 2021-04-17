package star.favorite.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OperationOfFavorite {
    public void check_favorite(JSONArray jsonArray6,JSONArray jsonArray8){
        //查看收藏夹列表
        for(int i=0;i<jsonArray8.size();i++)
        {
            System.out.println(jsonArray6.getJSONObject(i).get("favorite_name")+":     ");
            for(int j=0;j<jsonArray8.getJSONArray(i).size();j++)
                System.out.println(jsonArray8.getJSONArray(i).getJSONObject(j).toJSONString());
        }


    }
    public void check_star(JSONArray jsonArray) {
        //查看star列表
        for (int j = 1; j < jsonArray.size()+1; j++){
            JSONObject jsonObject=jsonArray.getJSONObject(j-1);
            System.out.println("index:   "+jsonObject.getString("index_in_star")+"   "+jsonObject.toJSONString());
        }
    }

    public void create_favorite(String favorite_name) {
        //创建收藏夹
        File f2 = new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\star", favorite_name + ".json");
        try (FileWriter writefile = new FileWriter(f2)) {
            writefile.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("成功创建"+favorite_name+"收藏夹");
    }

    public void add_to_favorite(String favorite_name, JSONObject jsonObject, int sentry[], int i,JSONArray jsonArray6,JSONArray jsonArray8) {
//将给定的仓库添加至给定的收藏夹，并且当该仓库已经在其他或者当前收藏夹中时，提示错误。
        if (sentry[i] == 1)
            System.out.println("该仓库已经进入某个收藏夹，请勿重复添加");
        else {
            int j=0;
            for(;j<jsonArray6.size();j++)
            {
                if(jsonArray6.getJSONObject(j).getString("favorite_name").equals(favorite_name))
                {
                    jsonArray8.getJSONArray(j).add(jsonObject);
                    break;
                }

            }
            if(j==jsonArray6.size())
            {
                System.out.println("没有这个收藏夹,请输入正确的收藏夹名称");
                return;
            }
            System.out.println("成功将仓库放入"+favorite_name+"收藏夹中");
            File f3 = new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\star", favorite_name + ".json");
            try (FileWriter writer_file = new FileWriter(f3)) {
                writer_file.write(jsonArray8.getJSONArray(j).toJSONString());
                sentry[i] = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void remove_from_favorite(JSONObject jsonObject, int i, int sentry[],String favorite_name,JSONArray jsonArray6,JSONArray jsonArray8) {
//将给定的仓库从给定的收藏夹移除，并且当该仓库不在该收藏夹中时，提示错误。
        int j=0;
        int k=0;
        boolean check=false;
        for(;j<jsonArray6.size();j++)
        {
            if(jsonArray6.getJSONObject(j).getString("favorite_name").equals(favorite_name))
            {
                for(;k<jsonArray8.getJSONArray(j).size();k++)
                {
                    if(jsonArray8.getJSONArray(j).getJSONObject(k).getString("id").equals(jsonObject.getString("id")))
                    {
                        jsonArray8.getJSONArray(j).remove(jsonObject);
                        check=true;
                        break;
                    }
                }
                if(k==jsonArray8.getJSONArray(j).size()&& !check)
                {
                    System.out.println("该仓库并不在此收藏夹中，请重新核对信息");
                    return;
                }
                break;
            }
        }
        if(j==jsonArray6.size())
        {
            System.out.println("收藏夹不存在，请输入正确的收藏夹名称");
            return;
        }
        System.out.println("成功将仓库从"+favorite_name+"收藏夹移除");
        File f3 = new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\star", favorite_name + ".json");
        try (FileWriter writer_file = new FileWriter(f3)) {
            writer_file.write(jsonArray8.getJSONArray(j).toJSONString());
            sentry[i] = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}