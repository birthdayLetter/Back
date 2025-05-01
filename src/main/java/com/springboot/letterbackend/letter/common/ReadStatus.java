package com.springboot.letterbackend.letter.common;

public enum ReadStatus {
    OPENED(0,"이미 읽은 편지 입니다"),COLSED(-1,"아직 읽지 않은 편지 입니다.");
    int code;
    String msg;

    ReadStatus(int code, String msg) {
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
