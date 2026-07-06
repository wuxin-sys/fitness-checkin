import { request } from './request'

/**
 * 用户 API
 */

// 登录
export function login(data) {
  return request.post('/user/login', data)
}

// 注册
export function register(data) {
  return request.post('/user/register', data)
}

// 获取用户信息
export function getUserInfo() {
  return request.get('/user/info')
}

// 修改密码
export function updatePassword(data) {
  return request.put('/user/password', data)
}

// 修改个人信息
export function updateProfile(data) {
  return request.put('/user/profile', data)
}
