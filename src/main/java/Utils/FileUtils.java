package Utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtils {
    public static String loadShader(String path){
       try {
           BufferedReader buffer =  new BufferedReader(new FileReader(path));
           StringBuilder builder = new StringBuilder();
           String line;
           while((line = buffer.readLine()) != null){
               builder.append(line).append("\n");
           }
           return builder.toString();
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }
}
