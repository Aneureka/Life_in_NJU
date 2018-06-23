package vo.stock;

/**
 * Created by kevin on 2017/4/17.
 */
public class StockAttributesVO {

    /**
     * 股票代码
     */
    public String code;

    /**
     * 股票名称
     */
    public String name;

    /**
     * 股票所属行业
     */
    public String section;

    public StockAttributesVO(String code, String name, String section) {
        this.code = code;
        this.name = name;
        this.section = section;
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
