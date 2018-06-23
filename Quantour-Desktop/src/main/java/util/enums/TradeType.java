package util.enums;

/**
 * Created by 97257 on 2017/4/4.
 */
public enum TradeType {

    Buy("买入"),

    Hold("持有"),

    Sell("卖出");

    private String tradeString;

    private TradeType(String tradeString){
        this.tradeString = tradeString;
    }

    @Override
    public String toString(){
        return String.valueOf(this.tradeString);
    }
}
