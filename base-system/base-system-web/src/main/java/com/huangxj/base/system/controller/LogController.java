package com.huangxj.base.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.LogDto;
import com.huangxj.base.system.entity.Log;
import com.huangxj.base.system.param.LogSelectParam;
import com.huangxj.base.system.service.LogService;
import com.huangxj.base.system.vo.LogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 日志管理
 * @Author: huangxj
 * @Create: 2018/11/17
 * @Version: 1.0
 **/
@Api(value = "系统管理-日志管理", tags = "系统管理-日志管理")
@RestController
@RequestMapping("system/log")
@AllArgsConstructor
public class LogController extends BaseController {

    private LogService logService;

    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean"),
            @ApiImplicitParam(name = "name", value = "名称")
    })
    @GetMapping
    public Result page(PageParam pageParam, boolean listMode, LogSelectParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page ret = logService.page(page, param);
        List<LogDto> list = ret.getRecords();
        List<LogVo> listVo = BeanConverter.convert(list, LogVo.class);
        ret.setRecords(listVo);
        return Result.success().data(ret);

    }

    @ApiOperation("获取指定数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "int", required = true)
    })
    @GetMapping("{id}")
    public Result getOne(@PathVariable Integer id) {
        Log obj = logService.getById(id);
        LogVo vo = BeanConverter.convert(obj, LogVo.class);
        return Result.success().data(vo);
    }

    @ApiOperation("创建")
    @PostMapping
    public Result create(@RequestBody LogVo vo) {
        Log obj = BeanConverter.convert(vo, Log.class, "id");
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        logService.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping
    public Result update(@RequestBody LogVo vo) {
        if (vo.getId() == null) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Log obj = logService.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo,obj);
        obj.setModifyId(getUserId());
        obj.setCreateTime(new Date());
        logService.updateById(obj);
        return Result.success();
    }

    @ApiOperation("删除,多个id用','分割")
    @DeleteMapping("{id}")
    public Result delete(@PathVariable String id) {
        String[] ids = id.split(",");
        logService.removeByIds(Arrays.asList(ids));
        return Result.success();
    }


    @ApiOperation("清理日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operateTime", value = "yyyy-MM-dd 清理指定日期之前的日志", dataType = "string", required = true)
    })
    @DeleteMapping("clean")
    public Result clean(@RequestBody LogVo vo) {
        logService.remove(new QueryWrapper<Log>().lambda().lt(Log::getOperateTime,vo.getOperateTime()));
        return Result.success();
    }

}
