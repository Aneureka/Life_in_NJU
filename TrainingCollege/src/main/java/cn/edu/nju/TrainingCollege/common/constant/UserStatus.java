package cn.edu.nju.TrainingCollege.common.constant;

/**
 * @author hiki on 2018-01-30
 */

public enum UserStatus {

    INACTIVATED("未激活"),

    ACTIVATED("激活"),

    DISQUALIFIED("取消资格");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
