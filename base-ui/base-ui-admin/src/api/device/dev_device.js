import request from '@/utils/request'
import uploadAjax from '@/utils/upload'
/**
 * 设备信息 api
 *
 * @author huangxj
 * @date 2019-10-08
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/device/device/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/device/device/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/device/device/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/device/device/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/device/device/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/device/device/delete/' + id,
    method: 'delete'
  })
}

const upload = (data) => {
  return uploadAjax({
    url: '/device/device/upload',
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
  upload
}
