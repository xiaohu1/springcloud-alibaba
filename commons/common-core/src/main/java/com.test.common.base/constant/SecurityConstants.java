package com.test.common.base.constant;

/**
 * @author liujian
 * 权限验证标识
 */
public interface SecurityConstants {


	/**
	 * 手机号登录URL
	 */
	String MOBILE_TOKEN_URL = "/mobile/token";

	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";


	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

	/**
	 * 检查access_token
	 */
	String OAUTH_CHECK_TOKEN ="http://localhost:6016/oauth/check_token";

	/**
	 * 标志
	 */
	String FROM = "from";


//	/**
//	 * 微信获取OPENID
//	 */
//	String WX_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
//		"?appid=%s&secret=%s&code=%s&grant_type=authorization_code";


}
