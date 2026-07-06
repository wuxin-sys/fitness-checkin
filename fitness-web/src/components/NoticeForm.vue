<template>
  <el-dialog
    :model-value="visible"
    :title="isEdit ? '修改公告' : '发布公告'"
    width="560px"
    @update:model-value="$emit('update:visible', $event)"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="公告标题" prop="title">
        <el-input v-model="form.title" placeholder="请输入公告标题" maxlength="100" show-word-limit />
      </el-form-item>
      <el-form-item label="公告内容" prop="content">
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="6"
          placeholder="请输入公告内容"
        />
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
  title: '',
  content: ''
})

const rules = {
  title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
}

watch(() => props.visible, (val) => {
  if (val) {
    if (props.isEdit && props.editData) {
      form.title = props.editData.title
      form.content = props.editData.content
    } else {
      resetForm()
    }
  }
})

function resetForm() {
  form.title = ''
  form.content = ''
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
