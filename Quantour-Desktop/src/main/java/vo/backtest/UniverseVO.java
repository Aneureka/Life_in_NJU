package vo.backtest;

import java.util.List;

/**
 * Created by kevin on 2017/4/16.
 */
public class UniverseVO {

    /**
     * 股票池的名称
     */
    public String name;

    /**
     * 股票池中包含的股票代码
     */
    public List<String> codes;

    public UniverseVO(String name, List<String> codes){
        this.name = name;
        this.codes = codes;
    }

}
