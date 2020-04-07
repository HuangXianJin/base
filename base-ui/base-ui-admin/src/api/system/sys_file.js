import request from '@/utils/request'

/**
 * 文件资源管理 api
 *
 * @author huangxj
 * @date 2020-02-20
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/system/file/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/system/file/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/file/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/file/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/file/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/file/delete/' + id,
    method: 'delete'
  })
}

const getAttachmentsByAttachmentCode = function(params) {
  return request({
    url: '/system/file/getAttachmentsByAttachmentCode',
    method: 'get',
    params
  })
}

const deleteAttachment = function(id) {
  return request({
    url: '/system/file/deleteAttachment/' + id,
    method: 'delete'
  })
}

const deleteByDate = function deleteByDate(params) {
  return request({
    url: '/system/file/deleteByDate',
    method: 'delete',
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
  getAttachmentsByAttachmentCode,
  deleteAttachment,
  deleteByDate
}
