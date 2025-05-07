<template>
  <div class="profile-page" v-if="user">
    <div class="container">
      <div class="profile-header">
        <div class="profile-cover"></div>
        <div class="profile-info">
          <img :src="user.avatar" :alt="user.nickname" class="profile-avatar" />
          <div class="profile-details">
            <h1 class="profile-name">{{ user.nickname }}</h1>
            <div class="profile-meta">
              <span class="meta-item">
                <span class="material-icons">school</span>
                {{ user.campus }}
              </span>
              <span class="meta-item">
                <span class="material-icons">stars</span>
                Level {{ user.level }}
              </span>
              <span class="meta-item">
                <span class="material-icons">workspace_premium</span>
                {{ user.credits }} 积分
              </span>
            </div>
          </div>
          <button class="edit-profile-btn" @click="showEditModal = true">
            <span class="material-icons">edit</span>
            编辑资料
          </button>
        </div>
      </div>

      <div class="profile-tabs">
        <button
            v-for="tab in tabs"
            :key="tab.value"
            :class="['tab-btn', { active: currentTab === tab.value }]"
            @click="currentTab = tab.value"
        >
          {{ tab.label }}
        </button>
      </div>

      <!-- 我的博客 -->
      <div v-if="currentTab === 'blogs'" class="tab-content">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <BlogCard
              v-for="blog in userBlogs"
              :key="blog.id"
              :blog="blog"
          />
        </div>
        <div v-if="userBlogs.length === 0" class="empty-state">
          <span class="material-icons">article</span>
          <p>还没有发布过博客</p>
          <router-link to="/blog/create" class="btn btn-primary">
            写第一篇博客
          </router-link>
        </div>
      </div>

      <!-- 我的评价 -->
      <div v-if="currentTab === 'reviews'" class="tab-content">
        <div class="reviews-list">
          <div v-for="review in userReviews" :key="review.id" class="review-card">
            <div class="review-header">
              <div class="review-target">
                <img :src="getTargetImage(review)" :alt="getTargetName(review)" class="target-image" />
                <div class="target-info">
                  <h3 class="target-name">{{ getTargetName(review) }}</h3>
                  <Rating :value="review.overallScore" size="small" />
                </div>
              </div>
              <div class="review-date">{{ review.createTime }}</div>
            </div>
            <p class="review-content">{{ review.content }}</p>
            <div v-if="review.images && review.images.length > 0" class="review-images">
              <div
                  v-for="(img, index) in review.images"
                  :key="index"
                  class="review-image-item"
              >
                <img :src="img" alt="评价图片" class="review-image" />
              </div>
            </div>
            <div class="review-footer">
              <div class="review-stats">
                <span class="stat-item">
                  <span class="material-icons">thumb_up</span>
                  {{ review.liked }}
                </span>
              </div>
            </div>
          </div>
        </div>
        <div v-if="userReviews.length === 0" class="empty-state">
          <span class="material-icons">rate_review</span>
          <p>还没有发表过评价</p>
        </div>
      </div>

      <!-- 我的收藏 -->
      <div v-if="currentTab === 'favorites'" class="tab-content">
        <div class="favorites-grid">
          <div v-for="favorite in userFavorites" :key="favorite.id" class="favorite-card">
            <img :src="favorite.image" :alt="favorite.name" class="favorite-image" />
            <div class="favorite-content">
              <h3 class="favorite-name">{{ favorite.name }}</h3>
              <p class="favorite-type">{{ getFavoriteType(favorite.type) }}</p>
            </div>
          </div>
        </div>
        <div v-if="userFavorites.length === 0" class="empty-state">
          <span class="material-icons">favorite</span>
          <p>还没有收藏内容</p>
        </div>
      </div>

      <!-- 我的勋章 -->
      <div v-if="currentTab === 'medals'" class="tab-content">
        <div class="medals-grid">
          <div v-for="medal in userMedals" :key="medal.id" class="medal-card">
            <img :src="medal.icon" :alt="medal.name" class="medal-icon" />
            <h3 class="medal-name">{{ medal.name }}</h3>
            <p class="medal-description">{{ medal.description }}</p>
            <span class="medal-date">{{ medal.obtainTime }}</span>
          </div>
        </div>
        <div v-if="userMedals.length === 0" class="empty-state">
          <span class="material-icons">military_tech</span>
          <p>还没有获得勋章</p>
          <router-link to="/rankings" class="btn btn-primary">
            查看如何获得勋章
          </router-link>
        </div>
      </div>
    </div>

    <!-- 编辑资料弹窗 -->
    <div v-if="showEditModal" class="modal-overlay" @click="showEditModal = false">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h2>编辑个人资料</h2>
          <button class="close-btn" @click="showEditModal = false">
            <span class="material-icons">close</span>
          </button>
        </div>
        <form @submit.prevent="handleUpdateProfile" class="edit-form">
          <div class="form-group">
            <label>昵称</label>
            <input type="text" v-model="editForm.nickname" />
          </div>
          <div class="form-group">
            <label>校区</label>
            <select v-model="editForm.campus">
              <option value="北洋园校区">北洋园校区</option>
              <option value="卫津路校区">卫津路校区</option>
            </select>
          </div>
          <div class="form-group">
            <label>个人简介</label>
            <textarea v-model="editForm.bio" rows="3"></textarea>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-outline" @click="showEditModal = false">
              取消
            </button>
            <button type="submit" class="btn btn-primary">
              保存
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useAuthStore } from '../../stores/auth'
import { useDataStore } from '../../stores/data'
import BlogCard from '../../components/cards/BlogCard.vue'
import Rating from '../../components/common/Rating.vue'

const authStore = useAuthStore()
const dataStore = useDataStore()

const user = computed(() => authStore.user)
const currentTab = ref('blogs')
const showEditModal = ref(false)

const tabs = [
  { label: '我的博客', value: 'blogs' },
  { label: '我的评价', value: 'reviews' },
  { label: '我的收藏', value: 'favorites' },
  { label: '我的勋章', value: 'medals' }
]

const editForm = ref({
  nickname: user.value?.nickname || '',
  campus: user.value?.campus || '',
  bio: user.value?.bio || ''
})

// Mock data - In real app, these would come from API calls
const userBlogs = computed(() => {
  return dataStore.getAllBlogs().filter(blog => blog.userId === user.value?.id)
})

const userReviews = computed(() => {
  return dataStore.getReviewsByDishId(1).filter(review => review.userId === user.value?.id)
})

const userFavorites = ref([
  {
    id: 1,
    type: 'dish',
    name: '鱼香肉丝',
    image: 'https://images.pexels.com/photos/2347311/pexels-photo-2347311.jpeg'
  },
  // Add more mock favorites...
])

const userMedals = ref([
  {
    id: 1,
    name: '美食达人',
    description: '发布10篇高质量美食博客',
    icon: '/medals/food_master.png',
    obtainTime: '2023-12-01'
  },
  // Add more mock medals...
])

function getTargetImage(review) {
  // Mock function to get the image of the reviewed item
  return 'https://images.pexels.com/photos/958545/pexels-photo-958545.jpeg'
}

function getTargetName(review) {
  // Mock function to get the name of the reviewed item
  return '鱼香肉丝'
}

function getFavoriteType(type) {
  const types = {
    dish: '菜品',
    canteen: '食堂',
    stall: '窗口',
    blog: '博客'
  }
  return types[type] || '未知'
}

function handleUpdateProfile() {
  // Update profile logic here
  showEditModal.value = false
}
</script>

<style scoped>
.profile-page {
  padding: var(--space-4) 0 var(--space-16);
}

.profile-header {
  background-color: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  overflow: hidden;
  margin-bottom: var(--space-6);
}

.profile-cover {
  height: 200px;
  background-color: var(--color-primary-100);
  background-image: linear-gradient(135deg, var(--color-primary-50) 0%, var(--color-primary-100) 100%);
}

.profile-info {
  display: flex;
  align-items: flex-end;
  padding: var(--space-4);
  margin-top: -60px;
  position: relative;
}

.profile-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  border: 4px solid white;
  box-shadow: var(--shadow-md);
  margin-right: var(--space-4);
}

.profile-details {
  flex: 1;
}

.profile-name {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-2);
}

.profile-meta {
  display: flex;
  gap: var(--space-4);
  color: var(--color-gray-600);
}

.meta-item {
  display: flex;
  align-items: center;
  gap: var(--space-1);
  font-size: 0.875rem;
}

.meta-item .material-icons {
  font-size: 18px;
}

.edit-profile-btn {
  display: flex;
  align-items: center;
  gap: var(--space-1);
  padding: var(--space-2) var(--space-4);
  background-color: white;
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-full);
  color: var(--color-gray-700);
  font-size: 0.875rem;
  cursor: pointer;
  transition: all var(--transition-fast);
}

.edit-profile-btn:hover {
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.edit-profile-btn .material-icons {
  font-size: 18px;
}

.profile-tabs {
  display: flex;
  gap: var(--space-2);
  margin-bottom: var(--space-6);
  overflow-x: auto;
  padding-bottom: var(--space-2);
}

.tab-btn {
  padding: var(--space-2) var(--space-4);
  background: white;
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-full);
  color: var(--color-gray-600);
  font-size: 0.875rem;
  cursor: pointer;
  transition: all var(--transition-fast);
  white-space: nowrap;
}

.tab-btn:hover {
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.tab-btn.active {
  background-color: var(--color-primary-50);
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.tab-content {
  min-height: 300px;
}

.empty-state {
  text-align: center;
  padding: var(--space-8);
  color: var(--color-gray-500);
}

.empty-state .material-icons {
  font-size: 48px;
  margin-bottom: var(--space-2);
}

.empty-state .btn {
  margin-top: var(--space-4);
}

.review-card {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  box-shadow: var(--shadow-md);
  margin-bottom: var(--space-4);
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--space-3);
}

.review-target {
  display: flex;
  align-items: center;
  gap: var(--space-3);
}

.target-image {
  width: 60px;
  height: 60px;
  border-radius: var(--radius-md);
  object-fit: cover;
}

.target-name {
  font-weight: 600;
  color: var(--color-gray-800);
  margin-bottom: var(--space-1);
}

.review-date {
  font-size: 0.875rem;
  color: var(--color-gray-500);
}

.review-content {
  color: var(--color-gray-800);
  line-height: 1.6;
  margin-bottom: var(--space-3);
}

.review-images {
  display: flex;
  gap: var(--space-2);
  overflow-x: auto;
  padding-bottom: var(--space-2);
  margin-bottom: var(--space-3);
}

.review-image-item {
  flex: 0 0 120px;
  height: 90px;
  border-radius: var(--radius-md);
  overflow: hidden;
}

.review-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.review-footer {
  display: flex;
  justify-content: flex-end;
}

.review-stats {
  display: flex;
  gap: var(--space-3);
}

.stat-item {
  display: flex;
  align-items: center;
  gap: var(--space-1);
  color: var(--color-gray-500);
  font-size: 0.875rem;
}

.favorites-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: var(--space-4);
}

.favorite-card {
  background-color: white;
  border-radius: var(--radius-lg);
  overflow: hidden;
  box-shadow: var(--shadow-md);
  transition: transform var(--transition-normal);
  cursor: pointer;
}

.favorite-card:hover {
  transform: translateY(-4px);
}

.favorite-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.favorite-content {
  padding: var(--space-3);
}

.favorite-name {
  font-weight: 600;
  color: var(--color-gray-800);
  margin-bottom: var(--space-1);
}

.favorite-type {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.medals-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: var(--space-4);
}

.medal-card {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  box-shadow: var(--shadow-md);
  text-align: center;
}

.medal-icon {
  width: 64px;
  height: 64px;
  margin-bottom: var(--space-3);
}

.medal-name {
  font-weight: 600;
  color: var(--color-gray-800);
  margin-bottom: var(--space-2);
}

.medal-description {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-bottom: var(--space-2);
}

.medal-date {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: var(--radius-lg);
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: var(--space-4);
  border-bottom: 1px solid var(--color-gray-200);
}

.modal-header h2 {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--color-gray-800);
}

.close-btn {
  background: none;
  border: none;
  color: var(--color-gray-500);
  cursor: pointer;
  padding: var(--space-1);
}

.close-btn:hover {
  color: var(--color-gray-700);
}

.edit-form {
  padding: var(--space-4);
}

.form-group {
  margin-bottom: var(--space-4);
}

.form-group label {
  display: block;
  margin-bottom: var(--space-2);
  font-weight: 500;
  color: var(--color-gray-700);
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: var(--space-2);
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-md);
  outline: none;
  transition: all var(--transition-fast);
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  border-color: var(--color-primary-400);
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: var(--space-2);
  margin-top: var(--space-4);
}

@media (min-width: 768px) {
  .profile-cover {
    height: 250px;
  }

  .profile-info {
    padding: var(--space-6);
    margin-top: -80px;
  }

  .profile-avatar {
    width: 160px;
    height: 160px;
  }

  .profile-name {
    font-size: 2rem;
  }
}
</style>