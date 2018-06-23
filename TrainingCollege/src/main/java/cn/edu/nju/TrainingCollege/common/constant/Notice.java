package cn.edu.nju.TrainingCollege.common.constant;

/**
 * @author hiki on 2018-04-01
 */

public enum Notice {

    USER_NOT_EXIST("该用户不存在！"),

    USER_INACTIVATED("该用户未激活！已经重新发送激活邮件。"),

    USER_DISQUALIFIED("该用户已注销！"),

    WRONG_PASSWORD("密码错误！"),

    LOGIN_SUCCESS("登录成功！"),

    REGISTER_SUCCESS("注册成功！请查收激活邮件！"),

    INSTITUTE_REGISTER_SUCCESS("您的机构注册申请已提交，请等待邮件通知，我们将尽快处理！"),

    EMAIL_ALREADY_EXISTED("注册邮箱已存在，换个邮箱注册吧！"),

    INVALID_TOKEN("激活链接不存在！"),

    EXPIRED_TOKEN("激活链接已过期！"),

    ACTIVATE_SUCCESS("激活成功！"),

    EMPTY_LIST("列表为空！"),

    QUERY_SUCCESS("查询成功！"),

    ORDER_SUCCESS("预订成功!"),

    CLASS_ASSIGN_FAILED("配班失败，你可以尝试选择其他班级或干脆不选择班级~"),

    PLAN_NOT_EXPIRED("课程还未结束，无法结算！"),

    BALANCE_INSUFFICIENT("余额不足！"),

    INSTITUTE_MODIFY_SUCCESS("您的机构信息修改申请已提交，请等待邮件通知，我们将尽快处理！");

    private String notice;

    Notice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return this.notice;
    }

}
