import request from '@/utils/request'

const queryLog = (params) => {
  return request({
    url: '/system/log',
    method: 'get',
    params
  })
}

const cleanLog = (data) => {
  return request({
    url: '/system/log/clean',
    method: 'delete',
    data
  })
}

export default {
  queryLog,
  cleanLog
}

