import { defineStore } from 'pinia'
import { ref } from 'vue'

/**
 * 用户状态管理
 */
export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

  // 设置登录信息
  function setLogin(tokenVal, user) {
    token.value = tokenVal
    userInfo.value = user
    localStorage.setItem('token', tokenVal)
    localStorage.setItem('userInfo', JSON.stringify(user))
  }

  // 更新用户信息
  function setUserInfo(user) {
    userInfo.value = user
    localStorage.setItem('userInfo', JSON.stringify(user))
  }

  // 退出登录
  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  return { token, userInfo, setLogin, setUserInfo, logout }
})
