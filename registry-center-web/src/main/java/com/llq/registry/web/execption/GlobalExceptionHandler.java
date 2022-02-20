package com.llq.registry.web.execption;

import com.llq.registry.pojo.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局异常处理
 * @author: llianqi@linewell.com
 * @since: 2021/5/14
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理 Exception 异常
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(Exception.class)
    public HttpResult handlerException(Exception e) {
        logger.error(e.getMessage(), e);
        return HttpResult.error("系统异常");
    }

    /**
     * 处理空指针异常
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(NullPointerException.class)
    public HttpResult handlerNullPointerException(NullPointerException e) {
        logger.error(e.getMessage(), e);
        return HttpResult.error("系统异常");
    }

    /**
     * 处理自定义异常
     * @param e 异常
     * @return 处理结果
     */
    @ExceptionHandler(GlobalException.class)
    public HttpResult handlerGlobalException(GlobalException e) {
        logger.error(e.getMessage(), e);
        return HttpResult.error(e.getCode(), e.getMessage());
    }

}
