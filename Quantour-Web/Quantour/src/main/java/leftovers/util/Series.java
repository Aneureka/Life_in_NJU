package leftovers.util;

import java.util.Arrays;

/**
 * Created by kevin on 2017/4/4.
 */
public class Series {

    private Object[] data;

    private int size;

    public Series(Object[] list){
        //初始化data
        data = new Object[list.length];
        //todo
        System.arraycopy(list, 0, data, 0, list.length);
        //初始化size
        size = list.length;
    }

    public Series multiply(Series series){
        Double[] data = new Double[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            data[i] = (this.data[i] == null || series.data[i] == null) ? null : (double)this.data[i] * (double)series.data[i];
        }
        return new Series(data);
    }

    public Series divide(Series series){
        Double[] data = new Double[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            data[i] = (this.data[i] == null || series.data[i] == null) ? null : (double)this.data[i] / (double)series.data[i];
        }
        return new Series(data);
    }

    public Series subtract(Series series){
        Double[] data = new Double[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            data[i] = (this.data[i] == null || series.data[i] == null) ? null : (double)this.data[i] - (double)series.data[i];
        }
        return new Series(data);
    }

    public Series allAdd(double numToAdd){
        Object[] data = new String[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            data[i] = this.data[i] == null ? null : (double)this.data[i] + numToAdd;
        }
        return new Series(data);
    }

    public Series allMultiply(double numToMultiply){
        Object[] data = new String[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            data[i] = this.data[i] == null ? null : (double)this.data[i] * numToMultiply;
        }
        return new Series(data);
    }

    public Series allSubtract(double numToSubtract){
        Double[] data = new Double[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            data[i] = this.data[i] == null ? null : (double)this.data[i] - numToSubtract;
        }
        return new Series(data);
    }

    public Series allDivide(double numToDivide){
        Double[] data = new Double[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            data[i] = this.data[i] == null ? null : (double)this.data[i] / numToDivide;
        }
        return new Series(data);
    }

    public double max(){
        return Arrays.stream(data).parallel().mapToDouble(num -> (double)num).max().orElse(Double.MIN_VALUE);
    }

    public double min(){
        return Arrays.stream(data).parallel().mapToDouble(num -> (double)num).min().orElse(Double.MAX_VALUE);
    }

    public double sum(){
        return Arrays.stream(data).parallel().mapToDouble(num -> (double)num).sum();
    }

    public double variance(){
        double res = 0;

        //计算标准差
        double average = average();
        for (Object num : data) {
            res += Math.pow((double)num - average, 2);
        }
        res = data.length == 0 ? 0 : res/data.length;

        return res;
    }

    public double stdev(){
        return Math.sqrt(variance());
    }

    public double average(){
        return Arrays.stream(data).parallel().mapToDouble(num -> (double)num).average().orElse(0);
    }

    public Series shift(int bias){
        Object[] dataCopy = new Object[data.length];
        System.arraycopy(data, 0, dataCopy, bias, data.length - bias);
        return new Series(dataCopy);
    }

    public Series set(int index, double numToSet){
        data[index] = numToSet;
        return this;
    }

    public Object get(int index){
        //todo
        return data[index];
    }

    public Object getLast(){
        return data[data.length - 1];
    }

    public Object getFirst(){
        return data[0];
    }

    public int size(){
        return size;
    }

    public Series get(int start, int end){
        Object[] subData = new Object[end - start];
        System.arraycopy(data, start, subData, 0, subData.length);
        return new Series(subData);
    }

    public Object[] toArray(){
        return data;
    }

}
