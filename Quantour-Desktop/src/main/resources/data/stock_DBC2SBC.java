import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevin on 2017/3/17.
 */
public class stock_DBC2SBC {

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
            writer.append(replaceDBC2SBC(line) + "\n");
        }

        //关闭流
        reader.close();
        writer.flush();
        writer.close();
    }

    public static String replaceDBC2SBC(String input) {
        Pattern pattern = Pattern.compile("[\u3000\uff01-\uff5f]{1}");

        Matcher m = pattern.matcher(input);
        StringBuffer s = new StringBuffer();
        while (m.find()) {
            char c = m.group(0).charAt(0);
            char replacedChar = c == '　' ? ' ' : (char) (c - 0xfee0);
            m.appendReplacement(s, String.valueOf(replacedChar));
        }

        m.appendTail(s);

        return s.toString();
    }

}
