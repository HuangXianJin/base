import request from '@/utils/request'

/**
 *  api
 *
 * @author huangxj
 * @date 2019-10-09
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/system/dictionary/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/system/dictionary/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/system/dictionary/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/system/dictionary/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/system/dictionary/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/system/dictionary/delete/' + id,
    method: 'delete'
  })
}

/**
 * 获取菜单树形数据
 */
const tree = (params) => {
  return request({
    url: '/system/dictionary/tree',
    method: 'get',
    params
  })
}

/**
 * 获取菜单树形数据
 */
const getDictionaryByCode = (params) => {
  return request({
    url: '/system/dictionary/getDictionaryByCode',
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
  tree,
  getDictionaryByCode
}
