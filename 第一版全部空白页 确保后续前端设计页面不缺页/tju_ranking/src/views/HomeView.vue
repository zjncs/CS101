<template>
  <div class="home-container">
    <header class="home-header">
      <!-- 建议在这里添加一个Logo图片 -->
      <!-- <img src="@/assets/logo.png" alt="天大点评Logo" class="logo"> -->
      <h1>欢迎来到 天大点评</h1>
      <p>发现天津大学的口碑美食</p>
    </header>

    <nav class="home-nav">
      <router-link to="/main" class="nav-button primary-action">
        <i class="fas fa-utensils"></i> <!-- 假设你使用了 Font Awesome 图标 -->
        <span>探索美食</span>
      </router-link>
      <router-link to="/login" class="nav-button secondary-action">
        <i class="fas fa-sign-in-alt"></i>
        <span>登录</span>
      </router-link>
      <router-link to="/register" class="nav-button secondary-action">
        <i class="fas fa-user-plus"></i>
        <span>注册</span>
      </router-link>
    </nav>

    <section class="features-preview">
      <h2>平台特色</h2>
      <div class="features-grid">
        <div class="feature-item">
          <i class="fas fa-star feature-icon"></i>
          <h3>真实点评</h3>
          <p>查看来自同学的真实用餐体验。</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-search feature-icon"></i>
          <h3>便捷搜索</h3>
          <p>快速找到你想尝试的菜品或食堂。</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-share-alt feature-icon"></i>
          <h3>分享交流</h3>
          <p>分享你的美食攻略，参与社区讨论。</p>
        </div>
      </div>
    </section>

    <!-- 建议在这里添加一个轮播图组件 (Carousel/Slider) 展示热门菜品或食堂 -->
    <!-- <FeaturedCarousel /> -->

    <footer class="home-footer">
      <p>&copy; 2024-2025 天大点评. 保留所有权利.</p>
    </footer>
  </div>
</template>

<script>
// 如果有需要，可以引入子组件
// import FeaturedCarousel from '@/components/FeaturedCarousel.vue';

export default {
  name: 'HomeView',
  components: {
    // FeaturedCarousel
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  font-family: 'Arial', sans-serif;
  background-color: #f4f7f6; /* 淡雅的背景色 */
  color: #333;
  padding: 20px;
  box-sizing: border-box;
}

.home-header {
  text-align: center;
  margin-bottom: 40px;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  width: 100%;
  max-width: 800px;
}

.logo {
  max-width: 150px;
  margin-bottom: 15px;
}

.home-header h1 {
  font-size: 2.8em;
  color: #2c3e50; /* 深蓝灰色 */
  margin-bottom: 10px;
}

.home-header p {
  font-size: 1.2em;
  color: #555;
}

.home-nav {
  display: flex;
  gap: 20px; /* 按钮之间的间距 */
  margin-bottom: 50px;
  flex-wrap: wrap; /* 确保在小屏幕上换行 */
  justify-content: center;
}

.nav-button {
  display: flex;
  align-items: center;
  padding: 15px 30px;
  border: none;
  border-radius: 25px; /* 圆角按钮 */
  font-size: 1.1em;
  font-weight: bold;
  text-decoration: none;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.nav-button i {
  margin-right: 8px;
}

.primary-action {
  background-color: #3498db; /* 主题蓝色 */
  color: white;
}

.primary-action:hover {
  background-color: #2980b9;
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.secondary-action {
  background-color: #ecf0f1; /* 浅灰色 */
  color: #2c3e50;
  border: 1px solid #bdc3c7;
}

.secondary-action:hover {
  background-color: #dadedf;
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

.features-preview {
  text-align: center;
  margin-bottom: 50px;
  padding: 30px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  width: 100%;
  max-width: 800px;
}

.features-preview h2 {
  font-size: 2em;
  color: #2c3e50;
  margin-bottom: 30px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 25px;
}

.feature-item {
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.feature-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.feature-icon {
  font-size: 2.5em; /* 调整图标大小 */
  color: #3498db; /* 图标颜色与主按钮一致 */
  margin-bottom: 15px;
}

.feature-item h3 {
  font-size: 1.4em;
  color: #34495e;
  margin-bottom: 10px;
}

.feature-item p {
  font-size: 0.95em;
  color: #7f8c8d;
  line-height: 1.6;
}

.home-footer {
  margin-top: auto; /* 将页脚推到底部 */
  padding: 20px;
  text-align: center;
  font-size: 0.9em;
  color: #7f8c8d;
  width: 100%;
  background-color: #e9ecef;
  border-top: 1px solid #dee2e6;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .home-header h1 {
    font-size: 2.2em;
  }
  .home-header p {
    font-size: 1em;
  }
  .nav-button {
    padding: 12px 25px;
    font-size: 1em;
  }
  .features-grid {
    grid-template-columns: 1fr; /* 小屏幕下单列显示 */
  }
}

@media (max-width: 480px) {
  .home-header h1 {
    font-size: 1.8em;
  }
  .home-nav {
    flex-direction: column; /* 小屏幕上按钮垂直排列 */
    gap: 15px;
  }
  .nav-button {
    width: 100%; /* 按钮宽度占满 */
    justify-content: center;
  }
}
</style>
