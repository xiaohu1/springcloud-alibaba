
package com.test.sys.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文件上传
 *
 * @author liu jian
 * @date 2019-06-26 18:55:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oss")
public class SysOssEntity extends Model<SysOssEntity> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * URL地址
     */
    private String url;
    /**
     * 创建时间
     */
    private LocalDateTime createDate;
    /**
     * 创建人
     */
    private String userId;
    /**
     * 高
     */
    private Integer high;
    /**
     * 宽
     */
    private Integer width;

}
