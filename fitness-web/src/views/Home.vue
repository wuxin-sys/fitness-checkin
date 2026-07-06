<template>
  <AppLayout>
    <div class="page-container">
      <div class="page-title">首页</div>

      <!-- 数据概览卡片 -->
      <StatsCards :cards="cards" />

      <!-- 最新公告 -->
      <el-card v-if="latestNotice" shadow="hover" class="notice-card">
        <template #header>
          <div class="notice-header">
            <span>📢 最新公告</span>
            <el-button text type="primary" @click="$router.push('/notice')">查看更多</el-button>
          </div>
        </template>
        <div class="notice-title">{{ latestNotice.title }}</div>
        <div class="notice-content">{{ latestNotice.content }}</div>
      </el-card>
      <el-card v-else shadow="hover" class="notice-card">
        <div class="no-data">暂无公告</div>
      </el-card>
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import AppLayout from '@/components/AppLayout.vue'
import StatsCards from '@/components/StatsCards.vue'
import { getTodayStatus, getStreak, getWeekCount } from '@/api/checkin'
import { getLatestNotice } from '@/api/notice'

const todayStatus = ref({})
const streakData = ref({})
const weekData = ref({})
const latestNotice = ref(null)

const cards = computed(() => [
  {
    label: '今日打卡',
    value: todayStatus.value.checked ? '已打卡' : '未打卡',
    color: todayStatus.value.checked ? '#67c23a' : '#f56c6c'
  },
  {
    label: '连续打卡',
    value: `${streakData.value.streak || 0} 天`,
    color: '#409eff'
  },
  {
    label: '本周打卡',
    value: `${weekData.value.count || 0} 次`,
    color: '#e6a23c'
  }
])

onMounted(async () => {
  try {
    const [today, streak, week, notice] = await Promise.all([
      getTodayStatus(),
      getStreak(),
      getWeekCount(),
      getLatestNotice()
    ])
    todayStatus.value = today.data
    streakData.value = streak.data
    weekData.value = week.data
    latestNotice.value = notice.data
  } catch {}
})
</script>

<style scoped>
.notice-card {
  max-width: 600px;
}

.notice-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.notice-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 8px;
  color: #303133;
}

.notice-content {
  font-size: 14px;
  color: #606266;
  line-height: 1.8;
  white-space: pre-wrap;
}

.no-data {
  text-align: center;
  color: #c0c4cc;
  padding: 24px 0;
}
</style>
