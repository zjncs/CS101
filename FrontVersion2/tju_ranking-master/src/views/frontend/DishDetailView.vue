<template>
  <div class="dish-detail-view container" v-if="dishData">
    <div class="page-header dish-header">
      <div class="header-main">
        <i class="fas fa-drumstick-bite header-icon"></i> <!-- 菜品图标 -->
        <h1>{{ dishData.name }}</h1>
      </div>
      <p class="window-breadcrumb">
        属于:
        <router-link :to="{ name: 'WindowDetail', params: { id: dishData.windowId } }">
          {{ dishData.windowName }}
        </router-link>
        ({{ dishData.canteenName }} - {{ dishData.campus === 'weijinlu' ? '卫津路' : '北洋园' }})
      </p>
    </div>

    <div class="dish-content-grid">
      <div class="dish-image-section">
        <!-- 菜品主图片占位 -->
        <div class="main-dish-image-placeholder">
          <i class="fas fa-utensils"></i>
          <span>菜品图片区域</span>
        </div>
        <!-- 如果有多张图片，可以做成轮播 -->
      </div>

      <div class="dish-info-section card">
        <h2 class="section-title">菜品信息</h2>
        <p class="dish-price-detail"><strong>价格:</strong> <span class="price">￥{{ dishData.price }}</span></p>
        <p class="dish-description-full"><strong>描述:</strong> {{ dishData.description || '暂无详细描述。' }}</p>
        <p v-if="dishData.ingredients"><strong>主要成分:</strong> {{ dishData.ingredients }}</p>
        <p v-if="dishData.taste"><strong>口味:</strong> {{ dishData.taste }}</p>

        <div class="dish-actions">
          <button @click="toggleFavorite" :class="['btn-action', { active: isFavorited }]">
            <i :class="isFavorited ? 'fas fa-heart' : 'far fa-heart'"></i>
            {{ isFavorited ? '已收藏' : '收藏' }} ({{ dishData.favoriteCount || 0 }})
          </button>
          <button @click="toggleLike" :class="['btn-action', { active: isLiked }]">
            <i :class="isLiked ? 'fas fa-thumbs-up' : 'far fa-thumbs-up'"></i>
            {{ isLiked ? '已点赞' : '点赞' }} ({{ dishData.likeCount || 0 }})
          </button>
        </div>
      </div>
    </div>

    <section class="dish-comments-section card">
      <h2 class="section-title"><i class="fas fa-comments"></i> 用户评价 ({{ comments.length }})</h2>
      <div class="add-comment-form" v-if="isUserLoggedIn">
        <h4>发表您的评价:</h4>
        <textarea v-model="newCommentText" rows="3" placeholder="输入您的评价内容..."></textarea>
        <!-- 简单星级评分占位 -->
        <div class="rating-input">
          <span>评分: </span>
          <i v-for="n in 5" :key="n" class="far fa-star" @click="setRating(n)" :class="{'fas': n <= newCommentRating, 'rated': n <= newCommentRating}"></i>
        </div>
        <button @click="submitComment" class="btn btn-primary btn-sm" :disabled="!newCommentText.trim()">提交评价</button>
      </div>
      <p v-else>
        <router-link :to="{name: 'Login', query: {redirect: $route.fullPath}}">登录</router-link> 后才能发表评价。
      </p>

      <div class="comments-list" v-if="comments.length > 0">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <div class="comment-header">
            <strong class="comment-user">{{ comment.user }}</strong>
            <span class="comment-date">{{ formatDate(comment.date) }}</span>
            <span class="comment-rating">
                    <i v-for="n in comment.rating" :key="`filled-${n}`" class="fas fa-star"></i>
                    <i v-for="n in (5 - comment.rating)" :key="`empty-${n}`" class="far fa-star"></i>
                </span>
          </div>
          <p class="comment-text">{{ comment.text }}</p>
        </div>
      </div>
      <p v-else-if="!isUserLoggedIn" class="no-data">暂无用户评价。</p>
      <p v-else class="no-data">暂无用户评价，快来抢沙发吧！</p>
    </section>

  </div>
  <div v-else-if="loading" class="loading-placeholder full-page-loader">
    <i class="fas fa-spinner fa-spin"></i> 正在加载菜品详情...
  </div>
  <div v-else class="no-data full-page-loader">
    <i class="fas fa-exclamation-circle"></i> 抱歉，找不到该菜品的信息。
  </div>
</template>

<script>
export default {
  name: 'DishDetailView',
  data() {
    return {
      loading: true,
      dishId: null,
      dishData: null, // { id, name, price, description, ingredients, taste, windowId, windowName, canteenName, campus, favoriteCount, likeCount }
      comments: [],   // [{ id, user, date, text, rating }]
      isFavorited: false, // 模拟用户是否收藏
      isLiked: false,     // 模拟用户是否点赞
      newCommentText: '',
      newCommentRating: 0,
      // 模拟数据
      allDishesFullData: {
        10101: { id: 10101, name: '红烧牛肉面', price: 15, description: '精选牛腱肉，搭配秘制酱料，慢火炖煮，面条劲道爽滑，汤汁浓郁。', ingredients: '牛肉、面条、青菜、香料', taste: '咸鲜微辣', windowId: 101, windowName: '红烧牛肉面窗口', canteenName: '学一食堂', campus:'weijinlu', favoriteCount: 125, likeCount: 302 },
        10102: { id: 10102, name: '加肉红烧牛肉面', price: 20, description: '双倍牛肉，满足食肉爱好者，其他同上。', ingredients: '加量牛肉、面条、青菜、香料', taste: '咸鲜微辣', windowId: 101, windowName: '红烧牛肉面窗口', canteenName: '学一食堂', campus:'weijinlu', favoriteCount: 88, likeCount: 150 },
        10201: { id: 10201, name: '鱼香肉丝', price: 12, description: '经典川菜，酸甜辣咸鲜，肉丝滑嫩，配菜爽口。', ingredients: '猪里脊、木耳、笋丝、胡萝卜', taste: '鱼香', windowId: 102, windowName: '特色小炒窗口', canteenName: '学一食堂', campus:'weijinlu', favoriteCount: 210, likeCount: 450 },
        20301: { id: 20301, name: '叉烧饭', price: 20, description: '蜜汁叉烧，肥瘦相间，搭配青菜和米饭。', ingredients: '叉烧肉、青菜、米饭', taste: '甜咸', windowId: 203, windowName: '竹园烧腊窗口', canteenName: '竹园食堂', campus:'beiyangyuan', favoriteCount: 95, likeCount: 180 },
      },
      allCommentsData: [
        { id: 1, dishId: 10101, user: '爱吃面的小王', date: '2024-05-20', text: '这个牛肉面味道太正了，汤都喝完了！推荐！', rating: 5 },
        { id: 2, dishId: 10101, user: '路人甲', date: '2024-05-18', text: '还不错，牛肉挺多的，就是对我来说有点咸。', rating: 4 },
        { id: 3, dishId: 10201, user: '下饭王', date: '2024-05-19', text: '鱼香肉丝YYDS，每次必点！', rating: 5 },
        { id: 4, dishId: 20301, user: '广式靓仔', date: '2024-05-21', text: '叉烧味道可以，就是米饭有点硬。', rating: 3 },
      ]
    };
  },
  computed: {
    isUserLoggedIn(){
      return this.$auth && this.$auth.isLoggedIn();
    }
  },
  methods: {
    fetchDishDetails() {
      this.loading = true;
      this.dishId = parseInt(this.$route.params.id);
      console.log(`Fetching details for dish ID: ${this.dishId}`);

      setTimeout(() => {
        this.dishData = this.allDishesFullData[this.dishId] || null;
        if (this.dishData) {
          this.comments = this.allCommentsData.filter(comment => comment.dishId === this.dishId);
          // 模拟获取用户是否已收藏/点赞 (实际应从API获取)
          this.isFavorited = Math.random() > 0.7;
          this.isLiked = Math.random() > 0.5;
        }
        this.loading = false;
      }, 800);
    },
    toggleFavorite() {
      if (!this.isUserLoggedIn) {
        alert('请先登录再进行操作！');
        this.$router.push({name: 'Login', query: {redirect: this.$route.fullPath}});
        return;
      }
      this.isFavorited = !this.isFavorited;
      this.dishData.favoriteCount += this.isFavorited ? 1 : -1;
      // 模拟API调用
      console.log(`菜品 ${this.dishData.name} ${this.isFavorited ? '已收藏' : '取消收藏'}`);
    },
    toggleLike() {
      if (!this.isUserLoggedIn) {
        alert('请先登录再进行操作！');
        this.$router.push({name: 'Login', query: {redirect: this.$route.fullPath}});
        return;
      }
      this.isLiked = !this.isLiked;
      this.dishData.likeCount += this.isLiked ? 1 : -1;
      // 模拟API调用
      console.log(`菜品 ${this.dishData.name} ${this.isLiked ? '已点赞' : '取消点赞'}`);
    },
    formatDate(dateString) {
      if (!dateString) return '';
      const options = { year: 'numeric', month: 'short', day: 'numeric' };
      return new Date(dateString).toLocaleDateString('zh-CN', options);
    },
    setRating(rating){
      this.newCommentRating = rating;
    },
    submitComment(){
      if(!this.newCommentText.trim() || this.newCommentRating === 0){
        alert('请输入评价内容并选择评分！');
        return;
      }
      const newComment = {
        id: Date.now(), // 简单唯一ID
        dishId: this.dishId,
        user: localStorage.getItem('username') || '匿名用户',
        date: new Date().toISOString().split('T')[0],
        text: this.newCommentText,
        rating: this.newCommentRating
      };
      this.comments.unshift(newComment); // 添加到列表顶部
      this.allCommentsData.push(newComment); // 更新模拟总数据
      this.newCommentText = '';
      this.newCommentRating = 0;
      alert('评价提交成功！');
      // 模拟API调用
      console.log('新评价提交:', newComment);
    }
  },
  created() {
    this.fetchDishDetails();
  },
  beforeRouteUpdate(to, from, next) {
    this.fetchDishDetails();
    next();
  }
}
</script>

<style scoped>
.dish-detail-view {
  padding-top: 20px;
  padding-bottom: 40px;
}

.page-header.dish-header {
  text-align: left; /* 标题靠左 */
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}
.header-main {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}
.header-icon {
  font-size: 2.8em; /* 更大的图标 */
  color: #e67e22; /* 橙色图标 */
  margin-right: 15px;
}
.dish-header h1 {
  font-size: 2.5em;
  color: #333;
  margin: 0;
}
.window-breadcrumb {
  font-size: 1em;
  color: #6c757d;
}
.window-breadcrumb a {
  color: #0056b3;
  text-decoration: none;
}
.window-breadcrumb a:hover {
  text-decoration: underline;
}

.dish-content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr; /* 两栏布局 */
  gap: 30px;
  margin-bottom: 30px;
}
@media (max-width: 992px) {
  .dish-content-grid {
    grid-template-columns: 1fr; /* 中等屏幕单栏 */
  }
}


.dish-image-section .main-dish-image-placeholder {
  width: 100%;
  min-height: 300px; /* 图片区域最小高度 */
  max-height: 450px;
  background-color: #e9ecef;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  color: #adb5bd;
}
.main-dish-image-placeholder i {
  font-size: 5em;
  margin-bottom: 15px;
}
.main-dish-image-placeholder span {
  font-size: 1.2em;
}

.dish-info-section.card,
.dish-comments-section.card {
  background-color: #fff;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 1px 5px rgba(0,0,0,0.05);
}

.section-title {
  font-size: 1.6em;
  color: #333;
  margin-top: 0;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}
.section-title i {
  margin-right: 10px;
}
.dish-info-section .section-title { color: #007bff; }
.dish-comments-section .section-title { color: #28a745; }


.dish-price-detail {
  font-size: 1.2em;
  margin-bottom: 15px;
}
.dish-price-detail .price {
  font-size: 1.5em;
  color: #e67e22;
  font-weight: bold;
  margin-left: 8px;
}
.dish-description-full,
.dish-info-section p {
  font-size: 1em;
  color: #555;
  line-height: 1.7;
  margin-bottom: 12px;
}
.dish-info-section p strong {
  color: #333;
}

.dish-actions {
  margin-top: 25px;
  padding-top: 20px;
  border-top: 1px solid #f0f0f0;
  display: flex;
  gap: 15px;
}
.btn-action {
  flex-grow: 1;
  padding: 10px 15px;
  border: 1px solid #ced4da;
  background-color: #f8f9fa;
  color: #495057;
  border-radius: 5px;
  font-size: 0.95em;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}
.btn-action i {
  margin-right: 8px;
  font-size: 1.1em;
}
.btn-action.active {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}
.btn-action.active .fa-heart { color: #e74c3c; } /* 已收藏的心形用红色 */
.btn-action.active .fa-thumbs-up { color: #f1c40f;} /* 已点赞用黄色 */

.btn-action:not(.active):hover {
  border-color: #007bff;
  color: #007bff;
}


.add-comment-form {
  margin-bottom: 30px;
  padding: 15px;
  background-color: #f9f9f9;
  border-radius: 5px;
  border: 1px solid #eee;
}
.add-comment-form h4 {
  margin-top: 0;
  margin-bottom: 10px;
  font-size: 1.1em;
  color: #333;
}
.add-comment-form textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
  min-height: 70px;
  font-size: 0.95em;
}
.rating-input {
  margin-bottom: 12px;
  font-size: 1.1em;
}
.rating-input span {
  margin-right: 8px;
  color: #555;
}
.rating-input i {
  color: #ccc;
  cursor: pointer;
  transition: color 0.2s;
  margin: 0 2px;
}
.rating-input i.rated,
.rating-input i:hover {
  color: #f0ad4e; /* 黄色星星 */
}
.btn-sm {
  padding: 6px 12px;
  font-size: 0.9em;
}


.comments-list .comment-item {
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}
.comments-list .comment-item:last-child {
  border-bottom: none;
}
.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
  font-size: 0.9em;
}
.comment-user {
  font-weight: bold;
  color: #0056b3;
  margin-right: 10px;
}
.comment-date {
  color: #777;
  margin-right: auto; /* 推开评分到最右边 */
}
.comment-rating i {
  color: #f0ad4e; /* 黄色星星 */
  margin-left: 2px;
  font-size: 0.9em;
}
.comment-text {
  font-size: 0.95em;
  color: #444;
  line-height: 1.6;
  white-space: pre-wrap; /* 保留换行 */
}

.full-page-loader, .no-data { /* 重复样式，确保生效 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 300px;
  color: #6c757d;
}
.full-page-loader i, .no-data i {
  font-size: 3em;
  margin-bottom: 15px;
}
</style>
    