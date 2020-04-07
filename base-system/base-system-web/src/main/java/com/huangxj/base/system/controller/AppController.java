package com.huangxj.base.system.controller;

import com.huangxj.base.system.api.AppServiceClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.AppDto;
import com.huangxj.base.system.entity.App;
import com.huangxj.base.system.enums.AppType;
import com.huangxj.base.system.param.AppParam;
import com.huangxj.base.system.service.AppService;
import com.huangxj.base.system.vo.AppVo;
import com.huangxj.base.system.wrapper.AppWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 客户端应用 前端控制器
 *
 * @author huangxj
 * @date 2019-08-08
 */
@Api(value = "系统应用-基础应用", tags = "系统应用-基础应用")
@RestController
@RequestMapping("/system/app")
@AllArgsConstructor
public class AppController extends BaseController implements AppServiceClient {

    private AppService service;

    private AppWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        App detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }


    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, AppParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<App> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody AppVo vo) {
        AppDto obj = BeanConverter.convert(vo, AppDto.class, "id");
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody AppVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        App obj = service.getById(vo.getId());
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
    public Result delete(@PathVariable String id) {
        String[] ids = id.split(",");
        for (String item : ids) {
            service.removeById(Integer.parseInt(item));
        }
        return Result.success();
    }

    /**
     * 获取应用详情
     *
     * @param clientId
     * @return
     */
    @ApiOperation(value = "获取应用详情", notes = "获取应用详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "应用ID", defaultValue = "1", required = true, paramType = "path"),
    })
    @GetMapping("/clientId/{clientId}")
    @Override
    public Result<App> getAppByClientId(@PathVariable("clientId") String clientId) {
        App appInfo = service.getOne(new QueryWrapper<App>().lambda().eq(App::getApiKey, clientId));
        return Result.success().data(appInfo);
    }

    @ApiOperation(value = "获取应用服务器应用详情", notes = "获取应用详情")
    @GetMapping("/serverApp")
    @Override
    public Result<App> getServerApp() {
        App app = service.getOne(new QueryWrapper<App>().lambda().eq(App::getAppType, AppType.SERVER.getCode()).last("limit 1"));
        return Result.success().data(app);
    }
}