
package com.test.sys.controller;

import com.test.common.base.util.Result;
import com.test.sys.service.SysOssService;
import com.test.sys.api.entity.SysOssEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 文件上传
 *
 * @authoResult liu jian
 * @date 2019-06-26 21:03:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oss")
public class SysOssController {

    private final SysOssService sysOssService;

    /**
     * 通过id查询单条记录
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public Result<SysOssEntity> getById(@PathVariable("id") Long id) {
        return new Result<>(sysOssService.getById(id));
    }


    /**
     * 新增记录
     *
     * @param sysOssEntity
     * @return R
     */
    @PostMapping
    public Result save(@RequestBody SysOssEntity sysOssEntity) {
        return new Result<>(sysOssService.save(sysOssEntity));
    }

    /**
     * 修改记录
     *
     * @param sysOssEntity
     * @return R
     */
    @PutMapping
    public Result update(@RequestBody SysOssEntity sysOssEntity) {
        return new Result<>(sysOssService.updateById(sysOssEntity));
    }

    /**
     * 通过id删除一条记录
     *
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public Result removeById(@PathVariable Long id) {
        return new Result<>(sysOssService.removeById(id));
    }


}
