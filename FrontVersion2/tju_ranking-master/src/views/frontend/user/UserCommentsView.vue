<template>
  <div class="user-comments-view">
    <h3 class="section-title">我的评价</h3>
    <div v-if="loading" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载评价列表...</div>
    <ul v-else-if="comments.length > 0" class="comment-list-detailed">
      <li v-for="comment in comments" :key="comment.id" class="comment-item-detailed">
        <p class="comment-text-detailed">“{{ comment.text }}”</p>
        <div class="comment-meta-detailed">
          <span class="rating">
            <i v-for="n in comment.rating" :key="`f-${n}`" class="fas fa-star"></i>
            <i v-for="n in (5-comment.rating)" :key="`e-${n}`" class="far fa-star"></i>
          </span>
          <span>评价于
            <router-link :to="comment.targetLink">{{ comment.targetName }}</router-link>
          </span>
          <span class="comment-time">{{ formatDate(comment.date) }}</span>
        </div>
      </li>
    </ul>
    <div v-else class="no-data">您还没有发表任何评价。</div>
  </div>
</template>

<script>
export default {
  name: 'UserCommentsView',
  data() {
    return {
      loading: true,
      comments: [],
    };
  },
  methods: {
    formatDate(dateString) { return new Date(dateString).toLocaleString('zh-CN'); },
    fetchComments() {
      this.loading = true;
      setTimeout(() => {
        this.comments = [
          { id: 1, text: '这个牛肉面味道太正了，汤都喝完了！推荐！', rating: 5, targetName: '红烧牛肉面', targetLink: { name: 'DishDetail', params: { id: 10101 } }, date: '2025-06-05T14:30:00Z' },
          { id: 3, text: '博主写得很详细，赞一个！', rating: 5, targetName: '北洋园梅园食堂早餐攻略', targetLink: { name: 'BlogDetail', params: { id: 102 } }, date: '2025-06-04T22:15:00Z' },
        ];
        this.loading = false;
      }, 800);
    }
  },
  created() {
    this.fetchComments();
  }
}
</script>

<style scoped>
.section-title { font-size: 1.4em; color: #333; margin-top: 0; margin-bottom: 20px; }
.loading-placeholder, .no-data { text-align: center; padding: 30px; color: #888; }
.comment-list-detailed { list-style: none; padding: 0; margin: 0; }
.comment-item-detailed { background-color: #fdfdfd; padding: 20px; border: 1px solid #f0f0f0; border-radius: 6px; margin-bottom: 15px; }
.comment-text-detailed { font-size: 1.1em; color: #333; margin-top: 0; margin-bottom: 15px; font-style: italic; }
.comment-meta-detailed { display: flex; align-items: center; gap: 15px; font-size: 0.9em; color: #666; }
.comment-meta-detailed .rating i { color: #f0ad4e; }
.comment-meta-detailed a { color: #007bff; text-decoration: none; }
.comment-meta-detailed a:hover { text-decoration: underline; }
.comment-meta-detailed .comment-time { margin-left: auto; color: #999; }
</style>
