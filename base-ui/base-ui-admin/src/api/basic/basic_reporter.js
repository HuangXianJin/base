import request from '@/utils/request'

/**
 * 上报人基础信息 api
 *
 * @author fjr
 * @date 2020-02-04
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/basic/basicReporter/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/basic/basicReporter/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/basic/basicReporter/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/basic/basicReporter/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/basic/basicReporter/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/basic/basicReporter/delete/' + id,
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
