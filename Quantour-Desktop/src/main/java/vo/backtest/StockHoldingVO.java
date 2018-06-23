package vo.backtest;

/**
 * Created by Hiki on 2017/4/16.
 */
public class StockHoldingVO {

    /**
     * 股票代码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 持仓数量
     */
    private int num;

    /**
     * 头寸
     */
    private double position;


    public StockHoldingVO(String code, String name, int num, double position) {
        this.code = code;
        this.name = name;
        this.num = num;
        this.position = position;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }


}
