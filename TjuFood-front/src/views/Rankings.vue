<template>
  <div class="rankings-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">排行榜</h1>
        <p class="page-description">发现最受欢迎的美食与食堂</p>
      </div>

      <div class="rankings-grid">
        <div class="ranking-section">
          <h2 class="section-title">
            <span class="material-icons">restaurant</span>
            热门菜品
          </h2>
          <div class="ranking-list">
            <div
                v-for="(dish, index) in dishRankings"
                :key="dish.id"
                class="ranking-item"
                :class="{ 'top-three': index < 3 }"
            >
              <div class="ranking-number">{{ index + 1 }}</div>
              <router-link :to="`/dish/${dish.id}`" class="ranking-content">
                <img :src="dish.image" :alt="dish.name" class="ranking-image" />
                <div class="ranking-info">
                  <h3 class="ranking-name">{{ dish.name }}</h3>
                  <Rating :value="dish.score" size="small" />
                </div>
              </router-link>
            </div>
          </div>
        </div>

        <div class="ranking-section">
          <h2 class="section-title">
            <span class="material-icons">store</span>
            人气食堂
          </h2>
          <div class="ranking-list">
            <div
                v-for="(canteen, index) in canteenRankings"
                :key="canteen.id"
                class="ranking-item"
                :class="{ 'top-three': index < 3 }"
            >
              <div class="ranking-number">{{ index + 1 }}</div>
              <router-link :to="`/canteen/${canteen.id}`" class="ranking-content">
                <img :src="canteen.image" :alt="canteen.name" class="ranking-image" />
                <div class="ranking-info">
                  <h3 class="ranking-name">{{ canteen.name }}</h3>
                  <Rating :value="canteen.score" size="small" />
                </div>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useDataStore } from '../stores/data'
import Rating from '../components/common/Rating.vue'

const dataStore = useDataStore()

const dishRankings = computed(() => dataStore.getDishRankings())
const canteenRankings = computed(() => dataStore.getCanteenRankings())
</script>

<style scoped>
.rankings-page {
  padding: var(--space-4) 0 var(--space-16);
}

.page-header {
  text-align: center;
  margin-bottom: var(--space-8);
}

.page-title {
  font-size: 2rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-2);
}

.page-description {
  font-size: 1rem;
  color: var(--color-gray-600);
}

.rankings-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: var(--space-8);
}

.ranking-section {
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-6);
  box-shadow: var(--shadow-md);
}

.section-title {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-4);
}

.section-title .material-icons {
  color: var(--color-primary-600);
}

.ranking-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-3);
}

.ranking-item {
  display: flex;
  align-items: center;
  padding: var(--space-2);
  border-radius: var(--radius-lg);
  transition: all var(--transition-normal);
}

.ranking-item:hover {
  background-color: var(--color-gray-50);
  transform: translateX(4px);
}

.ranking-number {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-gray-100);
  border-radius: 50%;
  font-weight: 700;
  color: var(--color-gray-700);
  margin-right: var(--space-3);
}

.ranking-item.top-three .ranking-number {
  background-color: var(--color-primary-100);
  color: var(--color-primary-700);
}

.ranking-item:nth-child(1) .ranking-number {
  background-color: var(--color-warning-100);
  color: var(--color-warning-700);
}

.ranking-content {
  display: flex;
  align-items: center;
  flex: 1;
}

.ranking-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: var(--radius-md);
  margin-right: var(--space-3);
}

.ranking-name {
  font-weight: 600;
  color: var(--color-gray-800);
  margin-bottom: var(--space-1);
}

@media (min-width: 768px) {
  .rankings-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>