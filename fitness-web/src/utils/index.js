/**
 * 工具函数
 */

/** 格式化日期 */
export function formatDate(date) {
  if (!date) return ''
  const d = new Date(date)
  const y = d.getFullYear()
  const m = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  return `${y}-${m}-${day}`
}

/** 格式化日期时间 */
export function formatDateTime(date) {
  if (!date) return ''
  const d = new Date(date)
  const y = d.getFullYear()
  const m = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const h = String(d.getHours()).padStart(2, '0')
  const min = String(d.getMinutes()).padStart(2, '0')
  const s = String(d.getSeconds()).padStart(2, '0')
  return `${y}-${m}-${day} ${h}:${min}:${s}`
}

/** 运动类型标签映射 */
export const sportTypeMap = {
  '跑步': 'success',
  '胸部': 'danger',
  '背部': 'warning',
  '腿部': 'primary',
  '肩部': 'info',
  '有氧': '',
  '其他': 'info'
}

export const sportTypeList = ['跑步', '胸部', '背部', '腿部', '肩部', '有氧', '其他']
