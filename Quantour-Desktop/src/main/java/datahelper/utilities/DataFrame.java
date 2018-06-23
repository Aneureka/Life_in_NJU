package datahelper.utilities;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Created by kevin on 2017/4/3.
 */
public class DataFrame {

    private String[][] data;

    /**
     * 读取内存文件为DataFrame
     *
     * @param memoryFile 内存文件
     * @param seperator  列分隔符
     */
    public DataFrame(String[] memoryFile, String seperator){
        //初始化data
        data = new String[memoryFile.length][];
        for (int i = 0; i < memoryFile.length; i++) {
            data[i] = memoryFile[i].split(seperator);
        }
    }

    /**
     * 获取某一行数据
     *
     * @param index 索引
     * @return 某一行数据
     */
    public Series getRow(int index){
        String[] rowCopy = new String[data[0].length];

        //拷贝行
        System.arraycopy(data[index + 1], 0, rowCopy, 0, data[0].length);

        return new Series(inferType(rowCopy));
    }

    /**
     * 获取某一列数据
     *
     * @param columnName 列名
     * @return 某一列数据
     */
    public Series getColumn(String columnName){
        String[] columnCopy = new String[data.length - 1];

        //获取列索引
        int columnIndex = getColumnIndex(columnName);

        //获取列数据
        for (int i = 1; i < data.length; i++) {
            columnCopy[i - 1] = data[i][columnIndex];
        }

        return new Series(inferType(columnCopy));
    }

    /**
     * 获取列索引
     *
     * @param columnName 列名
     * @return 列索引
     */
    private int getColumnIndex(String columnName){
        int res = -1;

        for (int i = 0; i < data[0].length; i++) {
            if (data[0][i].equals(columnName)){
                res = i;
                break;
            }
        }

        return res;
    }

    private Object[] inferType(String[] series){
        try{
            return Arrays.stream(series).map(Double::parseDouble).toArray();
        }catch (NumberFormatException e1){
            try {
                return Arrays.stream(series).map(LocalDate::parse).toArray();
            }catch (DateTimeParseException e2){
                return series;
            }
        }
    }

}

