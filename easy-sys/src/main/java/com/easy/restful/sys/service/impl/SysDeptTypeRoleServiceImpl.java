package com.easy.restful.sys.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.restful.common.core.common.tree.Tree;
import com.easy.restful.common.core.constant.CommonConst;
import com.easy.restful.sys.dao.SysDeptTypeRoleMapper;
import com.easy.restful.sys.model.SysDeptTypeRole;
import com.easy.restful.sys.service.SysDeptTypeRoleService;
import com.easy.restful.util.ToolUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门类型可选择的角色
 *
 * @author tengchong
 * @date 2018/12/3
 */
@Service
public class SysDeptTypeRoleServiceImpl extends ServiceImpl<SysDeptTypeRoleMapper, SysDeptTypeRole> implements SysDeptTypeRoleService {

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean saveDeptTypeRole(String deptTypeId, List<String> roles) {
        ToolUtil.checkParams(deptTypeId);
        // 删除原权限
        remove(new QueryWrapper<SysDeptTypeRole>().eq("dept_type_id", deptTypeId));
        if (Validator.isNotEmpty(roles)) {
            List<SysDeptTypeRole> sysDeptTypeRoles = new ArrayList<>();
            SysDeptTypeRole sysDeptTypeRole;
            for (String roleId : roles) {
                sysDeptTypeRole = new SysDeptTypeRole();
                sysDeptTypeRole.setRoleId(roleId);
                sysDeptTypeRole.setDeptTypeId(deptTypeId);
                sysDeptTypeRoles.add(sysDeptTypeRole);
            }
            saveBatch(sysDeptTypeRoles);
        }
        return true;
    }

    @Override
    public boolean removeDeptTypeRoleByDeptTypeIds(String deptTypeIds) {
        return remove(new QueryWrapper<SysDeptTypeRole>().in("dept_type_id", deptTypeIds.split(CommonConst.SPLIT)));
    }

    @Override
    public boolean removeDeptTypeRole(String roles) {
        return remove(new QueryWrapper<SysDeptTypeRole>().in("role_id", roles.split(CommonConst.SPLIT)));
    }

    @Override
    public List<Tree> selectRoleByDept(String deptId) {
        ToolUtil.checkParams(deptId);
        return getBaseMapper().selectRoleByDept(deptId);
    }
}
