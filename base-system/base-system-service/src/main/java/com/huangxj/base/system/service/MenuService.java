package com.huangxj.base.system.service;

import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.entity.Menu;

/**
 * 系统资源-菜单信息 服务类
 *
 * @author huangxj
 * @date 2019-08-19
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    void saveMenu(Menu menu);

    /**
     * 修改接口
     *
     * @param menu
     * @return
     */
    void updateMenu(Menu menu);

    Menu getMenuByMenuCode(String menuCode);

    /**
     * 移除接口
     *
     * @param menuId
     * @return
     */
    void removeMenu(Integer menuId);


}
