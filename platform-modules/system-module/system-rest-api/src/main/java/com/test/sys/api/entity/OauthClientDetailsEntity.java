
package com.test.sys.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liu jian
 * @date 2019-06-26 18:55:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oauth_client_details")
public class OauthClientDetailsEntity extends Model<OauthClientDetailsEntity> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String clientId;
    /**
     *
     */
    private String resourceIds;
    /**
     *
     */
    private String clientSecret;
    /**
     *
     */
    private String scope;
    /**
     *
     */
    private String authorizedGrantTypes;
    /**
     *
     */
    private String webServerRedirectUri;
    /**
     *
     */
    private String authorities;
    /**
     *
     */
    private Integer accessTokenValidity;
    /**
     *
     */
    private Integer refreshTokenValidity;
    /**
     *
     */
    private String additionalInformation;
    /**
     *
     */
    private String autoapprove;

}
