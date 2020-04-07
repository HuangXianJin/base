import request from '@/utils/request'

/**
 * 地址表 api
 *
 * @author yuguohui
 * @date 2020-03-21
 */

/**
 * 获取分页数据
 */
const list = (params) => {
  return request({
    url: '/questionnaire/address/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/questionnaire/address/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/questionnaire/address/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/questionnaire/address/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/questionnaire/address/delete/' + id,
    method: 'delete'
  })
}

/**
 * 导入数据
 */
const importData = (data) => {
  return request({
    url: '/questionnaire/address/importData',
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
  importData
}
