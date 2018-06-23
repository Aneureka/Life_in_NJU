package constant;

/**
 * Created by Hiki on 2017/5/13.
 */
public enum OrderFactor {

    /**
     * 涨跌幅
     */
    PERCENT("percent"),

    /**
     * 成交额
     */
    AMOUNT("amount"),

    /**
     * 成交量
     */
    VOLUMN("volumn");

    private String factor;

    private OrderFactor(String factor){
        this.factor = factor;
    }

    @Override
    public String toString(){
        return factor;
    }
}
