import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiki on 2017/3/9.
 */
public class stock_dateformatter {

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\Hiki\\Desktop\\股票历史数据ALL.csv");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        // 创建文件夹
        String dirPath = "C:\\Users\\Hiki\\Desktop\\ALL\\";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();

        // 先读取表头
        String th = br.readLine();

        // 读取股票信息
        List<String> formattedLines = new ArrayList<>();
        String line = "";
        while((line = br.readLine()) != null) {
            formattedLines.add(formatDate(line));

        }

        br.close();
        fr.close();

        // 读完了，写入新的文件
        FileWriter fw = new FileWriter(dirPath + "股票历史数据ALL.csv");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(th);
        bw.newLine();
        for(String formattedLine : formattedLines){
            bw.write(formattedLine);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    private static String formatDate(String line) {

        String[] words = line.split("\t");
        String dateString = words[1];
        String[] mdy = dateString.split("/");

        // 强行转换，代码惨不忍睹

        mdy[0] = (mdy[0].length() == 1 ? "0" : "") + mdy[0];
        mdy[1] = (mdy[1].length() == 1 ? "0" : "") + mdy[1];
        mdy[2] = "20" + mdy[2];

        words[1] =  mdy[2] + "-" + mdy[0] + "-" + mdy[1];

        return String.join("\t", words);
    }

}
