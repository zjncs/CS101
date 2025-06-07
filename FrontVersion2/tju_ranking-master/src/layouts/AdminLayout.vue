<template>
  <div class="admin-layout">
    <aside class="admin-sidebar">
      <div class="sidebar-header">
        <!-- 你可以在这里放置后台系统的 Logo -->
        <span class="admin-logo-text">天大点评 - 管理后台</span>
      </div>
      <nav class="sidebar-nav">
        <ul>
          <li>
            <!-- FIX: Added the 'exact' prop to ensure this link is only active on the exact path -->
            <router-link :to="{ name: 'AdminDashboard' }" exact active-class="active-link">
              <i class="fas fa-chart-pie"></i> 统计分析
            </router-link>
          </li>
          <li v-if="canAccess(['boss'])">
            <router-link :to="{ name: 'AdminRoleManage' }" active-class="active-link">
              <i class="fas fa-users-cog"></i> 角色管理
            </router-link>
          </li>
          <li v-if="canAccess(['admin', 'boss'])">
            <router-link :to="{ name: 'AdminFoodManagement' }" active-class="active-link">
              <i class="fas fa-utensils"></i> 食堂窗口菜品
            </router-link>
          </li>
          <li v-if="canAccess(['admin', 'boss'])">
            <router-link :to="{ name: 'AdminBlogCommentManage' }" active-class="active-link">
              <i class="fas fa-comments"></i> 博客评论管理
            </router-link>
          </li>
          <li v-if="canAccess(['boss'])"> <!-- 根据你的描述，这个似乎是Boss权限 -->
            <router-link :to="{ name: 'AdminUserPermission' }" active-class="active-link">
              <i class="fas fa-user-shield"></i> 用户权限管理
            </router-link>
          </li>
          <li v-if="canAccess(['boss'])">
            <router-link :to="{ name: 'AdminAnnouncementManage' }" active-class="active-link">
              <i class="fas fa-bullhorn"></i> 公告管理
            </router-link>
          </li>
          <li v-if="canAccess(['admin', 'boss'])">
            <router-link :to="{ name: 'AdminHomepageManage' }" active-class="active-link">
              <i class="fas fa-tachometer-alt"></i> 首页管理
            </router-link>
          </li>
        </ul>
      </nav>
      <div class="sidebar-footer">
        <button @click="logoutAndGoHome" class="btn-logout-admin">
          <i class="fas fa-sign-out-alt"></i> 退出并返回前台
        </button>
      </div>
    </aside>

    <div class="admin-main-panel">
      <header class="admin-top-bar">
        <div class="user-info">
          <i class="fas fa-user-circle"></i>
          <span>{{ adminUsername }} ({{ adminRoleDisplay }})</span>
        </div>
        <!-- 可以添加面包屑导航或快捷操作 -->
      </header>
      <main class="admin-content-area">
        <router-view :key="$route.fullPath"></router-view>
      </main>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminLayout',
  computed: {
    adminUsername() {
      return localStorage.getItem('username') || '管理员';
    },
    adminUserRole() {
      return this.$auth ? this.$auth.getUserRole() : '';
    },
    adminRoleDisplay() {
      if (this.adminUserRole === 'boss') return '平台管理员';
      if (this.adminUserRole === 'admin') return '后台管理员';
      return '未知角色';
    }
  },
  methods: {
    canAccess(requiredRoles) {
      const userRole = this.adminUserRole;
      return userRole && requiredRoles.includes(userRole);
    },
    logoutAndGoHome() {
      if (this.$auth) {
        this.$auth.logout();
        // 强制刷新页面到首页，确保所有状态重置
        window.location.href = '/';
      }
    }
  }
}
</script>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background-color: #f4f6f9; /* 后台常用浅灰色 */
}

.admin-sidebar {
  width: 260px; /* 侧边栏宽度 */
  background-color: #2c3e50; /* 深色侧边栏 */
  color: #ecf0f1;
  display: flex;
  flex-direction: column;
  position: fixed; /* 固定侧边栏 */
  top: 0;
  left: 0;
  bottom: 0;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
  z-index: 1001; /* 比前台header高一级 */
}

.sidebar-header {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #34495e;
}

.admin-logo-text {
  font-size: 1.5em;
  font-weight: bold;
  color: #ffffff;
}

.sidebar-nav {
  flex-grow: 1;
  overflow-y: auto; /* 内容多时可滚动 */
  padding-top: 15px;
}

.sidebar-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar-nav li a {
  display: flex; /* 使图标和文字在同一行并对齐 */
  align-items: center;
  padding: 15px 25px;
  color: #bdc3c7;
  text-decoration: none;
  font-size: 1em;
  transition: background-color 0.2s ease, color 0.2s ease;
  border-left: 3px solid transparent; /* 用于激活状态的指示条 */
}

.sidebar-nav li a i {
  margin-right: 12px;
  width: 20px; /* 固定图标宽度，使文字对齐 */
  text-align: center;
}

.sidebar-nav li a:hover {
  background-color: #34495e;
  color: #ffffff;
}

/* FIX: 使用 'active-link' 作为激活类名 */
.sidebar-nav li a.active-link {
  background-color: #1abc9c; /* 激活状态的背景色 (青色) */
  color: #ffffff;
  font-weight: 600;
  border-left-color: #ffffff; /* 激活指示条颜色 */
}
.sidebar-footer {
  padding: 20px;
  border-top: 1px solid #34495e;
  text-align: center;
}
.btn-logout-admin {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
  font-size: 0.95em;
  transition: background-color 0.2s;
}
.btn-logout-admin:hover {
  background-color: #c0392b;
}
.btn-logout-admin i {
  margin-right: 8px;
}


.admin-main-panel {
  margin-left: 260px; /* 为固定的侧边栏留出空间 */
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.admin-top-bar {
  background-color: #ffffff;
  padding: 0 30px;
  height: 64px; /* 与前台header一致或自定义 */
  display: flex;
  align-items: center;
  justify-content: flex-end; /* 用户信息靠右 */
  box-shadow: 0 1px 3px rgba(0,0,0,0.08);
  border-bottom: 1px solid #e0e0e0;
}

.user-info {
  display: flex;
  align-items: center;
  color: #333;
}
.user-info i {
  font-size: 1.5em;
  margin-right: 10px;
  color: #6c757d;
}
.user-info span {
  font-weight: 500;
}

.admin-content-area {
  flex-grow: 1;
  padding: 25px;
  background-color: #f4f6f9;
  overflow-y: auto; /* 内容多时可滚动 */
}

/* 响应式 - 小屏幕时可以考虑隐藏侧边栏，用汉堡菜单触发 */
@media (max-width: 992px) {
  .admin-sidebar {
    width: 220px;
  }
  .admin-main-panel {
    margin-left: 220px;
  }
  .admin-logo-text {
    font-size: 1.2em;
  }
  .sidebar-nav li a {
    padding: 12px 20px;
    font-size: 0.95em;
  }
}
@media (max-width: 768px) {
  .admin-sidebar {
    width: 60px; /* 仅显示图标 */
  }
  .admin-sidebar .admin-logo-text,
  .admin-sidebar .sidebar-nav li a span:not(i) {
    font-size: 0; /* 隐藏文字 */
  }
  .admin-sidebar .sidebar-nav li a i {
    margin-right: 0;
    font-size: 1.4em; /* 图标放大些 */
  }
  .admin-sidebar .sidebar-header {
    padding: 15px 5px;
  }
  .admin-sidebar .sidebar-nav li a {
    justify-content: center; /* 图标居中 */
  }
  .sidebar-footer {
    padding: 10px 5px;
  }
  .btn-logout-admin {
    font-size: 0; /* 隐藏文字 */
  }
  .btn-logout-admin i {
    margin-right: 0;
    font-size: 1.3em;
  }


  .admin-main-panel {
    margin-left: 60px;
  }
  .admin-top-bar {
    padding: 0 15px;
  }
}
</style>
