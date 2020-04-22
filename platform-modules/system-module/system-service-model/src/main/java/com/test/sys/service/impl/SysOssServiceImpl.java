package com.test.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.sys.mapper.SysOssMapper;
import com.test.sys.service.SysOssService;
import com.test.sys.api.entity.SysOssEntity;
import org.springframework.stereotype.Service;

/**
 * 文件上传
 *
 * @author liu jian
 * @date 2019-06-26 21:03:22
 */
@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssMapper, SysOssEntity> implements SysOssService {


}
