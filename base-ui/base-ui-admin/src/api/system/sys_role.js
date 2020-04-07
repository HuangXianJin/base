import request from '@/utils/request'

/**
 * 系统角色-基础信息 api
 *
 * @author huangxj
 * @date 2019-08-26
 */

/**
 * 获取分页数据
 */
const list = (params) => {
  return request({
    url: '/system/role/list',
    method: 'get',
    params
  })
}

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/system/role/page',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/role/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/role/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/role/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/role/delete/' + id,
    method: 'delete'
  })
}

export default {
  page,
  list,
  get,
  create,
  update,
  remove
}
