
package com.test.sys.controller;

import com.test.common.base.util.Result;
import com.test.sys.service.SysOssService;
import com.test.sys.api.entity.SysOssEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 文件上传
 *
 * @authoResult liu jian
 * @date 2019-06-26 21:03:22
 */
@RestController
@RequestMapping("/oss")
public class SysOssController {

    @Autowired
    private SysOssService sysOssService;

    /**
     * 通过id查询单条记录
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public Result<SysOssEntity> getById(@PathVariable("id") Long id) {
        return Result.ok(sysOssService.getById(id));
    }


    /**
     * 新增记录
     *
     * @param sysOssEntity
     * @return R
     */
    @PostMapping
    public Result save(@RequestBody SysOssEntity sysOssEntity) {
        return Result.ok(sysOssService.save(sysOssEntity));
    }

    /**
     * 修改记录
     *
     * @param sysOssEntity
     * @return R
     */
    @PutMapping
    public Result update(@RequestBody SysOssEntity sysOssEntity) {
        return Result.ok(sysOssService.updateById(sysOssEntity));
    }

    /**
     * 通过id删除一条记录
     *
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public Result removeById(@PathVariable Long id) {
        return Result.ok(sysOssService.removeById(id));
    }


}
