package com.llq.registry.enums;


/**
 * @description: 返回枚举信息
 * @author: llianqi@linewell.com
 * @since: 2021/5/14
 */
public enum ResultEnum {

    /**
     *  200 成功
     */

    SUCCESS(200, "请求成功"),
    ERROR(5000, "系统异常");

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
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
