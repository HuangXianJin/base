import request from '@/utils/request'

/**
 * 登录
 */
const login = (data) => {
  return request({
    url: '/login',
    data,
    method: 'post'
  })
}

/**
 * 获取用户信息
 * @param {*} params
 */
const getUserProfile = (params) => {
  return request({
    url: '/uaa-auth/current/user',
    params,
    method: 'get'
  })
}

export default {
  login,
  getUserProfile
}
