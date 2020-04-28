package com.test.sys.rpc;

import com.test.sys.mapper.SysRoleMapper;
import com.test.sys.mapper.SysRoleMenuMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author liujian
 * @ProjectName sy
 * @Description: SysRoleRest
 * @date 2019/4/1  14:46
 */
@RestController
@RequestMapping("/rpc/role")
public class SysRoleRpc {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @PostMapping("/queryMenuIdListByRoleIds")
    List<String> queryMenuIdListByRoleIds(@RequestParam("roleIds") List<Long> roleIds) {
        return sysRoleMenuMapper.queryMenuIdListByRoleIds(roleIds);
    }


}
