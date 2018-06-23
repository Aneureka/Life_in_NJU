package datahelper.utilities;

import util.exception.StockHasNoDataException;
import util.exception.StockNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 文件的读取分成打开文件，检查文件，读取文件。这里是检查文件部分。
 * Created by kevin on 2017/4/2.
 */
public class FileChecker {

    private static int lineLength = 200;

    /**
     * 检查股票文件
     *
     * @param br 股票文件
     * @throws StockHasNoDataException 股票无数据
     */
    public static void checkStockFile(BufferedReader br) throws StockHasNoDataException {
        checkStockFileHasData(br);
    }

    /**
     * 检查股票文件是否有数据
     *
     * @param br 股票文件
     * @throws StockHasNoDataException 股票无数据
     */
    private static void checkStockFileHasData(BufferedReader br) throws StockHasNoDataException {
        try {
            br.mark(lineLength);

            //读取表头
            String line = br.readLine();

            //检查是否股票是否有数据
            if ((line = br.readLine()) == null)
                throw new StockHasNoDataException();

            br.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
