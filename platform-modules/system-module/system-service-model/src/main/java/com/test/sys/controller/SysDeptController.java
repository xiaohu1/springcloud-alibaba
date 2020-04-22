
package com.test.sys.controller;

import com.test.common.base.util.Result;
import com.test.sys.service.SysDeptService;
import com.test.sys.api.entity.SysDeptEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 部门管理
 *
 * @authoResult liu jian
 * @date 2019-06-26 21:03:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
public class SysDeptController {

    private final SysDeptService sysDeptService;


    /**
     * 导航菜单
     */
    @PostMapping("/list")
    public Result<List<SysDeptEntity>> list(SysDeptEntity sysDeptEntity) {
        return new Result<>(sysDeptService.getDeptList(sysDeptEntity));
    }


    /**
     * 通过id查询单条记录
     *
     * @param deptId
     * @return R
     */
    @GetMapping("/{deptId}")
    public Result<SysDeptEntity> getById(@PathVariable("deptId") Long deptId) {
        return new Result<>(sysDeptService.getById(deptId));
    }


    /**
     * 新增记录
     *
     * @param sysDeptEntity
     * @return R
     */
    @PostMapping
    public Result save(@RequestBody SysDeptEntity sysDeptEntity) {
        return new Result<>(sysDeptService.save(sysDeptEntity));
    }

    /**
     * 修改记录
     *
     * @param sysDeptEntity
     * @return R
     */
    @PutMapping
    public Result update(@RequestBody SysDeptEntity sysDeptEntity) {
        return new Result<>(sysDeptService.updateById(sysDeptEntity));
    }

    /**
     * 通过id删除一条记录
     *
     * @param deptId
     * @return R
     */
    @DeleteMapping("/{deptId}")
    public Result removeById(@PathVariable Long deptId) {
        return new Result<>(sysDeptService.removeById(deptId));
    }


}
