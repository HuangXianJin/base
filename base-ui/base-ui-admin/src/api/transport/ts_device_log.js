import request from '@/utils/request'

/**
 * 设备信息 api
 *
 * @author huangxj
 * @date 2019-11-13
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/transport/deviceLog/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/transport/deviceLog/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/transport/deviceLog/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/transport/deviceLog/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/transport/deviceLog/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/transport/deviceLog/delete/' + id,
    method: 'delete'
  })
}

export default {
  list,
  page,
  get,
  create,
  update,
  remove
}
