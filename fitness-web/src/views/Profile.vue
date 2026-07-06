<template>
  <AppLayout>
    <div class="page-container">
      <div class="page-title">个人中心</div>

      <el-row :gutter="24">
        <!-- 修改个人信息 -->
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <span>修改个人信息</span>
            </template>
            <el-form ref="profileRef" :model="profileForm" label-width="80px">
              <el-form-item label="用户名">
                <el-input :model-value="store.userInfo?.username" disabled />
              </el-form-item>
              <el-form-item label="昵称">
                <el-input v-model="profileForm.nickname" placeholder="请输入昵称" />
              </el-form-item>
              <el-form-item label="性别">
                <el-radio-group v-model="profileForm.gender">
                  <el-radio :value="0">未知</el-radio>
                  <el-radio :value="1">男</el-radio>
                  <el-radio :value="2">女</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="手机号">
                <el-input v-model="profileForm.phone" placeholder="请输入手机号" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" :loading="saving" @click="handleUpdateProfile">
                  保存修改
                </el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>

        <!-- 修改密码 -->
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <span>修改密码</span>
            </template>
            <el-form ref="pwdRef" :model="pwdForm" :rules="pwdRules" label-width="90px">
              <el-form-item label="旧密码" prop="oldPassword">
                <el-input v-model="pwdForm.oldPassword" type="password" show-password />
              </el-form-item>
              <el-form-item label="新密码" prop="newPassword">
                <el-input v-model="pwdForm.newPassword" type="password" show-password />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" :loading="changing" @click="handleChangePassword">
                  修改密码
                </el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import AppLayout from '@/components/AppLayout.vue'
import { useUserStore } from '@/stores/user'
import { getUserInfo, updateProfile, updatePassword } from '@/api/user'

const store = useUserStore()
const profileRef = ref(null)
const pwdRef = ref(null)
const saving = ref(false)
const changing = ref(false)

const profileForm = reactive({
  nickname: '',
  gender: 0,
  phone: ''
})

const pwdForm = reactive({
  oldPassword: '',
  newPassword: ''
})

const pwdRules = {
  oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不少于6位', trigger: 'blur' }
  ]
}

onMounted(async () => {
  try {
    const res = await getUserInfo()
    const user = res.data
    store.setUserInfo(user)
    profileForm.nickname = user.nickname || ''
    profileForm.gender = user.gender ?? 0
    profileForm.phone = user.phone || ''
  } catch {}
})

async function handleUpdateProfile() {
  saving.value = true
  try {
    const res = await updateProfile(profileForm)
    store.setUserInfo(res.data)
    ElMessage.success('个人信息修改成功')
  } catch {}
  saving.value = false
}

async function handleChangePassword() {
  const valid = await pwdRef.value.validate().catch(() => false)
  if (!valid) return

  changing.value = true
  try {
    await updatePassword(pwdForm)
    ElMessage.success('密码修改成功，请重新登录')
    store.logout()
    window.location.href = '/login'
  } catch {}
  changing.value = false
}
</script>
