import request from '@/utils/request'

const loadExcel = (params) => {
  return request({
    url: '/common/loadExcel',
    method: 'get',
    params: params,
    responseType: 'arraybuffer' // 表明返回服务器返回的数据类型
  })
}

export default {
  loadExcel
}
