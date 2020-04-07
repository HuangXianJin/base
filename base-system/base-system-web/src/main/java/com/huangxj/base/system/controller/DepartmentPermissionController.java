package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.dto.DepartmentPermissionDto;
import com.huangxj.base.system.entity.DepartmentPermission;
import com.huangxj.base.system.param.DepartmentPermissionParam;
import com.huangxj.base.system.service.DepartmentPermissionService;
import com.huangxj.base.system.vo.DepartmentPermissionVo;
import com.huangxj.base.system.wrapper.DepartmentPermissionWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 部门权限表 前端控制器
 *
 * @author yuguohui
 * @date 2020-03-26
 */
@Api(value = "系统管理-机构权限管理", tags = "系统管理-机构权限管理")
@RestController
@RequestMapping("system/departmentPermission")
@AllArgsConstructor
public class DepartmentPermissionController extends BaseController {

    private DepartmentPermissionService service;

    private DepartmentPermissionWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        DepartmentPermission detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }

    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("list")
    public Result list(DepartmentPermissionParam param) {
        List<DepartmentPermission> list = service.list(param);
        return Result.success().data(wrapper.listVO(list));
    }

    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, DepartmentPermissionParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<DepartmentPermission> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody DepartmentPermissionVo vo) {
        DepartmentPermissionDto obj = BeanConverter.convert(vo, DepartmentPermissionDto.class, "id");
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody DepartmentPermissionVo vo) {
        if (Objects.isNull(vo.getDepartmentId())) {
            return Result.fail("所在部门不能为空");
        }
        if (StringUtils.isBlank(vo.getDepartmentPermissions())) {
            return Result.fail("部门权限不能为空");
        }
        service.saveDepartmentPermission(vo.getDepartmentId(), vo.getDepartmentPermissions());
        return Result.success();
    }

    @ApiOperation("删除,多个id用','分割")
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable String id) {
        String[] ids = id.split(",");
        service.removeByIds(Arrays.asList(ids));
        return Result.success();
    }
}
