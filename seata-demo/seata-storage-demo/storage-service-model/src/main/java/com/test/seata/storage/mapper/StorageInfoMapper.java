package com.test.seata.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.seata.storage.entity.StorageInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author liujian
 * @since 2020-03-07
 */
@Mapper
public interface StorageInfoMapper extends BaseMapper<StorageInfo> {

}
