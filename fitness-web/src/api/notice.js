import { request } from './request'

/**
 * 公告 API
 */

// 分页查询公告
export function pageNotices(params) {
  return request.get('/notice/page', { params })
}

// 公告详情
export function getNoticeById(id) {
  return request.get(`/notice/${id}`)
}

// 最新公告
export function getLatestNotice() {
  return request.get('/notice/latest')
}
