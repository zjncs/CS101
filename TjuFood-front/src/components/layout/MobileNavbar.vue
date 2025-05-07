<template>
  <div class="mobile-navbar">
    <router-link to="/" class="nav-item" :class="{ active: $route.path === '/' }">
      <span class="material-icons">home</span>
      <span class="nav-text">首页</span>
    </router-link>

    <router-link to="/campus" class="nav-item" :class="{ active: $route.path.startsWith('/campus') }">
      <span class="material-icons">location_on</span>
      <span class="nav-text">校区</span>
    </router-link>

    <router-link to="/search" class="nav-item" :class="{ active: $route.path === '/search' }">
      <span class="material-icons">search</span>
      <span class="nav-text">搜索</span>
    </router-link>

    <router-link to="/blogs" class="nav-item" :class="{ active: $route.path.startsWith('/blog') }">
      <span class="material-icons">article</span>
      <span class="nav-text">博客</span>
    </router-link>

    <router-link to="/profile" class="nav-item" :class="{ active: $route.path === '/profile' }">
      <span class="material-icons">person</span>
      <span class="nav-text">我的</span>
    </router-link>
  </div>
</template>

<script setup>
// Mobile navbar component
</script>

<style scoped>
.mobile-navbar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: white;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
  display: flex;
  justify-content: space-around;
  padding: var(--space-2) 0;
  z-index: 100;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: var(--color-gray-500);
  font-size: 0.75rem;
  transition: color var(--transition-fast);
}

.nav-item.active {
  color: var(--color-primary-600);
}

.nav-item .material-icons {
  font-size: 24px;
  margin-bottom: var(--space-1);
}

.nav-text {
  font-size: 12px;
}

@media (min-width: 768px) {
  .mobile-navbar {
    display: none;
  }
}
</style>