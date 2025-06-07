<template>
  <div class="main-view-container">
    <header class="main-header">
      <div class="logo-and-title">
        <!-- 建议在此处放置Logo -->
        <!-- <img src="@/assets/logo-small.png" alt="天大点评 Logo" class="header-logo"> -->
        <span class="app-title">天大点评 - 主页面</span>
      </div>
      <div class="user-actions">
        <span class="welcome-message">您好, {{ username }}!</span>
        <!--  实际项目中，这里通常是一个下拉菜单，包含用户头像、个人中心、设置、退出登录等 -->
        <button @click="logout" class="btn btn-outline">
          <i class="fas fa-sign-out-alt"></i> 退出登录
        </button>
      </div>
    </header>

    <main class="main-content">
      <h1 class="page-title">功能导航</h1>
      <p class="page-subtitle">根据您的权限，选择以下操作：</p>

      <div class="navigation-grid">
        <!-- 用户功能卡片 -->
        <router-link to="/user/profile" class="nav-card user-card">
          <div class="nav-card-icon">
            <i class="fas fa-user-circle"></i>
          </div>
          <div class="nav-card-content">
            <h3>用户信息</h3>
            <p>查看和编辑您的个人资料</p>
          </div>
        </router-link>

        <!-- Boss 功能卡片 - 实际应用中应根据用户角色条件渲染 (v-if="userRole === 'boss'") -->
        <router-link to="/boss/statistics" class="nav-card boss-card" v-if="showBossLinks">
          <div class="nav-card-icon">
            <i class="fas fa-chart-line"></i>
          </div>
          <div class="nav-card-content">
            <h3>统计分析</h3>
            <p>查看平台运营数据和分析报告</p>
          </div>
        </router-link>

        <!-- Admin 功能卡片 - 实际应用中应根据用户角色条件渲染 (v-if="userRole === 'admin'") -->
        <router-link to="/admin/bulletins" class="nav-card admin-card" v-if="showAdminLinks">
          <div class="nav-card-icon">
            <i class="fas fa-bullhorn"></i>
          </div>
          <div class="nav-card-content">
            <h3>公告管理</h3>
            <p>发布和管理系统公告信息</p>
          </div>
        </router-link>

        <!-- 通用功能卡片，例如返回应用首页（不是登录前的那个首页） -->
        <!-- 如果有应用内的“发现”或“浏览”首页，可以链接到那里 -->
        <!-- <router-link to="/explore" class="nav-card general-card">
          <div class="nav-card-icon">
            <i class="fas fa-compass"></i>
          </div>
          <div class="nav-card-content">
            <h3>浏览发现</h3>
            <p>探索热门菜品和食堂</p>
          </div>
        </router-link> -->

        <router-link to="/" class="nav-card home-card">
          <div class="nav-card-icon">
            <i class="fas fa-home"></i>
          </div>
          <div class="nav-card-content">
            <h3>返回门户首页</h3>
            <p>回到应用介绍页面</p>
          </div>
        </router-link>
      </div>
    </main>

    <footer class="main-footer">
      <p>&copy; 2024-2025 天大点评. 共享美食时光.</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'MainView',
  data() {
    return {
      // 假设从 Vuex store 或登录逻辑中获取用户名和角色
      username: '示例用户', // 应替换为真实用户名
      userRole: 'user', // 'user', 'admin', 'boss' - 应替换为真实用户角色
    };
  },
  computed: {
    // 根据用户角色判断是否显示特定链接
    showBossLinks() {
      return this.userRole === 'boss';
    },
    showAdminLinks() {
      return this.userRole === 'admin' || this.userRole === 'boss'; // Boss通常也有Admin权限
    }
  },
  methods: {
    logout() {
      // 在这里处理退出登录逻辑
      // 例如：清除token, 调用API，然后跳转到登录页
      console.log('用户请求退出登录');
      this.$router.push('/login'); // 跳转到登录页
    }
  },
  created() {
    // 可以在这里获取用户信息，例如从 store 或 API
    // this.username = this.$store.state.user.name;
    // this.userRole = this.$store.state.user.role;
  }
}
</script>

<style scoped>
.main-view-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f8f9fa; /* 更明亮的背景色 */
  font-family: 'Roboto', 'Helvetica Neue', Arial, sans-serif; /* 现代字体 */
}

.main-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 30px;
  background-color: #ffffff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
  border-bottom: 1px solid #e0e0e0;
  position: sticky;
  top: 0;
  z-index: 1000;
}

.logo-and-title {
  display: flex;
  align-items: center;
}

.header-logo {
  height: 35px; /* 调整Logo大小 */
  margin-right: 15px;
}

.app-title {
  font-size: 1.5em;
  font-weight: 600;
  color: #343a40;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 20px;
}

.welcome-message {
  font-size: 0.95em;
  color: #495057;
}

.btn {
  padding: 8px 15px;
  border-radius: 6px;
  font-size: 0.9em;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.btn i {
  margin-right: 6px;
}

.btn-outline {
  background-color: transparent;
  color: #007bff; /* 主题色 */
  border: 1px solid #007bff;
}

.btn-outline:hover {
  background-color: #007bff;
  color: white;
}

.main-content {
  flex-grow: 1;
  padding: 40px;
  max-width: 1200px;
  margin: 0 auto; /* 内容居中 */
  width: 100%;
}

.page-title {
  font-size: 2.5em;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 10px;
  text-align: center;
}

.page-subtitle {
  font-size: 1.1em;
  color: #555;
  margin-bottom: 40px;
  text-align: center;
}

.navigation-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); /* 响应式网格 */
  gap: 30px; /* 网格间距 */
}

.nav-card {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 25px;
  text-decoration: none;
  color: inherit;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.07);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  align-items: center; /* 图标和文字垂直居中对齐 */
}

.nav-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
}

.nav-card-icon {
  font-size: 2.8em; /* 图标大小 */
  margin-right: 20px;
  padding: 15px;
  border-radius: 50%; /* 圆形背景 */
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70px; /* 固定图标区域宽度 */
  height: 70px; /* 固定图标区域高度 */
}

.nav-card-icon i {
  color: #fff; /* 图标颜色 */
}

/* 不同卡片的背景色 */
.user-card .nav-card-icon { background-color: #3498db; } /* 蓝色 */
.boss-card .nav-card-icon { background-color: #9b59b6; } /* 紫色 */
.admin-card .nav-card-icon { background-color: #e67e22; } /* 橙色 */
.home-card .nav-card-icon { background-color: #2ecc71; } /* 绿色 */
.general-card .nav-card-icon { background-color: #1abc9c; } /* 蓝绿色 */


.nav-card-content h3 {
  font-size: 1.4em;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.nav-card-content p {
  font-size: 0.9em;
  color: #666;
  line-height: 1.5;
}

.main-footer {
  padding: 20px;
  text-align: center;
  font-size: 0.9em;
  color: #777;
  background-color: #e9ecef;
  border-top: 1px solid #dee2e6;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .main-header {
    padding: 15px 20px;
    flex-direction: column;
    gap: 10px;
  }
  .app-title {
    font-size: 1.3em;
  }
  .main-content {
    padding: 25px;
  }
  .page-title {
    font-size: 2em;
  }
  .nav-card {
    flex-direction: column; /* 小屏幕上图标和内容垂直排列 */
    text-align: center;
  }
  .nav-card-icon {
    margin-right: 0;
    margin-bottom: 15px;
  }
}

@media (max-width: 480px) {
  .navigation-grid {
    grid-template-columns: 1fr; /* 最小屏幕下单列显示 */
  }
  .page-title {
    font-size: 1.8em;
  }
  .user-actions .welcome-message {
    display: none; /* 在非常小的屏幕上隐藏欢迎语 */
  }
}
</style>
