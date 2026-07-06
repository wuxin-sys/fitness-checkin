<template>
  <AppLayout>
    <div class="page-container">
      <div class="page-title">数据统计</div>

      <!-- 统计卡片 -->
      <StatsCards :cards="cards" />

      <!-- 图表区域 -->
      <el-row :gutter="16">
        <el-col :span="14">
          <el-card shadow="hover">
            <template #header>
              <span>📊 每月打卡趋势（近6个月）</span>
            </template>
            <MonthChart :data="monthlyData" />
          </el-card>
        </el-col>
        <el-col :span="10">
          <el-card shadow="hover">
            <template #header>
              <span>🥧 训练类型占比</span>
            </template>
            <TypePieChart :data="typeData" />
          </el-card>
        </el-col>
      </el-row>
    </div>
  </AppLayout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import AppLayout from '@/components/AppLayout.vue'
import StatsCards from '@/components/StatsCards.vue'
import MonthChart from '@/components/MonthChart.vue'
import TypePieChart from '@/components/TypePieChart.vue'
import { getWeekCount, getMonthCount, getMonthlyStats, getTypeStats } from '@/api/checkin'

const weekData = ref({})
const monthData = ref({})
const monthlyData = ref([])
const typeData = ref([])

const cards = computed(() => [
  { label: '本周打卡', value: `${weekData.value.count || 0} 次`, color: '#409eff' },
  { label: '本月打卡', value: `${monthData.value.count || 0} 次`, color: '#67c23a' }
])

onMounted(async () => {
  try {
    const [week, month, monthly, type] = await Promise.all([
      getWeekCount(),
      getMonthCount(),
      getMonthlyStats(),
      getTypeStats()
    ])
    weekData.value = week.data
    monthData.value = month.data
    monthlyData.value = monthly.data || []
    typeData.value = type.data || []
  } catch {}
})
</script>
