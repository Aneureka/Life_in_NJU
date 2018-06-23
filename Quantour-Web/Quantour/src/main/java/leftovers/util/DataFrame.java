package leftovers.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kevin on 2017/4/3.
 */
public class DataFrame {

    private Object[][] data;

    /**
     * 读取内存文件为DataFrame
     *
     * @param memoryFile 内存文件
     * @param seperator  列分隔符
     */
    public DataFrame(String[] memoryFile, String seperator) {
        //初始化data
        data = new String[memoryFile.length][];
        for (int i = 0; i < memoryFile.length; i++) {
            data[i] = memoryFile[i].split(seperator);
        }
    }

    public DataFrame(Object[][] data) {
        this.data = data;
    }

    public DataFrame(Object[][] data, Object[] columns, Object[] index) {
        this.data = new Object[index.length + 1][columns.length];
        for (int i = 0; i < index.length + 1; i++) {
            for (int j = 0; j < columns.length; j++) {
                this.data[i][j] = i == 0 ? columns[j] : (j == 0 ? index[i - 1] : data[i - 1][j - 1]);
            }
        }
    }

    public Object get(int i1, int i2) {
        return data[i1 + 1][i2 + 1];
    }

    public int getHeight() {
        return data.length - 1;
    }

    public int getWidth() {
        return data[0].length - 1;
    }

    public Object[] getColumns() {
        return data[0];
    }

    public Object[] getIndex() {
        Object[] columnCopy = new Object[data.length - 1];
        //获取列数据
        for (int i = 1; i < data.length; i++) {
            columnCopy[i - 1] = data[i][0];
        }

        return columnCopy;
    }

    /**
     * 获取某一行数据
     *
     * @param index 索引
     * @return 某一行数据
     */
    public Series getRow(int index) {
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
    public Series getColumn(String columnName) {
        Object[] columnCopy = new String[data.length - 1];

        //获取列索引
        int columnIndex = getColumnIndex(columnName);

        //获取列数据
        for (int i = 1; i < data.length; i++) {
            columnCopy[i - 1] = data[i][columnIndex];
        }

        return new Series(inferType(columnCopy));
    }

    /**
     * 行切片
     *
     * @param index1 行索引1
     * @param index2 行索引2
     * @return 新的DataFrame
     */
    public DataFrame rowSlice(int index1, int index2) {
        Object[][] rowsCopy = new Object[index2 - index1 + 1][data[0].length];

        //拷贝列名
        System.arraycopy(data[0], 0, rowsCopy[0], 0, data[0].length);
        //拷贝数据
        for (int i = index1; i < index2; i++) {
            System.arraycopy(data[i + 1], 0, rowsCopy[i - index1 + 1], 0, data[0].length);
        }

        return new DataFrame(rowsCopy);
    }

    public String toJSON() {
        org.json.JSONArray jsonArray = new org.json.JSONArray();

        for (int i = 1; i < data.length; i++) {
            int iCopy = i;
            JSONObject jsonObject = new JSONObject() {
                {
                    for (int j = 0; j < data[0].length; j++) {
                        boolean nan = false;
                        Object o = data[iCopy][j];
                        if (o instanceof Double){
                            if (((Double)o).isNaN() || ((Double)o).isInfinite()){
                                nan = true;
                            }
                        }
                        put(data[0][j].toString(), nan ? o.toString() : o);
                    }
                }
            };
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    /**
     * 获取列索引
     *
     * @param columnName 列名
     * @return 列索引
     */
    private int getColumnIndex(String columnName) {
        int res = -1;

        for (int i = 0; i < data[0].length; i++) {
            if (data[0][i].equals(columnName)) {
                res = i;
                break;
            }
        }

        return res;
    }

    private Object[] inferType(Object[] series) {
        try {
            return Arrays.stream(series).map(Object::toString).map(Double::parseDouble).toArray();
        } catch (NumberFormatException e1) {
            try {
                return Arrays.stream(series).map(Object::toString).map(LocalDate::parse).toArray();
            } catch (DateTimeParseException e2) {
                return series;
            }
        }
    }

}

