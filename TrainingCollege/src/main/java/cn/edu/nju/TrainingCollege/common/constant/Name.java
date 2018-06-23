package cn.edu.nju.TrainingCollege.common.constant;

/**
 * @author hiki on 2018-04-01
 */

public enum Name {

    USER("user"),

    INSTITUTE("institute"),

    MANAGER("manager"),

    USER_LOGIN_NAME("email"),

    INSTITUTE_LOGIN_NAME("instituteId"),

    MANAGER_LOGIN_NAME("managerId");

    private String name;

    Name(String name) { this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
