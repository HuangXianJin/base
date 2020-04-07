import request from '@/utils/request'

/**
 *  api
 *
 * @author fjr
 * @date 2020-02-11
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/company/companyEmployee/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/company/companyEmployee/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/company/companyEmployee/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/company/companyEmployee/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/company/companyEmployee/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/company/companyEmployee/delete/' + id,
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
