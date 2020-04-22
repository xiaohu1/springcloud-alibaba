package com.test.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.common.base.util.Result;
import com.test.sys.feign.UserOrder;
import com.test.sys.service.SysUserService;
import com.test.sys.api.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 系统用户
 *
 * @authoResult liu jian
 * @date 2019-06-26 21:03:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class SysUserController {

    private final SysUserService sysUserService;

    private final UserOrder userOrder;

    /**
     * 简单分页查询
     *
     * @param map
     * @return
     */
    @PostMapping("/list")
    public Result<IPage<SysUserEntity>> getSysUserPage(@RequestBody Map<String, Object> map) {
        return new Result<>(sysUserService.getSysUserPage(map));
    }


    /**
     * 通过id查询单条记录
     *
     * @param userId
     * @return Result
     */
    @GetMapping("/info/{userId}")
    public Result<SysUserEntity> getById(@PathVariable("userId") Long userId) {

        return new Result<>(sysUserService.getById(userId));
    }


    /**
     * 新增记录
     *
     * @param sysUserEntity
     * @return R
     */
    @PostMapping
    public Result save(@RequestBody SysUserEntity sysUserEntity) {
        return new Result<>(sysUserService.save(sysUserEntity));
    }

    /**
     * 修改记录
     *
     * @param sysUserEntity
     * @return R
     */
    @PutMapping
    public Result update(@RequestBody SysUserEntity sysUserEntity) {
        return new Result<>(sysUserService.updateById(sysUserEntity));
    }

    /**
     * 通过id删除一条记录
     *
     * @param userId
     * @return R
     */
    @DeleteMapping("/{userId}")
    public Result removeById(@PathVariable Long userId) {
        return new Result<>(sysUserService.removeById(userId));
    }

}
