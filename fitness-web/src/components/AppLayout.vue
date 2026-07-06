<template>
  <!-- 用户端页面布局：顶部导航 + 内容区 + 底部 -->
  <el-container class="app-layout">
    <!-- 顶部导航 -->
    <el-header class="app-header">
      <div class="header-left">
        <span class="logo">💪 健身打卡</span>
      </div>
      <div class="header-center">
        <el-menu
          :default-active="activeMenu"
          mode="horizontal"
          :ellipsis="false"
          router
          class="header-menu"
        >
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item index="/checkin">健身打卡</el-menu-item>
          <el-menu-item index="/stats">数据统计</el-menu-item>
          <el-menu-item index="/notice">公告</el-menu-item>
        </el-menu>
      </div>
      <div class="header-right">
        <el-dropdown trigger="click">
          <span class="user-info">
            <el-icon><UserFilled /></el-icon>
            {{ store.userInfo?.nickname || store.userInfo?.username }}
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="$router.push('/profile')">
                <el-icon><User /></el-icon> 个人中心
              </el-dropdown-item>
              <el-dropdown-item divided @click="handleLogout">
                <el-icon><SwitchButton /></el-icon> 退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <!-- 内容区 -->
    <el-main class="app-main">
      <slot />
    </el-main>

    <!-- 底部 -->
    <el-footer class="app-footer">
      <span>© 2026 健身打卡系统 — 毕业设计作品</span>
    </el-footer>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const store = useUserStore()

const activeMenu = computed(() => route.path)

function handleLogout() {
  store.logout()
  router.push('/login')
}
</script>

<style scoped>
.app-layout {
  min-height: 100vh;
  background: #f5f7fa;
}

.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  padding: 0 32px;
  height: 60px;
}

.header-left .logo {
  font-size: 18px;
  font-weight: 700;
  color: #409eff;
  white-space: nowrap;
}

.header-menu {
  border-bottom: none !important;
}

.header-menu .el-menu-item {
  height: 60px;
  line-height: 60px;
  font-size: 15px;
}

.header-right {
  cursor: pointer;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: #606266;
}

.app-main {
  min-height: calc(100vh - 100px);
  padding: 0;
}

.app-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
  font-size: 12px;
  color: #909399;
  background: #fff;
  border-top: 1px solid #e4e7ed;
}
</style>
