<template>
  <div class="user-profile-layout container">
    <div class="profile-header-card">
      <div class="profile-info-main">
        <div class="avatar-container">
          <img :src="userData.avatarUrl || 'https://placehold.co/120x120/EFEFEF/AAAAAA?text=Avatar'" alt="User Avatar" class="profile-avatar">
        </div>
        <div class="info-text">
          <div class="name-and-edit">
            <h1 class="user-name">{{ userData.name }}</h1>
            <router-link :to="{ name: 'EditProfile' }" class="btn btn-edit-profile">
              <i class="fas fa-edit"></i> 编辑资料
            </router-link>
          </div>
          <div class="user-stats">
            <span><strong>{{ userData.followingCount }}</strong> 关注</span>
            <span><strong>{{ userData.followerCount }}</strong> 粉丝</span>
            <span><strong>{{ userData.likesReceived }}</strong> 获赞</span>
          </div>
          <p class="register-date">注册时间: {{ formatDate(userData.registerDate) }}</p>
        </div>
      </div>
    </div>

    <div class="profile-content-card">
      <nav class="profile-tabs">
        <router-link :to="{ name: 'UserProfileHome' }" exact-active-class="active">动态</router-link>
        <router-link :to="{ name: 'UserComments' }" active-class="active">评价</router-link>
        <router-link :to="{ name: 'UserFavorites' }" active-class="active">收藏</router-link>
        <!-- 更多标签页可以在此添加 -->
      </nav>
      <div class="tab-content">
        <router-view :key="$route.fullPath"></router-view> <!-- 用于显示子路由内容 -->
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'UserProfileLayout',
  data() {
    return {
      // 模拟当前登录用户的数据
      userData: {
        id: 1,
        name: '天大美食家',
        avatarUrl: 'https://placehold.co/120x120/007BFF/FFFFFF?text=TJU',
        followingCount: 15,
        followerCount: 28,
        likesReceived: 102,
        registerDate: '2023-09-01T10:00:00Z',
      }
    };
  },
  methods: {
    formatDate(dateString) {
      if (!dateString) return '';
      return new Date(dateString).toLocaleDateString('zh-CN');
    }
  },
  created() {
    // 实际项目中，在这里通过API获取当前登录用户的信息
    this.userData.name = localStorage.getItem('username') || '天大美食家';
  }
}
</script>

<style scoped>
.user-profile-layout { padding: 20px 0; }
.profile-header-card {
  background-color: #fff;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.08);
  margin-bottom: 25px;
}
.profile-info-main { display: flex; align-items: center; gap: 25px; }
.avatar-container { flex-shrink: 0; }
.profile-avatar { width: 120px; height: 120px; border-radius: 50%; border: 4px solid #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
.info-text { flex-grow: 1; }
.name-and-edit { display: flex; align-items: center; gap: 20px; margin-bottom: 10px; }
.user-name { font-size: 2.2em; color: #333; margin: 0; }
.btn-edit-profile { background-color: #f0f2f5; border: 1px solid #dcdfe6; color: #606266; padding: 6px 15px; border-radius: 20px; text-decoration: none; font-size: 0.9em; transition: all 0.2s; }
.btn-edit-profile:hover { background-color: #e4e7ed; border-color: #c0c4cc; }
.btn-edit-profile i { margin-right: 5px; }
.user-stats { display: flex; gap: 25px; margin-bottom: 10px; color: #555; }
.user-stats strong { font-size: 1.2em; color: #333; margin-right: 5px; }
.register-date { font-size: 0.85em; color: #999; margin: 0; }

.profile-content-card { background-color: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.06); }
.profile-tabs { display: flex; border-bottom: 1px solid #eef0f4; padding: 0 25px; }
.profile-tabs a { padding: 15px 20px; font-size: 1.1em; font-weight: 500; color: #606266; text-decoration: none; border-bottom: 3px solid transparent; transition: color 0.2s, border-color 0.2s; }
.profile-tabs a:hover { color: #007bff; }
.profile-tabs a.active { color: #007bff; border-bottom-color: #007bff; }
.tab-content { padding: 25px; }
</style>
