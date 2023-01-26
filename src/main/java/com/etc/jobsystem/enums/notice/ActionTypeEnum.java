package com.etc.jobsystem.enums.notice;


public enum ActionTypeEnum {

    NOTICE_TYPE(0,"通知类型"),
    ACTION_TYPE(1,"操作类型");

    int code;
    String msg;

    ActionTypeEnum(int code, String msg) {
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
