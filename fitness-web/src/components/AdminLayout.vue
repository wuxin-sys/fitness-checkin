<template>
  <!-- 管理端页面布局：侧边栏 + 内容区 -->
  <el-container class="admin-layout">
    <!-- 侧边栏 -->
    <el-aside class="admin-aside" width="220px">
      <div class="aside-logo" @click="$router.push('/admin/home')">
        ⚙️ 后台管理
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
        class="aside-menu"
      >
        <el-menu-item index="/admin/home">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据概览</span>
        </el-menu-item>
        <el-menu-item index="/admin/user">
          <el-icon><UserFilled /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/checkin">
          <el-icon><Calendar /></el-icon>
          <span>打卡管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/notice">
          <el-icon><Bell /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧内容 -->
    <el-container>
      <!-- 顶栏 -->
      <el-header class="admin-header">
        <span class="admin-title">健身打卡管理系统</span>
        <el-dropdown trigger="click">
          <span class="admin-user">
            <el-icon><UserFilled /></el-icon>
            {{ store.adminInfo?.username }}
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleLogout">
                <el-icon><SwitchButton /></el-icon> 退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>

      <!-- 内容区 -->
      <el-main class="admin-main">
        <slot />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAdminStore } from '@/stores/admin'

const route = useRoute()
const router = useRouter()
const store = useAdminStore()

const activeMenu = computed(() => route.path)

function handleLogout() {
  store.logout()
  router.push('/admin/login')
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
}

.admin-aside {
  background: #304156;
  overflow-y: auto;
}

.aside-logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  font-weight: 700;
  color: #fff;
  cursor: pointer;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.aside-menu {
  border-right: none;
}

.admin-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;
  padding: 0 24px;
  height: 60px;
}

.admin-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.admin-user {
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
  font-size: 14px;
  color: #606266;
}

.admin-main {
  background: #f5f7fa;
  min-height: calc(100vh - 60px);
}
</style>
