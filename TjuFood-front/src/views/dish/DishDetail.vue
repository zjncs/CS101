<template>
  <div class="dish-detail-page" v-if="dish">
    <div class="container">
      <div class="breadcrumb">
        <router-link to="/">首页</router-link>
        <span class="separator">/</span>
        <router-link :to="`/campus/${campus.id}`">{{ campus.name }}</router-link>
        <span class="separator">/</span>
        <router-link :to="`/canteen/${canteen.id}`">{{ canteen.name }}</router-link>
        <span class="separator">/</span>
        <router-link :to="`/stall/${stall.id}`">{{ stall.name }}</router-link>
        <span class="separator">/</span>
        <span class="current">{{ dish.name }}</span>
      </div>

      <div class="dish-detail">
        <div class="dish-gallery">
          <div class="main-image-container">
            <img :src="dish.images[0]" :alt="dish.name" class="main-image" />
          </div>
          <div class="image-thumbnails" v-if="dish.images.length > 1">
            <div
                v-for="(image, index) in dish.images"
                :key="index"
                class="thumbnail-container"
                :class="{ active: currentImageIndex === index }"
                @click="currentImageIndex = index"
            >
              <img :src="image" :alt="`${dish.name} - 图片 ${index + 1}`" class="thumbnail-image" />
            </div>
          </div>
        </div>

        <div class="dish-info">
          <div class="dish-header">
            <h1 class="dish-name">{{ dish.name }}</h1>
            <div class="dish-badge" v-if="dish.isSpecial">招牌菜</div>
          </div>

          <div class="dish-rating">
            <Rating :value="dish.score" />
            <span class="review-count">{{ dish.comments }} 条评价</span>
          </div>

          <div class="dish-price">¥{{ dish.price.toFixed(2) }}</div>

          <div class="dish-description">
            <h3 class="section-title">菜品介绍</h3>
            <p>{{ dish.description }}</p>
          </div>

          <div class="dish-meta">
            <div class="meta-item">
              <span class="meta-label">菜系</span>
              <span class="meta-value">{{ dish.category }}</span>
            </div>
            <div class="meta-item">
              <span class="meta-label">窗口</span>
              <router-link :to="`/stall/${stall.id}`" class="meta-value link">{{ stall.name }}</router-link>
            </div>
            <div class="meta-item">
              <span class="meta-label">食堂</span>
              <router-link :to="`/canteen/${canteen.id}`" class="meta-value link">{{ canteen.name }}</router-link>
            </div>
          </div>

          <div class="action-buttons">
            <button class="btn btn-primary">写评价</button>
            <button class="btn btn-outline">
              <span class="material-icons">favorite_border</span>
              收藏
            </button>
          </div>
        </div>
      </div>

      <div class="dish-reviews">
        <h2 class="section-heading">用户评价</h2>

        <div class="reviews-container">
          <div v-if="reviews.length === 0" class="no-reviews">
            暂无评价，来写第一条评价吧！
          </div>

          <div v-else class="review-list">
            <div v-for="review in reviews" :key="review.id" class="review-item">
              <div class="review-header">
                <div class="user-info">
                  <img :src="review.userAvatar" :alt="review.userNickname" class="user-avatar" />
                  <div class="user-details">
                    <div class="user-name">{{ review.userNickname }}</div>
                    <div class="review-date">{{ review.createTime }}</div>
                  </div>
                </div>
                <Rating :value="review.overallScore" size="small" />
              </div>

              <div class="review-content">
                <p>{{ review.content }}</p>

                <div v-if="review.images && review.images.length > 0" class="review-images">
                  <div class="img-item" v-for="(img, index) in review.images" :key="index">
                    <img :src="img" :alt="'评价图片'" class="review-img" />
                  </div>
                </div>
              </div>

              <div class="review-footer">
                <button class="action-btn">
                  <span class="material-icons">thumb_up</span>
                  <span>{{ review.liked }}</span>
                </button>
                <button class="action-btn">
                  <span class="material-icons">comment</span>
                  <span>评论</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useDataStore } from '../../stores/data'
import Rating from '../../components/common/Rating.vue'

const route = useRoute()
const dataStore = useDataStore()

const currentImageIndex = ref(0)
const dishId = computed(() => Number(route.params.id))

const dish = computed(() => dataStore.getDishById(dishId.value))
const stall = computed(() => {
  if (dish.value) return dataStore.getStallById(dish.value.stallId)
  return null
})
const canteen = computed(() => {
  if (stall.value) return dataStore.getCanteenById(stall.value.canteenId)
  return null
})
const campus = computed(() => {
  if (canteen.value) return dataStore.getCampusById(canteen.value.campusId)
  return null
})

const reviews = computed(() => {
  if (dish.value) return dataStore.getReviewsByDishId(dishId.value)
  return []
})

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
.dish-detail-page {
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

.dish-detail {
  display: flex;
  flex-direction: column;
  margin-bottom: var(--space-8);
}

.dish-gallery {
  margin-bottom: var(--space-6);
}

.main-image-container {
  width: 100%;
  height: 300px;
  border-radius: var(--radius-lg);
  overflow: hidden;
  margin-bottom: var(--space-2);
}

.main-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-thumbnails {
  display: flex;
  gap: var(--space-2);
  overflow-x: auto;
  padding-bottom: var(--space-2);
}

.thumbnail-container {
  width: 80px;
  height: 60px;
  border-radius: var(--radius-md);
  overflow: hidden;
  cursor: pointer;
  border: 2px solid transparent;
  transition: border-color var(--transition-fast);
  flex-shrink: 0;
}

.thumbnail-container.active {
  border-color: var(--color-primary-500);
}

.thumbnail-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.dish-info {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  box-shadow: var(--shadow-md);
}

.dish-header {
  display: flex;
  align-items: center;
  margin-bottom: var(--space-4);
}

.dish-name {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-right: var(--space-2);
}

.dish-badge {
  background-color: var(--color-accent-500);
  color: white;
  font-size: 0.75rem;
  font-weight: 500;
  padding: 2px 8px;
  border-radius: var(--radius-sm);
}

.dish-rating {
  display: flex;
  align-items: center;
  margin-bottom: var(--space-4);
}

.review-count {
  margin-left: var(--space-3);
  font-size: 0.875rem;
  color: var(--color-gray-600);
}

.dish-price {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-accent-600);
  margin-bottom: var(--space-4);
}

.dish-description {
  margin-bottom: var(--space-4);
}

.section-title {
  font-size: 1rem;
  font-weight: 600;
  color: var(--color-gray-800);
  margin-bottom: var(--space-2);
}

.dish-description p {
  color: var(--color-gray-700);
  line-height: 1.6;
}

.dish-meta {
  display: flex;
  flex-direction: column;
  gap: var(--space-2);
  margin-bottom: var(--space-4);
}

.meta-item {
  display: flex;
}

.meta-label {
  width: 60px;
  color: var(--color-gray-600);
}

.meta-value {
  font-weight: 500;
  color: var(--color-gray-800);
}

.meta-value.link {
  color: var(--color-primary-600);
}

.meta-value.link:hover {
  text-decoration: underline;
}

.action-buttons {
  display: flex;
  gap: var(--space-3);
}

.action-buttons .btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: var(--space-1);
}

.section-heading {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-4);
}

.no-reviews {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-6);
  text-align: center;
  color: var(--color-gray-500);
  box-shadow: var(--shadow-md);
}

.review-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-4);
}

.review-item {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  box-shadow: var(--shadow-md);
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--space-3);
}

.user-info {
  display: flex;
  align-items: center;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: var(--space-2);
}

.user-name {
  font-weight: 500;
  color: var(--color-gray-800);
}

.review-date {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.review-content {
  margin-bottom: var(--space-3);
}

.review-content p {
  color: var(--color-gray-800);
  line-height: 1.6;
  margin-bottom: var(--space-3);
}

.review-images {
  display: flex;
  gap: var(--space-2);
  overflow-x: auto;
  padding-bottom: var(--space-2);
}

.img-item {
  width: 120px;
  height: 90px;
  border-radius: var(--radius-md);
  overflow: hidden;
  flex-shrink: 0;
}

.review-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.review-footer {
  display: flex;
  gap: var(--space-4);
}

.action-btn {
  display: flex;
  align-items: center;
  gap: var(--space-1);
  background: none;
  border: none;
  color: var(--color-gray-600);
  font-size: 0.875rem;
  cursor: pointer;
  padding: var(--space-1) var(--space-2);
  border-radius: var(--radius-md);
  transition: all var(--transition-fast);
}

.action-btn:hover {
  background-color: var(--color-gray-100);
  color: var(--color-primary-600);
}

.action-btn .material-icons {
  font-size: 18px;
}

@media (min-width: 768px) {
  .dish-detail {
    flex-direction: row;
    gap: var(--space-6);
  }

  .dish-gallery {
    width: 50%;
    margin-bottom: 0;
  }

  .dish-info {
    width: 50%;
  }

  .main-image-container {
    height: 400px;
  }
}
</style>