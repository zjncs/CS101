<template>
  <div class="rating" :class="{ 'rating-sm': size === 'small' }">
    <div class="stars">
      <span
          v-for="i in 5"
          :key="i"
          class="star"
          :class="{
          'filled': value >= i,
          'half-filled': value > i - 1 && value < i
        }"
      >
        <span class="material-icons">
          {{ value >= i ? 'star' : (value > i - 1 && value < i ? 'star_half' : 'star_outline') }}
        </span>
      </span>
    </div>
    <span class="score" v-if="showScore">{{ value.toFixed(1) }}</span>
  </div>
</template>

<script setup>
defineProps({
  value: {
    type: Number,
    default: 0
  },
  size: {
    type: String,
    default: 'normal'
  },
  showScore: {
    type: Boolean,
    default: true
  }
})
</script>

<style scoped>
.rating {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.stars {
  display: flex;
  align-items: center;
}

.star {
  color: var(--color-warning-500);
  margin-right: -2px;
}

.star .material-icons {
  font-size: 24px;
}

.score {
  font-weight: 500;
  color: var(--color-gray-700);
}

.rating-sm .star .material-icons {
  font-size: 18px;
}

.rating-sm .score {
  font-size: 0.875rem;
}
</style>