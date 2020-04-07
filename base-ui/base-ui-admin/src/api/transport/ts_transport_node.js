import request from '@/utils/request'

/**
 * 数据流转组件 api
 *
 * @author huangxj
 * @date 2019-10-22
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/transport/transportNode/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/transport/transportNode/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/transport/transportNode/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/transport/transportNode/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/transport/transportNode/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/transport/transportNode/delete/' + id,
    method: 'delete'
  })
}


/**
 * 获取列表数据
 */
const services = (params) => {
  return request({
    url: '/transport/transportNode/services',
    method: 'get',
    params
  })
}

export default {
  list,
  page,
  get,
  create,
  update,
  remove,
  services
}
