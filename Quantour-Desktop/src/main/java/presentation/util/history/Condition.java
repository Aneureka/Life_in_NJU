package presentation.util.history;

import java.time.LocalDate;

/**
 * Created by Hitiger on 2017/4/18.
 * Description : 回测条件
 */
public class Condition {

    private String poolKind;
    private String[] pool;
    private String type;
    private int formdays;
    private int holddays;
    private double amount;
    private LocalDate start;
    private LocalDate end;
    private String standard;

    public Condition(String poolKind, String[] pool, String type, int formdays, int holddays, double amount, LocalDate start, LocalDate end, String standard) {
        this.poolKind = poolKind;
        this.pool = pool;
        this.type = type;
        this.formdays = formdays;
        this.holddays = holddays;
        this.amount = amount;
        this.start = start;
        this.end = end;
        this.standard = standard;
    }

    public String getPoolKind() {
        return poolKind;
    }

    public String[] getPool() {
        return pool;
    }

    public String getType() {
        return type;
    }

    public int getFormdays() {
        return formdays;
    }

    public int getHolddays() {
        return holddays;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getStandard() {
        return standard;
    }
}
