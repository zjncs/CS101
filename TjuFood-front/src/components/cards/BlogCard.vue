<template>
  <router-link :to="`/blog/${blog.id}`" class="blog-card card">
    <div class="blog-image-container">
      <img :src="blog.images[0]" :alt="blog.title" class="blog-image" />
    </div>
    <div class="blog-content">
      <h3 class="blog-title">{{ blog.title }}</h3>
      <p class="blog-excerpt">{{ truncateContent(blog.content) }}</p>
      <div class="blog-footer">
        <div class="blog-stats">
          <span class="blog-stat">
            <span class="material-icons">thumb_up</span>
            {{ blog.liked }}
          </span>
          <span class="blog-stat">
            <span class="material-icons">comment</span>
            {{ blog.comments }}
          </span>
        </div>
        <div class="blog-date">{{ blog.createTime }}</div>
      </div>
    </div>
  </router-link>
</template>

<script setup>
const props = defineProps({
  blog: {
    type: Object,
    required: true
  }
})

function truncateContent(text) {
  if (text.length > 80) {
    return text.substring(0, 80) + '...'
  }
  return text
}
</script>

<style scoped>
.blog-card {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.blog-image-container {
  width: 100%;
  height: 180px;
  overflow: hidden;
  border-top-left-radius: var(--radius-lg);
  border-top-right-radius: var(--radius-lg);
}

.blog-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}

.blog-card:hover .blog-image {
  transform: scale(1.05);
}

.blog-content {
  padding: var(--space-3);
  display: flex;
  flex-direction: column;
  flex: 1;
}

.blog-title {
  font-weight: 600;
  margin-bottom: var(--space-2);
  color: var(--color-gray-800);
}

.blog-excerpt {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-bottom: var(--space-3);
  flex: 1;
}

.blog-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.blog-stats {
  display: flex;
  gap: var(--space-3);
}

.blog-stat {
  display: flex;
  align-items: center;
  font-size: 0.75rem;
  color: var(--color-gray-500);
}

.blog-stat .material-icons {
  font-size: 16px;
  margin-right: 2px;
}

.blog-date {
  font-size: 0.75rem;
  color: var(--color-gray-500);
}
</style>