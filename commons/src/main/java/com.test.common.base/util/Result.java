
package com.test.common.base.util;

import com.test.common.base.constant.Constant;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 結果集封裝
 * @author liujian
 */
@ToString
@Accessors(chain = true)
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Integer code;

	@Getter
	@Setter
	private String msg;


	@Getter
	@Setter
	private T data;

	public static <T> Result<T> ok() {
		return restResult(null, Constant.SUCCESS, null);
	}

	public static <T> Result<T> ok(T data) {
		return restResult(data, Constant.SUCCESS, null);
	}

	public static <T> Result<T> ok(T data, String msg) {
		return restResult(data, Constant.SUCCESS, msg);
	}

	public static <T> Result<T> failed() {
		return restResult(null, Constant.FAIL, null);
	}

	/**
	 *  默认 code
	 * @param msg 自定义msg
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> failed(String msg) {
		return restResult(null, Constant.FAIL, msg);
	}

	/**
	 *
	 * @param code 自定义 code
	 * @param msg 自定义 msg
	 * @param <T>
	 * @return
	 */
	public static <T> Result<T> failed(Integer code,String msg) {
		return restResult(null, code, msg);
	}

	public static <T> Result<T> failed(T data) {
		return restResult(data, Constant.FAIL, null);
	}

	public static <T> Result<T> failed(T data, String msg) {
		return restResult(data, Constant.FAIL, msg);
	}


	public static <T> Result<T> restResult(T data, Integer code, String msg) {
		Result<T> apiResult = new Result<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}
}

