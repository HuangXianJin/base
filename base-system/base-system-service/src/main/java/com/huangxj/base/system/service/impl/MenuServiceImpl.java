package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangxj.common.core.constant.AppConstant;
import com.huangxj.common.core.exception.BaseException;
import com.huangxj.base.system.entity.Menu;
import com.huangxj.base.system.enums.AuthorityType;
import com.huangxj.base.system.mapper.MenuMapper;
import com.huangxj.base.system.service.AuthorityService;
import com.huangxj.base.system.service.MenuService;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 系统资源-菜单信息 服务实现类
 *
 * @author huangxj
 * @date 2019-08-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    AuthorityService authorityService;
    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @Override
    public void saveMenu(Menu menu) {
        if (null != getMenuByMenuCode(menu.getMenuCode())) {
            throw new BaseException(String.format("%s编码已存在!",menu.getMenuCode()));
        }
        if (menu.getStatus() == null) {
            menu.setStatus(AppConstant.ENABLED);
        }
        if (menu.getIsPersist() == null) {
            menu.setIsPersist(0);
        }
        if (menu.getParentId() == null) {
            menu.setParentId(0);
        }
        baseMapper.insert(menu);
        // 同步权限表里的信息
        authorityService.saveOrUpdateAuthority(menu.getId(), AuthorityType.MENU.getCode());
    }

    /**
     * 修改接口
     *
     * @param menu
     * @return
     */
    @Override
    public void updateMenu(Menu menu) {
        Menu saved = getById(menu.getId());
        if (saved == null) {
            throw new BaseException("信息不存在!");
        }
        if (!saved.getMenuCode().equals(menu.getMenuCode())) {
            // 和原来不一致重新检查唯一性
            if (null != getMenuByMenuCode(menu.getMenuCode())) {
                throw new BaseException(String.format("%s编码已存在!", menu.getMenuCode()));
            }
        }
        menu.setModifyTime(new Date());
        baseMapper.updateById(menu);
        // 同步权限表里的信息
        authorityService.saveOrUpdateAuthority(menu.getId(), AuthorityType.MENU.getCode());
    }

    @Override
    public Menu getMenuByMenuCode(String menuCode) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Menu::getMenuCode, menuCode);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 移除接口
     *
     * @param menuId
     * @return
     */
    @Override
    public void removeMenu(Integer menuId) {
        Menu menu = getById(menuId);
        if (menu != null && menu.getIsPersist().equals(AppConstant.ENABLED)) {
            throw new BaseException(String.format("保留数据,不允许删除"));
        }
        authorityService.removeAuthority(menuId, AuthorityType.MENU.getCode());
        baseMapper.deleteById(menuId);
    }

}
