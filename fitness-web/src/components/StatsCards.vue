<template>
  <!-- 统计卡片组 -->
  <div class="stats-cards" :class="`cols-${cards.length}`">
    <el-card v-for="card in cards" :key="card.label" shadow="hover" class="stat-card">
      <div class="stat-label">{{ card.label }}</div>
      <div class="stat-value" :style="{ color: card.color || '#409eff' }">
        {{ card.value }}
      </div>
      <div v-if="card.sub" class="stat-sub">{{ card.sub }}</div>
    </el-card>
  </div>
</template>

<script setup>
defineProps({
  cards: {
    type: Array,
    required: true
    // [{ label: '今日打卡', value: '已打卡', color: '#67c23a', sub: '' }]
  }
})
</script>

<style scoped>
.stats-cards {
  display: grid;
  gap: 16px;
  margin-bottom: 24px;
}

.stats-cards.cols-3 {
  grid-template-columns: repeat(3, 1fr);
}

.stats-cards.cols-4 {
  grid-template-columns: repeat(4, 1fr);
}

@media (max-width: 768px) {
  .stats-cards.cols-3,
  .stats-cards.cols-4 {
    grid-template-columns: repeat(2, 1fr);
  }
}

.stat-card {
  text-align: center;
  padding: 8px 0;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 12px;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 4px;
}

.stat-sub {
  font-size: 12px;
  color: #c0c4cc;
}
</style>
