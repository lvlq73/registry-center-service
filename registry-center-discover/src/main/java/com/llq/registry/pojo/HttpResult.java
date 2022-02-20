package com.llq.registry.pojo;


import com.llq.registry.enums.ResultEnum;

import java.io.Serializable;

/**
 * @description: http 请求返回封装类
 * @author: llianqi@linewell.com
 * @since: 2021/5/14
 */
@SuppressWarnings("serial")
public class HttpResult<T> implements Serializable{

	public HttpResult(){
		
	}

	/**
	 * 是否成功
	 */
	public Boolean success = true;
	/**
	 * 状态编码
	 */
	public String code;
	/**
	 * 信息
	 */
	public String message;
	/**
	 * 返回数据结果
	 */
	public T result;

	/**
	 * 成功时返回
	 * @param t 返回的数据
	 * @param <T>
	 * @return
	 */
	public static <T> HttpResult success(T t) {
		HttpResult dataResult = new HttpResult();
		dataResult.success = true;
		dataResult.code = String.valueOf(ResultEnum.SUCCESS.getCode());
		dataResult.message = ResultEnum.SUCCESS.getMsg();
		dataResult.result = t;
		return dataResult;
	}

	/**
	 * 失败时返回
	 * @param code 失败编码
	 * @param msg 失败信息
	 * @return
	 */
	public static HttpResult error(int code, String msg) {
		return error(String.valueOf(code), msg);
	}
	public static HttpResult error(String code, String msg) {
		HttpResult dataResult = new HttpResult();
		dataResult.success = false;
		dataResult.code = code;
		dataResult.message = msg;
		return dataResult;
	}

	public static HttpResult error(String msg) {
		return error(ResultEnum.ERROR.getCode(), msg);
	}

	public static HttpResult error(ResultEnum resultEnum) {
		return error(resultEnum.getCode(), resultEnum.getMsg());
	}
}
