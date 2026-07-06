import { request } from './request'

/**
 * 打卡 API
 */

// 新增打卡
export function addCheckin(data) {
  return request.post('/checkin', data)
}

// 修改打卡
export function updateCheckin(id, data) {
  return request.put(`/checkin/${id}`, data)
}

// 删除打卡
export function deleteCheckin(id) {
  return request.delete(`/checkin/${id}`)
}

// 分页查询打卡记录
export function pageCheckins(params) {
  return request.get('/checkin/page', { params })
}

// 今日打卡状态
export function getTodayStatus() {
  return request.get('/checkin/today')
}

// 连续打卡天数
export function getStreak() {
  return request.get('/checkin/streak')
}

// 本周打卡次数
export function getWeekCount() {
  return request.get('/checkin/week-count')
}

// 本月打卡次数
export function getMonthCount() {
  return request.get('/checkin/stats/month')
}

// 每月打卡趋势
export function getMonthlyStats() {
  return request.get('/checkin/stats/monthly')
}

// 训练类型占比
export function getTypeStats() {
  return request.get('/checkin/stats/type')
}
