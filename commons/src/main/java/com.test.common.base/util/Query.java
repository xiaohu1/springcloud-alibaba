
package com.test.common.base.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author liujian
 * @since 2.0.0 2018-11-14
 */
public class Query<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int pageSize = 10;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        if(params.get("page") != null && !String.valueOf(params.get("page")).equalsIgnoreCase("0")){
            currPage = Integer.parseInt(params.get("page").toString());
        }
        if(params.get("pageSize") != null && !String.valueOf(params.get("pageSize")).equalsIgnoreCase("0")){
            pageSize = Integer.parseInt(params.get("pageSize").toString());
        }

        this.put("offset", (currPage - 1) * pageSize);
        this.put("page", currPage);
        this.put("pageSize", pageSize);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
//        String sidx = SQLFilter.sqlInject(params.get("sidx").toString());
//        String order = SQLFilter.sqlInject(params.get("order").toString());
//        this.put("sidx", sidx);
//        this.put("order", order);

        //mybatis-plus分页
        this.page = new Page<>(currPage, pageSize);

        //排序
//        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
//            this.page.setOrderByField(sidx);
//            this.page.setAsc("ASC".equalsIgnoreCase(order));
//        }

    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getPageSize() {
        return pageSize;
    }
}
