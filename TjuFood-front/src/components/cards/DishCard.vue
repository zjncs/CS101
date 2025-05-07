<template>
  <router-link :to="`/dish/${dish.id}`" class="dish-card card">
    <div class="dish-image-container">
      <img :src="dish.images[0]" :alt="dish.name" class="dish-image" />
      <div class="dish-badge" v-if="dish.isSpecial">招牌菜</div>
    </div>
    <div class="dish-content">
      <h3 class="dish-name">{{ dish.name }}</h3>
      <p class="dish-description">{{ truncateDescription(dish.description) }}</p>
      <div class="dish-footer">
        <Rating :value="dish.score" size="small" />
        <div class="dish-price">¥{{ dish.price.toFixed(2) }}</div>
      </div>
    </div>
  </router-link>
</template>

<script setup>
import Rating from '../common/Rating.vue'

const props = defineProps({
  dish: {
    type: Object,
    required: true
  }
})

function truncateDescription(text) {
  if (text.length > 50) {
    return text.substring(0, 50) + '...'
  }
  return text
}
</script>

<style scoped>
.dish-card {
  display: flex;
  flex-direction: column;
  height: 100%;
  transition: transform var(--transition-normal), box-shadow var(--transition-normal);
}

.dish-image-container {
  position: relative;
  width: 100%;
  height: 150px;
  overflow: hidden;
  border-top-left-radius: var(--radius-lg);
  border-top-right-radius: var(--radius-lg);
}

.dish-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}

.dish-card:hover .dish-image {
  transform: scale(1.05);
}

.dish-badge {
  position: absolute;
  top: var(--space-2);
  right: var(--space-2);
  background-color: var(--color-accent-500);
  color: white;
  font-size: 0.75rem;
  font-weight: 500;
  padding: 2px 6px;
  border-radius: var(--radius-sm);
}

.dish-content {
  padding: var(--space-3);
  display: flex;
  flex-direction: column;
  flex: 1;
}

.dish-name {
  font-weight: 600;
  margin-bottom: var(--space-1);
  color: var(--color-gray-800);
}

.dish-description {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-bottom: var(--space-3);
  flex: 1;
}

.dish-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dish-price {
  font-weight: 700;
  color: var(--color-accent-600);
}
</style>