import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

/**
 * Axios 实例封装
 * - 请求拦截器自动携带 Token
 * - 响应拦截器统一处理错误
 */

// 用户端实例
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 管理员实例
const adminRequest = axios.create({
  baseURL: '/api/admin',
  timeout: 10000
})

// 请求拦截器 - 用户端
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => Promise.reject(error)
)

// 请求拦截器 - 管理员
adminRequest.interceptors.request.use(
  config => {
    const token = localStorage.getItem('adminToken')
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器 - 用户端
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res
    }
    ElMessage.error(res.message || '请求失败')
    // 401 跳转登录
    if (res.code === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      router.push('/login')
    }
    return Promise.reject(new Error(res.message))
  },
  error => {
    ElMessage.error('网络连接失败，请检查网络')
    return Promise.reject(error)
  }
)

// 响应拦截器 - 管理员
adminRequest.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res
    }
    ElMessage.error(res.message || '请求失败')
    if (res.code === 401 || res.code === 403) {
      localStorage.removeItem('adminToken')
      localStorage.removeItem('adminInfo')
      router.push('/admin/login')
    }
    return Promise.reject(new Error(res.message))
  },
  error => {
    ElMessage.error('网络连接失败，请检查网络')
    return Promise.reject(error)
  }
)

export { request, adminRequest }
