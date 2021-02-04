package com.easy.restful.scheduler.controller;

import com.easy.restful.common.core.common.pagination.Page;
import com.easy.restful.core.annotation.ResponseResult;
import com.easy.restful.scheduler.model.SchedulerJob;
import com.easy.restful.scheduler.service.SchedulerJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 定时任务
 *
 * @author TengChong
 * @date 2019-05-11
 */
@RestController
@ResponseResult
@RequestMapping("/auth/scheduler/job")
public class SchedulerJobController {

    /**
     * 定时任务  service
     */
    @Autowired
    private SchedulerJobService service;

    /**
     * 列表
     *
     * @param object 查询条件
     * @return Page<SchedulerJob>
     */
    @GetMapping()
    @RequiresPermissions("scheduler:job:select")
    public Page<SchedulerJob> select(SchedulerJob object, Page<SchedulerJob> page) {
        return service.select(object, page);
    }

    /**
     * 详情
     *
     * @param id id
     * @return SchedulerJob
     */
    @GetMapping("{id}")
    @RequiresPermissions("scheduler:job:select")
    public SchedulerJob input(@PathVariable("id") String id) {
        return service.input(id);
    }

    /**
     * 删除
     *
     * @param ids 数据ids
     * @return true/false
     */
    @DeleteMapping("{ids}")
    @RequiresPermissions("scheduler:job:remove")
    public boolean remove(@PathVariable("ids") String ids) {
        return service.remove(ids);
    }

    /**
     * 保存
     *
     * @param object 表单内容
     * @return SchedulerJob
     */
    @PostMapping()
    @RequiresPermissions("scheduler:job:save")
    public SchedulerJob saveData(@Valid @RequestBody SchedulerJob object) {
        return service.saveData(object);
    }

    /**
     * 开启
     *
     * @param id 数据id
     * @return true/false
     */
    @PostMapping("start/{ids}")
    @RequiresPermissions("scheduler:job:save")
    public boolean start(@PathVariable("ids") String id) {
        service.start(id);
        return true;
    }

    /**
     * 暂停
     *
     * @param id 数据id
     * @return true/false
     */
    @PostMapping("pause/{ids}")
    @RequiresPermissions("scheduler:job:save")
    public boolean pause(@PathVariable("ids") String id) {
        service.pause(id);
        return true;
    }

    /**
     * 全部开启
     *
     * @return true/false
     */
    @PostMapping("start/all")
    @RequiresPermissions("scheduler:job:save")
    public boolean startAll() {
        service.startAll();
        return true;
    }

    /**
     * 全部暂停
     *
     * @return true/false
     */
    @PostMapping("pause/all")
    @RequiresPermissions("scheduler:job:save")
    public boolean pauseAll() {
        service.pauseAll();
        return true;
    }

    /**
     * 立即执行指定任务
     *
     * @param id 数据id
     * @return true/false
     */
    @PostMapping("immediate/execution/{id}")
    @RequiresPermissions("scheduler:job:save")
    public boolean immediateExecution(@PathVariable("id") String id) {
        return service.immediateExecution(id);
    }
}