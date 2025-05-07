<template>
  <div class="blog-list-page">
    <div class="container">
      <div class="page-header">
        <h1 class="page-title">美食博客</h1>
        <p class="page-description">发现校园美食，分享美食体验</p>
      </div>

      <div class="filters">
        <div class="search-bar">
          <input
              type="text"
              v-model="searchQuery"
              placeholder="搜索博客..."
              @input="handleSearch"
          />
          <span class="material-icons search-icon">search</span>
        </div>

        <div class="sort-options">
          <button
              v-for="option in sortOptions"
              :key="option.value"
              :class="['sort-btn', { active: currentSort === option.value }]"
              @click="currentSort = option.value"
          >
            {{ option.label }}
          </button>
        </div>
      </div>

      <div class="blog-grid">
        <BlogCard
            v-for="blog in sortedBlogs"
            :key="blog.id"
            :blog="blog"
        />
      </div>

      <div v-if="sortedBlogs.length === 0" class="no-results">
        <span class="material-icons">search_off</span>
        <p>暂无相关博客</p>
      </div>

      <div class="floating-action-button" @click="handleCreateBlog" v-if="isAuthenticated">
        <span class="material-icons">edit</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useDataStore } from '../../stores/data'
import { useAuthStore } from '../../stores/auth'
import BlogCard from '../../components/cards/BlogCard.vue'

const router = useRouter()
const dataStore = useDataStore()
const authStore = useAuthStore()

const searchQuery = ref('')
const currentSort = ref('latest')

const sortOptions = [
  { label: '最新发布', value: 'latest' },
  { label: '最多点赞', value: 'liked' },
  { label: '最多评论', value: 'comments' }
]

const isAuthenticated = computed(() => authStore.isAuthenticated)

const blogs = computed(() => {
  const allBlogs = dataStore.getAllBlogs()
  if (!searchQuery.value) return allBlogs

  const query = searchQuery.value.toLowerCase()
  return allBlogs.filter(blog =>
      blog.title.toLowerCase().includes(query) ||
      blog.content.toLowerCase().includes(query)
  )
})

const sortedBlogs = computed(() => {
  const blogList = [...blogs.value]
  switch (currentSort.value) {
    case 'liked':
      return blogList.sort((a, b) => b.liked - a.liked)
    case 'comments':
      return blogList.sort((a, b) => b.comments - a.comments)
    default: // 'latest'
      return blogList.sort((a, b) => new Date(b.createTime) - new Date(a.createTime))
  }
})

function handleSearch() {
  // Debounce implementation could be added here
}

function handleCreateBlog() {
  if (!isAuthenticated.value) {
    router.push('/login')
    return
  }
  router.push('/blog/create')
}
</script>

<style scoped>
.blog-list-page {
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

.filters {
  margin-bottom: var(--space-6);
}

.search-bar {
  position: relative;
  margin-bottom: var(--space-4);
}

.search-bar input {
  width: 100%;
  padding: var(--space-3) var(--space-3) var(--space-3) var(--space-10);
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-full);
  outline: none;
  font-size: 0.875rem;
  transition: all var(--transition-fast);
}

.search-bar input:focus {
  border-color: var(--color-primary-400);
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

.search-icon {
  position: absolute;
  left: var(--space-3);
  top: 50%;
  transform: translateY(-50%);
  color: var(--color-gray-400);
  font-size: 20px;
}

.sort-options {
  display: flex;
  gap: var(--space-2);
  overflow-x: auto;
  padding-bottom: var(--space-2);
}

.sort-btn {
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

.sort-btn:hover {
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.sort-btn.active {
  background-color: var(--color-primary-50);
  border-color: var(--color-primary-400);
  color: var(--color-primary-600);
}

.blog-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: var(--space-4);
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

.floating-action-button {
  position: fixed;
  right: var(--space-4);
  bottom: calc(var(--space-16) + 56px);
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background-color: var(--color-primary-600);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: var(--shadow-lg);
  transition: all var(--transition-fast);
}

.floating-action-button:hover {
  background-color: var(--color-primary-700);
  transform: translateY(-2px);
}

.floating-action-button .material-icons {
  font-size: 24px;
}

@media (min-width: 640px) {
  .blog-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 768px) {
  .floating-action-button {
    bottom: var(--space-8);
  }
}

@media (min-width: 1024px) {
  .blog-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>