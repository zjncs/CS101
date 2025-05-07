<template>
  <nav class="navbar">
    <div class="container navbar-container">
      <router-link to="/" class="navbar-logo">
        <img src="/logo.png" alt="Logo" class="logo-img" />
        <span class="logo-text">天津大学美食社区</span>
      </router-link>

      <div class="search-bar">
        <input
            type="text"
            v-model="searchQuery"
            @keyup.enter="handleSearch"
            placeholder="搜索菜品、食堂、窗口..."
        />
        <button class="search-button" @click="handleSearch">
          <span class="material-icons">search</span>
        </button>
      </div>

      <div class="nav-links">
        <router-link to="/" class="nav-link">首页</router-link>
        <router-link to="/campus" class="nav-link">校区</router-link>
        <router-link to="/blogs" class="nav-link">博客</router-link>
        <router-link to="/rankings" class="nav-link">排行榜</router-link>
      </div>

      <div class="auth-buttons" v-if="!isAuthenticated">
        <router-link to="/login" class="btn btn-outline">登录</router-link>
        <router-link to="/register" class="btn btn-primary">注册</router-link>
      </div>

      <div class="user-menu" v-else>
        <div class="user-info" @click="toggleUserMenu">
          <img :src="user.avatar" alt="Avatar" class="user-avatar" />
          <span class="user-name">{{ user.nickname }}</span>
          <span class="material-icons">keyboard_arrow_down</span>

          <div class="dropdown-menu" v-if="showUserMenu">
            <router-link to="/profile" class="dropdown-item">个人主页</router-link>
            <a href="#" class="dropdown-item" @click.prevent="handleLogout">退出登录</a>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import { storeToRefs } from 'pinia'

const router = useRouter()
const authStore = useAuthStore()
const { user, isAuthenticated } = storeToRefs(authStore)

const searchQuery = ref('')
const showUserMenu = ref(false)

function handleSearch() {
  if (searchQuery.value.trim()) {
    router.push({
      path: '/search',
      query: { q: searchQuery.value }
    })
    searchQuery.value = ''
  }
}

function toggleUserMenu() {
  showUserMenu.value = !showUserMenu.value
}

function handleLogout() {
  authStore.logout()
  router.push('/')
  showUserMenu.value = false
}
</script>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  z-index: 100;
  background-color: white;
  box-shadow: var(--shadow-sm);
  padding: var(--space-3) 0;
}

.navbar-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.navbar-logo {
  display: flex;
  align-items: center;
  font-size: 1.2rem;
  font-weight: 700;
  color: var(--color-primary-700);
}

.logo-img {
  width: 32px;
  height: 32px;
  margin-right: var(--space-2);
}

.logo-text {
  display: none;
}

.search-bar {
  position: relative;
  flex: 1;
  max-width: 400px;
  margin: 0 var(--space-4);
}

.search-bar input {
  width: 100%;
  padding: var(--space-2) var(--space-6) var(--space-2) var(--space-3);
  border-radius: var(--radius-full);
  border: 1px solid var(--color-gray-300);
  outline: none;
  font-size: 0.9rem;
  transition: all var(--transition-fast);
}

.search-bar input:focus {
  border-color: var(--color-primary-400);
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

.search-button {
  position: absolute;
  right: var(--space-2);
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: var(--color-gray-500);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
}

.search-button:hover {
  color: var(--color-primary-500);
}

.material-icons {
  font-size: 20px;
  display: inline-block;
  width: 1em;
  height: 1em;
  line-height: 1;
  text-transform: none;
  letter-spacing: normal;
  word-wrap: normal;
  white-space: nowrap;
  direction: ltr;
  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
  -moz-osx-font-smoothing: grayscale;
  font-feature-settings: 'liga';
  user-select: none;
}

.nav-links {
  display: none;
}

.nav-link {
  padding: var(--space-2) var(--space-3);
  color: var(--color-gray-700);
  font-weight: 500;
  border-radius: var(--radius-md);
  transition: all var(--transition-fast);
}

.nav-link:hover {
  color: var(--color-primary-600);
  background-color: var(--color-primary-50);
}

.router-link-active {
  color: var(--color-primary-600);
  background-color: var(--color-primary-50);
}

.auth-buttons {
  display: flex;
  gap: var(--space-2);
}

.auth-buttons .btn {
  padding: var(--space-1) var(--space-3);
  font-size: 0.875rem;
}

.user-menu {
  position: relative;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: var(--space-1) var(--space-2);
  border-radius: var(--radius-md);
  transition: all var(--transition-fast);
}

.user-info:hover {
  background-color: var(--color-gray-100);
}

.user-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  display: none;
  margin: 0 var(--space-2);
  font-weight: 500;
  color: var(--color-gray-800);
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: white;
  box-shadow: var(--shadow-md);
  border-radius: var(--radius-md);
  min-width: 150px;
  z-index: 10;
  overflow: hidden;
  margin-top: var(--space-2);
}

.dropdown-item {
  display: block;
  padding: var(--space-3) var(--space-4);
  color: var(--color-gray-700);
  transition: all var(--transition-fast);
}

.dropdown-item:hover {
  background-color: var(--color-gray-100);
  color: var(--color-primary-600);
}

/* Media queries */
@media (min-width: 768px) {
  .logo-text {
    display: block;
  }

  .nav-links {
    display: flex;
    gap: var(--space-1);
  }

  .user-name {
    display: block;
  }
}

@media (max-width: 767px) {
  .search-bar {
    max-width: 200px;
  }

  .auth-buttons .btn-outline {
    display: none;
  }
}
</style>