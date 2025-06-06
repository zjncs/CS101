<template>
  <div class="search-results-view container">
    <div class="page-header">
      <h1><i class="fas fa-search"></i> 搜索结果</h1>
      <p v-if="searchQuery">关于 “<strong>{{ searchQuery }}</strong>” 的搜索结果</p>
      <p v-if="canteenName">正在查看 <strong>{{ canteenName }}</strong> 的窗口列表</p>
    </div>

    <div v-if="loading" class="loading-placeholder">
      <i class="fas fa-spinner fa-spin"></i> 正在加载搜索结果...
    </div>

    <div v-else-if="results.length === 0 && !loading" class="no-results">
      <i class="fas fa-folder-open"></i>
      <p>抱歉，没有找到相关的结果。</p>
      <router-link :to="{ name: 'Home' }" class="btn btn-primary">返回首页</router-link>
    </div>

    <div v-else class="results-list">
      <!-- 根据搜索类型显示不同结果卡片，这里假设主要显示窗口列表 -->
      <!-- 如果是关键词搜索，可能需要更复杂的逻辑来展示混合结果 -->
      <div
          v-for="item in results"
          :key="item.id"
          class="result-card window-card"
          @click="goToWindowDetail(item.id)"
      >
        <div class="window-image-placeholder">
          <i class="fas fa-store-alt"></i> <!-- 窗口图标 -->
        </div>
        <div class="window-info">
          <h3>{{ item.name }}</h3>
          <p class="canteen-name" v-if="item.canteenName">所属食堂: {{ item.canteenName }}</p>
          <p class="window-description">{{ item.description || '暂无窗口描述。' }}</p>
          <div class="window-meta">
            <!-- 可以显示评分、菜品数量等信息 -->
            <span v-if="item.avgPrice">人均: ￥{{ item.avgPrice }}</span>
            <span v-if="item.dishCount">菜品数: {{ item.dishCount }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SearchResultsView',
  data() {
    return {
      loading: true,
      searchQuery: '',
      canteenId: null,
      canteenName: '', // 用于显示食堂名称
      results: [], // 存储搜索结果
      // 模拟数据源
      allWindows: [
        { id: 101, name: '红烧牛肉面窗口', description: '招牌红烧牛肉面，汤浓味美。', canteenId: 1, canteenName: '学一食堂', avgPrice: 15, dishCount: 5, campus:'weijinlu' },
        { id: 102, name: '特色小炒窗口', description: '提供各类家常小炒，新鲜出炉。', canteenId: 1, canteenName: '学一食堂', avgPrice: 20, dishCount: 20, campus:'weijinlu' },
        { id: 103, name: '麻辣香锅窗口', description: '自选食材，麻辣过瘾。', canteenId: 2, canteenName: '学三食堂', avgPrice: 25, dishCount: 30, campus:'weijinlu' },
        { id: 201, name: '梅园小炒1号', description: '经济实惠，出餐快。', canteenId: 4, canteenName: '梅园食堂', avgPrice: 18, dishCount: 15, campus:'beiyangyuan' },
        { id: 202, name: '梅园面点窗口', description: '包子、馒头、粥品。', canteenId: 4, canteenName: '梅园食堂', avgPrice: 8, dishCount: 10, campus:'beiyangyuan' },
        { id: 203, name: '竹园烧腊窗口', description: '广式烧腊，味道正宗。', canteenId: 5, canteenName: '竹园食堂', avgPrice: 22, dishCount: 8, campus:'beiyangyuan' },
      ],
      allCanteens: [ // 用于根据canteenId查找食堂名称
        { id: 1, name: '学一食堂', campus: 'weijinlu' },
        { id: 2, name: '学三食堂', campus: 'weijinlu' },
        { id: 3, name: '留学生食堂', campus: 'weijinlu' },
        { id: 4, name: '梅园食堂', campus: 'beiyangyuan' },
        { id: 5, name: '竹园食堂', campus: 'beiyangyuan' },
      ]
    };
  },
  watch: {
    '$route.query': {
      immediate: true, // 立即执行一次，以便组件创建时加载数据
      handler(newQuery) {
        this.loadResults(newQuery);
      }
    }
  },
  methods: {
    loadResults(query) {
      this.loading = true;
      this.results = [];
      this.searchQuery = query.q || '';
      this.canteenId = query.canteen ? parseInt(query.canteen) : null;
      this.canteenName = '';


      console.log('Loading results for query:', query);

      // 模拟API调用和数据筛选
      setTimeout(() => {
        if (this.canteenId) {
          const targetCanteen = this.allCanteens.find(c => c.id === this.canteenId);
          if(targetCanteen) this.canteenName = targetCanteen.name;

          this.results = this.allWindows.filter(window => window.canteenId === this.canteenId);
        } else if (this.searchQuery) {
          const searchTerm = this.searchQuery.toLowerCase();
          this.results = this.allWindows.filter(window =>
                  window.name.toLowerCase().includes(searchTerm) ||
                  (window.description && window.description.toLowerCase().includes(searchTerm)) ||
                  (window.canteenName && window.canteenName.toLowerCase().includes(searchTerm))
              // 还可以搜索菜品名称，然后链接到菜品详情页，或者将菜品也作为结果项
          );
        } else {
          // 无有效查询参数，显示空结果或默认提示
        }
        this.loading = false;
      }, 1000);
    },
    goToWindowDetail(windowId) {
      this.$router.push({ name: 'WindowDetail', params: { id: windowId } });
    }
  }
}
</script>

<style scoped>
.search-results-view {
  padding-top: 20px;
  padding-bottom: 40px;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}
.page-header h1 {
  font-size: 2.2em;
  color: #333;
  margin-bottom: 10px;
}
.page-header h1 i {
  margin-right: 10px;
  color: #007bff;
}
.page-header p {
  font-size: 1.1em;
  color: #555;
}

.loading-placeholder,
.no-results {
  text-align: center;
  padding: 50px 20px;
  color: #6c757d;
}
.loading-placeholder i,
.no-results i {
  font-size: 3em;
  display: block;
  margin-bottom: 20px;
  color: #adb5bd;
}
.no-results p {
  font-size: 1.2em;
  margin-bottom: 25px;
}
.btn.btn-primary { /* 确保按钮样式生效 */
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  text-decoration: none;
  border-radius: 5px;
  font-weight: 500;
}
.btn.btn-primary:hover {
  background-color: #0056b3;
}


.results-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 25px;
}

.result-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.07);
  overflow: hidden;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
  display: flex; /* 使图片和信息部分水平排列 */
  min-height: 130px;
}
.result-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.window-card .window-image-placeholder {
  width: 120px; /* 固定图片区域宽度 */
  min-width: 120px;
  background-color: #e9ecef;
  display: flex;
  align-items: center;
  justify-content: center;
}
.window-image-placeholder i {
  font-size: 3em;
  color: #ced4da;
}
/* 如果用真实图片:
.window-card img {
    width: 120px;
    height: 100%;
    object-fit: cover;
}
*/

.window-info {
  padding: 15px;
  flex-grow: 1;
}
.window-info h3 {
  font-size: 1.4em;
  color: #0056b3;
  margin-top: 0;
  margin-bottom: 8px;
}
.canteen-name {
  font-size: 0.85em;
  color: #777;
  margin-bottom: 8px;
  font-style: italic;
}
.window-description {
  font-size: 0.9em;
  color: #555;
  line-height: 1.5;
  margin-bottom: 10px;
  display: -webkit-box; /* 用于多行文本截断 */
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  min-height: 2.7em; /* 至少两行的高度 */
}
.window-meta {
  font-size: 0.8em;
  color: #6c757d;
}
.window-meta span {
  margin-right: 15px;
}
.window-meta span:last-child {
  margin-right: 0;
}

</style>
    