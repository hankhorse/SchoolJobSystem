package com.etc.jobsystem.entity;


import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class JsonResult<T> {

    public static final int SUCCESS=0;

    private int[][] a;
    private String[] stringArray;
    private int state;
    private String message = "";
    private long count;
    private HashMap map;
    private T data;
    private List<T> datas;


    public JsonResult(long count, HashMap map) {
        this.count = count;
        this.map = map;
    }

    public JsonResult(String[] stringArray, List<T> datas) {
        this.stringArray = stringArray;
        this.datas = datas;
    }

    public JsonResult(long count, List<T> datas) {
        this.count = count;
        this.datas = datas;
    }



    //处理字符串数据
    public JsonResult(String message) {  //正常情况
        this.state = SUCCESS;
        this.message = message;
    }
    public JsonResult(int state, String message) {  //异常情况
        this.state = state;
        this.message = message;
    }

    //处理对象数据
    public JsonResult(T data) {
        this.state = SUCCESS;
        this.data = data;
    }
    public JsonResult(String message, T data) {
        this.state = SUCCESS;
        this.message = message;
        this.data = data;
    }
    public JsonResult(int state, String message, T data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    //处理集合数据
    public JsonResult(List<T> datas) {
        this.state = SUCCESS;
        this.datas = datas;
    }
    public JsonResult(String message, List<T> datas) {
        this.state = SUCCESS;
        this.message = message;
        this.datas = datas;
    }
    public JsonResult(int state, String message, List<T> datas) {
        this.state = state;
        this.message = message;
        this.datas = datas;
    }



}
