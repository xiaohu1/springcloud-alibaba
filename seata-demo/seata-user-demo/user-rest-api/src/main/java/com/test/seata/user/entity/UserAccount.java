package com.test.seata.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author liujian
 * @since 2020-03-07
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAccount implements Serializable {

  private static final long serialVersionUID = 1L;
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;


  private String userId;


  private Integer money;


}
