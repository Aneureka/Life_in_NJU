package po.stock;

/**
 * Created by kevin on 2017/4/17.
 */
public class StockAttributesPO {

    /**
     * 股票代码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 股票所属行业
     */
    private String section;

    public StockAttributesPO(String code, String name, String section) {
        this.code = code;
        this.name = name;
        this.section = section;
    }

    public static StockAttributesPO parse(String stockAttributesItem){
        String[] stockAttributes = stockAttributesItem.split("\t");

        String code = stockAttributes[0];
        String name = stockAttributes[1];
        String section = stockAttributes[2];

        return new StockAttributesPO(code, name, section);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

}
