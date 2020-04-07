package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangxj.common.core.utils.ExcelUtil;
import com.huangxj.common.core.utils.StringUtils;
import com.huangxj.base.system.api.UserServiceClient;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.dto.UserDto;
import com.huangxj.base.system.entity.Role;
import com.huangxj.base.system.entity.User;
import com.huangxj.base.system.param.UserParam;
import com.huangxj.base.system.service.DepartmentService;
import com.huangxj.base.system.service.RoleService;
import com.huangxj.base.system.service.UserService;
import com.huangxj.base.system.vo.UserVo;
import com.huangxj.base.system.wrapper.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 用户信息 前端控制器
 *
 * @author huangxj
 * @date 2019-08-08
 */
@Api(value = "系统用户-用户信息", tags = "系统用户-用户信息")
@RestController
@RequestMapping("/system/user")
@AllArgsConstructor
public class UserController extends BaseController implements UserServiceClient {

    private UserService service;

    private UserWrapper wrapper;

    private RoleService roleService;

    private DepartmentService departmentService;

    private PasswordEncoder passwordEncoder;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        User detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }


    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, UserParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<User> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody UserVo vo) {
        UserDto obj = BeanConverter.convert(vo, UserDto.class, "id");
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.createUser(obj);
        roleService.saveUserRoles(obj.getId(), vo.getRoleIds());
        departmentService.saveDepartmentUser(obj.getId(), vo.getDepartmentId());
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody UserVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        User obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj = BeanConverter.convert(vo, obj);
        obj.setModifyId(getUserId());
        obj.setModifyTime(new Date());
        service.updateById(obj);
        roleService.saveUserRoles(obj.getId(), vo.getRoleIds());
        departmentService.saveDepartmentUser(obj.getId(), vo.getDepartmentId());
        return Result.success();
    }

    @ApiOperation("修改密码")
    @PutMapping("modifyPassword")
    public Result modifyPassword(@RequestBody UserVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        User obj = service.getById(vo.getId());
        if (null == obj) {
            return Result.fail().code(ErrorCode.DATA_NOT_EXIST.getCode());
        }
        obj.setPassword(passwordEncoder.encode(vo.getPassword()));
        obj.setModifyId(getUserId());
        obj.setModifyTime(new Date());
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

    @ApiOperation(value = "获取用户信息", notes = "仅限系统内部调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, value = "登录名", paramType = "path"),
    })
    @GetMapping("/username/{username}")
    @Override
    public Result<UserVo> getUserByUsername(@PathVariable(value = "username") String username) {
        User account = service.getUserByUsername(username);
        return Result.success().data(wrapper.entityVO(account));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @ApiOperation("导入用户")
    @PostMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        List<Map<String, Object>> list = ExcelUtil.analysisExcel(file);
        Role role = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleCode,"ROLE_USER"));
        List<Integer> roleIds = new LinkedList<Integer>();
        roleIds.add(role.getId());
        for (Map<String, Object> map : list) {
            if (StringUtils.isEmpty((String) map.get("账号"))) {
                return Result.fail("账号不能为空");
            }
            if (StringUtils.isEmpty((String) map.get("密码"))) {
                return Result.fail("密码不能为空");
            }
            if (StringUtils.isEmpty((String) map.get("姓名"))) {
                return Result.fail("姓名不能为空");
            }
            User user = new User();
            user.setUserName((String) map.get("账号"));
            user.setPassword((String) map.get("密码"));
            user.setNickName((String) map.get("姓名"));
            if(role != null){
                service.createUser(user);
                roleService.saveUserRoles(user.getId(), roleIds);
            }
        }
        return Result.success();
    }
}
