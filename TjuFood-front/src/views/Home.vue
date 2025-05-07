<template>
  <div class="home-page">
    <div class="container">
      <Banner />

      <section class="section">
        <div class="section-header">
          <h2 class="section-title">人气排行</h2>
          <router-link to="/rankings" class="section-link">查看全部</router-link>
        </div>

        <div class="rankings">
          <div
              v-for="(dish, index) in dishRankings"
              :key="dish.id"
              class="ranking-item"
              :class="{'highlight': index === 0}"
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
      </section>

      <section class="section">
        <div class="section-header">
          <h2 class="section-title">校区美食</h2>
          <router-link to="/campus" class="section-link">查看全部</router-link>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2">
          <div
              v-for="campus in campuses"
              :key="campus.id"
              class="campus-card"
          >
            <router-link :to="`/campus/${campus.id}`" class="campus-link">
              <div class="campus-image-container">
                <img :src="campus.image" :alt="campus.name" class="campus-image" />
                <div class="campus-overlay"></div>
                <h3 class="campus-name">{{ campus.name }}</h3>
              </div>
            </router-link>
          </div>
        </div>
      </section>

      <section class="section">
        <div class="section-header">
          <h2 class="section-title">最新博客</h2>
          <router-link to="/blogs" class="section-link">查看全部</router-link>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <BlogCard v-for="blog in blogs" :key="blog.id" :blog="blog" />
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import Banner from '../components/common/Banner.vue'
import Rating from '../components/common/Rating.vue'
import BlogCard from '../components/cards/BlogCard.vue'
import { useDataStore } from '../stores/data'

const dataStore = useDataStore()
const dishRankings = dataStore.getDishRankings()
const campuses = dataStore.getCampuses()
const blogs = dataStore.getAllBlogs()
</script>

<style scoped>
.home-page {
  padding: var(--space-4) 0 var(--space-16);
}

.section {
  margin-bottom: var(--space-8);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--space-4);
}

.section-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
}

.section-link {
  font-size: 0.875rem;
  color: var(--color-primary-600);
  font-weight: 500;
  transition: color var(--transition-fast);
}

.section-link:hover {
  color: var(--color-primary-800);
  text-decoration: underline;
}

.rankings {
  display: flex;
  flex-direction: column;
  gap: var(--space-3);
}

.ranking-item {
  display: flex;
  align-items: center;
  background-color: white;
  border-radius: var(--radius-lg);
  padding: var(--space-2);
  box-shadow: var(--shadow-sm);
  transition: transform var(--transition-normal), box-shadow var(--transition-normal);
}

.ranking-item:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.ranking-item.highlight {
  border-left: 4px solid var(--color-accent-500);
}

.ranking-number {
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: var(--color-gray-100);
  border-radius: 50%;
  font-weight: 700;
  color: var(--color-gray-700);
  margin-right: var(--space-3);
}

.highlight .ranking-number {
  background-color: var(--color-accent-500);
  color: white;
}

.ranking-content {
  display: flex;
  align-items: center;
  flex: 1;
}

.ranking-image {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: var(--radius-md);
  margin-right: var(--space-3);
}

.ranking-name {
  font-weight: 600;
  margin-bottom: var(--space-1);
  color: var(--color-gray-800);
}

.campus-card {
  margin-bottom: var(--space-4);
}

.campus-link {
  display: block;
}

.campus-image-container {
  position: relative;
  height: 150px;
  border-radius: var(--radius-lg);
  overflow: hidden;
}

.campus-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}

.campus-link:hover .campus-image {
  transform: scale(1.05);
}

.campus-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, rgba(0,0,0,0.1), rgba(0,0,0,0.6));
}

.campus-name {
  position: absolute;
  bottom: var(--space-4);
  left: var(--space-4);
  color: white;
  font-weight: 700;
  font-size: 1.25rem;
  z-index: 1;
}

@media (min-width: 768px) {
  .campus-image-container {
    height: 200px;
  }
}
</style>