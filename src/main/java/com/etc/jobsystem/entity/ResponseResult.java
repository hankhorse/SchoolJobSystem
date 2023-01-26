package com.etc.jobsystem.entity;

import com.etc.jobsystem.enums.AppHttpCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter

//正确直接使用okResult
//错误直接使用errorResult  都是静态方法
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.error(code, msg);
    }
    //okresult是 成功返回
    public static ResponseResult okResult() {
        ResponseResult result = new ResponseResult();
        return result;
    }
    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(code, null, msg);
    }
    //只传入一个数据的正确返回
    public static ResponseResult okResult(Object data) {
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getMsg());
        if(data!=null) {
            result.setData(data);
        }
        return result;
    }
    //只传入一个数据的正确返回
    public static ResponseResult okResult(String msg) {
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getMsg());
        result.setMsg(msg);
        return result;
    }

    //传入自定义信息的正确返回
    public static ResponseResult okResult(Object data,String msg) {
        ResponseResult result = setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS, AppHttpCodeEnum.SUCCESS.getMsg());
        if(data!=null) {
            result.setData(data);
        }
        result.setMsg(msg);
        return result;
    }

    //不需要自己定义错误信息的错误响应
    public static ResponseResult errorResult(AppHttpCodeEnum enums){
        return setAppHttpCodeEnum(enums,enums.getMsg());
    }
    //自己定义错误信息的错误响应  okResult只是成功返回 不一定返回成功信息
    public static ResponseResult errorResult(AppHttpCodeEnum enums, String msg){
        return setAppHttpCodeEnum(enums,msg);
    }


    public static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums){
        return okResult(enums.getCode(),enums.getMsg());
    }

    private static ResponseResult setAppHttpCodeEnum(AppHttpCodeEnum enums, String msg){
        return okResult(enums.getCode(),msg);
    }

    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(T data) {
        this.data = data;
        return this;
    }




}