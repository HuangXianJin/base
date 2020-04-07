import request from '@/utils/request'
import uploadAjax from '@/utils/upload'
/**
 * 用户信息 api
 *
 * @author huangxj
 * @date 2019-08-28
 */

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/system/user/list',
    method: 'get',
    params
  })
}

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/system/user/page',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/user/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/user/create',
    method: 'post',
    data
  })
}

/**
 * 修改密码
 */
const modifyPassword = (data) => {
  return request({
    url: '/system/user/modifyPassword',
    method: 'put',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/user/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/user/delete/' + id,
    method: 'delete'
  })
}

const upload = (data) => {
  return uploadAjax({
    url: '/system/user/upload',
    method: 'post',
    data
  })
}

export default {
  list,
  page,
  get,
  create,
  update,
  remove,
  modifyPassword,
  upload
}
