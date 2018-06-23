package leftovers.service.backtestserviceimpl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Hiki on 2017/6/8.
 */
@Component
public class ResultReader {

    // 结果文件名
    private final static List<String> RESULT_NAMES = new ArrayList<>(Arrays.asList("summary", "portfolio", "benchmark_portfolio", "stock_positions", "stock_account", "trades"));

    // 结果文件类型
    private final static String SUFFIX = ".json";

    // 结果
    private Map<String, String> results;

    public ResultReader() {
        results = new HashMap<>();
    }

    // 读取结果文件
    public boolean readResults(String dirPath) {
        for (String resultName : RESULT_NAMES) {
            // 构建文件路径
            String filePath = dirPath + resultName + SUFFIX;
            Path path = Paths.get(filePath);
            // 若文件不存在，返回false
            if (!Files.exists(path)) {
                System.out.println(resultName + "文件不存在");
                return false;
            }
            // 读取文件
            try {
                Stream<String> stream = Files.lines(path);
                String json = stream.collect(Collectors.joining());
                results.put(resultName, JSONObject.parse(json).toString());
                if (Files.exists(path)){
                    Files.delete(path);
                }
                System.out.println(resultName + ": " + JSONObject.parse(json).toString());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }

        }

        return true;

    }

    public Map<String, String> getResults() {
        return results;
    }


}
