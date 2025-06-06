<template>
  <div class="home-view container">
    <div class="campus-selector">
      <button
          :class="['campus-button', { active: currentCampus === 'weijinlu' }]"
          @click="selectCampus('weijinlu')"
      >
        卫津路校区
      </button>
      <button
          :class="['campus-button', { active: currentCampus === 'beiyangyuan' }]"
          @click="selectCampus('beiyangyuan')"
      >
        北洋园校区
      </button>
    </div>

    <section class="canteen-list-section">
      <h2>{{ campusName }} - 食堂列表</h2>
      <div class="canteen-grid" v-if="!loadingCanteens">
        <div
            v-for="canteen in canteens"
            :key="canteen.id"
            class="canteen-card"
            @click="goToWindowList(canteen.id)"
        >
          <!-- 你可以在这里放置食堂图片 -->
          <div class="canteen-image-placeholder">
            <i class="fas fa-store"></i> {{ canteen.name }} ({{canteen.campus}})
          </div>
          <div class="canteen-info">
            <h3>{{ canteen.name }}</h3>
            <p>{{ canteen.description || '暂无描述' }}</p>
          </div>
        </div>
        <p v-if="canteens.length === 0" class="no-data">该校区暂无食堂信息。</p>
      </div>
      <div v-else class="loading-placeholder">
        <i class="fas fa-spinner fa-spin"></i> 加载食堂中...
      </div>
    </section>

    <section class="recommended-blogs-section">
      <h2>推荐博客</h2>
      <div class="blog-grid" v-if="!loadingBlogs">
        <div
            v-for="blog in recommendedBlogs"
            :key="blog.id"
            class="blog-card"
            @click="goToBlogDetail(blog.id)"
        >
          <!-- 你可以在这里放置博客的封面图片 -->
          <div class="blog-image-placeholder">
            <i class="fas fa-newspaper"></i>
          </div>
          <div class="blog-info">
            <h3>{{ blog.title }}</h3>
            <p class="blog-author">作者: {{ blog.author }}</p>
            <p class="blog-excerpt">{{ blog.excerpt || '暂无摘要...' }}</p>
          </div>
        </div>
        <p v-if="recommendedBlogs.length === 0" class="no-data">暂无推荐博客。</p>
      </div>
      <div v-else class="loading-placeholder">
        <i class="fas fa-spinner fa-spin"></i> 加载博客中...
      </div>
    </section>

    <div class="create-blog-button-container">
      <button @click="goToCreateBlog" class="btn-create-blog">
        <i class="fas fa-plus-circle"></i> 发表博客
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
  data() {
    return {
      currentCampus: 'weijinlu', // 默认校区
      loadingCanteens: true,
      loadingBlogs: true,
      canteensData: { // 模拟数据
        weijinlu: [
          { id: 1, name: '学一食堂', description: '卫津路历史最悠久的食堂之一', campus: '卫津路' },
          { id: 2, name: '学三食堂', description: '菜品丰富，口味多样', campus: '卫津路' },
          { id: 3, name: '留学生食堂', description: '异国风味，特色选择', campus: '卫津路' },
        ],
        beiyangyuan: [
          { id: 4, name: '梅园食堂', description: '北洋园校区中心食堂', campus: '北洋园' },
          { id: 5, name: '竹园食堂', description: '靠近教学楼，方便快捷', campus: '北洋园' },
        ]
      },
      recommendedBlogsData: [ // 模拟数据
        { id: 101, title: '探秘学三食堂二楼新品盖饭！', author: '美食家小张', excerpt: '今天尝试了学三食堂二楼新出的黑椒牛肉盖饭，味道简直绝了...', campusFilter: 'weijinlu' },
        { id: 102, title: '北洋园梅园食堂早餐攻略', author: '早起干饭人', excerpt: '梅园食堂的豆腐脑和煎饼果子是YYDS，每天的能量来源！', campusFilter: 'beiyangyuan' },
        { id: 103, title: '我的天大校园VLOG - 食堂篇', author: '校园UP主小李', excerpt: '带你云逛天大各个食堂，发现隐藏美味！', campusFilter: 'all' }, // 'all' 表示两个校区都推荐
        { id: 104, title: '期末周食堂点餐不排队小技巧', author: '机智学长', excerpt: '如何错峰吃饭，以及使用小程序提前点单的经验分享。', campusFilter: 'all' },
      ]
    };
  },
  computed: {
    campusName() {
      return this.currentCampus === 'weijinlu' ? '卫津路校区' : '北洋园校区';
    },
    canteens() {
      return this.canteensData[this.currentCampus] || [];
    },
    recommendedBlogs() {
      return this.recommendedBlogsData.filter(blog => blog.campusFilter === this.currentCampus || blog.campusFilter === 'all');
    }
  },
  methods: {
    selectCampus(campus) {
      if (this.currentCampus !== campus) {
        this.currentCampus = campus;
        // 实际项目中，切换校区后可能需要重新从API获取数据
        this.fetchCampusData();
      }
    },
    fetchCampusData() {
      this.loadingCanteens = true;
      this.loadingBlogs = true;
      // 模拟API加载
      console.log(`Fetching data for ${this.campusName}...`);
      setTimeout(() => {
        this.loadingCanteens = false;
        this.loadingBlogs = false;
        // 这里可以根据 this.currentCampus 更新 canteensData 和 recommendedBlogsData
        // 但由于目前是本地模拟数据，筛选已在 computed 中完成
      }, 800);
    },
    goToWindowList(canteenId) {
      // 实际应用中，你可能会跳转到一个专门的窗口列表页，或者直接在食堂详情页展示窗口
      // 这里我们假设有一个 SearchResults 页面可以处理食堂ID的查询
      console.log(`Navigating to windows for canteen ID: ${canteenId}`);
      this.$router.push({ name: 'SearchResults', query: { canteen: canteenId, campus: this.currentCampus } });
    },
    goToBlogDetail(blogId) {
      console.log(`Navigating to blog detail ID: ${blogId}`);
      // 假设有一个 BlogDetails 页面，但根据你的路由设计，目前没有这个页面
      // 暂时跳转到首页并带上参数，或者你可以创建一个博客详情页
      alert(`将跳转到博客详情页 (ID: ${blogId}) - 页面待创建`);
      // this.$router.push({ name: 'BlogDetail', params: { id: blogId } }); // 需创建对应路由和组件
    },
    goToCreateBlog() {
      if (this.$auth && this.$auth.isLoggedIn()) {
        this.$router.push({ name: 'CreateBlog' });
      } else {
        alert('请先登录再发表博客。');
        this.$router.push({ name: 'Login', query: { redirect: this.$route.fullPath } });
      }
    }
  },
  created() {
    // 初始化加载当前校区数据
    this.fetchCampusData();
  }
}
</script>

<style scoped>
.home-view {
  padding-top: 20px;
  padding-bottom: 20px;
}

.campus-selector {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
  border: 1px solid #007bff;
  border-radius: 25px;
  overflow: hidden;
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
}

.campus-button {
  flex-grow: 1;
  padding: 12px 20px;
  background-color: #ffffff;
  color: #007bff;
  border: none;
  /* border-right: 1px solid #007bff; */
  cursor: pointer;
  font-size: 1em;
  font-weight: 500;
  transition: background-color 0.3s, color 0.3s;
}
.campus-button:last-child {
  border-right: none;
}
.campus-button.active,
.campus-button:hover {
  background-color: #007bff;
  color: white;
}
.campus-button:not(.active):hover {
  background-color: #e9ecef;
}


.canteen-list-section,
.recommended-blogs-section {
  margin-bottom: 40px;
  background-color: #fff;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.07);
}

h2 {
  font-size: 1.8em;
  color: #333;
  margin-bottom: 25px;
  border-bottom: 2px solid #007bff;
  padding-bottom: 10px;
  display: inline-block;
}

.loading-placeholder, .no-data {
  text-align: center;
  padding: 30px;
  color: #6c757d;
  font-size: 1.1em;
}
.loading-placeholder i {
  margin-right: 8px;
}

.canteen-grid,
.blog-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 25px;
}

.canteen-card,
.blog-card {
  background-color: #f8f9fa;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
  display: flex;
  flex-direction: column;
}

.canteen-card:hover,
.blog-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.canteen-image-placeholder,
.blog-image-placeholder {
  height: 160px;
  background-color: #e9ecef;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #adb5bd;
  font-size: 1.2em; /* Placeholder text/icon size */
}
.canteen-image-placeholder i,
.blog-image-placeholder i {
  font-size: 2.5em; /* Icon size */
  color: #ced4da;
}
/* If using actual images:
.canteen-card img, .blog-card img {
    width: 100%;
    height: 160px;
    object-fit: cover;
}
*/

.canteen-info,
.blog-info {
  padding: 15px;
  flex-grow: 1;
}

.canteen-info h3,
.blog-info h3 {
  font-size: 1.3em;
  color: #0056b3; /* Slightly darker blue for titles */
  margin-top: 0;
  margin-bottom: 8px;
}

.canteen-info p,
.blog-author,
.blog-excerpt {
  font-size: 0.9em;
  color: #555;
  line-height: 1.5;
  margin-bottom: 5px;
}
.blog-author {
  font-style: italic;
  color: #777;
  margin-bottom: 10px;
}

.create-blog-button-container {
  position: fixed; /* 固定定位 */
  bottom: 30px;
  right: 30px;
  z-index: 999; /* 确保在其他内容之上 */
}

.btn-create-blog {
  background-color: #28a745; /* 绿色 */
  color: white;
  padding: 15px 25px;
  border: none;
  border-radius: 50px; /* 圆形按钮 */
  font-size: 1.1em;
  font-weight: bold;
  cursor: pointer;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  transition: background-color 0.2s, transform 0.2s;
  display: flex;
  align-items: center;
}
.btn-create-blog i {
  margin-right: 8px;
  font-size: 1.2em;
}

.btn-create-blog:hover {
  background-color: #218838;
  transform: scale(1.05);
}

@media (max-width: 768px) {
  .canteen-grid, .blog-grid {
    grid-template-columns: 1fr; /* 小屏幕下单列 */
  }
  h2 {
    font-size: 1.5em;
  }
  .create-blog-button-container {
    bottom: 20px;
    right: 20px;
  }
  .btn-create-blog {
    padding: 12px 20px;
    font-size: 1em;
  }
}
</style>
