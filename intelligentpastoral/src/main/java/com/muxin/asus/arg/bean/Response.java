package com.muxin.asus.arg.bean;

public class Response<T> {

    public String code;
    public String message;
    public T object;


    public boolean isSuccess() {
        return code.equals(Constants.OK);
    }
}
