package com.huangxj.base.system.controller;

import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.dto.DepartmentDto;
import com.huangxj.base.system.entity.Department;
import com.huangxj.base.system.param.DepartmentParam;
import com.huangxj.base.system.service.DepartmentService;
import com.huangxj.base.system.vo.DepartmentVo;
import com.huangxj.base.system.wrapper.DepartmentWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 部门表 前端控制器
 *
 * @author yuguohui
 * @date 2020-03-18
 */
@Api(value = "系统管理-组织机构信息", tags = "系统管理-组织机构信息")
@RestController
@RequestMapping("system/department")
@AllArgsConstructor
public class DepartmentController extends BaseController {

    private DepartmentService service;

    private DepartmentWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        Department detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }


    @ApiOperation("列表")
    @GetMapping("list")
    public Result list(DepartmentParam param) {
        param.setTenantCode(getUser().getTenantCode());
        List<Department> list = service.list(param);
        return Result.success().data(wrapper.toTree(list));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody DepartmentVo vo) {
        if (StringUtils.isBlank(vo.getDepartmentName())) {
            return Result.fail("机构名称不能为空");
        }
        if (StringUtils.isBlank(vo.getTenantCode())) {
            vo.setTenantCode(getUser().getTenantCode());
        }
        DepartmentDto obj = BeanConverter.convert(vo, DepartmentDto.class, "id");
        service.checkConflict(obj);
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody DepartmentVo vo) {
        if (StringUtils.isBlank(vo.getDepartmentName())) {
            return Result.fail("机构名称不能为空");
        }
        if (StringUtils.isBlank(vo.getTenantCode())) {
            vo.setTenantCode(getUser().getTenantCode());
        }
        if (null == vo.getId()) {
            return Result.fail(ErrorCode.PRIMARY_KEY_IS_NULL.getMessage()).code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Department obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail(ErrorCode.DATA_NOT_EXIST.getMessage()).code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
        service.checkConflict(obj);
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
