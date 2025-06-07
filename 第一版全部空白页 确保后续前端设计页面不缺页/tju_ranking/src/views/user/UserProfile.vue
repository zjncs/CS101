<template>
  <div class="user-profile">
    <h3>个人资料</h3>
    <div>
      <h1>用户信息</h1>
      <router-link to="/user/dishes"><button>菜品列表</button></router-link>
      <router-link to="/user/canteens"><button>食堂列表</button></router-link>
      <router-link to="/user/blogs"><button>博客列表</button></router-link>
      <router-link to="/main"><button>主页面</button></router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserProfile'
}
</script>

<style scoped>
.user-profile {
  padding: 20px;
}
</style> 