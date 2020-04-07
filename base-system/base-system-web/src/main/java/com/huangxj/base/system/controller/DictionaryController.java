package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.DictionaryDto;
import com.huangxj.base.system.entity.Dictionary;
import com.huangxj.base.system.param.DictionaryParam;
import com.huangxj.base.system.service.DictionaryService;
import com.huangxj.base.system.vo.DictionaryVo;
import com.huangxj.base.system.wrapper.DictionaryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 前端控制器
 *
 * @author huangxj
 * @date 2019-10-09
 */
@Api(value = "系统管理-数据字典", tags = "系统管理-数据字典")
@RestController
@RequestMapping("system/dictionary")
@AllArgsConstructor
public class DictionaryController extends BaseController {

    private DictionaryService service;

    private DictionaryWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        Dictionary detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }

    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("list")
    public Result list(DictionaryParam param) {
        List<Dictionary> list = service.list(param);
        return Result.success().data(wrapper.listVO(list));
    }

    @ApiOperation("树")
    @GetMapping("tree")
    public Result tree(DictionaryParam param) {
        List<Dictionary> menus = service.list(param);
        List<DictionaryVo> voList = wrapper.listVO(menus);
        Collections.sort(voList);
        voList = BeanConverter.listToTree(null, voList, DictionaryVo.class);
        return Result.success().data(voList);
    }

    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, DictionaryParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<Dictionary> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody DictionaryVo vo) {
        LambdaQueryWrapper<Dictionary> queryWrapper = new QueryWrapper<Dictionary>().lambda()
                .eq(Dictionary::getItemCode, vo.getItemCode());

        if (vo.getParentId() == null) {
            queryWrapper.isNull(Dictionary::getParentId);
        } else {
            queryWrapper.eq(Dictionary::getParentId, vo.getParentId());
        }

        Dictionary dictionary = service.getOne(queryWrapper);
        if (dictionary != null) {
            return Result.fail("字典编码已存在");
        }

        DictionaryDto obj = BeanConverter.convert(vo, DictionaryDto.class, "id");
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody DictionaryVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Dictionary obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
        service.updateById(obj);
        return Result.success();
    }

    @ApiOperation("删除,多个id用','分割")
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable String id) {
        String[] ids = id.split(",");
        service.removeByIds(Arrays.asList(ids));
        return Result.success();
    }

    @ApiOperation("树")
    @GetMapping("getDictionaryByCode")
    public Result tree(String code) {
        LambdaQueryWrapper queryWrapper = new QueryWrapper<Dictionary>().lambda()
                .isNull(Dictionary::getParentId)
                .eq(Dictionary::getItemCode, code)
                .eq(Dictionary::getStatus, 1);
        Dictionary dictionary = service.getOne(queryWrapper);
        if (dictionary != null) {
            List dictionaries = service.list(new QueryWrapper<Dictionary>().lambda()
                    .isNotNull(Dictionary::getParentId)
                    .eq(Dictionary::getIndexCode, code)
                    .eq(Dictionary::getStatus, 1));
            List<DictionaryVo> voList = wrapper.listVO(dictionaries);
            Collections.sort(voList);
            voList = BeanConverter.listToTree(dictionary.getId(), voList, DictionaryVo.class);
            return Result.success().data(voList);
        }
        return Result.success();
    }
}
