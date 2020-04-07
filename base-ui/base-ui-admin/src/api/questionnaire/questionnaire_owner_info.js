import request from '@/utils/request'

/**
 * 业主信息表 api
 *
 * @author yuguohui
 * @date 2020-03-04
 */

/**
 * 获取分页数据
 */
const list = (params) => {
  return request({
    url: '/questionnaire/ownerInfo/page',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/questionnaire/ownerInfo/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/questionnaire/ownerInfo/ownerRegister',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/questionnaire/ownerInfo/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/questionnaire/ownerInfo/delete/' + id,
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
