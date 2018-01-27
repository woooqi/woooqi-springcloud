package com.woooqi.springcloud.entity;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

    private int code; //>0 正确 ，0< 错误
    private List<T> datas = new ArrayList<T>(); //结果集
    private String message;//提示信息
    private int total; //结果集总数

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mssage) {
        this.message = mssage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
