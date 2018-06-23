package leftovers.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by kevin on 2017/5/17.
 */
@Entity
@Table(name = "market_snapshot")
public class StockCurInfo {
    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "SEC_NAME")
    private String name;

    @Column(name = "RT_LAST")
    private double curPrice;

    @Column(name = "RT_PCT_CHG")
    private double changePercent;

    @Column(name = "RT_CHG")
    private double change;

    @Column(name = "RT_TURN")
    private double turnRate;

    @Column(name = "RT_VOL_RATIO")
    private double volRatio;

    @Column(name = "RT_SWING")
    private double swing;

    @Column(name = "RT_AMT")
    private double amount;

    @Column(name = "RT_FLOAT_MKT_CAP")
    private double floatMarketCap;

    @Column(name = "RT_PE_TTM")
    private double peTTM;

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stockCurInfo")
//    @JsonManagedReference
//    private Collection<Board> boards = new ArrayList<>(0);

    public StockCurInfo() {
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

    public double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(double curPrice) {
        this.curPrice = curPrice;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(double turnRate) {
        this.turnRate = turnRate;
    }

    public double getVolRatio() {
        return volRatio;
    }

    public void setVolRatio(double volRatio) {
        this.volRatio = volRatio;
    }

    public double getSwing() {
        return swing;
    }

    public void setSwing(double swing) {
        this.swing = swing;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFloatMarketCap() {
        return floatMarketCap;
    }

    public void setFloatMarketCap(double floatMarketCap) {
        this.floatMarketCap = floatMarketCap;
    }

    public double getPeTTM() {
        return peTTM;
    }

    public void setPeTTM(double peTTM) {
        this.peTTM = peTTM;
    }

//    public Collection<Board> getBoards() {
//        return boards;
//    }
//
//    public void setBoards(Collection<Board> boards) {
//        this.boards = boards;
//    }
}
