
package com.test.common.base.util;

import java.util.HashMap;


/**
 * Map工具类
 *
 * @author liujain
 * @since 2.0.0
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
