<template>
  <div class="blog-detail-view container" v-if="blogData">
    <article class="blog-article card">
      <header class="blog-header-detail">
        <h1>{{ blogData.title }}</h1>
        <div class="meta-info">
          <span class="author"><i class="fas fa-user"></i> 作者: {{ blogData.author }}</span>
          <span class="publish-date"><i class="fas fa-calendar-alt"></i> 发布于: {{ formatDate(blogData.publishedAt) }}</span>
          <span class="campus-tag" v-if="blogData.campus"><i class="fas fa-map-marker-alt"></i> {{ getCampusName(blogData.campus) }}</span>
        </div>
        <div class="related-canteen-tag" v-if="blogData.relatedCanteenName">
          <i class="fas fa-store"></i> 提及: {{ blogData.relatedCanteenName }}
        </div>
      </header>

      <!-- 博客主图片占位 -->
      <div class="blog-main-image-placeholder" v-if="blogData.coverImageUrl">
        <img :src="blogData.coverImageUrl" :alt="blogData.title" @error="onImageError">
      </div>
      <div class="blog-main-image-placeholder" v-else-if="blogData.imagePlaceholders && blogData.imagePlaceholders.length > 0">
        <i :class="blogData.imagePlaceholders[0].icon || 'fas fa-image'"></i> <!-- 使用博客数据中定义的占位图标 -->
        <span>{{ blogData.imagePlaceholders[0].text || '博客封面区域' }}</span>
      </div>


      <section class="blog-content" v-html="blogData.content"></section>

      <footer class="blog-footer-actions">
        <button @click="toggleLikeBlog" :class="['btn-action', { active: isBlogLiked }]">
          <i :class="isBlogLiked ? 'fas fa-thumbs-up' : 'far fa-thumbs-up'"></i>
          {{ isBlogLiked ? '已点赞' : '点赞' }} ({{ blogData.likeCount || 0 }})
        </button>
        <button @click="toggleFavoriteBlog" :class="['btn-action', { active: isBlogFavorited }]">
          <i :class="isBlogFavorited ? 'fas fa-heart' : 'far fa-heart'"></i>
          {{ isBlogFavorited ? '已收藏' : '收藏' }} ({{ blogData.favoriteCount || 0 }})
        </button>
        <!-- <button @click="shareBlog" class="btn-action">
          <i class="fas fa-share-alt"></i> 分享
        </button> -->
      </footer>
    </article>

    <section class="blog-comments-section card">
      <h2 class="section-title"><i class="fas fa-comments"></i> 评论区 ({{ comments.length }})</h2>
      <div class="add-comment-form" v-if="isUserLoggedIn">
        <h4>发表您的看法:</h4>
        <textarea v-model="newCommentText" rows="4" placeholder="输入您的评论内容..."></textarea>
        <button @click="submitBlogComment" class="btn btn-primary btn-sm" :disabled="!newCommentText.trim() || submittingComment">
          <i class="fas fa-spinner fa-spin" v-if="submittingComment"></i>
          <span v-else>提交评论</span>
        </button>
      </div>
      <p v-else>
        <router-link :to="{name: 'Login', query: {redirect: $route.fullPath}}">登录</router-link> 后才能发表评论。
      </p>

      <div class="comments-list" v-if="comments.length > 0">
        <div v-for="comment in comments" :key="comment.id" class="comment-item">
          <div class="comment-header">
            <strong class="comment-user">{{ comment.user }}</strong>
            <span class="comment-date">{{ formatDate(comment.date) }}</span>
          </div>
          <p class="comment-text" v-html="comment.text"></p> <!-- 假设评论内容也可能是HTML -->
          <!-- 回复等功能可在此扩展 -->
        </div>
      </div>
      <p v-else-if="!isUserLoggedIn && !loadingComments" class="no-data">暂无评论。</p>
      <p v-else-if="!loadingComments" class="no-data">暂无评论，快来发表第一条评论吧！</p>
      <div v-if="loadingComments" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载评论中...</div>
    </section>

  </div>
  <div v-else-if="loadingBlog" class="loading-placeholder full-page-loader">
    <i class="fas fa-spinner fa-spin"></i> 正在加载博客内容...
  </div>
  <div v-else class="no-data full-page-loader">
    <i class="fas fa-exclamation-circle"></i> 抱歉，找不到该博客。
    <p><router-link :to="{ name: 'Home' }">返回首页</router-link></p>
  </div>
</template>

<script>
export default {
  name: 'BlogDetailView',
  data() {
    return {
      loadingBlog: true,
      loadingComments: true,
      blogId: null,
      blogData: null,
      /*
      blogData: {
        id: null,
        title: '',
        author: '',
        publishedAt: '',
        campus: '', // 'weijinlu', 'beiyangyuan', 'all'
        relatedCanteenId: null,
        relatedCanteenName: '',
        coverImageUrl: '',
        imagePlaceholders: [{icon: 'fas fa-utensils', text: '美食图片'}], // 用于无图时的占位
        content: '', // HTML content
        likeCount: 0,
        favoriteCount: 0,
      },
      */
      comments: [], // [{ id, user, date, text (can be HTML) }]
      isBlogLiked: false,
      isBlogFavorited: false,
      newCommentText: '',
      submittingComment: false,
      // 模拟数据
      allBlogsFullData: {
        101: { id: 101, title: '探秘学三食堂二楼新品盖饭！', author: '美食家小张', publishedAt: '2024-05-28T10:30:00Z', campus: 'weijinlu', relatedCanteenId: 2, relatedCanteenName: '学三食堂', coverImageUrl: 'https://placehold.co/800x400/007bff/white?text=美味盖饭', content: '<p>今天我鼓起勇气，再次踏入了传说中的学三食堂二楼，目标直指最近风头正劲的新品——<strong>黑椒铁板牛肉盖饭</strong>！</p><p>排队的人不算太多，看来我错峰成功了。打饭阿姨手速飞快，一份热气腾腾的盖饭很快就到了我的手中。铁板滋滋作响，黑椒的香气扑鼻而来，让人食指大动。</p><h2>外观与份量</h2><p>牛肉片给的还挺足，洋葱和青椒作为配菜，颜色搭配也挺好看。米饭的份量对于我一个男生来说刚刚好，女生可能会觉得稍微多一点点。</p><h2>口感与味道</h2><p>牛肉很嫩，黑椒汁调味浓郁，非常下饭！洋葱的微甜和青椒的清爽也很好地中和了黑椒的辛辣感。米饭粒粒分明，裹满了酱汁，每一口都很满足。</p><h3>小建议：</h3><ul><li>可以尝试让阿姨少给点汁，如果口味比较清淡的话。</li><li>搭配一杯冰饮会更爽！</li></ul><p>总的来说，这款新品盖饭我给打 <strong>4.5颗星</strong> (满分5星)！性价比很高，值得一试！</p>', likeCount: 158, favoriteCount: 77 },
        102: { id: 102, title: '北洋园梅园食堂早餐攻略（2024版）', author: '早起干饭人', publishedAt: '2024-05-27T07:45:00Z', campus: 'beiyangyuan', relatedCanteenId: 4, relatedCanteenName: '梅园食堂', coverImageUrl: 'https://placehold.co/800x400/28a745/white?text=梅园早餐', content: '<p>作为一名资深的北洋园“早八魂”，梅园食堂的早餐是我每天的续命源泉。经过长时间的探索和品尝，特此整理一份超全早餐攻略，希望能帮到同样早起的你！</p><h3>必吃榜单 TOP 3:</h3><ol><li><strong>豆腐脑（咸）+ 煎饼果子:</strong> 经典搭配，豆腐脑嫩滑，卤汁鲜美；煎饼果子现点现做，薄脆香酥。</li><li><strong>小笼包:</strong> 皮薄馅大，汤汁浓郁，一屉根本不够吃！</li><li><strong>各类粥品 + 鸡蛋饼:</strong> 养生组合，粥熬得很糯，鸡蛋饼松软可口。</li></ol><h4>其他推荐：</h4><ul><li>烧麦</li><li>油条（偶尔会有）</li><li>豆浆（甜/无糖可选）</li></ul><p>梅园食堂的早餐窗口比较多，建议提前一点去，不然热门档口可能会排长队哦！祝大家每天都能吃到元气满满的早餐！☀️</p>', likeCount: 230, favoriteCount: 120 },
        103: { id: 103, title: '我的天大校园VLOG - 食堂篇', author: '校园UP主小李', publishedAt: '2024-05-25T18:00:00Z', campus: 'all', relatedCanteenId: null, relatedCanteenName: '多个食堂', coverImageUrl: null, imagePlaceholders: [{icon:'fas fa-video', text:'VLOG封面'}], content: '<p>哈喽大家好！我是小李！这期VLOG带大家云游我们美丽的<strong>天津大学</strong>，重点打卡那些传说中的神仙食堂！</p><p>从卫津路的老牌经典到北洋园的现代美食广场，我们一一探访！</p><p><em>视频内容请看XXX...（此处应为视频嵌入或链接）</em></p><p>记得三连支持一下哦！评论区告诉我你们最爱哪个食堂的哪道菜！</p>', likeCount: 500, favoriteCount: 250 },
      },
      allBlogCommentsData: [
        { id: 201, blogId: 101, user: '牛肉面忠粉', date: '2024-05-28T12:00:00Z', text: '博主说的太对了！这个牛肉饭真的绝！我连吃三天了！' },
        { id: 202, blogId: 101, user: '吃货小探员', date: '2024-05-29T09:15:00Z', text: '感谢测评！明天就去试试看！希望不要排队太久。' },
        { id: 203, blogId: 102, user: '夜猫子', date: '2024-05-27T23:30:00Z', text: '呜呜呜，为什么我起床的时候梅园早餐都没了😭' },
      ]
    };
  },
  computed: {
    isUserLoggedIn(){
      return this.$auth && this.$auth.isLoggedIn();
    }
  },
  methods: {
    fetchBlogDetails() {
      this.loadingBlog = true;
      this.loadingComments = true;
      this.blogId = parseInt(this.$route.params.id);
      console.log(`Fetching details for blog ID: ${this.blogId}`);

      setTimeout(() => {
        this.blogData = this.allBlogsFullData[this.blogId] || null;
        if (this.blogData) {
          // 模拟获取用户是否已收藏/点赞
          this.isBlogLiked = Math.random() > 0.6;
          this.isBlogFavorited = Math.random() > 0.5;
        }
        this.loadingBlog = false;
        this.fetchBlogComments(); // 获取博客评论
      }, 700);
    },
    fetchBlogComments() {
      if (!this.blogData) {
        this.loadingComments = false;
        return;
      }
      // 模拟API调用
      setTimeout(() => {
        this.comments = this.allBlogCommentsData.filter(comment => comment.blogId === this.blogId);
        this.loadingComments = false;
      }, 500);
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A';
      return new Date(dateString).toLocaleString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric', hour:'2-digit', minute: '2-digit' });
    },
    getCampusName(campusKey) {
      const names = { weijinlu: '卫津路校区', beiyangyuan: '北洋园校区', all: '全校区' };
      return names[campusKey] || '未知校区';
    },
    onImageError(event){
      event.target.src = 'https://placehold.co/800x400/cccccc/969696?text=图片加载失败';
      event.target.style.objectFit = 'contain';
    },
    toggleLikeBlog() {
      if (!this.isUserLoggedIn) { this.redirectToLogin(); return; }
      this.isBlogLiked = !this.isBlogLiked;
      this.blogData.likeCount += this.isBlogLiked ? 1 : -1;
      console.log(`博客 ${this.blogData.title} ${this.isBlogLiked ? '已点赞' : '取消点赞'}`);
    },
    toggleFavoriteBlog() {
      if (!this.isUserLoggedIn) { this.redirectToLogin(); return; }
      this.isBlogFavorited = !this.isBlogFavorited;
      this.blogData.favoriteCount += this.isBlogFavorited ? 1 : -1;
      console.log(`博客 ${this.blogData.title} ${this.isBlogFavorited ? '已收藏' : '取消收藏'}`);
    },
    // shareBlog() {
    //   // 实现分享功能，例如复制链接或调用社交分享API
    //   alert('分享功能待实现！');
    // },
    submitBlogComment() {
      if (!this.newCommentText.trim()) return;
      if (!this.isUserLoggedIn) { this.redirectToLogin(); return; }

      this.submittingComment = true;
      const newComment = {
        id: Date.now(),
        blogId: this.blogId,
        user: localStorage.getItem('username') || '访客用户',
        date: new Date().toISOString(),
        text: this.newCommentText,
      };
      // 模拟API提交
      setTimeout(() => {
        this.comments.unshift(newComment);
        this.allBlogCommentsData.push(newComment); // 更新总数据
        this.newCommentText = '';
        this.submittingComment = false;
        alert('评论发表成功！');
      }, 1000);
    },
    redirectToLogin(){
      alert('请先登录再进行操作！');
      this.$router.push({name: 'Login', query: {redirect: this.$route.fullPath}});
    }
  },
  created() {
    this.fetchBlogDetails();
  },
  beforeRouteUpdate(to, from, next) {
    // 如果从一个博客详情页跳转到另一个博客详情页
    this.blogId = parseInt(to.params.id);
    this.fetchBlogDetails();
    next();
  }
}
</script>

<style scoped>
.blog-detail-view {
  padding-top: 20px;
  padding-bottom: 40px;
}
.card { /* 通用卡片样式 */
  background-color: #fff;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.07);
  margin-bottom: 30px;
}

.blog-article.card {
  padding: 30px 35px; /* 博客正文卡片内边距稍大 */
}

.blog-header-detail {
  margin-bottom: 25px;
  padding-bottom: 20px;
  border-bottom: 1px solid #f0f0f0;
}
.blog-header-detail h1 {
  font-size: 2.6em;
  color: #2c3e50; /* 深蓝灰色 */
  margin-top: 0;
  margin-bottom: 15px;
  line-height: 1.3;
}
.meta-info {
  display: flex;
  flex-wrap: wrap;
  gap: 10px 20px; /* 行间距和列间距 */
  font-size: 0.9em;
  color: #7f8c8d; /* 灰蓝色 */
  margin-bottom: 10px;
}
.meta-info span i {
  margin-right: 6px;
}
.campus-tag {
  background-color: #e9f5ff;
  color: #007bff;
  padding: 3px 8px;
  border-radius: 4px;
  font-size: 0.85em;
}
.related-canteen-tag {
  font-size: 0.9em;
  color: #555;
  background-color: #f1f3f5;
  padding: 5px 10px;
  border-radius: 4px;
  display: inline-block;
  margin-top:5px;
}
.related-canteen-tag i { margin-right: 5px; color: #e67e22; }


.blog-main-image-placeholder {
  width: 100%;
  max-height: 450px; /* 限制最大高度 */
  min-height: 250px;
  background-color: #e9ecef;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  color: #adb5bd;
  margin-bottom: 25px;
  overflow: hidden; /* 确保图片不超出圆角 */
}
.blog-main-image-placeholder img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 图片覆盖整个区域 */
}
.blog-main-image-placeholder i {
  font-size: 5em;
  margin-bottom: 15px;
}
.blog-main-image-placeholder span {
  font-size: 1.2em;
}

.blog-content {
  font-size: 1.1em;
  line-height: 1.8;
  color: #34495e; /* 文本颜色 */
  word-wrap: break-word;
}
/* 针对 v-html 内容的样式，需要确保它们不带 scoped 或使用深度选择器 */
.blog-content ::v-deep p { margin-bottom: 1.5em; }
.blog-content ::v-deep h2 { font-size: 1.8em; margin-top: 1.8em; margin-bottom: 0.8em; border-bottom: 1px solid #eee; padding-bottom: 0.3em;}
.blog-content ::v-deep h3 { font-size: 1.5em; margin-top: 1.5em; margin-bottom: 0.6em;}
.blog-content ::v-deep ul,
.blog-content ::v-deep ol { margin-left: 1.5em; margin-bottom: 1.5em; }
.blog-content ::v-deep li { margin-bottom: 0.5em; }
.blog-content ::v-deep strong { color: #2c3e50; }
.blog-content ::v-deep a { color: #007bff; text-decoration: underline; }
.blog-content ::v-deep img { max-width: 100%; height: auto; border-radius: 6px; margin: 1em 0; }


.blog-footer-actions {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #f0f0f0;
  display: flex;
  justify-content: center; /* 按钮居中 */
  gap: 20px;
}
.btn-action {
  padding: 10px 20px;
  border: 1px solid #ced4da;
  background-color: #f8f9fa;
  color: #495057;
  border-radius: 25px; /* 圆角按钮 */
  font-size: 1em;
  font-weight:500;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
}
.btn-action i {
  margin-right: 8px;
  font-size: 1.2em;
}
.btn-action.active {
  border-color: #007bff;
  color: #007bff;
}
.btn-action.active .fa-heart { color: #e74c3c; } /* 已收藏的心形用红色 */
.btn-action.active .fa-thumbs-up { color: #007bff; } /* 已点赞的图标也用主题色 */

.btn-action:not(.active):hover {
  border-color: #007bff;
  color: #007bff;
  /* background-color: #e9f5ff; */
}

.blog-comments-section .section-title {
  font-size: 1.8em;
  color: #333;
  margin-top: 0;
  margin-bottom: 25px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}
.blog-comments-section .section-title i { margin-right: 10px; color: #28a745; }

.add-comment-form {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 6px;
  border: 1px solid #f0f0f0;
}
.add-comment-form h4 { margin-top: 0; margin-bottom: 12px; font-size: 1.2em; color: #333; }
.add-comment-form textarea { width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 4px; margin-bottom: 12px; min-height: 80px; font-size: 1em; }
.btn-sm { padding: 8px 15px; font-size: 0.9em; }
.btn-primary { background-color: #007bff; color: white; border: none; }
.btn-primary:hover:not(:disabled) { background-color: #0056b3; }
.btn-primary:disabled { background-color: #007bffaa; cursor: not-allowed; }

.comments-list .comment-item { padding: 18px 0; border-bottom: 1px solid #f0f0f0; }
.comments-list .comment-item:last-child { border-bottom: none; }
.comment-header { display: flex; align-items: center; margin-bottom: 8px; font-size: 0.9em; }
.comment-user { font-weight: bold; color: #0056b3; margin-right: 12px; }
.comment-date { color: #777; }
.comment-text { font-size: 1em; color: #444; line-height: 1.7; white-space: pre-wrap; }

.full-page-loader, .no-data { display: flex; flex-direction: column; justify-content: center; align-items: center; min-height: 300px; color: #6c757d; }
.full-page-loader i, .no-data i { font-size: 3em; margin-bottom: 15px; }
.no-data p { margin-top:10px; }
.no-data a { color: #007bff; text-decoration:none; }
.no-data a:hover { text-decoration:underline; }
</style>
