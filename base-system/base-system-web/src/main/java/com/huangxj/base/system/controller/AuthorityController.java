package com.huangxj.base.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.huangxj.common.core.constant.AppConstant;
import com.huangxj.common.core.constant.ErrorCode;
import com.huangxj.common.core.controller.BaseController;
import com.huangxj.common.core.exception.BaseException;
import com.huangxj.common.core.model.PageParam;
import com.huangxj.common.core.model.Result;
import com.huangxj.common.core.security.AuthAuthority;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.base.system.api.AuthorityServiceClient;
import com.huangxj.base.system.dto.ApiDto;
import com.huangxj.base.system.dto.AuthorityDto;
import com.huangxj.base.system.dto.MenuDto;
import com.huangxj.base.system.entity.Authority;
import com.huangxj.base.system.entity.Menu;
import com.huangxj.base.system.entity.User;
import com.huangxj.base.system.param.AuthorityParam;
import com.huangxj.base.system.service.AuthorityService;
import com.huangxj.base.system.service.MenuService;
import com.huangxj.base.system.service.UserService;
import com.huangxj.base.system.vo.ApiVo;
import com.huangxj.base.system.vo.AuthorityOwnerVo;
import com.huangxj.base.system.vo.AuthorityVo;
import com.huangxj.base.system.vo.MenuVo;
import com.huangxj.base.system.wrapper.AuthorityWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统权限-菜单权限、API权限 前端控制器
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Api(value = "系统权限-菜单权限、API权限", tags = "系统权限-菜单权限、API权限")
@RestController
@RequestMapping("/system/authority")
@AllArgsConstructor
public class AuthorityController extends BaseController implements AuthorityServiceClient {

    private AuthorityService service;

    private AuthorityWrapper wrapper;

    private MenuService menuService;

    private UserService userService;

    @GetMapping("detail/{id}")
    @ApiOperation(value = "详情", notes = "传入Id")
    public Result detail(@PathVariable Integer id) {
        Authority detail = service.getById(id);
        return Result.success().data(wrapper.entityVO(detail));
    }


    @ApiOperation("分页/列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "int"),
            @ApiImplicitParam(name = "current", value = "页码", dataType = "int"),
            @ApiImplicitParam(name = "listMode", value = "集合模式，不进行分页直接返回所有结果集", dataType = "boolean")
    })
    @GetMapping("page")
    public Result page(PageParam pageParam, boolean listMode, AuthorityParam param) {
        Page page = pageParam.getPage();
        if (listMode) {
            page.setSize(-1);
        }
        Page<Authority> ret = service.page(page, param);
        return Result.success().data(wrapper.pageVO(ret));
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Result create(@RequestBody AuthorityVo vo) {
        AuthorityDto obj = BeanConverter.convert(vo, AuthorityDto.class, "id");
        obj.setCreateId(getUserId());
        obj.setCreateTime(new Date());
        service.save(obj);
        return Result.success();
    }

    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody AuthorityVo vo) {
        if (null == vo.getId()) {
            return Result.fail().code(ErrorCode.PRIMARY_KEY_IS_NULL.getCode());
        }
        Authority obj = service.getById(vo.getId());
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
        service.removeByIds(Arrays.asList(ids));
        return Result.success();
    }


    /**
     * 获取角色已分配权限
     *
     * @param roleId 角色ID
     * @return
     */
    @ApiOperation(value = "获取角色已分配权限")
    @GetMapping("/role/{roleId}")
    @Override
    public Result<List<AuthAuthority>> findAuthorityRole(@PathVariable Integer roleId) {
        List<AuthAuthority> result = service.findAuthorityByRole(roleId);
        return Result.success().data(result);
    }


    @ApiOperation(value = "分配权限")
    @PostMapping("/grant")
    public Result grantAuthority(@RequestBody AuthorityOwnerVo ao) {
        service.grantAuthority(ao.getMenuId(), ao.getRoleId(), ao.getAppId(), ao.getTenantCode(), ao.getExpireTime(), ao.getAuthoritys());
        return Result.success();
    }

    /**
     * 获取用户已分配权限
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "获取用户已分配权限")
    @GetMapping("/user/{userId}")
    @Override
    public Result<List<AuthAuthority>> findAuthorityUser(@PathVariable Integer userId) {
        List<AuthAuthority> result = service.findAuthorityByUser(userId);
        return Result.success().data(result);
    }

    /**
     * 获取用户已分配的菜单
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "获取用户已分配的菜单")
    @GetMapping("/userMenu/{userId}")
    public Result<List<MenuVo>> findMenuAuthorityUser(@PathVariable Integer userId) {
        User user = userService.getById(userId);
        if (user == null) {
            new BaseException("用户不存在");
        }
        List<AuthAuthority> userAuths = service.findAuthorityByUser(user.getId());
        List<AuthAuthority> tenantAuths = service.findAuthorityByTenant(user.getTenantCode());
        Set<AuthAuthority> authSet = new HashSet<>(userAuths);
        authSet.retainAll(tenantAuths);
        List<Integer> menuIds = Lists.newArrayList();
        menuIds.add(0);
        authSet.forEach(e -> {
            if (e.getMenuId() != null) {
                menuIds.add(e.getMenuId());
            }
        });
        List<Menu> menus = menuService.list(new QueryWrapper<Menu>().lambda().in(Menu::getId, menuIds).eq(Menu::getStatus, AppConstant.ENABLED));
        List<MenuVo> voList = BeanConverter.convert(menus, MenuVo.class);
        return Result.success().data(voList);
    }

    @ApiOperation(value = "获取应用已分配权限")
    @GetMapping("/app/{appId}")
    @Override
    public Result<List<AuthAuthority>> findAuthorityApp(@PathVariable Integer appId) {
        List<AuthAuthority> result = service.findAuthorityByApp(appId);
        return Result.success().data(result);
    }

    @ApiOperation(value = "获取菜单已分配权限")
    @GetMapping("/menu/{menuId}")
    public Result<List<AuthAuthority>> findAuthorityMenu(@PathVariable Integer menuId) {
        List<AuthAuthority> result = service.findAuthorityByMenu(Lists.newArrayList(menuId));
        return Result.success().data(result);
    }

    @ApiOperation(value = "获取租户已分配权限")
    @GetMapping("/tenant/{tenantCode}")
    @Override
    public Result<List<AuthAuthority>> findAuthorityTenant(@PathVariable String tenantCode) {
        List<AuthAuthority> result = service.findAuthorityByTenant(tenantCode);
        return Result.success().data(result);
    }

    @ApiOperation(value = "获取api资源")
    @GetMapping("/api")
    @Override
    public Result<List<ApiDto>> findApiAuthorityResource() {
        List<ApiDto> result = service.findApiAuthorityResource();
        Collections.sort(result);
        return Result.success().data(result);
    }

    @ApiOperation(value = "获取api资源")
    @GetMapping("/apiTree")
    public Result<List<ApiDto>> findApiAuthorityResourceTree() {
        List<ApiDto> result = service.findApiAuthorityResource();
        Collections.sort(result);
        List<ApiVo> voList = BeanConverter.convert(result, ApiVo.class);
        Set<ApiVo> tree = new TreeSet<>();
        Map<String, ApiVo> map = new HashMap<String, ApiVo>();
        voList.forEach(e -> {
            ApiVo parent = map.get(e.getClassName());
            if (parent == null) {
                parent = new ApiVo();
                parent.setApiCode(e.getClassName());
                parent.setApiName(e.getApiName().split("：")[0]);
                parent.setAuthority(e.getClassName());
                map.put(e.getClassName(), parent);
                tree.add(parent);
            }
            parent.getChildren().add(e);
        });
        return Result.success().data(tree);
    }

    @ApiOperation(value = "获取所有menu资源")
    @GetMapping("/menu")
    public Result<List<MenuDto>> findMenuAuthorityResource() {
        List<MenuDto> result = service.findMenuAuthorityResource();
        List<MenuVo> voList = BeanConverter.convert(result, MenuVo.class);
        voList = BeanConverter.listToTree(0, voList, MenuVo.class);
        return Result.success().data(voList);
    }


}