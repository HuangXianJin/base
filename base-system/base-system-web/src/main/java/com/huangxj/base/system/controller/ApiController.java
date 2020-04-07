package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.ApiDto;
import com.huangxj.base.system.entity.Api;
import com.huangxj.base.system.param.ApiParam;
import com.huangxj.base.system.service.ApiService;
import com.huangxj.base.system.vo.ApiVo;
import com.huangxj.base.system.wrapper.ApiWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统资源-API接口 前端控制器
 *
 * @author huangxj
 * @date 2019-08-19
 */
@io.swagger.annotations.Api(value = "系统资源-API接口", tags = "系统资源-API接口" )
@RestController
@RequestMapping("/system/api" )
public class ApiController extends BaseController {

    @Autowired
    private ApiService service;

    @Autowired
    private ApiWrapper wrapper;

    @GetMapping("detail/{id}" )
    @ApiOperation(value = "详情", notes = "传入Id" )
    public Result detail(@PathVariable Integer id) {
        Api detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }


    @ApiOperation("分页/列表" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int" ),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int" ),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean" )
    })
    @GetMapping("page" )
    public Result page(PageParam pageParam, boolean listMode, ApiParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<Api> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建" )
    @PostMapping("create" )
    public Result create(@RequestBody ApiVo vo) {
        ApiDto obj = BeanConverter.convert(vo, ApiDto.class, "id" );
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新" )
    @PutMapping("update" )
    public Result update(@RequestBody ApiVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Api obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
        obj.setModifyId(getUserId());
        obj.setModifyTime(new Date());
        service.updateById(obj);
        return Result.success();
    }

    @ApiOperation("删除,多个id用','分割" )
    @DeleteMapping("delete/{id}" )
    public Result delete(@PathVariable String id) {
        String[] ids = id.split("," );
        service.removeByIds(Arrays.asList(ids));
        return Result.success();
    }

    @Value("${spring.application.name}" )
    protected String applicationName;


    @ApiOperation("清理无效api" )
    @DeleteMapping("clearApi" )
    public Result clearApi() {
        Set<String> codes = service.scanApi().stream().map(e -> e.getApiCode()).collect(Collectors.toSet());
        service.clearInvalidApi(applicationName, codes);
        return Result.success();
    }
}