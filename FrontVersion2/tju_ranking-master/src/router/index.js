import Vue from 'vue'
import Router from 'vue-router'

// 布局组件
import FrontendLayout from '../layouts/FrontendLayout.vue'
import AdminLayout from '../layouts/AdminLayout.vue'

// 前台页面组件
import HomeView from '../views/frontend/HomeView.vue'
import LoginView from '../views/frontend/LoginView.vue'
import RegisterView from '../views/frontend/RegisterView.vue'
import CreateBlogView from '../views/frontend/CreateBlogView.vue'
import SearchResultsView from '../views/frontend/SearchResultsView.vue'
import WindowDetailView from '../views/frontend/WindowDetailView.vue'
import DishDetailView from '../views/frontend/DishDetailView.vue'
import BlogDetailView from '../views/frontend/BlogDetailView.vue'
import ForgotPasswordView from '../views/frontend/ForgotPasswordView.vue' // 新增导入

// 用户个人主页相关组件
import UserProfileLayout from '../views/frontend/user/UserProfileLayout.vue'
import UserProfileHomeView from '../views/frontend/user/UserProfileHomeView.vue'
import UserCommentsView from '../views/frontend/user/UserCommentsView.vue'
import UserFavoritesView from '../views/frontend/user/UserFavoritesView.vue'
import EditProfileView from '../views/frontend/user/EditProfileView.vue'

// 后台管理页面组件
import AdminDashboardView from '../views/admin/AdminDashboardView.vue'
import AdminRoleManageView from '../views/admin/AdminRoleManageView.vue'
import AdminFoodManagementView from '../views/admin/AdminFoodManagementView.vue'
import AdminBlogCommentManageView from '../views/admin/AdminBlogCommentManageView.vue'
import AdminUserPermissionView from '../views/admin/AdminUserPermissionView.vue' // 用户权限管理 (Boss)
import AdminAnnouncementManageView from '../views/admin/AdminAnnouncementManageView.vue'
import AdminHomepageManageView from '../views/admin/AdminHomepageManageView.vue'

Vue.use(Router)

// 简易的登录状态管理 (实际项目中应使用Vuex或类似方案)
const auth = {
  isLoggedIn: () => !!localStorage.getItem('userRole'),
  getUserRole: () => localStorage.getItem('userRole'),
  login(role, username = '用户') {
    localStorage.setItem('userRole', role);
    localStorage.setItem('username', username);
  },
  logout() {
    localStorage.removeItem('userRole');
    localStorage.removeItem('username');
  }
};
// 初始化一个超级管理员，如果不存在
if (!localStorage.getItem('superadmin_init')) {
  // 在实际应用中，超级管理员通常不由前端创建或硬编码密码在此处。
  // 此处仅为满足需求描述中的“写死在代码里”，但实际应由后端管理。
  console.log("模拟超级管理员信息（仅用于演示，实际应由后端处理）：admin/admin");
  localStorage.setItem('superadmin_init', 'true');
}


const router = new Router({
  mode: 'history', // 可选，使用 history 模式去除 URL 中的 '#'
  routes: [
    // 前台路由 (使用 FrontendLayout)
    {
      path: '/',
      component: FrontendLayout,
      children: [
        {
          path: '',
          name: 'Home',
          component: HomeView,
        },
        {
          path: 'create-blog',
          name: 'CreateBlog',
          component: CreateBlogView,
          meta: { requiresAuth: true, roles: ['user'] } // 仅限登录用户
        },
        {
          path: 'search', // 例如: /search?query=食堂
          name: 'SearchResults',
          component: SearchResultsView,
        },
        {
          path: 'window/:id', // 例如: /window/1
          name: 'WindowDetail',
          component: WindowDetailView,
        },
        {
          path: 'dish/:id', // 例如: /dish/1
          name: 'DishDetail',
          component: DishDetailView,
        },
        {
          path: 'blog/:id', // 例如: /blog/101
          name: 'BlogDetail',
          component: BlogDetailView,
        },
        // 用户个人主页的路由组
        {
          path: 'profile', // 父路由，用于包裹个人主页相关页面
          component: UserProfileLayout, // 使用个人主页的专用布局
          meta: { requiresAuth: true, roles: ['user'] },
          children: [
            {
              path: '', // 默认路径 /profile, 指向动态页
              name: 'UserProfileHome',
              component: UserProfileHomeView,
            },
            {
              path: 'comments', // 路径 /profile/comments
              name: 'UserComments',
              component: UserCommentsView,
            },
            {
              path: 'favorites', // 路径 /profile/favorites
              name: 'UserFavorites',
              component: UserFavoritesView,
            }
          ]
        },
        {
          path: 'profile/edit', // 单独的编辑资料页
          name: 'EditProfile',
          component: EditProfileView,
          meta: { requiresAuth: true, roles: ['user'] }
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginView,
      beforeEnter: (to, from, next) => { // 如果已登录，尝试访问登录页则重定向
        if (auth.isLoggedIn()) {
          const role = auth.getUserRole();
          if (role === 'user') {
            next({ name: 'Home' });
          } else if (role === 'admin' || role === 'boss') {
            next({ name: 'AdminDashboard' });
          } else {
            auth.logout(); // 未知角色，登出
            next();
          }
        } else {
          next();
        }
      }
    },
    {
      path: '/register',
      name: 'Register',
      component: RegisterView,
    },
    // 新增：忘记密码页路由
    {
      path: '/forgot-password',
      name: 'ForgotPassword',
      component: ForgotPasswordView,
    },

    // 后台管理路由 (使用 AdminLayout)
    {
      path: '/admin',
      component: AdminLayout,
      meta: { requiresAuth: true, roles: ['admin', 'boss'] }, // 需要管理员或平台管理员权限
      children: [
        {
          path: '', // 默认指向仪表盘/统计分析
          name: 'AdminDashboard',
          component: AdminDashboardView,
          // 统计分析页默认对所有后台角色开放，具体内容显示可再细分
        },
        {
          path: 'role-management',
          name: 'AdminRoleManage',
          component: AdminRoleManageView,
          meta: { roles: ['boss'] } // 仅限平台管理员 (Boss)
        },
        {
          path: 'food-management',
          name: 'AdminFoodManagement',
          component: AdminFoodManagementView,
          meta: { roles: ['admin', 'boss'] } // 后台管理员和平台管理员
        },
        {
          path: 'blog-comment-management',
          name: 'AdminBlogCommentManage',
          component: AdminBlogCommentManageView,
          meta: { roles: ['admin', 'boss'] } // 后台管理员和平台管理员
        },
        {
          path: 'user-permission', // 用户权限管理
          name: 'AdminUserPermission',
          component: AdminUserPermissionView,
          meta: { roles: ['boss'] } // 仅限平台管理员 (Boss)
        },
        {
          path: 'announcement-management',
          name: 'AdminAnnouncementManage',
          component: AdminAnnouncementManageView,
          meta: { roles: ['boss'] } // 仅限平台管理员 (Boss)
        },
        {
          path: 'homepage-management',
          name: 'AdminHomepageManage',
          component: AdminHomepageManageView,
          meta: { roles: ['admin', 'boss'] } // 后台管理员和平台管理员
        }
      ]
    },
    // 404 页面 (可选)
    // { path: '*', component: NotFoundView }
  ]
});

// 全局前置守卫
router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const requiredRoles = to.matched.reduce((acc, record) => {
    if (record.meta.roles) {
      acc.push(...record.meta.roles);
    }
    return acc;
  }, []);

  if (requiresAuth) {
    if (!auth.isLoggedIn()) {
      // 如果未登录且访问需要授权的页面，则重定向到登录页
      alert('请先登录后再访问此页面。'); // 简单提示
      next({ name: 'Login', query: { redirect: to.fullPath } }); // 保存原目标路径，登录后可跳回
    } else {
      const userRole = auth.getUserRole();
      if (requiredRoles.length > 0 && !requiredRoles.includes(userRole)) {
        // 如果角色不匹配
        alert('您没有权限访问此页面。'); // 简单提示
        // 根据角色尝试重定向到合适的首页
        if (userRole === 'user') {
          next({ name: 'Home' });
        } else if (userRole === 'admin' || userRole === 'boss') {
          next({ name: 'AdminDashboard' });
        } else { // 未知或无默认首页的角色
          auth.logout();
          next({ name: 'Login' });
        }
      } else {
        // 身份验证通过
        next();
      }
    }
  } else {
    // 不需要授权的页面，直接进入
    next();
  }
});

// 将auth对象挂载到Vue实例，方便组件内使用 (可选，更推荐Vuex)
Vue.prototype.$auth = auth;

export default router;
