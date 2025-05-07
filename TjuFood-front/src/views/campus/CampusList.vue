<template>
  <div class="campus-list-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">校区导览</h1>
        <p class="page-description">探索天津大学各校区的美食文化</p>
      </div>

      <div class="campus-grid">
        <div
            v-for="campus in campuses"
            :key="campus.id"
            class="campus-item"
        >
          <router-link :to="`/campus/${campus.id}`" class="campus-card">
            <div class="campus-image-container">
              <img :src="campus.image" :alt="campus.name" class="campus-image" />
              <div class="campus-overlay"></div>
            </div>
            <div class="campus-content">
              <h2 class="campus-name">{{ campus.name }}</h2>
              <div class="campus-address">
                <span class="material-icons">location_on</span>
                <span>{{ campus.address }}</span>
              </div>
              <div class="campus-stats">
                <div class="campus-stat">
                  <span class="stat-value">{{ getCanteenCount(campus.id) }}</span>
                  <span class="stat-label">食堂</span>
                </div>
                <div class="separator"></div>
                <div class="campus-stat">
                  <span class="stat-value">{{ getStallCount(campus.id) }}</span>
                  <span class="stat-label">窗口</span>
                </div>
                <div class="separator"></div>
                <div class="campus-stat">
                  <span class="stat-value">{{ getDishCount(campus.id) }}</span>
                  <span class="stat-label">菜品</span>
                </div>
              </div>
            </div>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useDataStore } from '../../stores/data'

const dataStore = useDataStore()
const campuses = dataStore.getCampuses()

function getCanteenCount(campusId) {
  return dataStore.getCanteensByCampusId(campusId).length
}

function getStallCount(campusId) {
  const canteens = dataStore.getCanteensByCampusId(campusId)
  let stallCount = 0

  canteens.forEach(canteen => {
    stallCount += dataStore.getStallsByCanteenId(canteen.id).length
  })

  return stallCount
}

function getDishCount(campusId) {
  const canteens = dataStore.getCanteensByCampusId(campusId)
  let dishCount = 0

  canteens.forEach(canteen => {
    const stalls = dataStore.getStallsByCanteenId(canteen.id)
    stalls.forEach(stall => {
      dishCount += dataStore.getDishesByStallId(stall.id).length
    })
  })

  return dishCount
}
</script>

<style scoped>
.campus-list-page {
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

.campus-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: var(--space-6);
}

.campus-item {
  transform-origin: center;
  transition: transform var(--transition-normal);
}

.campus-item:hover {
  transform: translateY(-4px);
}

.campus-card {
  display: block;
  background-color: white;
  border-radius: var(--radius-lg);
  overflow: hidden;
  box-shadow: var(--shadow-md);
}

.campus-image-container {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.campus-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}

.campus-card:hover .campus-image {
  transform: scale(1.05);
}

.campus-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, rgba(0,0,0,0), rgba(0,0,0,0.3));
}

.campus-content {
  padding: var(--space-4);
}

.campus-name {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: var(--space-2);
  color: var(--color-gray-800);
}

.campus-address {
  display: flex;
  align-items: center;
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-bottom: var(--space-4);
}

.campus-address .material-icons {
  font-size: 18px;
  margin-right: var(--space-1);
  color: var(--color-gray-500);
}

.campus-stats {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.campus-stat {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 1;
}

.stat-value {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--color-primary-600);
}

.stat-label {
  font-size: 0.75rem;
  color: var(--color-gray-600);
  margin-top: 2px;
}

.separator {
  width: 1px;
  height: 20px;
  background-color: var(--color-gray-300);
}

@media (min-width: 768px) {
  .campus-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>