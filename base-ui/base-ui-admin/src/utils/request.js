import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/auth'
import { Loading } from 'element-ui'

var loading
// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // api 的 base_url
  withCredentials: false
  // timeout: 5000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    const token = getToken()
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    if (config.method === 'get' || config.method === 'post') {
      config.params = {
        _t: Date.parse(new Date()) / 1000,
        ...config.params
      }
    }

    if (config.method === 'put' || config.method === 'post') {
      loading = Loading.service({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    }
    return config
  },
  error => {
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    var config = response.config
    if (config.method === 'put' || config.method === 'post') {
      loading.close()
    }
    if (response.request.responseType === 'arraybuffer') {
      return response.data
    }
    const res = response.data
    if (res.success) {
      return response.data
    } else {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(res.message)
    }
  },
  error => {
    if (loading) { loading.close() }
    let message = '连接服务器失败'
    if (error && error.response) {
      switch (error.response.status) {
        case 502:
          message = '连接服务器失败'
          break
        case 429:
          message = '访问太过频繁，请稍后再试!'
          break
        default:
          message = error.response.data.path + error.response.data.message ? error.response.data.message : '服务器错误'
          break
      }

      const res = error.response.data
      if (res.code === 2012 || res.code === 2000) {
        MessageBox.confirm(
          '认证失败,请重新登录!',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('user/logout').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      }
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
