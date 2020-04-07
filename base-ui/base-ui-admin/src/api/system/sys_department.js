import request from '@/utils/request'

/**
 * 部门表 api
 *
 * @author yuguohui
 * @date 2020-03-18
 */

/**
 * 获取分页数据
 */
const list = (params) => {
  return request({
    url: '/system/department/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/department/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/department/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/department/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/department/delete/' + id,
    method: 'delete'
  })
}

export default {
  list,
  get,
  create,
  update,
  remove
}
