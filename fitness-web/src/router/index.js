import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  // 用户端
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/checkin',
    name: 'Checkin',
    component: () => import('@/views/Checkin.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/stats',
    name: 'Stats',
    component: () => import('@/views/Stats.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/notice',
    name: 'Notice',
    component: () => import('@/views/Notice.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/Profile.vue'),
    meta: { requiresAuth: true }
  },
  // 管理端
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('@/views/admin/AdminLogin.vue')
  },
  {
    path: '/admin',
    redirect: '/admin/home'
  },
  {
    path: '/admin/home',
    name: 'AdminHome',
    component: () => import('@/views/admin/AdminHome.vue'),
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin/user',
    name: 'AdminUser',
    component: () => import('@/views/admin/AdminUser.vue'),
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin/checkin',
    name: 'AdminCheckin',
    component: () => import('@/views/admin/AdminCheckin.vue'),
    meta: { requiresAdmin: true }
  },
  {
    path: '/admin/notice',
    name: 'AdminNotice',
    component: () => import('@/views/admin/AdminNotice.vue'),
    meta: { requiresAdmin: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userToken = localStorage.getItem('token')
  const adminToken = localStorage.getItem('adminToken')

  if (to.meta.requiresAuth && !userToken) {
    next('/login')
  } else if (to.meta.requiresAdmin && !adminToken) {
    next('/admin/login')
  } else {
    next()
  }
})

export default router
