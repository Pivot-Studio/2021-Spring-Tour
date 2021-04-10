package part1.util;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JSONUtil {
    public static void main(String[] args) {
        File f1=new File("E:\\untitled\\src","student.json");
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
        User user = JSONObject.parseObject(jsonString, User.class);
        System.out.println(jsonString);
        System.out.println(user.getName());
        System.out.println(user.getPassword());
    }
}
