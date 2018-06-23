import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevin on 2017/3/17.
 */
public class stock_RemoveNameSpaces {

    public static void main(String[] args) throws IOException {
        //task
    }

    public static void start(String inputFile, String outputFile) throws IOException {
        //连接到输入文件
        File file = new File(inputFile);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //连接到输出文件
        File file1 = new File(outputFile);
        file1.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file1));

        //逐行替换
        String line;
        while ((line = reader.readLine()) != null){
            writer.append(removeNameSpaces(line) + System.getProperty("line.separator"));
        }

        //关闭流
        reader.close();
        writer.flush();
        writer.close();
    }

    public static String removeNameSpaces(String input) {
        String[] attributes = input.split("\t");

        String name = attributes[attributes.length - 2];
        name = name.replace(" ", "");
        attributes[attributes.length - 2] = name;

        return String.join("\t", attributes);
    }

}
