<template>
  <el-dialog
    :model-value="visible"
    :title="isEdit ? '修改打卡' : '新增打卡'"
    width="500px"
    @update:model-value="$emit('update:visible', $event)"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="训练日期" prop="checkinDate">
        <el-date-picker
          v-model="form.checkinDate"
          type="date"
          placeholder="选择日期"
          value-format="YYYY-MM-DD"
          :disabled-date="disabledDate"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="训练类型" prop="sportType">
        <el-select v-model="form.sportType" placeholder="请选择训练类型" style="width: 100%">
          <el-option
            v-for="item in sportTypes"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="训练时长" prop="duration">
        <el-input-number
          v-model="form.duration"
          :min="1"
          :max="600"
          placeholder="分钟"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="消耗热量" prop="calorie">
        <el-input-number
          v-model="form.calorie"
          :min="1"
          :max="9999"
          placeholder="千卡"
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="备注">
        <el-input
          v-model="form.remark"
          type="textarea"
          :maxlength="200"
          show-word-limit
          placeholder="可选，最多200字"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="$emit('update:visible', false)">取消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading">
        {{ isEdit ? '保存修改' : '提交打卡' }}
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { sportTypeList } from '@/utils'

const props = defineProps({
  visible: Boolean,
  isEdit: { type: Boolean, default: false },
  editData: { type: Object, default: null }
})

const emit = defineEmits(['update:visible', 'submit'])

const formRef = ref(null)
const loading = ref(false)
const sportTypes = sportTypeList

const form = reactive({
  checkinDate: '',
  sportType: '',
  duration: null,
  calorie: null,
  remark: ''
})

const rules = {
  checkinDate: [{ required: true, message: '请选择训练日期', trigger: 'change' }],
  sportType: [{ required: true, message: '请选择训练类型', trigger: 'change' }],
  duration: [
    { required: true, message: '请输入训练时长', trigger: 'blur' },
    { type: 'number', min: 1, max: 600, message: '时长应在1-600分钟之间', trigger: 'blur' }
  ],
  calorie: [
    { required: true, message: '请输入消耗热量', trigger: 'blur' },
    { type: 'number', min: 1, max: 9999, message: '热量应在1-9999千卡之间', trigger: 'blur' }
  ]
}

function disabledDate(time) {
  return time.getTime() > Date.now()
}

// 监听弹窗打开，填充编辑数据
watch(() => props.visible, (val) => {
  if (val) {
    if (props.isEdit && props.editData) {
      form.checkinDate = props.editData.checkinDate
      form.sportType = props.editData.sportType
      form.duration = props.editData.duration
      form.calorie = props.editData.calorie
      form.remark = props.editData.remark || ''
    } else {
      resetForm()
    }
  }
})

function resetForm() {
  form.checkinDate = ''
  form.sportType = ''
  form.duration = null
  form.calorie = null
  form.remark = ''
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
