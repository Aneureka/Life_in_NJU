package presentation.util.candlestick;

/**
 * Created by Hitiger on 2017/3/15.
 * Description :
 */
public class Average {
    private double ma5;
    private double ma10;
    private double ma30;
    private double ma60;
    private double ma120;
    private double ma240;

    public Average (double ma5, double ma10, double ma30, double ma60, double ma120, double ma240) {
        this.ma5 = ma5;
        this.ma10 = ma10;
        this.ma30 = ma30;
        this.ma60 = ma60;
        this.ma120 = ma120;
        this.ma240 = ma240;
    }

    public double getMa5() {
        return ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public double getMa30() {
        return ma30;
    }

    public double getMa60() {
        return ma60;
    }

    public double getMa120() {
        return ma120;
    }

    public double getMa240() {
        return ma240;
    }
}
