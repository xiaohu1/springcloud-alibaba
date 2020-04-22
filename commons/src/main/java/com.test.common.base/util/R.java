package com.test.common.base.util;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujian
 * @Description: 返回数据
 * @date 2018/10/19  10:50
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", HttpStatus.SC_OK);
		put("msg", "success");
	}

	public static R ok() {
		return  new R();
	}

	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器异常");
	}
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

    public static R success(Object data) {
        R r = new R();
        r.put("data",data);
        return r;
    }

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}


	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

}
