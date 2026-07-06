<template>
  <AppLayout>
    <div class="page-container">
      <div class="page-title">公告</div>

      <el-card shadow="hover">
        <div v-if="notices.length === 0 && !loading" class="no-data">暂无公告</div>

        <div v-for="notice in notices" :key="notice.id" class="notice-item" @click="showDetail(notice)">
          <div class="notice-item-title">{{ notice.title }}</div>
          <div class="notice-item-time">{{ formatDateTime(notice.createTime) }}</div>
        </div>

        <div class="pagination" v-if="total > 0">
          <el-pagination
            v-model:current-page="query.page"
            v-model:page-size="query.pageSize"
            :total="total"
            layout="total, prev, pager, next"
            @current-change="loadData"
          />
        </div>
      </el-card>

      <!-- 公告详情弹窗 -->
      <el-dialog v-model="detailVisible" :title="currentNotice?.title" width="560px">
        <div class="detail-content">{{ currentNotice?.content }}</div>
        <div class="detail-time" v-if="currentNotice">
          发布时间：{{ formatDateTime(currentNotice.createTime) }}
        </div>
      </el-dialog>
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import AppLayout from '@/components/AppLayout.vue'
import { pageNotices } from '@/api/notice'
import { formatDateTime } from '@/utils'

const loading = ref(false)
const notices = ref([])
const total = ref(0)
const detailVisible = ref(false)
const currentNotice = ref(null)

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
    const res = await pageNotices(query)
    notices.value = res.data.records
    total.value = res.data.total
  } catch {}
  loading.value = false
}

function showDetail(notice) {
  currentNotice.value = notice
  detailVisible.value = true
}
</script>

<style scoped>
.notice-item {
  padding: 16px 0;
  border-bottom: 1px solid #ebeef5;
  cursor: pointer;
  transition: background 0.2s;
}

.notice-item:hover {
  background: #f5f7fa;
  margin: 0 -20px;
  padding: 16px 20px;
}

.notice-item:last-child {
  border-bottom: none;
}

.notice-item-title {
  font-size: 15px;
  font-weight: 500;
  color: #303133;
  margin-bottom: 6px;
}

.notice-item-time {
  font-size: 12px;
  color: #c0c4cc;
}

.detail-content {
  font-size: 14px;
  color: #606266;
  line-height: 1.8;
  white-space: pre-wrap;
  margin-bottom: 16px;
}

.detail-time {
  font-size: 12px;
  color: #c0c4cc;
  text-align: right;
}

.no-data {
  text-align: center;
  color: #c0c4cc;
  padding: 40px 0;
}

.pagination {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
</style>
