<template>
  <div class="user-favorites-view">
    <h3 class="section-title">我的收藏</h3>
    <div v-if="loading" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载收藏列表...</div>
    <div v-else-if="favorites.length > 0" class="content-grid">
      <!-- 假设只收藏菜品 -->
      <router-link v-for="item in favorites" :key="item.id" :to="{ name: 'DishDetail', params: { id: item.id } }" class="content-card-link">
        <div class="image-placeholder"><i class="fas fa-drumstick-bite"></i></div>
        <h4>{{ item.name }}</h4>
        <p class="meta-info">来自: {{ item.windowName }}</p>
        <p class="price">￥{{ item.price }}</p>
      </router-link>
    </div>
    <div v-else class="no-data">您还没有收藏任何菜品。</div>
  </div>
</template>

<script>
export default {
  name: 'UserFavoritesView',
  data() {
    return {
      loading: true,
      favorites: [], // 模拟收藏数据
    };
  },
  methods: {
    fetchFavorites() {
      this.loading = true;
      setTimeout(() => {
        this.favorites = [
          { id: 20301, name: '叉烧饭', windowName: '竹园烧腊窗口', price: 20 },
          { id: 10201, name: '鱼香肉丝', windowName: '特色小炒窗口', price: 12 },
          { id: 10102, name: '加肉红烧牛肉面', windowName: '红烧牛肉面窗口', price: 20 },
        ];
        this.loading = false;
      }, 800);
    }
  },
  created() {
    this.fetchFavorites();
  }
}
</script>

<style scoped>
.section-title { font-size: 1.4em; color: #333; margin-top: 0; margin-bottom: 20px; }
.loading-placeholder, .no-data { text-align: center; padding: 30px; color: #888; }
.content-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
}
.content-card-link {
  text-decoration: none; color: inherit; display: block;
  background-color: #f8f9fa; border: 1px solid #e9ecef;
  border-radius: 8px; overflow: hidden; transition: all 0.2s ease;
}
.content-card-link:hover { transform: translateY(-4px); box-shadow: 0 4px 10px rgba(0,0,0,0.08); }
.image-placeholder { height: 120px; background-color: #e9ecef; display: flex; align-items: center; justify-content: center; font-size: 2.5em; color: #ced4da; }
.content-card-link h4 { font-size: 1.1em; color: #333; margin: 10px 15px 5px; }
.content-card-link .meta-info { font-size: 0.85em; color: #777; margin: 0 15px 5px; }
.content-card-link .price { font-size: 1.1em; font-weight: bold; color: #e67e22; margin: 0 15px 12px; }
</style>
