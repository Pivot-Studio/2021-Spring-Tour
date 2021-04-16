package privacy;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JSONUtil {
    public User get_key() {
        //创建文件对象和byte型数组，为后续工作做准备
        File f=new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\privacy","user.json");
        byte[] bytes = new byte[]{};
        try {
            FileInputStream stream=new FileInputStream(f);//创建一个输入流
            try {
                bytes=stream.readAllBytes();//读取输入流中所有字节并将其存入一个字节数组
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jsonString = new String(bytes);//将这个字节数组转换成json字符串
        return JSONObject.parseObject(jsonString, User.class);//将json字符串转换成json对象

    }
}
