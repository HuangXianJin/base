import request from '@/utils/request'

/**
 * 出租屋信息 api
 *
 * @author fjr
 * @date 2020-03-01
 */

/**
 * 获取分页数据
 */
const page = (params) => {
  return request({
    url: '/questionnaire/questionnaireRentalHouse/page',
    method: 'get',
    params
  })
}

/**
 * 获取列表数据
 */
const list = (params) => {
  return request({
    url: '/questionnaire/questionnaireRentalHouse/list',
    method: 'get',
    params
  })
}

/**
 * 根据ID查找数据
 */
const get = (id) => {
  return request({
    url: '/questionnaire/questionnaireRentalHouse/detail/' + id,
    method: 'get'
  })
}

/**
 * 添加数据
 */
const create = (data) => {
  return request({
    url: '/questionnaire/questionnaireRentalHouse/create',
    method: 'post',
    data
  })
}

/**
 * 更新数据
 */
const update = (data) => {
  return request({
    url: '/questionnaire/questionnaireRentalHouse/update',
    method: 'put',
    data
  })
}

/**
 * 删除数据
 */
const remove = (id) => {
  return request({
    url: '/questionnaire/questionnaireRentalHouse/delete/' + id,
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
