package com.easy.restful.sample.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.restful.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import com.easy.restful.common.core.common.pagination.Page;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.easy.restful.sys.service.ImportService;
import com.easy.restful.util.office.ExcelUtil;
import com.easy.restful.sample.model.SampleGeneral;
import com.easy.restful.sample.service.SampleGeneralService;
import com.easy.restful.sample.dao.SampleGeneralMapper;

/**
 * 代码生成示例
 *
 * @author 系统管理员
 * @date 2021-02-23
 */
@Service
public class SampleGeneralServiceImpl extends ServiceImpl<SampleGeneralMapper, SampleGeneral> implements SampleGeneralService ,ImportService {

    /**
     * 列表
     * @param object 查询条
     * @param page   分页
     * @return Page<SampleGeneral>
     */
    @Override
    public Page<SampleGeneral> select(SampleGeneral object, Page<SampleGeneral> page) {
        QueryWrapper<SampleGeneral> queryWrapper = new QueryWrapper<>();
        if(object != null){
            // 查询条件
            // 姓名
            if (Validator.isNotEmpty(object.getName())) {
                queryWrapper.like("t.name", object.getName());
            }
            // 性别
            if (Validator.isNotEmpty(object.getSex())) {
                queryWrapper.eq("t.sex", object.getSex());
            }
            // 年龄
            if (Validator.isNotEmpty(object.getAge())) {
                queryWrapper.eq("t.age", object.getAge());
            }
            // 手机号码
            if (Validator.isNotEmpty(object.getPhone())) {
                queryWrapper.eq("t.phone", object.getPhone());
            }
            // 状态
            if (Validator.isNotEmpty(object.getStatus())) {
                queryWrapper.eq("t.status", object.getStatus());
            }
            // 地址
            if (Validator.isNotEmpty(object.getAddress())) {
                queryWrapper.eq("t.address", object.getAddress());
            }
        }
        page.setRecords(getBaseMapper().select(page, queryWrapper));
        return page;
    }

    /**
     * 详情
     *
     * @param id id
     * @return SampleGeneral
     */
    @Override
    public SampleGeneral input(String id) {
        ToolUtil.checkParams(id);
        return getBaseMapper().getById(id);
    }

    /**
     * 新增
     *
     * @return SampleGeneral
     */
    @Override
    public SampleGeneral add() {
        SampleGeneral object = new SampleGeneral();
        // 设置默认值
        return object;
    }

    /**
     * 删除
     *
     * @param ids 数据ids
     * @return true/false
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean remove(String ids) {
        ToolUtil.checkParams(ids);
        List<String> idList = Arrays.asList(ids.split(","));
        return removeByIds(idList);
    }

    /**
     * 保存
     *
     * @param object 表单内容
     * @return SampleGeneral
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public SampleGeneral saveData(SampleGeneral object) {
        ToolUtil.checkParams(object);
        if (Validator.isNotEmpty(object.getId())) {
            // 新增,设置默认值
        }
        return (SampleGeneral) ToolUtil.checkResult(saveOrUpdate(object), object);
    }

    /**
     * 验证数据，插入临时表后调用
     * 注: 返回false会触发异常回滚
     *
     * @param templateId 模板id
     * @param userId 用户id
     * @return true/false
     */
    @Override
    public boolean verificationData(String templateId, String userId) {
        return true;
    }

    /**
     * 导入前回调，插入正式表之前会调用此方法，建议导入正式表之前使用次方法再次验证数据，防止验证 ~ 导入之间数据发送变动
     * 注: 返回false会触发异常回滚
     *
     * @param templateId 模板id
     * @param userId 用户id
     * @return true/false
     */
    @Override
    public boolean beforeImport(String templateId, String userId) {
        return true;
    }

    /**
     * 导入后回调，插入正式表后会调用此方法
     * 注: 返回false会触发异常回滚
     *
     * @return true/false
     */
    @Override
    public boolean afterImport() {
        return true;
    }

    @Override
    public String exportData(SampleGeneral object) {
        QueryWrapper<SampleGeneral> queryWrapper = new QueryWrapper<>();
        if(object != null){
            // 查询条件
            // 姓名
            if (Validator.isNotEmpty(object.getName())) {
                queryWrapper.like("t.name", object.getName());
            }
            // 性别
            if (Validator.isNotEmpty(object.getSex())) {
                queryWrapper.eq("t.sex", object.getSex());
            }
            // 年龄
            if (Validator.isNotEmpty(object.getAge())) {
                queryWrapper.eq("t.age", object.getAge());
            }
            // 手机号码
            if (Validator.isNotEmpty(object.getPhone())) {
                queryWrapper.eq("t.phone", object.getPhone());
            }
            // 状态
            if (Validator.isNotEmpty(object.getStatus())) {
                queryWrapper.eq("t.status", object.getStatus());
            }
            // 地址
            if (Validator.isNotEmpty(object.getAddress())) {
                queryWrapper.eq("t.address", object.getAddress());
            }
        }
        List<SampleGeneral> list = getBaseMapper().exportData(queryWrapper);
        return ExcelUtil.writeAndGetDownloadId("代码生成示例", "代码生成示例", list, SampleGeneral.class);
    }
}