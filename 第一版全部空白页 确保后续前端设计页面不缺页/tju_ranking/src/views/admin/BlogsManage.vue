<template>
  <div class="admin-blogs-manage-container">
    <header class="admin-header">
      <div class="header-content">
        <h1 class="admin-title">
          <i class="fas fa-newspaper"></i> 博客管理
        </h1>
        <div class="admin-actions">
          <button @click="navigateTo('/main')" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> 返回主页面
          </button>
          <!-- <button @click="openCreateBlogModal" class="btn btn-primary">
            <i class="fas fa-plus"></i> 创建新博客
          </button> -->
        </div>
      </div>
    </header>

    <nav class="admin-sub-nav">
      <router-link to="/admin/bulletins" class="sub-nav-item">
        <i class="fas fa-bullhorn"></i> 公告管理
      </router-link>
      <router-link to="/admin/homepage" class="sub-nav-item">
        <i class="fas fa-home"></i> 首页管理
      </router-link>
      <router-link to="/admin/data" class="sub-nav-item">
        <i class="fas fa-database"></i> 数据管理
      </router-link>
      <!-- 可以添加更多后台管理模块的快捷链接 -->
    </nav>

    <main class="admin-main-content">
      <div class="content-card">
        <div class="table-controls">
          <input type="text" v-model="searchQuery" placeholder="搜索博客标题或作者..." class="search-input">
          <div class="filter-group">
            <select v-model="filterStatus" class="filter-select">
              <option value="">所有状态</option>
              <option value="published">已发布</option>
              <option value="draft">草稿</option>
              <option value="pending">待审核</option>
            </select>
            <select v-model="filterCategory" class="filter-select">
              <option value="">所有分类</option>
              <!-- 动态加载分类 -->
              <option value="food_review">美食测评</option>
              <option value="canteen_guide">食堂指南</option>
              <option value="news">校园资讯</option>
            </select>
          </div>
        </div>

        <div class="blogs-table-container">
          <table class="blogs-table">
            <thead>
            <tr>
              <th><input type="checkbox" @change="toggleSelectAll" v-model="allSelected"></th>
              <th>标题</th>
              <th>作者</th>
              <th>分类</th>
              <th>状态</th>
              <th>发布日期</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-if="loading">
              <td colspan="7" class="text-center">
                <i class="fas fa-spinner fa-spin"></i> 加载中...
              </td>
            </tr>
            <tr v-else-if="filteredBlogs.length === 0">
              <td colspan="7" class="text-center">未找到相关博客。</td>
            </tr>
            <tr v-for="blog in paginatedBlogs" :key="blog.id">
              <td><input type="checkbox" v-model="selectedBlogs" :value="blog.id"></td>
              <td>{{ blog.title }}</td>
              <td>{{ blog.author }}</td>
              <td>{{ getCategoryName(blog.category) }}</td>
              <td>
                <span :class="['status-badge', `status-${blog.status}`]">{{ getStatusName(blog.status) }}</span>
              </td>
              <td>{{ formatDate(blog.publishDate) }}</td>
              <td class="action-buttons">
                <button @click="viewBlog(blog.id)" class="btn-icon btn-view" title="查看">
                  <i class="fas fa-eye"></i>
                </button>
                <button @click="editBlog(blog.id)" class="btn-icon btn-edit" title="编辑">
                  <i class="fas fa-edit"></i>
                </button>
                <button @click="deleteBlog(blog.id)" class="btn-icon btn-delete" title="删除">
                  <i class="fas fa-trash-alt"></i>
                </button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>

        <div class="pagination-controls" v-if="totalPages > 1">
          <button @click="prevPage" :disabled="currentPage === 1" class="btn btn-pagination">
            <i class="fas fa-chevron-left"></i> 上一页
          </button>
          <span>第 {{ currentPage }} 页 / 共 {{ totalPages }} 页</span>
          <button @click="nextPage" :disabled="currentPage === totalPages" class="btn btn-pagination">
            下一页 <i class="fas fa-chevron-right"></i>
          </button>
        </div>
      </div>
    </main>

    <footer class="admin-footer">
      <p>&copy; 2024-2025 天大点评 - 管理后台</p>
    </footer>

    <!-- 模态框组件占位 (用于创建/编辑博客) -->
    <!-- <CreateEditBlogModal v-if="showModal" @close="showModal = false" :blogData="selectedBlogData" /> -->
  </div>
</template>

<script>
// import CreateEditBlogModal from '@/components/admin/CreateEditBlogModal.vue'; // 假设的模态框组件

export default {
  name: 'AdminBlogsManageView',
  // components: { CreateEditBlogModal },
  data() {
    return {
      loading: true,
      searchQuery: '',
      filterStatus: '',
      filterCategory: '',
      blogs: [], // 应该从API获取
      selectedBlogs: [],
      allSelected: false,
      currentPage: 1,
      itemsPerPage: 10,
      // showModal: false,
      // selectedBlogData: null,
    };
  },
  computed: {
    filteredBlogs() {
      return this.blogs.filter(blog => {
        const matchesSearch = blog.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
            blog.author.toLowerCase().includes(this.searchQuery.toLowerCase());
        const matchesStatus = this.filterStatus ? blog.status === this.filterStatus : true;
        const matchesCategory = this.filterCategory ? blog.category === this.filterCategory : true;
        return matchesSearch && matchesStatus && matchesCategory;
      });
    },
    totalPages() {
      return Math.ceil(this.filteredBlogs.length / this.itemsPerPage);
    },
    paginatedBlogs() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredBlogs.slice(start, end);
    }
  },
  methods: {
    navigateTo(route) {
      this.$router.push(route);
    },
    fetchBlogs() {
      this.loading = true;
      // 模拟API调用
      setTimeout(() => {
        this.blogs = [
          { id: 1, title: '探索学三食堂：美味与实惠并存', author: '小明', category: 'food_review', status: 'published', publishDate: '2024-05-01' },
          { id: 2, title: '校园樱花季摄影作品征集', author: '管理员', category: 'news', status: 'published', publishDate: '2024-04-28' },
          { id: 3, title: '关于调整图书馆开放时间的通知 (草稿)', author: '图书馆', category: 'news', status: 'draft', publishDate: '2024-05-03' },
          { id: 4, title: '北洋园校区新生用餐指南', author: '学生会', category: 'canteen_guide', status: 'pending', publishDate: '2024-05-05' },
          { id: 5, title: '求实BBS近期热门话题汇总', author: 'BBS观察员', category: 'news', status: 'published', publishDate: '2024-05-02'},
          // ...更多博客数据
        ];
        this.loading = false;
      }, 1000);
    },
    getStatusName(status) {
      const names = { published: '已发布', draft: '草稿', pending: '待审核' };
      return names[status] || status;
    },
    getCategoryName(category) {
      const names = { food_review: '美食测评', canteen_guide: '食堂指南', news: '校园资讯' };
      return names[category] || category;
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A';
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(dateString).toLocaleDateString('zh-CN', options);
    },
    toggleSelectAll() {
      if (this.allSelected) {
        this.selectedBlogs = this.paginatedBlogs.map(blog => blog.id);
      } else {
        this.selectedBlogs = [];
      }
    },
    viewBlog(id) {
      console.log('查看博客:', id);
      // this.$router.push(`/blog/${id}`); // 或打开一个预览模态框
    },
    editBlog(id) {
      console.log('编辑博客:', id);
      // this.selectedBlogData = this.blogs.find(b => b.id === id);
      // this.showModal = true;
    },
    deleteBlog(id) {
      if (confirm('确定要删除这篇博客吗？此操作不可撤销。')) {
        console.log('删除博客:', id);
        this.blogs = this.blogs.filter(blog => blog.id !== id);
        // 在此调用API删除
      }
    },
    // openCreateBlogModal() {
    //   this.selectedBlogData = null; // 清空数据以创建新博客
    //   this.showModal = true;
    // },
    prevPage() {
      if (this.currentPage > 1) this.currentPage--;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) this.currentPage++;
    }
  },
  watch: {
    // 当筛选条件或当前页的博客列表变化时，重置全选状态
    paginatedBlogs() {
      this.allSelected = false;
      this.selectedBlogs = [];
    },
    currentPage() {
      this.allSelected = false;
      this.selectedBlogs = [];
    }
  },
  created() {
    this.fetchBlogs();
  }
}
</script>

<style scoped>
.admin-blogs-manage-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f4f6f9; /* 后台常用浅灰色背景 */
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.admin-header {
  background-color: #ffffff;
  padding: 0 30px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  border-bottom: 1px solid #e0e0e0;
  position: sticky;
  top: 0;
  z-index: 1000;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px; /* 固定头部高度 */
}

.admin-title {
  font-size: 1.6em;
  font-weight: 600;
  color: #333;
  display: flex;
  align-items: center;
}
.admin-title i {
  margin-right: 10px;
  color: #555;
}

.admin-actions .btn {
  margin-left: 15px;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  font-size: 0.9em;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
  text-decoration: none;
  display: inline-flex;
  align-items: center;
}
.btn i {
  margin-right: 6px;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}
.btn-primary:hover {
  background-color: #0069d9;
  box-shadow: 0 2px 5px rgba(0,123,255,0.3);
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
}
.btn-secondary:hover {
  background-color: #5a6268;
}

.admin-sub-nav {
  display: flex;
  background-color: #343a40; /* 深色子导航 */
  padding: 0 20px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.sub-nav-item {
  padding: 12px 18px;
  color: #f8f9fa;
  text-decoration: none;
  font-size: 0.95em;
  font-weight: 500;
  transition: background-color 0.2s ease;
  display: flex;
  align-items: center;
}
.sub-nav-item i {
  margin-right: 8px;
}
.sub-nav-item:hover,
.sub-nav-item.router-link-exact-active { /* 当前激活的链接样式 */
  background-color: #495057;
}

.admin-main-content {
  flex-grow: 1;
  padding: 30px;
}

.content-card {
  background-color: #ffffff;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.08);
}

.table-controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 15px;
}

.search-input {
  padding: 10px 12px;
  border: 1px solid #ced4da;
  border-radius: 5px;
  font-size: 0.9em;
  min-width: 250px;
}

.filter-group {
  display: flex;
  gap: 10px;
}

.filter-select {
  padding: 10px 12px;
  border: 1px solid #ced4da;
  border-radius: 5px;
  font-size: 0.9em;
  background-color: white;
}

.blogs-table-container {
  overflow-x: auto; /* 表格内容多时可横向滚动 */
}

.blogs-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.9em;
}

.blogs-table th,
.blogs-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e9ecef;
}

.blogs-table th {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #495057;
}

.blogs-table tbody tr:hover {
  background-color: #f1f3f5;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 0.8em;
  font-weight: bold;
  color: white;
  text-transform: capitalize;
}
.status-published { background-color: #28a745; } /* 绿色 */
.status-draft { background-color: #ffc107; color: #333; } /* 黄色 */
.status-pending { background-color: #17a2b8; } /* 蓝色 */

.action-buttons .btn-icon {
  background: none;
  border: none;
  cursor: pointer;
  padding: 5px;
  margin: 0 3px;
  font-size: 1.1em;
  transition: color 0.2s ease;
}
.btn-view { color: #007bff; }
.btn-edit { color: #ffc107; }
.btn-delete { color: #dc3545; }

.btn-view:hover { color: #0056b3; }
.btn-edit:hover { color: #e0a800; }
.btn-delete:hover { color: #c82333; }

.text-center {
  text-align: center !important;
  padding: 20px;
  color: #6c757d;
}
.text-center i {
  margin-right: 5px;
}

.pagination-controls {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-top: 25px;
  gap: 10px;
  font-size: 0.9em;
}

.btn-pagination {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  color: #007bff;
  padding: 6px 12px;
}
.btn-pagination:disabled {
  color: #6c757d;
  cursor: not-allowed;
  background-color: #e9ecef;
}
.btn-pagination:not(:disabled):hover {
  background-color: #e2e6ea;
}


.admin-footer {
  text-align: center;
  padding: 15px;
  background-color: #e9ecef;
  border-top: 1px solid #ced4da;
  font-size: 0.85em;
  color: #6c757d;
}

/* 响应式 */
@media (max-width: 768px) {
  .admin-header { padding: 0 15px; }
  .admin-title { font-size: 1.4em; }
  .admin-actions .btn { font-size: 0.8em; padding: 6px 10px; }
  .admin-main-content { padding: 20px; }
  .table-controls { flex-direction: column; align-items: stretch; }
  .search-input, .filter-group { width: 100%; }
  .filter-group { flex-direction: column; }
  .filter-select { width: 100%; }
}
</style>
