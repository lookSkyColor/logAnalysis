package com.example.demo.enums;

public enum ResultCodeEnum {

    SUCCESS(0,"success"),

    ERROR(1,"error"),

    SESSIONINVALID(2,"登录失效,请重新授权登录"),

    GROUPPURCHASEUNABLE(3,""),

    WAITDRUGSTORE(4,""),

    INTERNAL_SERVER_ERROR(5,"服务器开小差，请稍后重试"),

    BIZ_UNBIND_PHONE(300001,"用户未绑定手机号");

    private int code;

    private String msg;

    ResultCodeEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
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
