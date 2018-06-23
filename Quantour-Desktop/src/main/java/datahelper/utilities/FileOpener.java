package datahelper.utilities;

import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import util.exception.StockNotFoundException;

import java.io.*;

/**
 * 文件的读取分成打开文件，检查文件，读取文件。这里是打开文件部分。
 * Created by kevin on 2017/3/18.
 */
public class FileOpener {

    private String universesDir = System.getProperty("user.dir") + File.separator + "universes";

    /**
     * 把文件打开成InputStream
     *
     * @param file 文件路径
     * @return the input stream
     */
    public InputStream getStream(String file) {
        return this.getClass().getResourceAsStream(file);
    }

    public BufferedReader getBufferedReader(String file) {
        InputStream inputStream = getStream(file);

        if (inputStream == null)
            try {
                throw new FileNotFoundException(file + " not found.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        return new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * 把股票文件打开成InputStream
     *
     * @param code 股票代码
     * @return the input stream
     * @throws StockNotFoundException 股票不存在
     */
    public InputStream openStockFileAsIS(String code) throws StockNotFoundException {
        InputStream res = getStream("/data/stocks/" + code + ".csv");

        if (res == null)
            throw new StockNotFoundException("Stcok " + code + " not found.");

        return res;
    }

    /**
     * 把股票代码文件打开成InputStream
     *
     * @return the input stream
     * @throws FileNotFoundException the file not found exception
     */
    public InputStream openCodesFileAsIS() throws FileNotFoundException {
        InputStream res = getStream("/data/codes.txt");

        if (res == null)
            throw new FileNotFoundException("File codes.txt not found.");

        return res;
    }

    /**
     * 把股票文件打开成BufferedReader
     *
     * @param code 股票代码
     * @return the buffered reader
     * @throws StockNotFoundException 股票不存在
     */
    public BufferedReader openStockFileAsBR(String code) throws StockNotFoundException {
        return new BufferedReader(new InputStreamReader(openStockFileAsIS(code)));
    }

    /**
     * 把股票代码文件打开成BufferedReader
     *
     * @return the buffered reader
     * @throws FileNotFoundException the file not found exception
     */
    public BufferedReader openCodesFileAsBR() throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(openCodesFileAsIS()));
    }

    /**
     * 把交易日文件打开成BufferedReader
     *
     * @return the buffered reader
     */
    public BufferedReader openTradeDayFileAsBR() throws FileNotFoundException {
        InputStream inputStream = getStream("/data/tradeday.txt");

        if (inputStream == null)
            throw new FileNotFoundException("Tradeday.txt not found.");

        return new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * 把代码名称文件打开成BufferedReader
     *
     * @return the buffered reader
     */
    public BufferedReader openCodeNameFileAsBR() throws FileNotFoundException {
        InputStream inputStream = getStream("/data/code_name.txt");

        if (inputStream == null)
            throw new FileNotFoundException("Code_name.txt not found.");

        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public BufferedReader openStockAttributeFileAsBR() {
        InputStream inputStream = getStream("/data/code_name_section.txt");

        if (inputStream == null)
            try {
                throw new FileNotFoundException("Code_name.txt not found.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public BufferedReader openUniverseFileAsBR(String universeName) {
        File file = new File(universesDir + File.separator + universeName + ".txt");
        BufferedReader res = null;

        try {
            res = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println(file.toString() + " not found.");
            e.printStackTrace();
        }

        return res;
    }

    public BufferedWriter openNewUniverseFileAsBW(String universeName) throws DuplicateName {
        File file = new File(universesDir + File.separator + universeName + ".txt");
        BufferedWriter res = null;

        try {
            if (file.exists())
                throw new DuplicateName();
            new File(universesDir).mkdir();
            file.createNewFile();
            res = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public BufferedWriter openUniverseFileAsBW(String universeName) {
        File file = new File(universesDir + File.separator + universeName + ".txt");
        BufferedWriter res = null;

        try {
            if (!file.exists())
                throw new FileNotFoundException("Universe " + universeName + " not exists.");
            res = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public File openUniversesDir() {
        File res = new File(universesDir);

        res.mkdir();

        return res;
    }

    public BufferedReader openBenchmarkFileAsBR(String benchmark) {
        return getBufferedReader("/data/benchmark/" + benchmark + ".txt");
    }

}
