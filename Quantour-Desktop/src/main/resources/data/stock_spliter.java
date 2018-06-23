import java.io.*;
import java.util.*;

/**
 * Created by Hiki on 2017/3/6.
 * Notice：有些带星号的股票名称生成文件地址时会出错，所以把星号去掉，如 *ST中华A 改成 ST中华A
 */
public class stock_spliter {

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\Hiki\\Desktop\\股票历史数据ALL.csv");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        // 创建文件夹
        String dirPath = "C:\\Users\\Hiki\\Desktop\\stocks\\";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();

        // 先读取表头
        String th = br.readLine();

        // 读取股票数据，并放在Map中
        Map<String, ArrayList<String>> map = new HashMap<>();
        Map<String, String> code_name = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }
        });

        String code = "";
        for (; ; ) {
            String line = br.readLine();
            if (line == null)
                break;

            if (!code.equals(getCode(line))) {
                code = getCode(line);
                code_name.put(getCode(line), getName(line));
                // 文件名是股票名称的情况
                // map.put(getName(line), new ArrayList<>());
                map.put(getCode(line), new ArrayList<>());
            }

            map.get(getCode(line)).add(line);

        }

        saveStockFiles(map, dirPath, th);
        saveCodeStock(code_name, dirPath);


    }

    private static String getCode(String line) {
        String[] words = line.split("\t");
        return words[8];
    }

    private static String getName(String line) {
        String[] words = line.split("\t");
        return words[9];
    }

    private static void saveStockFiles(Map<String, ArrayList<String>> map, String dirPath, String th) throws IOException {

        // 遍历Map，并存到文件中
        Iterator<Map.Entry<String, ArrayList<String>>> entries = map.entrySet().iterator();
        while (entries.hasNext()){
            Map.Entry<String, ArrayList<String>> entry = entries.next();
//            String name = entry.getKey();
//
//            if (name.contains("*ST")) {
//                System.out.println(name + "（星号已经去掉）");
//                name = name.substring(1);
//            } else {
//                System.out.println(name);
//            }
            String stock_code = entry.getKey();

            ArrayList<String> lines = entry.getValue();
            FileWriter fw = new FileWriter(dirPath + stock_code + ".csv");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(th);
            bw.newLine();
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            fw.close();
        }

    }

    private static void saveCodeStock(Map<String, String> map, String dirPath) throws IOException {

        FileWriter fw = new FileWriter(dirPath + "code_name.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String code = entry.getKey();
            String name = entry.getValue();
            bw.write(code + " " + name);
            bw.newLine();
        }

        bw.close();
        fw.close();

    }

}
