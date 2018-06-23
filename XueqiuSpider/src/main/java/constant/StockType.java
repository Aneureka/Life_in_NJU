package constant;

/**
 * Created by Hiki on 2017/5/14.
 */
public enum StockType {

    /**
     * 沪A
     */
    SH_A("sha"),

    /**
     * 沪B
     */
    SH_B("shb"),

    /**
     * 深A
     */
    SZ_A("sza"),

    /**
     * 深B
     */
    SZ_B("szb"),

    /**
     * 创业板
     */
    SECOND_BOARD("cyb"),

    /**
     * 中小板
     */
    SME_BOARD("zxb");

    private String type;

    private StockType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}
