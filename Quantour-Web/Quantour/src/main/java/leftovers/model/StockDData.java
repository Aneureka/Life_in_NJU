package leftovers.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * Created by kevin on 2017/5/11.
 */
@Entity
@Table(name = "stock_1d_data")
public class StockDData implements Serializable{
    @EmbeddedId
    private StockDDataPK pk;

    @Column(name = "OPEN")
    private Double open;

    @Column(name = "HIGH")
    private Double high;

    @Column(name = "LOW")
    private Double low;

    @Column(name = "CLOSE")
    private Double close;

    @Column(name = "VOLUME")
    private Double volume;

    @Column(name = "AMT")
    private Double amount;

    @Column(name = "PCT_CHG")
    private Double changePercentage;

    public StockDData() {
    }

    public StockDData(StockDDataPK pk, Double open, Double high, Double low, Double close, Double volume, Double amount, Double changePercentage) {
        this.pk = pk;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.amount = amount;
        this.changePercentage = changePercentage;
    }

    public StockDDataPK getPk() {
        return pk;
    }

    public void setPk(StockDDataPK pk) {
        this.pk = pk;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(Double changePercentage) {
        this.changePercentage = changePercentage;
    }

    @Embeddable
    public static class StockDDataPK implements Serializable {
        @Column(name = "CODE", nullable = false)
        private String code;

        @Column(name = "DateTime", nullable = false)
        private LocalDate date;

        public StockDDataPK(){}

        public StockDDataPK(String code, LocalDate date) {
            this.code = code;
            this.date = date;
        }

        @Override
        public boolean equals(Object o){
            if (o instanceof StockDDataPK){
                StockDDataPK codeAndDate = (StockDDataPK)o;
                return code.equals(code) && date.isEqual(date);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCode(), getDate());
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }
}
