<template>
  <AdminLayout>
    <div class="page-container">
      <div class="page-title">公告管理</div>

      <el-card shadow="hover">
        <!-- 操作栏 -->
        <div class="toolbar">
          <el-button type="primary" @click="openAdd">
            <el-icon><Plus /></el-icon> 发布公告
          </el-button>
        </div>

        <!-- 表格 -->
        <el-table :data="tableData" stripe v-loading="loading" style="width: 100%">
          <el-table-column prop="id" label="ID" width="70" />
          <el-table-column prop="title" label="标题" min-width="200" show-overflow-tooltip />
          <el-table-column prop="createTime" label="发布时间" width="170">
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

      <!-- 公告表单弹窗 -->
      <NoticeForm
        v-model:visible="formVisible"
        :is-edit="isEdit"
        :edit-data="editData"
        @submit="handleSubmit"
      />
    </div>
  </AdminLayout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import AdminLayout from '@/components/AdminLayout.vue'
import NoticeForm from '@/components/NoticeForm.vue'
import { pageAdminNotices, addNotice, updateNotice, deleteNotice } from '@/api/admin'
import { formatDateTime } from '@/utils'

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
    const res = await pageAdminNotices(query)
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
      await updateNotice(editData.value.id, formData)
      ElMessage.success('公告修改成功')
    } else {
      await addNotice(formData)
      ElMessage.success('公告发布成功')
    }
    formVisible.value = false
    loadData()
  } catch {}
}

async function handleDelete(row) {
  await ElMessageBox.confirm('确定删除这条公告吗？', '提示', { type: 'warning' })
  try {
    await deleteNotice(row.id)
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
