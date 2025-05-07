<template>
  <div class="blog-detail-page" v-if="blog">
    <div class="container">
      <div class="breadcrumb">
        <router-link to="/">首页</router-link>
        <span class="separator">/</span>
        <router-link to="/blogs">美食博客</router-link>
        <span class="separator">/</span>
        <span class="current">{{ blog.title }}</span>
      </div>

      <article class="blog-content">
        <header class="blog-header">
          <h1 class="blog-title">{{ blog.title }}</h1>
          <div class="blog-meta">
            <div class="author-info">
              <img :src="author.avatar" :alt="author.nickname" class="author-avatar" />
              <div class="author-details">
                <span class="author-name">{{ author.nickname }}</span>
                <span class="blog-date">{{ blog.createTime }}</span>
              </div>
            </div>
            <div class="blog-stats">
              <span class="stat-item">
                <span class="material-icons">thumb_up</span>
                {{ blog.liked }}
              </span>
              <span class="stat-item">
                <span class="material-icons">comment</span>
                {{ blog.comments }}
              </span>
            </div>
          </div>
        </header>

        <div class="blog-gallery" v-if="blog.images && blog.images.length > 0">
          <swiper
              :slides-per-view="1"
              :pagination="{ clickable: true }"
              :navigation="true"
              :modules="[Pagination, Navigation]"
              class="blog-swiper"
          >
            <swiper-slide v-for="(image, index) in blog.images" :key="index">
              <div class="image-container">
                <img :src="image" :alt="`博客图片 ${index + 1}`" class="blog-image" />
              </div>
            </swiper-slide>
          </swiper>
        </div>

        <div class="blog-text">
          {{ blog.content }}
        </div>

        <div class="blog-footer">
          <div class="action-buttons">
            <button
                class="action-btn like-btn"
                :class="{ active: isLiked }"
                @click="handleLike"
            >
              <span class="material-icons">{{ isLiked ? 'favorite' : 'favorite_border' }}</span>
              点赞
            </button>
            <button class="action-btn">
              <span class="material-icons">share</span>
              分享
            </button>
          </div>

          <div class="related-items" v-if="relatedDishes.length > 0">
            <h3 class="section-title">相关菜品</h3>
            <div class="related-dishes">
              <DishCard
                  v-for="dish in relatedDishes"
                  :key="dish.id"
                  :dish="dish"
              />
            </div>
          </div>
        </div>
      </article>

      <section class="comments-section">
        <h2 class="section-title">评论 ({{ comments.length }})</h2>

        <div class="comment-form" v-if="isAuthenticated">
          <textarea
              v-model="newComment"
              placeholder="写下你的评论..."
              rows="3"
          ></textarea>
          <button
              class="submit-btn"
              :disabled="!newComment.trim()"
              @click="handleSubmitComment"
          >
            发表评论
          </button>
        </div>

        <div class="login-prompt" v-else>
          <router-link to="/login" class="login-link">登录</router-link>
          后参与评论
        </div>

        <div class="comments-list">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <div class="comment-header">
              <div class="commenter-info">
                <img :src="comment.userAvatar" :alt="comment.userNickname" class="commenter-avatar" />
                <div class="commenter-details">
                  <span class="commenter-name">{{ comment.userNickname }}</span>
                  <span class="comment-date">{{ comment.createTime }}</span>
                </div>
              </div>
              <button
                  class="like-btn"
                  :class="{ active: isCommentLiked(comment.id) }"
                  @click="handleCommentLike(comment.id)"
              >
                <span class="material-icons">{{ isCommentLiked(comment.id) ? 'favorite' : 'favorite_border' }}</span>
                <span>{{ comment.liked }}</span>
              </button>
            </div>
            <p class="comment-content">{{ comment.content }}</p>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useDataStore } from '../../stores/data'
import { useAuthStore } from '../../stores/auth'
import { Swiper, SwiperSlide } from 'swiper/vue'
import { Pagination, Navigation } from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/pagination'
import 'swiper/css/navigation'
import DishCard from '../../components/cards/DishCard.vue'

const route = useRoute()
const router = useRouter()
const dataStore = useDataStore()
const authStore = useAuthStore()

const blogId = computed(() => Number(route.params.id))
const blog = computed(() => dataStore.getBlogById(blogId.value))
const author = computed(() => {
  return {
    nickname: blog.value?.userId === 1 ? '美食达人' : '用户',
    avatar: 'https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg'
  }
})
const comments = computed(() => dataStore.getCommentsByBlogId(blogId.value))
const relatedDishes = computed(() => {
  if (!blog.value?.stallId) return []
  return dataStore.getDishesByStallId(blog.value.stallId).slice(0, 3)
})

const isAuthenticated = computed(() => authStore.isAuthenticated)
const isLiked = ref(false)
const likedComments = ref(new Set())
const newComment = ref('')

function handleLike() {
  if (!isAuthenticated.value) {
    router.push('/login')
    return
  }
  isLiked.value = !isLiked.value
}

function isCommentLiked(commentId) {
  return likedComments.value.has(commentId)
}

function handleCommentLike(commentId) {
  if (!isAuthenticated.value) {
    router.push('/login')
    return
  }
  if (likedComments.value.has(commentId)) {
    likedComments.value.delete(commentId)
  } else {
    likedComments.value.add(commentId)
  }
}

function handleSubmitComment() {
  if (!newComment.value.trim()) return
  // Add comment logic here
  newComment.value = ''
}

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
.blog-detail-page {
  padding: var(--space-4) 0 var(--space-16);
}

.breadcrumb {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  font-size: 0.875rem;
  margin-bottom: var(--space-6);
  color: var(--color-gray-500);
}

.breadcrumb a {
  color: var(--color-gray-600);
  transition: color var(--transition-fast);
}

.breadcrumb a:hover {
  color: var(--color-primary-600);
}

.separator {
  margin: 0 var(--space-2);
  color: var(--color-gray-400);
}

.current {
  color: var(--color-gray-800);
  font-weight: 500;
}

.blog-content {
  background-color: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  overflow: hidden;
  margin-bottom: var(--space-8);
}

.blog-header {
  padding: var(--space-6);
  border-bottom: 1px solid var(--color-gray-200);
}

.blog-title {
  font-size: 2rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-4);
}

.blog-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.author-info {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.author-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.author-details {
  display: flex;
  flex-direction: column;
}

.author-name {
  font-weight: 500;
  color: var(--color-gray-800);
}

.blog-date {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.blog-stats {
  display: flex;
  gap: var(--space-4);
}

.stat-item {
  display: flex;
  align-items: center;
  gap: var(--space-1);
  color: var(--color-gray-600);
  font-size: 0.875rem;
}

.stat-item .material-icons {
  font-size: 18px;
}

.blog-gallery {
  margin-bottom: var(--space-6);
}

.blog-swiper {
  width: 100%;
  height: 400px;
}

.image-container {
  width: 100%;
  height: 100%;
}

.blog-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.blog-text {
  padding: var(--space-6);
  color: var(--color-gray-800);
  line-height: 1.8;
  font-size: 1.125rem;
}

.blog-footer {
  padding: var(--space-6);
  border-top: 1px solid var(--color-gray-200);
}

.action-buttons {
  display: flex;
  gap: var(--space-4);
  margin-bottom: var(--space-6);
}

.action-btn {
  display: flex;
  align-items: center;
  gap: var(--space-1);
  padding: var(--space-2) var(--space-4);
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-full);
  background: white;
  color: var(--color-gray-600);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.action-btn:hover {
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.action-btn.active {
  background-color: var(--color-primary-50);
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.like-btn.active {
  color: var(--color-accent-600);
  border-color: var(--color-accent-300);
  background-color: var(--color-accent-50);
}

.section-title {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-4);
}

.related-dishes {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: var(--space-4);
}

.comments-section {
  background-color: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  padding: var(--space-6);
}

.comment-form {
  margin-bottom: var(--space-6);
}

.comment-form textarea {
  width: 100%;
  padding: var(--space-3);
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-md);
  resize: vertical;
  min-height: 100px;
  margin-bottom: var(--space-3);
  outline: none;
  transition: all var(--transition-fast);
}

.comment-form textarea:focus {
  border-color: var(--color-primary-400);
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

.submit-btn {
  padding: var(--space-2) var(--space-4);
  background-color: var(--color-primary-600);
  color: white;
  border: none;
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.submit-btn:hover:not(:disabled) {
  background-color: var(--color-primary-700);
}

.submit-btn:disabled {
  background-color: var(--color-gray-400);
  cursor: not-allowed;
}

.login-prompt {
  text-align: center;
  padding: var(--space-4);
  color: var(--color-gray-600);
  margin-bottom: var(--space-6);
}

.login-link {
  color: var(--color-primary-600);
  font-weight: 500;
}

.login-link:hover {
  text-decoration: underline;
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-4);
}

.comment-item {
  padding: var(--space-4);
  border: 1px solid var(--color-gray-200);
  border-radius: var(--radius-md);
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--space-2);
}

.commenter-info {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.commenter-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.commenter-details {
  display: flex;
  flex-direction: column;
}

.commenter-name {
  font-weight: 500;
  color: var(--color-gray-800);
}

.comment-date {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.comment-content {
  color: var(--color-gray-800);
  line-height: 1.6;
}

@media (min-width: 768px) {
  .blog-title {
    font-size: 2.5rem;
  }

  .blog-swiper {
    height: 500px;
  }
}
</style>