package constant;

/**
 * Created by Hiki on 2017/5/13.
 */
public enum Exchange {

    /**
     * 沪深
     */
    CHINA("CN"),

    /**
     * 香港
     */
    HONGKONG("HK"),

    /**
     * 美国
     */
    USA("US");

    private String exchange;

    private Exchange(String exchange){
        this.exchange = exchange;
    }

    @Override
    public String toString(){
        return exchange;
    }
}
