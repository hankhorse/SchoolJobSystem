package com.etc.jobsystem.enums.notice;

public enum MsgTypeEnum {

    ADD_COMPANY(1,"用户请求添加公司"),
    PASS_ADD_COMPANY(2,"管理员通过添加公司"),
    NOT_PASS_ADD_COMPANY(3,"管理员拒绝添加公司");


    int code;
    String msg;

    MsgTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
