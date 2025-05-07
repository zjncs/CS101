<template>
  <div class="canteen-detail-page" v-if="canteen">
    <div class="container">
      <div class="breadcrumb">
        <router-link to="/">首页</router-link>
        <span class="separator">/</span>
        <router-link :to="`/campus/${campus.id}`">{{ campus.name }}</router-link>
        <span class="separator">/</span>
        <span class="current">{{ canteen.name }}</span>
      </div>

      <div class="canteen-header">
        <swiper
            :slides-per-view="1"
            :pagination="{ clickable: true }"
            :modules="[Pagination]"
            class="canteen-swiper"
        >
          <swiper-slide v-for="(image, index) in canteen.images" :key="index">
            <div class="image-container">
              <img :src="image" :alt="`${canteen.name} - 图片 ${index + 1}`" class="canteen-image" />
            </div>
          </swiper-slide>
        </swiper>

        <div class="canteen-info">
          <div class="info-header">
            <h1 class="canteen-name">{{ canteen.name }}</h1>
            <div class="rating-section">
              <Rating :value="canteen.score" />
              <span class="review-count">{{ canteen.comments }} 条评价</span>
            </div>
          </div>

          <div class="info-grid">
            <div class="info-item">
              <span class="info-label">人均消费</span>
              <span class="info-value price">¥{{ canteen.avgPrice }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">营业时间</span>
              <span class="info-value">{{ canteen.openHours }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">具体位置</span>
              <span class="info-value">{{ canteen.address }}</span>
            </div>
          </div>

          <div class="score-details">
            <div class="score-item">
              <span class="score-label">口味</span>
              <Rating :value="canteen.tasteScore" :showScore="false" size="small" />
              <span class="score-value">{{ canteen.tasteScore.toFixed(1) }}</span>
            </div>
            <div class="score-item">
              <span class="score-label">环境</span>
              <Rating :value="canteen.environmentScore" :showScore="false" size="small" />
              <span class="score-value">{{ canteen.environmentScore.toFixed(1) }}</span>
            </div>
            <div class="score-item">
              <span class="score-label">服务</span>
              <Rating :value="canteen.serviceScore" :showScore="false" size="small" />
              <span class="score-value">{{ canteen.serviceScore.toFixed(1) }}</span>
            </div>
          </div>

          <p class="introduction">{{ canteen.introduction }}</p>
        </div>
      </div>

      <div class="stalls-section">
        <h2 class="section-title">窗口列表</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <div
              v-for="stall in stalls"
              :key="stall.id"
              class="stall-card card"
              @click="$router.push(`/stall/${stall.id}`)"
          >
            <div class="stall-image-container">
              <img :src="stall.images[0]" :alt="stall.name" class="stall-image" />
            </div>
            <div class="stall-content">
              <h3 class="stall-name">{{ stall.name }}</h3>
              <div class="stall-rating">
                <Rating :value="stall.score" size="small" />
                <span class="comment-count">{{ stall.comments }}评价</span>
              </div>
              <p class="stall-intro">{{ stall.introduction }}</p>
              <div class="stall-footer">
                <span class="location">
                  <span class="material-icons">storefront</span>
                  {{ stall.location }}
                </span>
                <span class="hours">{{ stall.openHours }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="reviews-section" v-if="reviews.length > 0">
        <h2 class="section-title">最新评价</h2>
        <div class="reviews-container">
          <div v-for="review in reviews" :key="review.id" class="review-card">
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
                <div
                    v-for="(img, index) in review.images"
                    :key="index"
                    class="review-image-item"
                >
                  <img :src="img" alt="评价图片" class="review-image" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useDataStore } from '../../stores/data'
import { Swiper, SwiperSlide } from 'swiper/vue'
import { Pagination } from 'swiper/modules'
import 'swiper/css'
import 'swiper/css/pagination'
import Rating from '../../components/common/Rating.vue'

const route = useRoute()
const dataStore = useDataStore()

const canteenId = computed(() => Number(route.params.id))
const canteen = computed(() => dataStore.getCanteenById(canteenId.value))
const campus = computed(() => canteen.value ? dataStore.getCampusById(canteen.value.campusId) : null)
const stalls = computed(() => dataStore.getStallsByCanteenId(canteenId.value))
const reviews = computed(() => {
  return dataStore.getReviewsByDishId(canteenId.value).slice(0, 5) // Show only latest 5 reviews
})

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
.canteen-detail-page {
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

.canteen-header {
  margin-bottom: var(--space-8);
}

.canteen-swiper {
  width: 100%;
  height: 300px;
  border-radius: var(--radius-lg);
  overflow: hidden;
  margin-bottom: var(--space-4);
}

.image-container {
  width: 100%;
  height: 100%;
}

.canteen-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.canteen-info {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  box-shadow: var(--shadow-md);
}

.info-header {
  margin-bottom: var(--space-4);
}

.canteen-name {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-2);
}

.rating-section {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.review-count {
  font-size: 0.875rem;
  color: var(--color-gray-600);
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: var(--space-4);
  margin-bottom: var(--space-4);
}

.info-item {
  display: flex;
  flex-direction: column;
}

.info-label {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-bottom: var(--space-1);
}

.info-value {
  font-weight: 500;
  color: var(--color-gray-800);
}

.info-value.price {
  color: var(--color-accent-600);
  font-weight: 700;
}

.score-details {
  display: flex;
  gap: var(--space-6);
  margin-bottom: var(--space-4);
  padding: var(--space-4);
  background-color: var(--color-gray-50);
  border-radius: var(--radius-md);
}

.score-item {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.score-label {
  font-size: 0.875rem;
  color: var(--color-gray-600);
}

.score-value {
  font-weight: 500;
  color: var(--color-warning-500);
}

.introduction {
  color: var(--color-gray-700);
  line-height: 1.6;
}

.section-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-4);
}

.stall-card {
  cursor: pointer;
  transition: transform var(--transition-normal), box-shadow var(--transition-normal);
}

.stall-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
}

.stall-image-container {
  width: 100%;
  height: 180px;
  overflow: hidden;
  border-top-left-radius: var(--radius-lg);
  border-top-right-radius: var(--radius-lg);
}

.stall-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}

.stall-card:hover .stall-image {
  transform: scale(1.05);
}

.stall-content {
  padding: var(--space-3);
}

.stall-name {
  font-weight: 600;
  margin-bottom: var(--space-2);
  color: var(--color-gray-800);
}

.stall-rating {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  margin-bottom: var(--space-2);
}

.comment-count {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.stall-intro {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-bottom: var(--space-3);
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.stall-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.location {
  display: flex;
  align-items: center;
  gap: 2px;
}

.location .material-icons {
  font-size: 16px;
}

.reviews-section {
  margin-top: var(--space-8);
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

.user-info {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.user-name {
  font-weight: 500;
  color: var(--color-gray-800);
}

.review-date {
  font-size: 0.75rem;
  color: var(--color-gray-500);
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

@media (min-width: 768px) {
  .canteen-swiper {
    height: 400px;
  }

  .info-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>