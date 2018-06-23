package util.tool;

import java.text.DecimalFormat;

/**
 * Created by kevin on 2017/3/11.
 */
public class NumberFormatter {

    /**
     * 数据格式
     */
    private static final String format = "%.2f";

    /**
     * 将数字转化为保留两位小数的字符串, 示例10.2 → 10.20
     *
     * @param number 数字
     * @return 保留两位小数的字符串
     */
    public static String format(double number){
        return String.format(format, number).toString();
    }


    /**
     * 将数字转化为以万为单位并且保留两位小数的字符串（包括“万”字），示例1.23456E5 → 12.35 万
     *
     * @param number 数字
     * @return 以万为单位并且保留两位小数的字符串
     */
    public static String formatToBaseWan(double number){
        return String.format(format, number / 10000).toString() + " 万";
    }

    /**
     * 将数字转化为百分数并且保留两位小数的字符串（没有包括%），示例0.05666 → 5.67
     *
     * @param number 数字
     * @return 百分制并且保留两位小数的字符串
     */
    public static String formatToPercent(double number){
        return String.format(format, number * 100).toString();
    }

    /**
     * 将数字转化科学计数法，示例0.05666 → 5.6E-2
     *
     * @param number 数字
     * @return 科学计数法
     */
    public static String formatToScientific(double number){
        DecimalFormat exponentFormat = new DecimalFormat("0.00E00");
        return exponentFormat.format(number);
    }

}
