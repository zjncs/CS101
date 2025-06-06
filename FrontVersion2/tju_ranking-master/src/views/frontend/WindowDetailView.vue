<template>
  <div class="window-detail-view container" v-if="windowData">
    <div class="page-header window-header">
      <div class="header-main">
        <i class="fas fa-store-alt header-icon"></i>
        <h1>{{ windowData.name }}</h1>
      </div>
      <p class="canteen-breadcrumb">
        <router-link :to="{ name: 'Home', query: { campus: windowData.campus } }">
          {{ windowData.campus === 'weijinlu' ? '卫津路校区' : '北洋园校区' }}
        </router-link>
        /
        <router-link :to="{ name: 'SearchResults', query: { canteen: windowData.canteenId, campus: windowData.campus } }">
          {{ windowData.canteenName }}
        </router-link>
      </p>
      <p class="window-description-detail">{{ windowData.description }}</p>
    </div>

    <section class="dish-list-section">
      <h2><i class="fas fa-utensils"></i> 菜品列表</h2>
      <div class="dish-slider-container" v-if="dishes.length > 0">
        <!-- 简易的左右滑动条效果，实际项目可用轮播组件 -->
        <button @click="scrollDishes(-1)" class="scroll-button prev" :disabled="scrollPosition === 0">&lt;</button>
        <div class="dish-slider" ref="dishSlider">
          <div
              v-for="dish in dishes"
              :key="dish.id"
              class="dish-card-slider"
              @click="goToDishDetail(dish.id)"
          >
            <div class="dish-image-placeholder-small">
              <i class="fas fa-drumstick-bite"></i> <!-- 菜品图标 -->
            </div>
            <div class="dish-info-slider">
              <h4>{{ dish.name }}</h4>
              <p class="dish-price">￥{{ dish.price }}</p>
            </div>
          </div>
        </div>
        <button @click="scrollDishes(1)" class="scroll-button next" :disabled="isScrollEnd">&gt;</button>
      </div>
      <p v-else class="no-data">该窗口暂无菜品信息。</p>
    </section>

    <!-- 其他信息，如窗口开放时间、评价等，可以在此添加 -->
    <section class="window-extra-info">
      <h2><i class="fas fa-info-circle"></i> 更多信息</h2>
      <p><strong>开放时间:</strong> {{ windowData.openHours || '暂无信息' }}</p>
      <p><strong>平均评分:</strong> {{ windowData.rating ? windowData.rating + ' / 5' : '暂无评分' }}</p>
      <!-- 用户评价组件占位 -->
    </section>

  </div>
  <div v-else-if="loading" class="loading-placeholder full-page-loader">
    <i class="fas fa-spinner fa-spin"></i> 正在加载窗口详情...
  </div>
  <div v-else class="no-data full-page-loader">
    <i class="fas fa-exclamation-circle"></i> 抱歉，找不到该窗口的信息。
  </div>
</template>

<script>
export default {
  name: 'WindowDetailView',
  data() {
    return {
      loading: true,
      windowId: null,
      windowData: null, // { id, name, description, canteenId, canteenName, campus, openHours, rating }
      dishes: [],       // [{ id, name, price, windowId }]
      scrollPosition: 0,
      // 模拟数据
      allWindowsData: {
        101: { id: 101, name: '红烧牛肉面窗口', description: '招牌红烧牛肉面，汤浓味美，每日限量供应，使用新鲜牛骨熬制汤底。', canteenId: 1, canteenName: '学一食堂', campus:'weijinlu', openHours: '10:30-13:30, 16:30-19:30', rating: 4.5 },
        102: { id: 102, name: '特色小炒窗口', description: '提供各类家常小炒，新鲜出炉，可自选搭配。', canteenId: 1, canteenName: '学一食堂', campus:'weijinlu', openHours: '11:00-14:00, 17:00-20:00', rating: 4.2 },
        103: { id: 103, name: '麻辣香锅窗口', description: '自选食材，麻辣过瘾，多种辣度可选。', canteenId: 2, canteenName: '学三食堂', campus:'weijinlu', openHours: '11:00-20:00', rating: 4.7 },
        201: { id: 201, name: '梅园小炒1号', description: '经济实惠，出餐快，适合学生党。', canteenId: 4, canteenName: '梅园食堂', campus:'beiyangyuan', openHours: '10:00-19:00', rating: 4.0 },
        202: { id: 202, name: '梅园面点窗口', description: '各式包子、馒头、花卷、粥品、豆浆等。', canteenId: 4, canteenName: '梅园食堂', campus:'beiyangyuan', openHours: '06:30-09:30, 11:00-13:00', rating: 4.3 },
        203: { id: 203, name: '竹园烧腊窗口', description: '广式烧腊，味道正宗，叉烧、烧鸭、白切鸡。', canteenId: 5, canteenName: '竹园食堂', campus:'beiyangyuan', openHours: '11:00-13:30, 17:00-19:00', rating: 4.6 },
      },
      allDishesData: [
        { id: 10101, name: '红烧牛肉面', price: 15, windowId: 101 },
        { id: 10102, name: '加肉红烧牛肉面', price: 20, windowId: 101 },
        { id: 10103, name: '小碗牛肉面', price: 12, windowId: 101 },
        { id: 10201, name: '鱼香肉丝', price: 12, windowId: 102 },
        { id: 10202, name: '宫保鸡丁', price: 15, windowId: 102 },
        { id: 10203, name: '清炒时蔬', price: 8, windowId: 102 },
        { id: 10301, name: '自选麻辣香锅(素)', price: 18, windowId: 103 }, // 按斤称重，这里是示例
        { id: 10302, name: '自选麻辣香锅(荤素)', price: 28, windowId: 103 },
        { id: 20101, name: '番茄炒蛋套餐', price: 16, windowId: 201 },
        { id: 20201, name: '肉包', price: 2, windowId: 202 },
        { id: 20301, name: '叉烧饭', price: 20, windowId: 203 },
      ]
    };
  },
  computed: {
    isScrollEnd() {
      if (!this.$refs.dishSlider) return true;
      const slider = this.$refs.dishSlider;
      // scrollWidth 是内容总宽度，clientWidth 是可见宽度，scrollLeft 是已滚动距离
      return slider.scrollWidth - slider.clientWidth - slider.scrollLeft < 1;
    }
  },
  methods: {
    fetchWindowDetails() {
      this.loading = true;
      this.windowId = parseInt(this.$route.params.id);
      console.log(`Fetching details for window ID: ${this.windowId}`);

      setTimeout(() => {
        this.windowData = this.allWindowsData[this.windowId] || null;
        if (this.windowData) {
          this.dishes = this.allDishesData.filter(dish => dish.windowId === this.windowId);
        }
        this.loading = false;
      }, 800);
    },
    goToDishDetail(dishId) {
      this.$router.push({ name: 'DishDetail', params: { id: dishId } });
    },
    scrollDishes(direction) {
      const slider = this.$refs.dishSlider;
      if (slider) {
        // 假设每个卡片宽度大致相同，或者可以获取第一个卡片的宽度
        const cardWidth = slider.children.length > 0 ? slider.children[0].offsetWidth + 20 : 200; // 20是gap
        slider.scrollBy({ left: direction * cardWidth * 2, behavior: 'smooth' }); // 一次滚动2个卡片
        // 更新 scrollPosition 比较复杂，因为 scrollLeft 的变化不是即时的，
        // 且 scrollBy 结束后才更新。此处仅作简单演示，isScrollEnd更可靠些。
        this.scrollPosition = slider.scrollLeft;
      }
    }
  },
  created() {
    this.fetchWindowDetails();
  },
  // 当路由参数变化时（例如从一个窗口详情跳到另一个窗口详情），重新加载数据
  beforeRouteUpdate(to, from, next) {
    this.fetchWindowDetails();
    next();
  }
}
</script>

<style scoped>
.window-detail-view {
  padding-top: 20px;
  padding-bottom: 40px;
}

.page-header.window-header {
  background-color: #fff;
  padding: 25px;
  border-radius: 8px;
  margin-bottom: 30px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.06);
}
.header-main {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.header-icon {
  font-size: 2.5em;
  color: #007bff;
  margin-right: 15px;
}
.window-header h1 {
  font-size: 2.4em;
  color: #333;
  margin: 0;
}
.canteen-breadcrumb {
  font-size: 0.95em;
  color: #6c757d;
  margin-bottom: 15px;
}
.canteen-breadcrumb a {
  color: #0056b3;
  text-decoration: none;
}
.canteen-breadcrumb a:hover {
  text-decoration: underline;
}
.window-description-detail {
  font-size: 1.05em;
  color: #555;
  line-height: 1.6;
}

.dish-list-section, .window-extra-info {
  margin-bottom: 30px;
  background-color: #fff;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 1px 5px rgba(0,0,0,0.05);
}
.dish-list-section h2, .window-extra-info h2 {
  font-size: 1.6em;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 8px;
  border-bottom: 1px solid #eee;
}
.dish-list-section h2 i, .window-extra-info h2 i {
  margin-right: 10px;
  color: #28a745; /* 绿色图标 */
}
.window-extra-info h2 i {
  color: #17a2b8; /* 蓝色图标 */
}
.window-extra-info p {
  font-size: 1em;
  color: #444;
  margin-bottom: 10px;
  line-height: 1.7;
}
.window-extra-info p strong {
  color: #222;
  margin-right: 5px;
}


.dish-slider-container {
  position: relative;
  display: flex;
  align-items: center;
}

.dish-slider {
  display: flex;
  overflow-x: auto; /* 允许横向滚动 */
  scroll-behavior: smooth;
  gap: 20px; /* 卡片之间的间距 */
  padding: 10px 0; /* 给卡片上下一点空间，避免阴影被裁 */
  flex-grow: 1;
  /* 隐藏原生滚动条 (可选) */
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}
.dish-slider::-webkit-scrollbar { /* Chrome, Safari, Opera */
  display: none;
}

.scroll-button {
  background-color: rgba(255,255,255,0.8);
  border: 1px solid #ccc;
  color: #333;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  font-size: 1.5em;
  cursor: pointer;
  position: absolute; /* 或者使用 flex 定位在两侧 */
  z-index: 10;
  transition: background-color 0.2s, transform 0.2s;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}
.scroll-button:hover:not(:disabled) {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
  transform: scale(1.1);
}
.scroll-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.scroll-button.prev {
  left: -20px; /* 调整位置使其不完全遮挡内容 */
}
.scroll-button.next {
  right: -20px;
}


.dish-card-slider {
  min-width: 180px; /* 卡片最小宽度 */
  max-width: 220px;
  background-color: #f8f9fa;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
  transition: transform 0.2s, box-shadow 0.2s;
}
.dish-card-slider:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 10px rgba(0,0,0,0.08);
}

.dish-image-placeholder-small {
  height: 100px;
  background-color: #e9ecef;
  display: flex;
  align-items: center;
  justify-content: center;
}
.dish-image-placeholder-small i {
  font-size: 2.5em;
  color: #ced4da;
}

.dish-info-slider {
  padding: 10px;
  text-align: center;
}
.dish-info-slider h4 {
  font-size: 1.1em;
  color: #333;
  margin: 0 0 5px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.dish-price {
  font-size: 1em;
  color: #e67e22; /* 价格用橙色突出 */
  font-weight: bold;
  margin: 0;
}

.full-page-loader { /* 用于页面级加载或错误提示 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 300px;
}
.no-data {
  text-align: center;
  padding: 20px;
  color: #6c757d;
  font-size: 1.1em;
}
</style>
    