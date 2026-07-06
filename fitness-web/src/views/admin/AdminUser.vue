<template>
  <AdminLayout>
    <div class="page-container">
      <div class="page-title">用户管理</div>

      <el-card shadow="hover">
        <!-- 搜索 + 新增 -->
        <div class="toolbar">
          <el-input
            v-model="query.keyword"
            placeholder="搜索用户名或昵称"
            clearable
            style="width: 240px"
            @clear="loadData"
            @keyup.enter="loadData"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          <el-button type="primary" @click="search">
            <el-icon><Search /></el-icon> 搜索
          </el-button>
          <el-button type="success" @click="openAdd">
            <el-icon><Plus /></el-icon> 新增用户
          </el-button>
        </div>

        <!-- 表格 -->
        <el-table :data="tableData" stripe v-loading="loading" style="width: 100%">
          <el-table-column prop="id" label="ID" width="70" />
          <el-table-column prop="username" label="用户名" width="130" />
          <el-table-column prop="nickname" label="昵称" width="130" />
          <el-table-column prop="gender" label="性别" width="70">
            <template #default="{ row }">
              {{ row.gender === 1 ? '男' : row.gender === 2 ? '女' : '未知' }}
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="手机号" width="140" />
          <el-table-column prop="createTime" label="注册时间" min-width="160">
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

      <!-- 用户表单弹窗 -->
      <UserForm
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
import UserForm from '@/components/UserForm.vue'
import { pageUsers, addUser, updateUser, deleteUser } from '@/api/admin'
import { formatDateTime } from '@/utils'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const formVisible = ref(false)
const isEdit = ref(false)
const editData = ref(null)

const query = reactive({
  page: 1,
  pageSize: 10,
  keyword: ''
})

onMounted(() => {
  loadData()
})

function search() {
  query.page = 1
  loadData()
}

async function loadData() {
  loading.value = true
  try {
    const res = await pageUsers(query)
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
      await updateUser(editData.value.id, formData)
      ElMessage.success('用户修改成功')
    } else {
      await addUser(formData)
      ElMessage.success('用户添加成功')
    }
    formVisible.value = false
    loadData()
  } catch {}
}

async function handleDelete(row) {
  await ElMessageBox.confirm(
    `确定删除用户「${row.username}」吗？该用户的所有打卡记录将一并删除！`,
    '警告',
    { type: 'warning', confirmButtonText: '确定删除' }
  )
  try {
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch {}
}
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
</style>
