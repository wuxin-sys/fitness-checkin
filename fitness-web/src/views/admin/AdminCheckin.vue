<template>
  <AdminLayout>
    <div class="page-container">
      <div class="page-title">打卡管理</div>

      <el-card shadow="hover">
        <!-- 搜索栏 -->
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
        </div>

        <!-- 表格 -->
        <el-table :data="tableData" stripe v-loading="loading" style="width: 100%">
          <el-table-column prop="id" label="ID" width="70" />
          <el-table-column prop="username" label="用户名" width="120" />
          <el-table-column prop="nickname" label="昵称" width="120" />
          <el-table-column prop="checkinDate" label="训练日期" width="120" />
          <el-table-column prop="sportType" label="训练类型" width="100">
            <template #default="{ row }">
              <el-tag :type="sportTypeMap[row.sportType] || 'info'" size="small">
                {{ row.sportType }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="duration" label="时长(分钟)" width="100" />
          <el-table-column prop="calorie" label="热量(千卡)" width="100" />
          <el-table-column prop="remark" label="备注" min-width="140" show-overflow-tooltip />
          <el-table-column prop="createTime" label="记录时间" width="170">
            <template #default="{ row }">
              {{ formatDateTime(row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="90" fixed="right">
            <template #default="{ row }">
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
    </div>
  </AdminLayout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import AdminLayout from '@/components/AdminLayout.vue'
import { pageAllCheckins, adminDeleteCheckin } from '@/api/admin'
import { formatDateTime, sportTypeMap } from '@/utils'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)

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
    const res = await pageAllCheckins(query)
    tableData.value = res.data.records
    total.value = res.data.total
  } catch {}
  loading.value = false
}

async function handleDelete(row) {
  await ElMessageBox.confirm('确定删除这条打卡记录吗？', '提示', { type: 'warning' })
  try {
    await adminDeleteCheckin(row.id)
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
}

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
</style>
