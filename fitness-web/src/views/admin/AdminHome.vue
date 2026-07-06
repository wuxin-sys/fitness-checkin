<template>
  <AdminLayout>
    <div class="page-container">
      <div class="page-title">数据概览</div>

      <StatsCards :cards="cards" />
    </div>
  </AdminLayout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import AdminLayout from '@/components/AdminLayout.vue'
import StatsCards from '@/components/StatsCards.vue'
import { getAdminStats } from '@/api/admin'

const stats = ref({})

const cards = computed(() => [
  { label: '用户总数', value: stats.value.userCount ?? 0, color: '#409eff' },
  { label: '打卡总数', value: stats.value.checkinCount ?? 0, color: '#67c23a' },
  { label: '今日打卡人数', value: stats.value.todayCheckinCount ?? 0, color: '#e6a23c' }
])

onMounted(async () => {
  try {
    const res = await getAdminStats()
    stats.value = res.data
  } catch {}
})
</script>
