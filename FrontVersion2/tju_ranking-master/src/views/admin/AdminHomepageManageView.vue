<template>
  <div class="admin-homepage-manage-view">
    <div class="page-title-bar">
      <h2><i class="fas fa-tachometer-alt"></i> 前台首页内容管理</h2>
      <p>管理前台首页的轮播图、推荐内容等模块。</p>
    </div>

    <div class="content-card">
      <!-- 轮播图管理 -->
      <section class="management-section">
        <h3><i class="fas fa-images"></i> 轮播图管理</h3>
        <div class="actions-bar section-actions">
          <button @click="openCarouselModal()" class="btn btn-sm btn-success">
            <i class="fas fa-plus"></i> 添加轮播图
          </button>
        </div>
        <div v-if="loading.carousels" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载轮播图...</div>
        <div v-else-if="carousels.length === 0" class="no-data">暂无轮播图数据。</div>
        <ul v-else class="item-list carousel-list">
          <li v-for="(item, index) in carousels" :key="item.id" class="list-item card">
            <div class="item-preview">
              <img v-if="item.imageUrl" :src="item.imageUrl" :alt="item.title || '轮播图'" @error="onImageError">
              <div v-else class="image-placeholder-list"><i class="fas fa-image"></i></div>
            </div>
            <div class="item-info">
              <h4>{{ item.title || '无标题轮播图' }}</h4>
              <p><strong>链接到:</strong> {{ item.linkUrl || '无链接' }}</p>
              <p><strong>排序:</strong> {{ item.sortOrder || '未设置' }} | <strong>状态:</strong> {{ item.isActive ? '显示中' : '已隐藏' }}</p>
            </div>
            <div class="item-actions">
              <button @click="openCarouselModal(item)" class="btn-icon btn-edit" title="编辑"><i class="fas fa-edit"></i></button>
              <button @click="deleteCarouselItem(item.id, index)" class="btn-icon btn-delete" title="删除"><i class="fas fa-trash-alt"></i></button>
            </div>
          </li>
        </ul>
      </section>

      <!-- 推荐内容管理 (例如：首页推荐菜品/博客) -->
      <section class="management-section">
        <h3><i class="fas fa-star"></i> 推荐位管理 (占位)</h3>
        <div class="actions-bar section-actions">
          <button class="btn btn-sm btn-success" @click="alertPlaceholderFeature">
            <i class="fas fa-plus"></i> 添加推荐内容
          </button>
        </div>
        <p class="placeholder-text">
          此处用于管理首页特定推荐位的内容，例如：<br>
          - 今日推荐菜品<br>
          - 热门博客精选<br>
          - 活动推广位等。<br>
          具体实现方式会根据推荐内容的类型和业务逻辑而定。
        </p>
      </section>
    </div>

    <!-- 轮播图添加/编辑模态框 -->
    <div v-if="showCarouselFormModal" class="modal-overlay" @click.self="closeCarouselModal">
      <div class="modal-content item-form-modal">
        <h3>{{ carouselModalTitle }}</h3>
        <form @submit.prevent="saveCarouselItem">
          <div class="form-group">
            <label for="carouselTitle">标题 (可选):</label>
            <input type="text" id="carouselTitle" v-model="currentCarouselItem.title">
          </div>
          <div class="form-group">
            <label for="carouselImageUrl">图片 URL:</label>
            <input type="url" id="carouselImageUrl" v-model="currentCarouselItem.imageUrl" required placeholder="https://example.com/banner.jpg">
            <img v-if="currentCarouselItem.imageUrl" :src="currentCarouselItem.imageUrl" alt="图片预览" class="form-image-preview" @error="onImageError">
          </div>
          <div class="form-group">
            <label for="carouselLinkUrl">链接 URL (可选):</label>
            <input type="url" id="carouselLinkUrl" v-model="currentCarouselItem.linkUrl" placeholder="/dish/123 或 https://example.com/activity">
          </div>
          <div class="form-group">
            <label for="carouselSortOrder">显示顺序 (数字越小越靠前):</label>
            <input type="number" id="carouselSortOrder" v-model.number="currentCarouselItem.sortOrder" min="0">
          </div>
          <div class="form-group">
            <label>
              <input type="checkbox" v-model="currentCarouselItem.isActive"> 是否激活显示?
            </label>
          </div>
          <div class="modal-actions">
            <button type="button" @click="closeCarouselModal" class="btn btn-secondary">取消</button>
            <button type="submit" class="btn btn-primary">保存轮播图</button>
          </div>
        </form>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'AdminHomepageManageView',
  data() {
    return {
      loading: {
        carousels: true,
        recommendations: true, // 假设有推荐内容加载
      },
      carousels: [], // 模拟轮播图数据
      // 推荐内容数据 (占位)
      // recommendedItems: [],

      showCarouselFormModal: false,
      carouselModalTitle: '',
      currentCarouselItem: {},
      isEditingCarousel: false,
    };
  },
  methods: {
    fetchHomepageData() {
      this.loading.carousels = true;
      // 模拟API调用
      setTimeout(() => {
        this.carousels = [
          { id: 1, title: '春季美食节', imageUrl: 'https://placehold.co/800x300/28a745/white?text=美食节Banner1', linkUrl: '/activity/spring-food-festival', sortOrder: 1, isActive: true },
          { id: 2, title: '新品上市：XXX套餐', imageUrl: 'https://placehold.co/800x300/007bff/white?text=新品套餐Banner2', linkUrl: '/dish/10102', sortOrder: 2, isActive: true },
          { id: 3, title: '校园摄影大赛', imageUrl: 'https://placehold.co/800x300/ffc107/black?text=摄影大赛Banner3', linkUrl: '/blog/topic/photography', sortOrder: 3, isActive: false },
        ].sort((a,b) => (a.sortOrder || 999) - (b.sortOrder || 999));
        this.loading.carousels = false;
      }, 1000);
    },
    onImageError(event){
      event.target.src = 'https://placehold.co/100x60/cccccc/969696?text=图片加载失败'; // 替换为统一的错误占位图
      event.target.style.objectFit = 'contain'; // 避免占位图被拉伸
    },
    openCarouselModal(item = null) {
      this.isEditingCarousel = !!item;
      this.carouselModalTitle = item ? '编辑轮播图' : '添加新轮播图';
      this.currentCarouselItem = item ? { ...item } : { title: '', imageUrl: '', linkUrl: '', sortOrder: (this.carousels.length + 1) * 10, isActive: true };
      this.showCarouselFormModal = true;
    },
    closeCarouselModal() {
      this.showCarouselFormModal = false;
      this.currentCarouselItem = {};
      this.isEditingCarousel = false;
    },
    saveCarouselItem() {
      console.log('保存轮播图:', this.currentCarouselItem);
      if (this.isEditingCarousel) {
        const index = this.carousels.findIndex(c => c.id === this.currentCarouselItem.id);
        if (index !== -1) this.carousels.splice(index, 1, { ...this.currentCarouselItem });
      } else {
        this.carousels.push({ ...this.currentCarouselItem, id: Date.now() });
      }
      this.carousels.sort((a,b) => (a.sortOrder || 999) - (b.sortOrder || 999));
      alert(`轮播图已${this.isEditingCarousel ? '更新' : '添加'}！`);
      this.closeCarouselModal();
    },
    deleteCarouselItem(id, index) {
      if (confirm('确定要删除这个轮播图吗？')) {
        console.log('删除轮播图 ID:', id);
        this.carousels.splice(index, 1);
        alert('轮播图已删除！');
      }
    },
    alertPlaceholderFeature(){
      alert("此功能区域为占位符，待后续开发具体推荐内容的管理界面。");
    }
  },
  created() {
    this.fetchHomepageData();
  }
}
</script>

<style scoped>
.admin-homepage-manage-view { padding: 20px; }
.page-title-bar { margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid #e0e0e0; }
.page-title-bar h2 { font-size: 1.8em; color: #333; margin-top: 0; margin-bottom: 5px; display:flex; align-items:center; }
.page-title-bar h2 i { margin-right: 12px; color: #17a2b8; } /* 青色图标 */
.page-title-bar p { font-size: 1em; color: #666; margin-bottom: 0; }

.content-card { background-color: #fff; padding: 0; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.08); }
.management-section { padding: 20px 25px; border-bottom: 1px solid #f0f0f0; }
.management-section:last-child { border-bottom: none; }
.management-section h3 { font-size: 1.5em; color: #333; margin-top:0; margin-bottom:15px; display:flex; align-items:center; }
.management-section h3 i { margin-right:10px; color:#6c757d; }

.actions-bar.section-actions { margin-bottom: 15px; text-align:right; }
.btn { padding: 8px 16px; border: none; border-radius: 5px; font-size: 0.9em; font-weight: 500; cursor: pointer; transition: background-color 0.2s ease; display:inline-flex; align-items:center; }
.btn i { margin-right: 6px; }
.btn-sm { padding: 6px 12px; font-size:0.85em; }
.btn-success { background-color: #28a745; color:white; }
.btn-success:hover { background-color: #218838; }
.btn-primary { background-color: #007bff; color: white; }
.btn-primary:hover { background-color: #0069d9; }
.btn-secondary { background-color: #6c757d; color:white; }
.btn-secondary:hover { background-color: #5a6268; }


.loading-placeholder, .no-data { text-align:center; padding: 20px; color: #6c757d; font-size: 1em; }
.placeholder-text { color: #888; font-style: italic; margin: 20px 0; line-height:1.6; }

.item-list { list-style: none; padding: 0; margin: 0; }
.list-item.card {
  background-color: #fdfdfd;
  border: 1px solid #eee;
  border-radius: 6px;
  margin-bottom: 15px;
  padding: 15px;
  display: flex;
  align-items: center;
  gap: 15px;
}
.item-preview {
  width: 150px; /* 固定预览图宽度 */
  height: 80px; /* 固定预览图高度 */
  flex-shrink: 0;
}
.item-preview img, .image-placeholder-list {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保持图片比例 */
  border-radius: 4px;
  background-color: #e9ecef;
}
.image-placeholder-list {
  display:flex; align-items:center; justify-content:center; color:#adb5bd; font-size:2em;
}
.item-info { flex-grow: 1; }
.item-info h4 { margin:0 0 8px 0; font-size:1.1em; color:#0056b3;}
.item-info p { margin: 4px 0; font-size:0.85em; color:#555; }
.item-info p strong { color:#333; }

.item-actions { display:flex; gap:8px; }
.item-actions .btn-icon { background: none; border: none; cursor: pointer; padding: 6px; font-size: 1.1em; transition: color 0.2s ease; }
.btn-edit { color: #007bff; }
.btn-edit:hover { color: #0056b3; }
.btn-delete { color: #dc3545; }
.btn-delete:hover { color: #c82333; }

/* Modal Styles */
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1050;}
.item-form-modal { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); width: 90%; max-width: 650px; }
.item-form-modal h3 { margin-top: 0; margin-bottom: 20px; font-size:1.4em; color:#333; }
.item-form-modal .form-group { margin-bottom: 15px; }
.item-form-modal .form-group label { display: block; font-weight: bold; margin-bottom: 6px; font-size:0.9em; }
.item-form-modal input[type="text"],
.item-form-modal input[type="url"],
.item-form-modal input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; font-size:0.95em; }
.item-form-modal input[type="checkbox"] { margin-right: 5px; vertical-align: middle;}
.form-image-preview { max-width:100%; max-height:150px; margin-top:10px; border-radius:4px; border:1px solid #eee; object-fit:contain;}
.modal-actions { margin-top: 25px; display: flex; justify-content: flex-end; gap: 10px; }
</style>
    