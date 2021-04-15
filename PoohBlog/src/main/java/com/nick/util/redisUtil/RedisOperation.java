package com.nick.util.redisUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nick.pojo.Blog;
import com.nick.util.ObjectToJson;
import redis.clients.jedis.Jedis;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.*;

public class RedisOperation {
    public static void addItem(Jedis jedis, Blog blog) throws JsonProcessingException {
        jedis.set(Integer.toString(blog.getId()), ObjectToJson.objectToJson(blog));
        jedis.zadd("BlogZset", blog.getReleaseDateTime().getNanos(),Integer.toString(blog.getId()));
    }


    public static void updateItem(Jedis jedis,Blog blog) throws Exception
    {
        jedis.zadd("BlogZset", Double.parseDouble(Double.toString(blog.getReleaseDateTime().getNanos())),Integer.toString(blog.getId()));
        jedis.set(Integer.toString(blog.getId()),ObjectToJson.objectToJson(blog));
    }


    public static void deleteItem(Jedis jedis,int id) throws Exception
    {
        jedis.del(Integer.toString(id));
        jedis.zrem("BlogZset",Integer.toString(id));
    }
    public static List<String> queryBlogByPagingDesc(int beginIndex, int stepSize,Jedis jedis)
    {
        //redis中下标从0开始
        Set<String> blogZset = jedis.zrevrange("BlogZset", beginIndex, beginIndex+stepSize-1);
        Iterator<String> iterator = blogZset.iterator();
        List<String> res=new LinkedList<>();
        while (iterator.hasNext())
        {
            res.add(jedis.get(iterator.next()));
        }
        return res;
    }
    public static void main(String[] args) throws Exception {
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Blog blog=new Blog();
        blog.setWriterId(1);
        blog.setTypeId(1);
        blog.setTitle("hellofucker");
        blog.setContent("oooooyeah");
        blog.setCommentCount(1);
        blog.setId(1);
        blog.setReleaseDateTime(new Timestamp(System.nanoTime()));

        System.out.println(ObjectToJson.objectToJson(blog));
//        Iterator<String> iterator = queryBlogByPagingDesc(1, -1, jedis).iterator();
//        while (iterator.hasNext())
//        {
//            System.out.println(iterator.next());
//        }
    }
}
