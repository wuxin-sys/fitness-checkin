import { defineStore } from 'pinia'
import { ref } from 'vue'

/**
 * 管理员状态管理
 */
export const useAdminStore = defineStore('admin', () => {
  const token = ref(localStorage.getItem('adminToken') || '')
  const adminInfo = ref(JSON.parse(localStorage.getItem('adminInfo') || 'null'))

  function setLogin(tokenVal, admin) {
    token.value = tokenVal
    adminInfo.value = admin
    localStorage.setItem('adminToken', tokenVal)
    localStorage.setItem('adminInfo', JSON.stringify(admin))
  }

  function logout() {
    token.value = ''
    adminInfo.value = null
    localStorage.removeItem('adminToken')
    localStorage.removeItem('adminInfo')
  }

  return { token, adminInfo, setLogin, logout }
})
