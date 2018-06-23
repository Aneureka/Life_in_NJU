package cn.edu.nju.TrainingCollege.common.constant;

/**
 * @author hiki on 2018-01-29
 */

public enum OrderStatus {

    UNPAID("未付款"),

    PAID("已付款"),

    PAID_UNASSIGNED("未配班"),

    PAID_ASSIGNED("已配班"),

    CANCEL("已取消"),

    ASSIGN_FAILED("配班失败"),

    WITHDRAWN("已退订");     // before the opening time

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
