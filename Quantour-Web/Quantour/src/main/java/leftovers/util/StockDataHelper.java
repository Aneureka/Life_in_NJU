package leftovers.util;

import java.io.IOException;

/**
 * Created by kevin on 2017/5/11.
 */
public class StockDataHelper {

    private Runtime runtime = Runtime.getRuntime();

    public String getStockHistoryData(){
        //执行对应的python方法
        try {
            Process proc = runtime.exec("python \"C:\\Users\\kevin\\OneDrive - smail.nju.edu.cn\\Documents\\Study\\2017_Spring\\SE3\\Homework\\Project\\Quantour\\Iteration3\\djklf\\src\\main\\java\\getStockData.py\"");
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取结果
        return null;
    }
}
