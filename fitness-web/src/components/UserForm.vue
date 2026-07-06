<template>
  <el-dialog
    :model-value="visible"
    :title="isEdit ? '修改用户' : '新增用户'"
    width="460px"
    @update:model-value="$emit('update:visible', $event)"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" :disabled="isEdit" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="密码" :prop="isEdit ? '' : 'password'">
        <el-input
          v-model="form.password"
          type="password"
          show-password
          :placeholder="isEdit ? '留空则不修改' : '请输入密码'"
        />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" placeholder="请输入昵称" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.gender">
          <el-radio :value="0">未知</el-radio>
          <el-radio :value="1">男</el-radio>
          <el-radio :value="2">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.phone" placeholder="请输入手机号" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="$emit('update:visible', false)">取消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'

const props = defineProps({
  visible: Boolean,
  isEdit: { type: Boolean, default: false },
  editData: { type: Object, default: null }
})

const emit = defineEmits(['update:visible', 'submit'])

const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  nickname: '',
  gender: 0,
  phone: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

watch(() => props.visible, (val) => {
  if (val) {
    if (props.isEdit && props.editData) {
      form.username = props.editData.username
      form.password = ''
      form.nickname = props.editData.nickname || ''
      form.gender = props.editData.gender ?? 0
      form.phone = props.editData.phone || ''
    } else {
      resetForm()
    }
  }
})

function resetForm() {
  form.username = ''
  form.password = ''
  form.nickname = ''
  form.gender = 0
  form.phone = ''
  formRef.value?.resetFields()
}

function handleClose() {
  resetForm()
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  loading.value = true
  try {
    emit('submit', { ...form })
  } finally {
    loading.value = false
  }
}
</script>
