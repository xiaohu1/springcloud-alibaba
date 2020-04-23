package com.test.common.base.util;

import org.apache.commons.codec.binary.Base64;

/**
 * @author liujian
 * @Description: OauthUtils
 * @date 2019/7/5  9:43
 */
public class OauthUtils {

    public static String authHeaders(){
        String base64Source = "client_id" + ":" + "secret";
        return "Basic " + Base64.encodeBase64String(base64Source.getBytes());
    }

    public static void main(String[] args) {
        System.out.println("11111111:  "+ authHeaders());
    }

}
