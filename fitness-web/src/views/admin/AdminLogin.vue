<template>
  <div class="admin-login-page">
    <div class="admin-login-card">
      <h2 class="admin-login-title">⚙️ 后台管理</h2>
      <p class="admin-login-subtitle">健身打卡系统</p>
      <el-form ref="formRef" :model="form" :rules="rules" size="large">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="管理员账号" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="密码" prefix-icon="Lock" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="admin-login-btn" :loading="loading" @click="handleLogin">
            登 录
          </el-button>
        </el-form-item>
      </el-form>
      <div class="admin-login-footer">
        <router-link to="/login">← 返回用户登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAdminStore } from '@/stores/admin'
import { adminLogin } from '@/api/admin'

const router = useRouter()
const store = useAdminStore()
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入管理员账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    const res = await adminLogin(form)
    store.setLogin(res.data.token, res.data.admin)
    ElMessage.success('登录成功')
    router.push('/admin/home')
  } catch {}
  loading.value = false
}
</script>

<style scoped>
.admin-login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: #304156;
}

.admin-login-card {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.admin-login-title {
  text-align: center;
  font-size: 24px;
  color: #303133;
  margin-bottom: 4px;
}

.admin-login-subtitle {
  text-align: center;
  font-size: 14px;
  color: #909399;
  margin-bottom: 32px;
}

.admin-login-btn {
  width: 100%;
}

.admin-login-footer {
  text-align: center;
  font-size: 14px;
}

.admin-login-footer a {
  color: #409eff;
}
</style>
