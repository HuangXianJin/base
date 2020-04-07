import request from '@/utils/request'

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
  getDictionaryByCode
}
