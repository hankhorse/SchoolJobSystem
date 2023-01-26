package com.etc.jobsystem.enums.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public enum  IsReadEnum {

    IS_READ(0,"已读"),
    NOT_READ(1,"未读");


    int code;
    String msg;

    IsReadEnum(int code, String msg) {
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
