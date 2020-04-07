package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.TenantDto;
import com.huangxj.base.system.entity.Tenant;
import com.huangxj.base.system.param.TenantParam;
import com.huangxj.base.system.service.TenantService;
import com.huangxj.base.system.vo.TenantVo;
import com.huangxj.base.system.wrapper.TenantWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 租户信息 前端控制器
 *
 * @author huangxj
 * @date 2019-08-08
 */
@Api(value = "系统租户-租户信息", tags = "系统租户-租户信息")
@RestController
@RequestMapping("/system/tenant")
@AllArgsConstructor
public class TenantController extends BaseController {

    @Autowired
    private TenantService service;

    private TenantWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        Tenant detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }

    @GetMapping("list")
    @ApiOperation(value = "列表", notes = "列表")
    public Result list(TenantParam param) {
        List<Tenant> list =  service.list(param);
        return Result.success().data(wrapper.listVO(list));
    }

    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, TenantParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<Tenant> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody TenantVo vo) {
        TenantDto obj = BeanConverter.convert(vo, TenantDto.class, "id");
        obj.setIsDeleted(0);
//        obj.setCreateId(getUserId());
//        obj.setCreateTime(new Date());
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody TenantVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Tenant obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
//        obj.setModifyId(getUserId());
//        obj.setModifyTime(new Date());
        service.updateById(obj);
        return Result.success();
    }

    @ApiOperation("删除,多个id用','分割")
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable String id) {
        String[] ids = id.split(",");
        for (String key : ids) {
            Tenant obj = service.getById(Integer.valueOf(key));
            if (null == obj) {
                return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
            }
            obj.setIsDeleted(1);
            obj.setModifyId(getUserId());
            obj.setModifyTime(new Date());
            service.updateById(obj);
        }
        return Result.success();
    }
}
