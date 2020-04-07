package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.RoleDto;
import com.huangxj.base.system.entity.Role;
import com.huangxj.base.system.param.RoleParam;
import com.huangxj.base.system.param.TenantParam;
import com.huangxj.base.system.service.RoleService;
import com.huangxj.base.system.vo.RoleVo;
import com.huangxj.base.system.wrapper.RoleWrapper;
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
 * 系统角色-基础信息 前端控制器
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Api(value = "系统角色-角色信息", tags = "系统角色-角色信息")
@RestController
@RequestMapping("/system/role")
@AllArgsConstructor
public class RoleController extends BaseController {

    private RoleService service;

    private RoleWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        Role detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }

    @GetMapping("list")
    @ApiOperation(value = "列表", notes = "列表")
    public Result list(TenantParam param) {
        List<Role> list =  service.list(param);
        return Result.success().data(wrapper.listVO(list));
    }

    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, RoleParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<Role> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody RoleVo vo) {
        RoleDto obj = BeanConverter.convert(vo, RoleDto.class, "id");
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody RoleVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Role obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
        obj.setModifyId(getUserId());
        obj.setModifyTime(new Date());
        service.updateById(obj);
        return Result.success();
    }

    @ApiOperation("删除,多个id用','分割")
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable String id)  {
        String[] ids = id.split(",");
        service.removeByIds(Arrays.asList(ids));
        return Result.success();
    }
}