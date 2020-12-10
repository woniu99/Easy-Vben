package com.easy.restful.sys.service;

import com.easy.restful.common.core.common.pagination.Page;
import com.easy.restful.common.core.common.select.Select;
import com.easy.restful.sys.model.SysDict;

import java.util.List;

/**
 * 字典
 *
 * @author tengchong
 * @date 2018/11/4
 */
public interface SysDictService {
    /**
     * 列表
     *
     * @param sysDict 查询条件
     * @param page    分页
     * @return Page<SysDict>
     */
    Page<SysDict> select(SysDict sysDict, Page<SysDict> page);

    /**
     * 根据字典类型获取字典
     *
     * @param dictType 字典类型
     * @return List<Select>
     */
    List<Select> selectByDictType(String dictType);

    /**
     * 根据字典类型获取字典
     *
     * @param dictTypes 字典类型
     * @return List<SysDict>
     */
    List<SysDict> selectDictType(List<String> dictTypes);

    /**
     * 详情
     *
     * @param id 字典id
     * @return SysDict
     */
    SysDict get(String id);

    /**
     * 根据编码查询字典信息
     *
     * @param type 类型
     * @param code 编码
     * @return SysDict
     */
    SysDict getDictByCode(String type, String code);

    /**
     * 新增
     *
     * @param pId      上级id
     * @param dictType 字典类型
     * @return SysDict
     */
    SysDict add(String pId, String dictType);

    /**
     * 删除
     *
     * @param ids 字典ids
     * @return true/false
     */
    boolean remove(String ids);

    /**
     * 保存
     *
     * @param object 表单内容
     * @return SysDict
     */
    SysDict saveData(SysDict object);

    /**
     * 获取字典类型
     *
     * @return List<Select>
     */
    List<Select> selectDictType();

    /**
     * 将数据库中字典数据生成成js文件
     *
     * @return true/false
     */
    boolean generateDictData();

}
