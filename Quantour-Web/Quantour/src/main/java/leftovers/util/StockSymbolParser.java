package leftovers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hiki on 2017/6/2.
 */
public class StockSymbolParser {


    // 获取股票代码数字编号
    public static String getStockCodeNum(String code){
        // 用正则表达式匹配数字
        Pattern r = Pattern.compile("\\d+");
        Matcher m = r.matcher(code);

        if (m.find()) {
            return m.group(0);
        }

        return m.find() ? m.group(0) : "";
    }

    public static String toAnotherForm(String symbol){

        Pattern p = Pattern.compile("([A-Z]+)(\\d+)");
        Matcher m = p.matcher(symbol);

        String word = null;
        String number = null;


        if (m.find()){
            word = m.group(1);
            number = m.group(2);
        }

        return number + "." + word;
    }

}
