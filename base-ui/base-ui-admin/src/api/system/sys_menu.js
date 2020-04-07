import request from '@/utils/request'

/**
 * 系统资源-菜单信息 api
 *
 * @author huangxj
 * @date 2019-08-27
 */

/**
 * 获取分页数据
 */
const list = (params) => {
  return request({
    url: '/system/menu/page',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/menu/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/menu/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/menu/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/menu/delete/' + id,
    method: 'delete'
  })
}

/**
 * 获取菜单树形数据
 */
const tree = (params) => {
  return request({
    url: '/system/menu/tree',
    method: 'get',
    params
  })
}

export default {
  list,
  get,
  create,
  update,
  remove,
  tree
}
