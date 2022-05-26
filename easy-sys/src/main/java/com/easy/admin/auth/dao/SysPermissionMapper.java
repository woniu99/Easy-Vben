package com.easy.admin.auth.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easy.admin.common.core.common.tree.Tree;
import com.easy.admin.auth.model.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单/权限
 * @author TengChongChong
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 查询
     *
     * @param queryWrapper 查询条件
     * @return List<SysPermission>
     */
    List<SysPermission> select(@Param("ew") QueryWrapper<SysPermission> queryWrapper);

    /**
     * 获取所有数据
     * @param status 状态
     * @return List<JsTree>
     */
    List<Tree> selectAll(@Param("status") String status);

    /**
     * 获取详情信息
     *
     * @param id 权限id
     * @return SysPermissions
     */
    SysPermission get(@Param("id") String id);

    /**
     * 获取最大排序值
     *
     * @param id 父Id
     * @return Integer
     */
    Integer getMaxOrderNo(@Param("parentId") String id);
}