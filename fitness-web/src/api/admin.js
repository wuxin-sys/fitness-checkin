import { adminRequest } from './request'

/**
 * 管理员 API
 */

// 管理员登录
export function adminLogin(data) {
  return adminRequest.post('/login', data)
}

// 数据概览
export function getAdminStats() {
  return adminRequest.get('/stats')
}

// 分页查询用户
export function pageUsers(params) {
  return adminRequest.get('/user/page', { params })
}

// 新增用户
export function addUser(data) {
  return adminRequest.post('/user', data)
}

// 修改用户
export function updateUser(id, data) {
  return adminRequest.put(`/user/${id}`, data)
}

// 删除用户
export function deleteUser(id) {
  return adminRequest.delete(`/user/${id}`)
}

// 分页查询打卡记录
export function pageAllCheckins(params) {
  return adminRequest.get('/checkin/page', { params })
}

// 删除打卡
export function adminDeleteCheckin(id) {
  return adminRequest.delete(`/checkin/${id}`)
}

// 分页查询公告
export function pageAdminNotices(params) {
  return adminRequest.get('/notice/page', { params })
}

// 发布公告
export function addNotice(data) {
  return adminRequest.post('/notice', data)
}

// 修改公告
export function updateNotice(id, data) {
  return adminRequest.put(`/notice/${id}`, data)
}

// 删除公告
export function deleteNotice(id) {
  return adminRequest.delete(`/notice/${id}`)
}
