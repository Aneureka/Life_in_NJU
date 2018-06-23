package leftovers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kevin on 2017/6/7.
 */
@Entity
@Table(name = "a_board_stocks")
public class SearchStockItem {
    @Id
    @Column(name = "wind_code")
    private String code;

    @Column(name = "sec_name")
    private String name;

    @Column(name = "full_spell")
    private String pinyinFullSpell;

    @Column(name = "first_spell")
    private String pinyinFirstSpell;

    public SearchStockItem() {
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

    public String getPinyinFullSpell() {
        return pinyinFullSpell;
    }

    public void setPinyinFullSpell(String pinyinFullSpell) {
        this.pinyinFullSpell = pinyinFullSpell;
    }

    public String getPinyinFirstSpell() {
        return pinyinFirstSpell;
    }

    public void setPinyinFirstSpell(String pinyinFirstSpell) {
        this.pinyinFirstSpell = pinyinFirstSpell;
    }
}
