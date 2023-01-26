package com.etc.jobsystem.enums.notice;


public enum ResultEnum {

    NONE_OPTION(-1,"未操作此消息"),
    PASS(0,"通过"),
    NONE_PASS(1,"未通过");

     int code;
    String msg;

    ResultEnum(int code, String msg) {
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
