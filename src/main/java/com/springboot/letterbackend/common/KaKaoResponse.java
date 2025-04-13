package com.springboot.letterbackend.common;

public enum KaKaoResponse {
    SIGNUP(0,"SIGNUP"),SIGNIN(-1,"SIGNIN");
    int code;
    String msg;

    KaKaoResponse(int code, String msg) {
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
