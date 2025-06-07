<template>
  <div class="frontend-layout">
    <header class="app-header">
      <div class="container header-container">
        <router-link :to="{ name: 'Home' }" class="logo-link">
          <img src="https://placehold.co/100x40/FFFFFF/005A9C?text=TJU-DP" alt="天大点评 Logo" class="logo-image">
          <span class="logo-text">天大点评</span>
        </router-link>

        <div class="search-bar-container">
          <input type="text" v-model="searchQuery" @keyup.enter="performSearch" placeholder="搜索食堂、餐厅或菜品..." class="search-input">
          <button @click="performSearch" class="search-button">
            <i class="fas fa-search"></i>
          </button>
        </div>

        <nav class="header-nav">
          <template v-if="!isLoggedIn">
            <router-link :to="{ name: 'Register' }" class="nav-button btn-register">
              <i class="fas fa-user-plus"></i> 注册
            </router-link>
            <router-link :to="{ name: 'Login' }" class="nav-button btn-login">
              <i class="fas fa-sign-in-alt"></i> 登录
            </router-link>
          </template>
          <template v-else>
            <router-link :to="{ name: 'UserProfileHome' }" class="nav-button profile-center-btn">
              <img :src="userAvatar" alt="User Avatar" class="user-avatar-small"/>
              <span>{{ username }}</span>
            </router-link>
            <button @click="logout" class="nav-button btn-logout" title="退出登录">
              <i class="fas fa-sign-out-alt"></i>
            </button>
          </template>
        </nav>
      </div>
    </header>

    <main class="main-content">
      <router-view :key="$route.fullPath"></router-view>
    </main>

    <footer class="app-footer">
      <div class="container">
        <p>&copy; {{ new Date().getFullYear() }} 天大点评. 保留所有权利。</p>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'FrontendLayout',
  data() {
    return {
      searchQuery: '',
      authReactivityKey: 0,
    };
  },
  mounted() {
    window.scrollTo(0, 0);
  },
  computed: {
    isLoggedIn() {
      return this.authReactivityKey >= 0 && this.$auth && this.$auth.isLoggedIn() && this.$auth.getUserRole() === 'user';
    },
    username() {
      return this.isLoggedIn ? localStorage.getItem('username') : '';
    },
    userAvatar() {
      return `https://placehold.co/32x32/007BFF/FFFFFF?text=${this.username.charAt(0)}`;
    }
  },
  methods: {
    performSearch() {
      if (this.searchQuery.trim()) {
        this.$router.push({ name: 'SearchResults', query: { q: this.searchQuery.trim() } }).catch(err => {
          if (err.name !== 'NavigationDuplicated') {
            console.error(err);
          }
        });
      } else {
        alert('请输入搜索关键词！');
      }
    },
    logout() {
      if (this.$auth) {
        this.$auth.logout();
        this.authReactivityKey++;
        if (this.$route.name !== 'Home') {
          this.$router.push({ name: 'Home' });
        }
      }
    }
  }
}
</script>

<!--
  FIX: Critical global styles to fix layout issues.
  This non-scoped style tag will reset browser default margins,
  which is the definitive solution for the white space at the top.
-->
<style>
/*
  全局样式重置
  这会移除浏览器为 <html> 和 <body> 元素添加的默认外边距(margin),
  从根本上解决页面顶部出现不应有的白边的问题。
*/
html, body {
  margin: 0 !important;
  padding: 0 !important;
}
</style>

<style scoped>
.frontend-layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f8f9fa; /* Restored a light background color */
}

.app-header {
  background-color: rgba(255, 255, 255, 0.95); /* Slightly less transparent */
  -webkit-backdrop-filter: blur(8px);
  backdrop-filter: blur(8px);
  padding: 15px 0;
  box-shadow: 0 1px 12px rgba(0, 0, 0, 0.08);
  position: -webkit-sticky;
  position: sticky;
  top: 0;
  z-index: 1000;
  border-bottom: 1px solid rgba(0, 0, 0, 0.08);
}

.container {
  width: 90%;
  max-width: 1400px;
  margin: 0 auto;
}

.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-link { text-decoration: none; display: flex; align-items: center; gap: 12px; }
.logo-image { height: 40px; }
.logo-text { font-size: 1.8em; font-weight: 600; color: #333; }

.search-bar-container { display: flex; align-items: center; background-color: #fff; border: 1px solid #dee2e6; border-radius: 8px; flex-grow: 1; margin: 0 30px; max-width: 600px; box-shadow: 0 1px 3px rgba(0,0,0,0.04); }
.search-input { border: none; outline: none; background-color: transparent; font-size: 1em; padding: 10px 15px; flex-grow: 1; color: #495057; }
.search-button { background-color: #007bff; color: white; border: none; border-radius: 0 7px 7px 0; width: 45px; height: 44px; display: flex; align-items: center; justify-content: center; cursor: pointer; transition: background-color 0.2s; }
.search-button:hover { background-color: #0056b3; }

.header-nav { display: flex; align-items: center; gap: 10px; }
.welcome-user { margin-right: 5px; color: #555; font-size: 0.9em; }

.nav-button {
  padding: 8px 16px;
  text-decoration: none;
  border: 1px solid transparent;
  border-radius: 6px;
  font-weight: 500;
  transition: all 0.2s ease;
  font-size: 0.95em;
  display: inline-flex;
  align-items: center;
  gap: 8px;
}
.btn-register { color: #007bff; }
.btn-register:hover { background-color: rgba(0, 123, 255, 0.05); }

.btn-login {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}
.btn-login:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}

.user-avatar-small {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  border: 2px solid #fff;
  box-shadow: 0 0 4px rgba(0,0,0,0.2);
}

.profile-center-btn {
  color: #333;
  font-weight: 500;
}
.profile-center-btn:hover {
  background-color: rgba(0, 0, 0, 0.04);
}
.btn-logout {
  background-color: transparent;
  border: none;
  color: #6c757d;
  font-size: 1.2em; /* Just show the icon */
  padding: 6px 10px;
}
.btn-logout:hover {
  color: #dc3545;
  background-color: #f8f9fa;
}


.main-content {
  flex-grow: 1;
  padding: 30px 0;
}
.app-footer {
  background-color: #343a40;
  color: #f8f9fa;
  padding: 25px 0;
  text-align: center;
  font-size: 0.9em;
  border-top: 1px solid #495057;
}
.app-footer p { margin: 0; }

@media (max-width: 992px) {
  .search-bar-container { max-width: 350px; }
  .logo-text { display:none; }
}
@media (max-width: 768px) {
  .header-container { flex-wrap: wrap; justify-content: space-between; }
  .search-bar-container { order: 3; width: 100%; max-width: none; margin: 15px 0 0 0; }
  .header-nav { order: 2; }
}
</style>
