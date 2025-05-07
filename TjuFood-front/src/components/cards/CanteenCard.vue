<template>
  <router-link :to="`/canteen/${canteen.id}`" class="canteen-card card">
    <div class="canteen-image-container">
      <img :src="canteen.images[0]" :alt="canteen.name" class="canteen-image" />
    </div>
    <div class="canteen-content">
      <h3 class="canteen-name">{{ canteen.name }}</h3>
      <div class="canteen-info">
        <span class="canteen-price">¥{{ canteen.avgPrice }} / 人均</span>
        <Rating :value="canteen.score" size="small" />
      </div>
      <p class="canteen-description">{{ truncateDescription(canteen.introduction) }}</p>
      <div class="canteen-footer">
        <div class="canteen-address">
          <span class="material-icons icon-sm">location_on</span>
          <span>{{ canteen.address }}</span>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script setup>
import Rating from '../common/Rating.vue'

const props = defineProps({
  canteen: {
    type: Object,
    required: true
  }
})

function truncateDescription(text) {
  if (text.length > 60) {
    return text.substring(0, 60) + '...'
  }
  return text
}
</script>

<style scoped>
.canteen-card {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.canteen-image-container {
  width: 100%;
  height: 180px;
  overflow: hidden;
  border-top-left-radius: var(--radius-lg);
  border-top-right-radius: var(--radius-lg);
}

.canteen-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}

.canteen-card:hover .canteen-image {
  transform: scale(1.05);
}

.canteen-content {
  padding: var(--space-3);
  display: flex;
  flex-direction: column;
  flex: 1;
}

.canteen-name {
  font-weight: 600;
  margin-bottom: var(--space-2);
  color: var(--color-gray-800);
}

.canteen-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--space-2);
}

.canteen-price {
  font-weight: 500;
  color: var(--color-accent-600);
}

.canteen-description {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-bottom: var(--space-3);
  flex: 1;
}

.canteen-footer {
  display: flex;
  align-items: center;
}

.canteen-address {
  display: flex;
  align-items: center;
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.icon-sm {
  font-size: 16px;
  margin-right: 2px;
}
</style>