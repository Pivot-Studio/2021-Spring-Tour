package part4;

import com.alibaba.fastjson.JSONArray;

public class Sort {
    public void sort(JSONArray jsonArray,String key){
        //排序算法
        int n[]=new int[jsonArray.size()];
        for(int i =0;i<jsonArray.size();i++)
        {
            n[i]=i;
        }
        quicksort(jsonArray,n,jsonArray.size(), key);
        for(int j=0;j<jsonArray.size();j++)
        {
            System.out.println(key+":    "+jsonArray.getJSONObject(n[j]).getString(key)+"    "+jsonArray.getJSONObject(n[j]).toJSONString());
        }
    }
    public void quicksort(JSONArray jsonArray,int n[],int m,String key)
    {
        quicksort1(jsonArray,n,0,m-1,key);
    }
    public void quicksort1(JSONArray jsonArray,int n[],int s,int t,String key)
    {
        if(s<t)
        {
            int i;
            if(key.equals("stargazers_count")||key.equals("id"))
                 i=partition2(s,t,jsonArray,key,n);
            else
                 i=partition1(s,t,jsonArray,key,n);
            quicksort1(jsonArray,n,s,i-1,key);
            quicksort1(jsonArray,n,i+1,t,key);
        }
    }
    public int partition1(int s,int t,JSONArray jsonArray,String key,int n[])
    {
        int i=s,j=t;
        int base=n[s];
        while(i!=j)
        {
            while (j>i&&jsonArray.getJSONObject(n[j]).getString(key).compareTo(jsonArray.getJSONObject(base).getString(key))>=0)
                j--;
            if(j>i)
            {
                n[i]=n[j];
                i++;
            }
            while(i<j&&jsonArray.getJSONObject(n[i]).getString(key).compareTo(jsonArray.getJSONObject(base).getString(key))<=0)
                i++;
            if(i<j)
            {
                n[j]=n[i];
                j--;
            }
        }
        n[i]=base;
        return i;
    }
    public int partition2(int s,int t,JSONArray jsonArray,String key,int n[])
    {
        int i=s,j=t;
        int base=n[s];
        while(i!=j)
        {
            while (j>i&&Integer.parseInt(jsonArray.getJSONObject(n[j]).getString(key))>=Integer.parseInt(jsonArray.getJSONObject(base).getString(key)))
                j--;
            if(j>i)
            {
                n[i]=n[j];
                i++;
            }
            while(i<j&&Integer.parseInt(jsonArray.getJSONObject(n[i]).getString(key))<=Integer.parseInt(jsonArray.getJSONObject(base).getString(key)))
                i++;
            if(i<j)
            {
                n[j]=n[i];
                j--;
            }
        }
        n[i]=base;
        return i;
    }
}
