package po.backtest;

import java.util.List;

/**
 * Created by kevin on 2017/4/16.
 */
public class UniversePO {

    /**
     * 股票池的名称
     */
    private String name;

    /**
     * 股票池里包含的股票代码
     */
    private List<String> codes;

    public UniversePO(String name, List<String> codes){
        this.name = name;
        this.codes = codes;
    }

    public String getName(){
        return name;
    }

    public List<String> getCodes(){
        return this.codes;
    }

}
