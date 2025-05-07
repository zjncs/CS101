<template>
  <div class="campus-detail-page" v-if="campus">
    <div class="container">
      <div class="breadcrumb">
        <router-link to="/">首页</router-link>
        <span class="separator">/</span>
        <span class="current">{{ campus.name }}</span>
      </div>

      <div class="campus-header">
        <div class="campus-image-container">
          <img :src="campus.image" :alt="campus.name" class="campus-image" />
          <div class="campus-overlay"></div>
          <h1 class="campus-title">{{ campus.name }}</h1>
        </div>

        <div class="campus-info">
          <div class="info-item">
            <span class="material-icons">location_on</span>
            <span>{{ campus.address }}</span>
          </div>
          <div class="campus-stats">
            <div class="stat-item">
              <span class="stat-value">{{ canteens.length }}</span>
              <span class="stat-label">食堂</span>
            </div>
            <div class="separator"></div>
            <div class="stat-item">
              <span class="stat-value">{{ getTotalStalls() }}</span>
              <span class="stat-label">窗口</span>
            </div>
            <div class="separator"></div>
            <div class="stat-item">
              <span class="stat-value">{{ getTotalDishes() }}</span>
              <span class="stat-label">菜品</span>
            </div>
          </div>
        </div>
      </div>

      <div class="canteen-section">
        <h2 class="section-title">校区食堂</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <CanteenCard
              v-for="canteen in canteens"
              :key="canteen.id"
              :canteen="canteen"
          />
        </div>
      </div>

      <div class="blog-section" v-if="relatedBlogs.length > 0">
        <h2 class="section-title">相关美食博客</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <BlogCard
              v-for="blog in relatedBlogs"
              :key="blog.id"
              :blog="blog"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useDataStore } from '../../stores/data'
import CanteenCard from '../../components/cards/CanteenCard.vue'
import BlogCard from '../../components/cards/BlogCard.vue'

const route = useRoute()
const dataStore = useDataStore()

const campusId = computed(() => Number(route.params.id))
const campus = computed(() => dataStore.getCampusById(campusId.value))
const canteens = computed(() => dataStore.getCanteensByCampusId(campusId.value))
const relatedBlogs = computed(() => {
  return dataStore.getAllBlogs().filter(blog => {
    const blogCanteen = dataStore.getCanteenById(blog.canteenId)
    return blogCanteen && blogCanteen.campusId === campusId.value
  })
})

function getTotalStalls() {
  let total = 0
  canteens.value.forEach(canteen => {
    total += dataStore.getStallsByCanteenId(canteen.id).length
  })
  return total
}

function getTotalDishes() {
  let total = 0
  canteens.value.forEach(canteen => {
    const stalls = dataStore.getStallsByCanteenId(canteen.id)
    stalls.forEach(stall => {
      total += dataStore.getDishesByStallId(stall.id).length
    })
  })
  return total
}

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
.campus-detail-page {
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

.campus-header {
  margin-bottom: var(--space-8);
}

.campus-image-container {
  position: relative;
  width: 100%;
  height: 300px;
  border-radius: var(--radius-lg);
  overflow: hidden;
  margin-bottom: var(--space-4);
}

.campus-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.campus-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, rgba(0,0,0,0.1), rgba(0,0,0,0.7));
}

.campus-title {
  position: absolute;
  bottom: var(--space-6);
  left: var(--space-6);
  color: white;
  font-size: 2rem;
  font-weight: 700;
  z-index: 1;
}

.campus-info {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-4);
  box-shadow: var(--shadow-md);
}

.info-item {
  display: flex;
  align-items: center;
  color: var(--color-gray-600);
  margin-bottom: var(--space-4);
}

.info-item .material-icons {
  font-size: 20px;
  margin-right: var(--space-2);
  color: var(--color-gray-500);
}

.campus-stats {
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-primary-600);
}

.stat-label {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-top: 2px;
}

.separator {
  width: 1px;
  height: 30px;
  background-color: var(--color-gray-300);
}

.section-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-4);
}

.canteen-section,
.blog-section {
  margin-bottom: var(--space-8);
}

@media (min-width: 768px) {
  .campus-image-container {
    height: 400px;
  }

  .campus-title {
    font-size: 2.5rem;
  }
}
</style>