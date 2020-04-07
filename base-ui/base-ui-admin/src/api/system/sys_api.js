import request from '@/utils/request'

/**
 * 系统资源-API接口 api
 *
 * @author huangxj
 * @date 2019-08-26
 */

/**
 * 获取分页数据
 */
const list = (params) => {
  return request({
    url: '/system/api/page',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/api/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/api/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/api/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/api/delete/' + id,
    method: 'delete'
  })
}

/**
 * 清理无效api
 */
const clearApi = (id) => {
  return request({
    url: '/system/api/clearApi',
    method: 'delete'
  })
}

export default {
  list,
  get,
  create,
  update,
  remove,
  clearApi
}
