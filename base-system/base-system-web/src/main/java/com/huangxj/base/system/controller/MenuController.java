package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.MenuDto;
import com.huangxj.base.system.entity.Menu;
import com.huangxj.base.system.param.MenuParam;
import com.huangxj.base.system.service.MenuService;
import com.huangxj.base.system.vo.MenuVo;
import com.huangxj.base.system.wrapper.MenuWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 系统资源-菜单信息 前端控制器
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Api(value = "系统资源-菜单信息", tags = "系统资源-菜单信息")
@RestController
@RequestMapping("/system/menu")
@AllArgsConstructor
public class MenuController extends BaseController {

    private MenuService service;

    private MenuWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        Menu detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }


    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, MenuParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<Menu> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("树")
    @GetMapping("tree")
    public Result tree(MenuParam param) {
        List<Menu> menus = service.list(param);
        List<MenuVo> voList = wrapper.listVO(menus);
        Collections.sort(voList);
        voList = BeanConverter.listToTree(0, voList, MenuVo.class);
        return Result.success().data(voList);
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody MenuVo vo) {
        MenuDto obj = BeanConverter.convert(vo, MenuDto.class, "id");
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.saveMenu(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody MenuVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Menu obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
        obj.setModifyId(getUserId());
        obj.setModifyTime(new Date());
        service.updateMenu(obj);
        return Result.success();
    }

    @ApiOperation("删除,多个id用','分割")
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable String id) {
        String[] ids = id.split(",");
        for (String menuId : ids) {
            service.removeMenu(Integer.valueOf(menuId));
        }
        return Result.success();
    }
}