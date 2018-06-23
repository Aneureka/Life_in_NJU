package leftovers.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Hiki on 2017/6/9.
 */

@Entity
@Table(name = "algorithm")
public class Algorithm {

    @Id
    @Column(name = "algoId")
    private String algoId;

    @Column(name = "algoName")
    private String algoName;

    @Column(name = "username")
    private String username;

    @Column(name = "time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String time;

    @Column(name = "code", columnDefinition = "TEXT")
    private String code;

    @Column(name = "beginDate")
    private String beginDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "stockStartCash")
    private double stockStartCash;

    @Column(name = "benchmark")
    private String benchmark;


    public Algorithm(){

    }

    public Algorithm(String algoId, String algoName, String username, String time, String code, String beginDate, String endDate, double stockStartCash, String benchmark) {
        this.algoId = algoId;
        this.algoName = algoName;
        this.username = username;
        this.time = time;
        this.code = code;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.stockStartCash = stockStartCash;
        this.benchmark = benchmark;
    }

    public String getAlgoId() {
        return algoId;
    }

    public void setAlgoId(String algoId) {
        this.algoId = algoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getStockStartCash() {
        return stockStartCash;
    }

    public void setStockStartCash(double stockStartCash) {
        this.stockStartCash = stockStartCash;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }
}
