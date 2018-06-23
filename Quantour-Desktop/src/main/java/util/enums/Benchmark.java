package util.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 97257 on 2017/4/2.
 */
public enum Benchmark {

    /**
     * 沪深300
     */
    HS300("沪深300"),


    /**
     * 中小板指数
     */
    SSC("中小板指数"),



    /**
     * 创业板指数
     */
    GEI("创业板指数");


    private String benchmarkString;

    private Benchmark(String benchmarkString){
        this.benchmarkString = benchmarkString;
    }


    @Override
    public String toString(){
        return String.valueOf(this.benchmarkString);
    }


    public static Enum<Benchmark> getEnum(String benchmarkString){
        Map<String, Enum<Benchmark>> stringEnum = new HashMap<>();
        stringEnum.put("沪深300", HS300);
        stringEnum.put("中小板指数", SSC);
        stringEnum.put("创业板指数", GEI);

        return stringEnum.get(benchmarkString);
    }

}
