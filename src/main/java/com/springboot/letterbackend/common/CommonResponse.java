package com.springboot.letterbackend.common;

public enum CommonResponse {
    SUCCESS(0,"Success"),FAIL(-1,"Fail"),PENDING(1,"Pending"),DUPLICATED(2,"User is already exsited.");
    int code;
    String msg;

    CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
