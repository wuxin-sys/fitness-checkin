<template>
  <AppLayout>
    <div class="page-container">
      <div class="page-title">健身打卡</div>

      <!-- 操作栏 -->
      <div class="toolbar">
        <el-button type="primary" @click="openAdd">
          <el-icon><Plus /></el-icon> 新增打卡
        </el-button>
      </div>

      <!-- 打卡记录表格 -->
      <el-card shadow="hover">
        <el-table :data="tableData" stripe v-loading="loading" style="width: 100%">
          <el-table-column prop="checkinDate" label="训练日期" width="120" />
          <el-table-column prop="sportType" label="训练类型" width="100">
            <template #default="{ row }">
              <el-tag :type="sportTypeMap[row.sportType] || 'info'" size="small">
                {{ row.sportType }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="duration" label="时长(分钟)" width="110" />
          <el-table-column prop="calorie" label="热量(千卡)" width="110" />
          <el-table-column prop="remark" label="备注" min-width="160" show-overflow-tooltip />
          <el-table-column prop="createTime" label="记录时间" width="170">
            <template #default="{ row }">
              {{ formatDateTime(row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150" fixed="right">
            <template #default="{ row }">
              <el-button text type="primary" size="small" @click="openEdit(row)">修改</el-button>
              <el-button text type="danger" size="small" @click="handleDelete(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination">
          <el-pagination
            v-model:current-page="query.page"
            v-model:page-size="query.pageSize"
            :total="total"
            :page-sizes="[10, 20, 50]"
            layout="total, sizes, prev, pager, next"
            @size-change="loadData"
            @current-change="loadData"
          />
        </div>
      </el-card>

      <!-- 打卡表单弹窗 -->
      <CheckinForm
        v-model:visible="formVisible"
        :is-edit="isEdit"
        :edit-data="editData"
        @submit="handleSubmit"
      />
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import AppLayout from '@/components/AppLayout.vue'
import CheckinForm from '@/components/CheckinForm.vue'
import { pageCheckins, addCheckin, updateCheckin, deleteCheckin } from '@/api/checkin'
import { formatDateTime, sportTypeMap } from '@/utils'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const formVisible = ref(false)
const isEdit = ref(false)
const editData = ref(null)

const query = reactive({
  page: 1,
  pageSize: 10
})

onMounted(() => {
  loadData()
})

async function loadData() {
  loading.value = true
  try {
    const res = await pageCheckins(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } catch {}
  loading.value = false
}

function openAdd() {
  isEdit.value = false
  editData.value = null
  formVisible.value = true
}

function openEdit(row) {
  isEdit.value = true
  editData.value = { ...row }
  formVisible.value = true
}

async function handleSubmit(formData) {
  try {
    if (isEdit.value) {
      await updateCheckin(editData.value.id, formData)
      ElMessage.success('修改成功')
    } else {
      await addCheckin(formData)
      ElMessage.success('打卡成功')
    }
    formVisible.value = false
    loadData()
  } catch {}
}

async function handleDelete(row) {
  await ElMessageBox.confirm('确定删除这条打卡记录吗？', '提示', { type: 'warning' })
  try {
    await deleteCheckin(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch {}
}
</script>

<style scoped>
.toolbar {
  margin-bottom: 16px;
}

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
</style>
