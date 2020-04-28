
package com.test.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.common.base.util.Result;
import com.test.sys.service.SysRoleService;
import com.test.sys.api.entity.SysRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 角色
 *
 * @authoResult liu jian
 * @date 2019-06-26 21:03:22
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 简单分页查询
     *
     * @param params 对象
     * @return
     */
    @PostMapping("/page")
    public Result<IPage<SysRoleEntity>> getSysRolePage(Map<String, Object> params) {
        return Result.ok(sysRoleService.getSysRolePage(params));
    }


    /**
     * 通过id查询单条记录
     *
     * @param roleId
     * @return R
     */
    @GetMapping("/{roleId}")
    public Result<SysRoleEntity> getById(@PathVariable("roleId") Long roleId) {
        return Result.ok(sysRoleService.getById(roleId));
    }


    /**
     * 新增记录
     *
     * @param sysRoleEntity
     * @return R
     */
    @PostMapping
    public Result save(@RequestBody SysRoleEntity sysRoleEntity) {
        return Result.ok(sysRoleService.save(sysRoleEntity));
    }

    /**
     * 修改记录
     *
     * @param sysRoleEntity
     * @return R
     */
    @PutMapping
    public Result update(@RequestBody SysRoleEntity sysRoleEntity) {
        return Result.ok(sysRoleService.updateById(sysRoleEntity));
    }

    /**
     * 通过id删除一条记录
     *
     * @param roleId
     * @return R
     */
    @DeleteMapping("/{roleId}")
    public Result removeById(@PathVariable Long roleId) {
        return Result.ok(sysRoleService.removeById(roleId));
    }


}
