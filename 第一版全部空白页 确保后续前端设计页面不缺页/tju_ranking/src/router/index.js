import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import(/* webpackChunkName: "home" */ '../views/HomeView.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import(/* webpackChunkName: "login" */ '../views/LoginView.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import(/* webpackChunkName: "register" */ '../views/RegisterView.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/main',
      name: 'main',
      component: () => import(/* webpackChunkName: "main" */ '../views/MainView.vue'),
      meta: { requiresAuth: true }
    },
    // user 子路由
    {
      path: '/user/profile',
      name: 'user-profile',
      component: () => import(/* webpackChunkName: "user-profile" */ '../views/user/UserProfile.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/user/dishes',
      name: 'user-dishes',
      component: () => import(/* webpackChunkName: "user-dishes" */ '../views/user/DishesList.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/user/canteens',
      name: 'user-canteens',
      component: () => import(/* webpackChunkName: "user-canteens" */ '../views/user/CanteensList.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/user/blogs',
      name: 'user-blogs',
      component: () => import(/* webpackChunkName: "user-blogs" */ '../views/user/BlogsList.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/user/add-blog',
      name: 'user-add-blog',
      component: () => import(/* webpackChunkName: "user-add-blog" */ '../views/user/AddBlog.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/user/canteen-details',
      name: 'user-canteen-details',
      component: () => import(/* webpackChunkName: "user-canteen-details" */ '../views/user/CanteenDetails.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/user/blog-details',
      name: 'user-blog-details',
      component: () => import(/* webpackChunkName: "user-blog-details" */ '../views/user/BlogDetails.vue'),
      meta: { requiresAuth: true }
    },
    // boss 子路由
    {
      path: '/boss/statistics',
      name: 'boss-statistics',
      component: () => import(/* webpackChunkName: "boss-statistics" */ '../views/boss/StatisticsAnalytics.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/boss/role',
      name: 'boss-role',
      component: () => import(/* webpackChunkName: "boss-role" */ '../views/boss/RoleManage.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/boss/program',
      name: 'boss-program',
      component: () => import(/* webpackChunkName: "boss-program" */ '../views/boss/ProgrameManage.vue'),
      meta: { requiresAuth: true }
    },
    // admin 子路由
    {
      path: '/admin/bulletins',
      name: 'admin-bulletins',
      component: () => import(/* webpackChunkName: "admin-bulletins" */ '../views/admin/BulletinsManage.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/homepage',
      name: 'admin-homepage',
      component: () => import(/* webpackChunkName: "admin-homepage" */ '../views/admin/HomepageManage.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/data',
      name: 'admin-data',
      component: () => import(/* webpackChunkName: "admin-data" */ '../views/admin/DataManage.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/blogs',
      name: 'admin-blogs',
      component: () => import(/* webpackChunkName: "admin-blogs" */ '../views/admin/BlogsManage.vue'),
      meta: { requiresAuth: true }
    }
  ]
})
