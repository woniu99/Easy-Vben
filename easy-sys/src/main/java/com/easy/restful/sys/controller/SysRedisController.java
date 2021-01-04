package com.easy.restful.sys.controller;

import com.easy.restful.core.annotation.ResponseResult;
import com.easy.restful.sys.model.SysRedisVO;
import com.easy.restful.sys.service.SysRedisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * redis 管理
 *
 * @author tengchong
 * @date 2019-01-25
 */
@RestController
@ResponseResult
@RequestMapping("/auth/sys/redis")
public class SysRedisController {

    @Autowired
    private SysRedisService service;

    /**
     * 根据前缀查询redis列表
     *
     * @param prefix 前缀
     * @return Tips
     */
    @GetMapping("prefix/{prefix}")
    @RequiresPermissions("sys:redis:select")
    @ResponseBody
    public Set<String> selectByPrefix(@PathVariable("prefix") String prefix){
        return service.selectByPrefix(prefix);
    }

    /**
     * 根据键获取信息
     *
     * @param key 键
     * @return Tips
     */
    @GetMapping("key/{key}")
    @RequiresPermissions("sys:redis:select")
    @ResponseBody
    public SysRedisVO get(@PathVariable("key") String key){
        return service.get(key);
    }

    /**
     * 根据键删除信息
     *
     * @param key 键
     * @return Tips
     */
    @DeleteMapping("{key}")
    @RequiresPermissions("sys:redis:remove")
    @ResponseBody
    public boolean remove(@PathVariable("key") String key){
        return service.remove(key);
    }

    /**
     * 保存
     *
     * @param sysRedis redis信息
     * @return Tips
     */
    @PutMapping()
    @RequiresPermissions("sys:redis:save")
    @ResponseBody
    public boolean save(SysRedisVO sysRedis){
        return service.save(sysRedis);
    }

}
