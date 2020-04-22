
package com.test.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.common.base.util.Result;
import com.test.sys.service.SysMenuService;
import com.test.sys.api.entity.SysMenuEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 菜单管理
 *
 * @authoResult liu jian
 * @date 2019-06-26 21:03:21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 导航菜单
     */
//    @PostMapping("/nav")
//    public R nav() {
//        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(SecurityUtils.getUser().getUserId());
//        Set<String> permissions = sysMenuService.getUserPermissions(SecurityUtils.getUser().getUserId());
//        Map<String, Object> map = new HashMap<>();
//        map.put("menuList", menuList);
//        map.put("permissions", permissions);
//        return R.ok(map);
//    }

    /**
     * 所有菜单列表
     */
    @PostMapping("/list")
    public Result<List<SysMenuEntity>> list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = sysMenuService.getOne(
                    new QueryWrapper<SysMenuEntity>().eq("parent_id", sysMenuEntity.getParentId()));
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }
        return new Result<>(menuList);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @PostMapping("/select")
    public Result<List<SysMenuEntity>> select() {
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.list(
                new QueryWrapper<SysMenuEntity>().ne("type", 2).orderByAsc("order_num"));

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId((long) 0);
        root.setName("根节点");
        root.setParentId((long) -1);
        root.setOpen(true);
        menuList.add(root);
        return new Result<>(menuList);
    }


    /**
     * 通过id查询单条记录
     *
     * @param menuId
     * @return R
     */
    @GetMapping("/{menuId}")
    public Result<SysMenuEntity> getById(@PathVariable("menuId") Long menuId) {
        return new Result<>(sysMenuService.getById(menuId));
    }


    /**
     * 新增记录
     *
     * @param sysMenuEntity
     * @return R
     */
    @PostMapping("save")
    public Result save(@RequestBody SysMenuEntity sysMenuEntity) {
        return new Result<>(sysMenuService.save(sysMenuEntity));
    }

    /**
     * 修改记录
     *
     * @param sysMenuEntity
     * @return R
     */
    @PutMapping("update")
    public Result update(@RequestBody SysMenuEntity sysMenuEntity) {
        return new Result<>(sysMenuService.updateById(sysMenuEntity));
    }

    /**
     * 通过id删除一条记录
     *
     * @param menuId
     * @return Result
     */
    @DeleteMapping("/{menuId}")
    public Result removeById(@PathVariable Long menuId) {
        return new Result<>(sysMenuService.removeById(menuId));
    }


}
