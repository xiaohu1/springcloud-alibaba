package com.test.seata.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.seata.storage.entity.StorageInfo;
import com.test.seata.storage.mapper.StorageInfoMapper;
import com.test.seata.storage.service.IStorageInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2020-03-07
 */
@Service
public class StorageInfoServiceImpl extends ServiceImpl<StorageInfoMapper, StorageInfo> implements
        IStorageInfoService {

}
