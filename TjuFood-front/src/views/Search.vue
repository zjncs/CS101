<template>
  <div class="search-page">
    <div class="container">
      <div class="search-header">
        <div class="search-bar">
          <input
              type="text"
              v-model="searchQuery"
              placeholder="搜索菜品、食堂、窗口、博客..."
              @input="handleSearch"
              ref="searchInput"
          />
          <span class="material-icons search-icon">search</span>
        </div>

        <div class="search-filters">
          <button
              v-for="filter in filters"
              :key="filter.type"
              :class="['filter-btn', { active: activeFilter === filter.type }]"
              @click="activeFilter = filter.type"
          >
            {{ filter.label }}
            <span class="count" v-if="getResultCount(filter.type)">
              {{ getResultCount(filter.type) }}
            </span>
          </button>
        </div>
      </div>

      <div class="search-results" v-if="searchQuery">
        <!-- 菜品结果 -->
        <div class="result-section" v-if="showSection('dishes')">
          <h2 class="section-title">菜品</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            <DishCard
                v-for="dish in searchResults.dishes"
                :key="dish.id"
                :dish="dish"
            />
          </div>
        </div>

        <!-- 食堂结果 -->
        <div class="result-section" v-if="showSection('canteens')">
          <h2 class="section-title">食堂</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <CanteenCard
                v-for="canteen in searchResults.canteens"
                :key="canteen.id"
                :canteen="canteen"
            />
          </div>
        </div>

        <!-- 窗口结果 -->
        <div class="result-section" v-if="showSection('stalls')">
          <h2 class="section-title">窗口</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div
                v-for="stall in searchResults.stalls"
                :key="stall.id"
                class="stall-card card"
                @click="$router.push(`/stall/${stall.id}`)"
            >
              <div class="stall-image-container">
                <img :src="stall.images[0]" :alt="stall.name" class="stall-image" />
              </div>
              <div class="stall-content">
                <h3 class="stall-name">{{ stall.name }}</h3>
                <Rating :value="stall.score" size="small" />
                <p class="stall-intro">{{ stall.introduction }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 博客结果 -->
        <div class="result-section" v-if="showSection('blogs')">
          <h2 class="section-title">博客</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <BlogCard
                v-for="blog in searchResults.blogs"
                :key="blog.id"
                :blog="blog"
            />
          </div>
        </div>

        <!-- 无结果提示 -->
        <div class="no-results" v-if="noResults">
          <span class="material-icons">search_off</span>
          <p>未找到相关内容</p>
        </div>
      </div>

      <!-- 搜索建议 -->
      <div class="search-suggestions" v-else>
        <h2 class="suggestions-title">热门搜索</h2>
        <div class="suggestions-grid">
          <button
              v-for="suggestion in suggestions"
              :key="suggestion"
              class="suggestion-btn"
              @click="searchQuery = suggestion"
          >
            {{ suggestion }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useDataStore } from '../stores/data'
import DishCard from '../components/cards/DishCard.vue'
import CanteenCard from '../components/cards/CanteenCard.vue'
import BlogCard from '../components/cards/BlogCard.vue'
import Rating from '../components/common/Rating.vue'

const dataStore = useDataStore()
const searchQuery = ref('')
const activeFilter = ref('all')
const searchInput = ref(null)

const filters = [
  { type: 'all', label: '全部' },
  { type: 'dishes', label: '菜品' },
  { type: 'canteens', label: '食堂' },
  { type: 'stalls', label: '窗口' },
  { type: 'blogs', label: '博客' }
]

const suggestions = [
  '鱼香肉丝',
  '第一食堂',
  '川湘居',
  '麻辣香锅',
  '兰州拉面',
  '早餐',
  '特色菜'
]

const searchResults = computed(() => {
  if (!searchQuery.value.trim()) return { dishes: [], canteens: [], stalls: [], blogs: [] }
  return dataStore.searchItems(searchQuery.value.trim())
})

const noResults = computed(() => {
  return (
      searchResults.value.dishes.length === 0 &&
      searchResults.value.canteens.length === 0 &&
      searchResults.value.stalls.length === 0 &&
      searchResults.value.blogs.length === 0
  )
})

function getResultCount(type) {
  if (type === 'all') {
    return (
        searchResults.value.dishes.length +
        searchResults.value.canteens.length +
        searchResults.value.stalls.length +
        searchResults.value.blogs.length
    )
  }
  return searchResults.value[type].length
}

function showSection(type) {
  return (
      (activeFilter.value === 'all' || activeFilter.value === type) &&
      searchResults.value[type].length > 0
  )
}

function handleSearch() {
  // Debounce implementation could be added here
}

onMounted(() => {
  searchInput.value?.focus()
})
</script>

<style scoped>
.search-page {
  padding: var(--space-4) 0 var(--space-16);
}

.search-header {
  margin-bottom: var(--space-8);
}

.search-bar {
  position: relative;
  margin-bottom: var(--space-4);
}

.search-bar input {
  width: 100%;
  padding: var(--space-4) var(--space-4) var(--space-4) var(--space-12);
  border: 2px solid var(--color-gray-300);
  border-radius: var(--radius-full);
  font-size: 1.125rem;
  outline: none;
  transition: all var(--transition-fast);
}

.search-bar input:focus {
  border-color: var(--color-primary-400);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}

.search-icon {
  position: absolute;
  left: var(--space-4);
  top: 50%;
  transform: translateY(-50%);
  color: var(--color-gray-400);
  font-size: 24px;
}

.search-filters {
  display: flex;
  gap: var(--space-2);
  overflow-x: auto;
  padding-bottom: var(--space-2);
}

.filter-btn {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  padding: var(--space-2) var(--space-4);
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-full);
  background: white;
  color: var(--color-gray-600);
  font-size: 0.875rem;
  cursor: pointer;
  transition: all var(--transition-fast);
  white-space: nowrap;
}

.filter-btn:hover {
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.filter-btn.active {
  background-color: var(--color-primary-50);
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.count {
  background-color: var(--color-gray-100);
  padding: 2px 6px;
  border-radius: var(--radius-full);
  font-size: 0.75rem;
}

.active .count {
  background-color: var(--color-primary-100);
}

.result-section {
  margin-bottom: var(--space-8);
}

.section-title {
  font-size: 1.25rem;
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

.stall-intro {
  font-size: 0.875rem;
  color: var(--color-gray-600);
  margin-top: var(--space-2);
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.no-results {
  text-align: center;
  padding: var(--space-8);
  color: var(--color-gray-500);
}

.no-results .material-icons {
  font-size: 48px;
  margin-bottom: var(--space-2);
}

.search-suggestions {
  padding: var(--space-8) 0;
}

.suggestions-title {
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-4);
}

.suggestions-grid {
  display: flex;
  flex-wrap: wrap;
  gap: var(--space-2);
}

.suggestion-btn {
  padding: var(--space-2) var(--space-4);
  background-color: var(--color-gray-100);
  border: none;
  border-radius: var(--radius-full);
  color: var(--color-gray-700);
  font-size: 0.875rem;
  cursor: pointer;
  transition: all var(--transition-fast);
}

.suggestion-btn:hover {
  background-color: var(--color-gray-200);
  color: var(--color-gray-900);
}

@media (min-width: 768px) {
  .search-bar input {
    font-size: 1.25rem;
  }
}
</style>