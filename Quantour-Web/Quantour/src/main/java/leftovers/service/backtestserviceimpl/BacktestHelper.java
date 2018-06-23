package leftovers.service.backtestserviceimpl;

/**
 * Created by Hiki on 2017/6/6.
 */

import leftovers.enums.DefaultAlgorithmInfo;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class BacktestHelper {

    private final static String resultFileName = "result.pkl";
    private static String BundlePath;
    private static String ProjectPath;
    private static String Separator;

    private String resultPath;
    private String algorithmPath;
    private String summaryPath;
    private String scriptPath;

    private StringBuilder commands;

    static {
        Separator = File.pathSeparator;
        if(isWindows()) {
            String currentUser = System.getProperty("user.name");
            BundlePath = "C:\\Users\\" + currentUser + "\\.rqalpha\\bundle\\";
            ProjectPath = "C:\\Users\\" + currentUser + "\\projects\\Quantour\\";
//            ProjectPath = "D:\\projects\\Quantour\\";
            Separator = "\\";
        }
        else {
            BundlePath = "/root/.rqalpha/bundle/";
            ProjectPath = "/root/projects/Quantour/";
            Separator = "/";
        }
    }

    private void init(String username){
        commands = new StringBuilder();
        if(isWindows()){
//            commands.append("activate rqalpha\n");
        }
        else{
            commands.append("#!/bin/bash\n");
            commands.append("source /root/anaconda2/envs/rqalpha/bin/activate rqalpha\n");
        }

        resultPath = ProjectPath + "result" + Separator + username + Separator;
        algorithmPath = ProjectPath + "algorithm" + Separator + username + Separator;
        summaryPath = ProjectPath + "summary" + Separator + username + Separator;
        scriptPath = ProjectPath + "script" + Separator + username + Separator;
        // 各自生成路径
        createDir(resultPath);
        createDir(algorithmPath);
        createDir(summaryPath);
        createDir(scriptPath);
    }

    public boolean backtest(String algoId, String username, String code, String beginDate, String endDate, double stockStartCash, String benchmark){

        // 初始化
        System.out.println("init...");
        init(username);

        // 生成py文件
        System.out.println("generate algorithm...");
        if (!generateAlgoPy(algoId, DefaultAlgorithmInfo.CODE_PREFIX + code)) {
            return false;
        }

        // 补充命令
        commands.append("rqalpha run")
                .append(" -f ").append(algorithmPath)
                .append(" -d ").append(BundlePath)
                .append(" -s ").append(beginDate)
                .append(" -e ").append(endDate)
                .append(" --stock-starting-cash ").append(stockStartCash)
                .append(" --benchmark ").append(benchmark)
                .append(" -o ").append(resultPath + resultFileName)
                .append(" -l ").append("info")
//                .append(" --report ").append(summaryPath)
                .append(" --progress ")
                .append("\n");

        // 生成临时脚本
        System.out.println("create script...");
        File tempScript = null;
        try {
            tempScript = createTempScript(algoId);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // 执行命令，生成pkl文件
        try {
            System.out.println("execute algorithm...");
            ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
            if(isWindows())
                pb = new ProcessBuilder("cmd.exe", "/C", tempScript.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (tempScript != null)
                tempScript.delete();
        }

        return true;
    }

    private boolean generateAlgoPy(String algoId, String code){

        FileOutputStream pyos = null;
        try {
            // 创建路径
            Path path = Paths.get(algorithmPath);
            if (!Files.exists(path))
                Files.createDirectories(path);
            // 创建文件
            algorithmPath += (algoId + ".py");
            path = Paths.get(algorithmPath);
            if (!Files.exists(path)){
                Files.createFile(path);
                if (Files.exists(path));
                else
                    System.out.println("failed to create .py...");
            }

            pyos = new FileOutputStream(new File(algorithmPath));
            FileChannel channel = pyos.getChannel();
            ByteBuffer src = Charset.forName("utf-8").encode(code);

            int length = 0;
            while((length = channel.write(src)) != 0);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (pyos != null) {
                try {
                    pyos.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }

        return true;
    }

    private File createTempScript(String fileName) throws IOException{
        File tempScript = null;
        if(isWindows())
            tempScript = new File(scriptPath + fileName + ".bat");
        else
            tempScript = new File(scriptPath + fileName + ".sh");

        if (!tempScript.exists())
            tempScript.createNewFile();

        tempScript.setExecutable(true);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
        PrintWriter pw = new PrintWriter(streamWriter);
        pw.print(commands.toString());
        pw.close();
        return tempScript;
    }

    private boolean createDir(String dir){

        Path path = Paths.get(dir);
        if (!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        return true;

    }

    private static boolean isWindows(){
       return System.getProperty("os.name").contains("Windows");
    }

    public String getResultPath() {
        return resultPath;
    }

}
