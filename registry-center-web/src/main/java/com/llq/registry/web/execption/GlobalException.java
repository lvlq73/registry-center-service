package com.llq.registry.web.execption;

import com.llq.registry.enums.ResultEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @description: 全局异常
 * @author: llianqi@linewell.com
 * @since: 2021/5/14
 */
@Getter
public class GlobalException extends RuntimeException{
    /**
     * 保存异常信息
     */
    private String message;

    /**
     * 保存响应状态码
     */
    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 默认构造方法，根据异常信息 构建一个异常实例对象
     * @param message 异常信息
     */
    public GlobalException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 根据异常信息、响应状态码构建 一个异常实例对象
     * @param message 异常信息
     * @param code 响应状态码
     */
    public GlobalException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    /**
     * 根据异常信息，异常对象构建 一个异常实例对象
     * @param message 异常信息
     * @param e 异常对象
     */
    public GlobalException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    /**
     * 根据异常信息，响应状态码，异常对象构建 一个异常实例对象
     * @param message 异常信息
     * @param code 响应状态码
     * @param e 异常对象
     */
    public GlobalException(String message, int code, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
    }

    public GlobalException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.message = resultEnum.getMsg();
        this.code = resultEnum.getCode();
    }

    public GlobalException(ResultEnum resultEnum, Throwable e) {
        super(resultEnum.getMsg(), e);
        this.message = resultEnum.getMsg() + e.getMessage();
        this.code = resultEnum.getCode();
    }

    public GlobalException(String title, ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.message = title + "," + resultEnum.getMsg();
        this.code = resultEnum.getCode();
    }

    public GlobalException(ResultEnum resultEnum, String message) {
        super(resultEnum.getMsg());
        this.message = resultEnum.getMsg() + "," + message;
        this.code = resultEnum.getCode();
    }
}
