
package com.test.common.base.util;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 响应信息主体
 *
 * @param <T>
 * @author liujian
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS_CODE = 200;
	public static final String SUCCESS_MSG = "success";
	public static final int SERVER_ERROR = 500;

	@Getter
	@Setter
	private int code;

	@Getter
	@Setter
	private String msg;

	@Getter
	@Setter
	private T data;

	public Result() {
		this.code = SUCCESS_CODE;
		this.msg = SUCCESS_MSG;
	}

	public Result(T data) {
		super();
		this.data = data;
	}

	public Result(T data, String msg) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public Result(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = SERVER_ERROR;
	}
}
