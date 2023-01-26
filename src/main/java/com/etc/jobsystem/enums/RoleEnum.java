package com.etc.jobsystem.enums;

public enum  RoleEnum {
    NONE_ROLE("-1","没有角色"),
    USER_ROLE("0","用户角色"),
    HR_ROLE("1","HR角色"),
    COMPANY_ROLE("2","公司角色"),
    SEND_COMPANY_ROLE("3","填写了申请的公司角色"),
    NO_SEND_COMPANY_ROLE("4","未填写了申请的公司角色"),
    ;

    String code;
    String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    RoleEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
