package part4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Favorite {
    public Favorite(String name) {
        File f2 = new File("C:\\Program Files\\Git\\2021-Spring-Tour\\github-management-system\\src\\part4", name+".txt");
        try(FileWriter writefile = new FileWriter(f2);) {
            writefile.write("名叫"+name+"的收藏夹"+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //public static void main(String[] args) {
        //Favorite favorite=new Favorite("python");
    //}
}
