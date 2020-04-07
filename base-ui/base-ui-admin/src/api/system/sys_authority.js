import request from '@/utils/request'

/**
 * 系统权限-菜单权限、API权限 api
 *
 * @author huangxj
 * @date 2019-08-26
 */

/**
 * 获取分页数据
 */
const list = (params) => {
  return request({
    url: '/system/authority/page',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/authority/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/authority/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/authority/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/authority/delete/' + id,
    method: 'delete'
  })
}

/**
 * 获取角色已分配权限
 */
const role = (id) => {
  return request({
    url: '/system/authority/role/' + id,
    method: 'get'
  })
}

/**
 * 获取租户已分配权限
 */
const tenant = (tenantCode) => {
  return request({
    url: '/system/authority/tenant/' + tenantCode,
    method: 'get'
  })
}

/**
 * 获取用户已分配菜单
 */
const userMenu = (id) => {
  return request({
    url: '/system/authority/userMenu/' + id,
    method: 'get'
  })
}

/**
 * 获取租户已分配权限
 */
const menuAuthority = (id) => {
  return request({
    url: '/system/authority/menu/' + id,
    method: 'get'
  })
}

/**
 * 获取api权限
 */
const api = () => {
  return request({
    url: '/system/authority/api',
    method: 'get'
  })
}

/**
 * 获取api权限
 */
const apiTree = () => {
  return request({
    url: '/system/authority/apiTree',
    method: 'get'
  })
}

/**
 * 获取menu权限
 */
const menu = () => {
  return request({
    url: '/system/authority/menu',
    method: 'get'
  })
}

/**
 * 添加数据
 */
const grant = (data) => {
  return request({
    url: '/system/authority/grant',
    method: 'post',
    data
  })
}

export default {
  list,
  get,
  create,
  update,
  remove,
  role,
  tenant,
  api,
  apiTree,
  menu,
  grant,
  userMenu,
  menuAuthority
}
