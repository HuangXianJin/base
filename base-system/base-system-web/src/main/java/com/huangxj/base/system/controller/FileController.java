package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.SysFileDto;
import com.huangxj.base.system.entity.SysFile;
import com.huangxj.base.system.param.FileParam;
import com.huangxj.base.system.service.FileService;
import com.huangxj.base.system.vo.SysFileVo;
import com.huangxj.base.system.wrapper.FileWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件资源管理 前端控制器
 *
 * @author huangxj
 * @date 2020-02-20
 */
@Api(value = "系统管理-文件资源管理", tags = "系统管理-文件资源管理")
@RestController
@RequestMapping("system/file")
@AllArgsConstructor
public class FileController extends BaseController {

    private FileService service;

    private FileWrapper wrapper;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        SysFile detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }

    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("list")
    public Result list(FileParam param) {
        List<SysFile> list =  service.list(param);
        return Result.success().data(wrapper.listVO(list));
    }

    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, FileParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<SysFile> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody SysFileVo vo) {
        SysFileDto obj = BeanConverter.convert(vo, SysFileDto.class, "id");
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody SysFileVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        SysFile obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
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


    @ApiOperation("上传附件")
    @PostMapping("uploadAttachment")
    @ResponseBody
    public Result uploadAttachment(@RequestParam("file") MultipartFile file, @RequestParam("attachmentCode") String attachmentCode, @RequestParam("sysFileType") String sysFileType, @RequestParam("desc") String desc) throws IOException {
        if (StringUtils.isEmpty(desc)) {
            desc = "附件";
        }
        String originName = file.getOriginalFilename();
        InputStream is = file.getInputStream();
        try {
            if (null == is || is.available() == 0) {
                return Result.fail();
            }
            SysFileDto systemFile = service.upload(is, sysFileType, desc, originName, attachmentCode);
            SysFileVo sysFileVo = BeanConverter.convert(systemFile, SysFileVo.class);
            return Result.success().data(sysFileVo);

        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @ApiOperation("获取附件")
    @GetMapping("getAttachmentsByAttachmentCode")
    public Result getAttachmentsByAttachmentCode(String attachmentCode, String sysFileType) throws IOException {

        QueryWrapper<SysFile> queryWrapper = new QueryWrapper<SysFile>();
        if (!StringUtils.isEmpty(attachmentCode)) {
            queryWrapper.lambda().eq(SysFile::getAttachmentCode, attachmentCode);
        } else {
            return Result.fail();
        }
        if (!StringUtils.isEmpty(sysFileType)) {
            queryWrapper.lambda().eq(SysFile::getFileType, sysFileType);
        }
        List<SysFile> list = service.list(queryWrapper);
        List<SysFileVo> voList = BeanConverter.convert(list, SysFileVo.class);
        return Result.success().data(voList);
    }

    @ApiOperation("删除附件")
    @DeleteMapping("deleteAttachment/{id}")
    public Result deleteAttachment(@PathVariable String id) {
        service.deleteFile(id);
        return Result.success();
    }

    @ApiOperation("按时间段清理数据")
    @DeleteMapping("deleteByDate")
    public Result deleteByDate(FileParam param) {
        List<SysFile> listSysFile = service.list(new QueryWrapper<SysFile>().lambda()
                .ge(SysFile::getOperateTime, param.getStartDate())
                .le(SysFile::getOperateTime, param.getEndDate()));
        List<Integer> id = listSysFile.stream().map(e -> e.getId()).collect(Collectors.toList());
        if (id.size() > 0) {
                id.forEach(s -> service.deleteFile(Joiner.on(",").join(id)));
        }
        return Result.success();
    }

}
