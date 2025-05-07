<template>
  <div class="stall-detail-page" v-if="stall">
    <div class="container">
      <div class="breadcrumb">
        <router-link to="/">首页</router-link>
        <span class="separator">/</span>
        <router-link :to="`/campus/${campus.id}`">{{ campus.name }}</router-link>
        <span class="separator">/</span>
        <router-link :to="`/canteen/${canteen.id}`">{{ canteen.name }}</router-link>
        <span class="separator">/</span>
        <span class="current">{{ stall.name }}</span>
      </div>

      <div class="stall-header">
        <swiper
            :slides-per-view="1"
            :pagination="{ clickable: true }"
            :modules="[Pagination]"
            class="stall-swiper"
        >
          <swiper-slide v-for="(image, index) in stall.images" :key="index">
            <div class="image-container">
              <img :src="image" :alt="`${stall.name} - 图片 ${index + 1}`" class="stall-image" />
            </div>
          </swiper-slide>
        </swiper>

        <div class="stall-info">
          <div class="info-header">
            <h1 class="stall-name">{{ stall.name }}</h1>
            <div class="rating-section">
              <Rating :value="stall.score" />
              <span class="review-count">{{ stall.comments }} 条评价</span>
            </div>
          </div>

          <div class="info-grid">
            <div class="info-item">
              <span class="info-label">窗口位置</span>
              <span class="info-value">{{ stall.location }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">营业时间</span>
              <span class="info-value">{{ stall.openHours }}</span>
            </div>
          </div>

          <div class="score-details">
            <div class="score-item">
              <span class="score-label">口味</span>
              <Rating :value="stall.tasteScore" :showScore="false" size="small" />
              <span class="score-value">{{ stall.tasteScore.toFixed(1) }}</span>
            </div>
            <div class="score-item">
              <span class="score-label">性价比</span>
              <Rating :value="stall.priceScore" :showScore="false" size="small" />
              <span class="score-value">{{ stall.priceScore.toFixed(1) }}</span>
            </div>
          </div>

          <p class="introduction">{{ stall.introduction }}</p>
        </div>
      </div>

      <div class="dishes-section">
        <h2 class="section-title">菜品列表</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          <DishCard
              v-for="dish in dishes"
              :key="dish.id"
              :dish="dish"
          />
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
import DishCard from '../../components/cards/DishCard.vue'

const route = useRoute()
const dataStore = useDataStore()

const stallId = computed(() => Number(route.params.id))
const stall = computed(() => dataStore.getStallById(stallId.value))
const canteen = computed(() => stall.value ? dataStore.getCanteenById(stall.value.canteenId) : null)
const campus = computed(() => canteen.value ? dataStore.getCampusById(canteen.value.campusId) : null)
const dishes = computed(() => dataStore.getDishesByStallId(stallId.value))
const reviews = computed(() => {
  return dataStore.getReviewsByDishId(stallId.value).slice(0, 5) // Show only latest 5 reviews
})

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
.stall-detail-page {
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

.stall-header {
  margin-bottom: var(--space-8);
}

.stall-swiper {
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

.stall-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.stall-info {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  box-shadow: var(--shadow-md);
}

.info-header {
  margin-bottom: var(--space-4);
}

.stall-name {
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

.dishes-section,
.reviews-section {
  margin-bottom: var(--space-8);
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
  .stall-swiper {
    height: 400px;
  }
}
</style>