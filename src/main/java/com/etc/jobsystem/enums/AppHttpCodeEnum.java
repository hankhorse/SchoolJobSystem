package com.etc.jobsystem.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    LOSS_LOGIN(401,"账号或密码错误"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    TOKEN_ERRO(407,"token错误"),
    COMPANYCODE_ERRO(408,"序列号错误"),
    User_ERRO(409,"用户错误"),
    SEND_EXISTS(410,"已经投递过此公司"),
    SYSTEM_ERROR(500,"出现错误");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}