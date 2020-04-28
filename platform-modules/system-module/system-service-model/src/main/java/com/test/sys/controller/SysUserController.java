package com.test.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.common.base.util.Result;
import com.test.sys.service.SysUserService;
import com.test.sys.api.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 系统用户
 *
 * @authoResult liu jian
 * @date 2019-06-26 21:03:22
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 简单分页查询
     *
     * @param map
     * @return
     */
    @PostMapping("/list")
    public Result<IPage<SysUserEntity>> getSysUserPage(@RequestBody Map<String, Object> map) {
        return Result.ok(sysUserService.getSysUserPage(map));
    }


    /**
     * 通过id查询单条记录
     *
     * @param userId
     * @return Result
     */
    @GetMapping("/info/{userId}")
    public Result<SysUserEntity> getById(@PathVariable("userId") Long userId) {

        return Result.ok(sysUserService.getById(userId));
    }


    /**
     * 新增记录
     *
     * @param sysUserEntity
     * @return R
     */
    @PostMapping
    public Result save(@RequestBody SysUserEntity sysUserEntity) {
        return Result.ok(sysUserService.save(sysUserEntity));
    }

    /**
     * 修改记录
     *
     * @param sysUserEntity
     * @return R
     */
    @PutMapping
    public Result update(@RequestBody SysUserEntity sysUserEntity) {
        return Result.ok(sysUserService.updateById(sysUserEntity));
    }

    /**
     * 通过id删除一条记录
     *
     * @param userId
     * @return R
     */
    @DeleteMapping("/{userId}")
    public Result removeById(@PathVariable Long userId) {
        return Result.ok(sysUserService.removeById(userId));
    }

}
