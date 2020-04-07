import request from '@/utils/request'
import uploadAjax from '@/utils/upload'
/**
 * 街道和派出所关联 api
 *
 * @author fjr
 * @date 2020-03-21
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/questionnaire/questionnaireStreetSetting/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/questionnaire/questionnaireStreetSetting/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/questionnaire/questionnaireStreetSetting/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/questionnaire/questionnaireStreetSetting/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/questionnaire/questionnaireStreetSetting/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/questionnaire/questionnaireStreetSetting/delete/' + id,
    method: 'delete'
  })
}

const upload = (data) => {
  return uploadAjax({
    url: '/questionnaire/questionnaireStreetSetting/upload',
    method: 'post',
    data
  })
}

/**
 * 关联派出所
 */
const reset = (data) => {
  return request({
    url: '/questionnaire/questionnaireStreetSetting/reSet',
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
  upload,
  reset
}
