<template>
  <div class="admin-announcement-manage-view">
    <div class="page-title-bar">
      <h2><i class="fas fa-bullhorn"></i> 公告管理</h2>
      <p>创建、编辑和管理在前台展示的系统公告。</p>
    </div>

    <div class="content-card">
      <div class="actions-bar">
        <input type="text" v-model="searchQuery" placeholder="搜索公告标题..." class="search-input">
        <button @click="openAnnouncementModal()" class="btn btn-primary">
          <i class="fas fa-plus"></i> 发布新公告
        </button>
      </div>

      <div v-if="loading" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载公告中...</div>
      <div v-else-if="filteredAnnouncements.length === 0" class="no-data-table">暂无公告信息。</div>
      <table v-else class="data-table announcements-table">
        <thead>
        <tr>
          <th>标题</th>
          <th>状态</th>
          <th>发布时间</th>
          <th>显示起止时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="announcement in filteredAnnouncements" :key="announcement.id">
          <td>{{ announcement.title }}</td>
          <td>
                  <span :class="['status-badge', getStatusClass(announcement)]">
                    {{ getStatusText(announcement) }}
                  </span>
          </td>
          <td>{{ formatDate(announcement.createdAt) }}</td>
          <td>{{ formatDate(announcement.startTime) }} - {{ formatDate(announcement.endTime) || '永久' }}</td>
          <td class="action-buttons">
            <button @click="openAnnouncementModal(announcement)" class="btn-icon btn-edit" title="编辑"><i class="fas fa-edit"></i></button>
            <button @click="toggleAnnouncementStatus(announcement)" class="btn-icon" :class="announcement.isActive ? 'btn-disable' : 'btn-enable'" :title="announcement.isActive ? '下线' : '上线'"><i :class="announcement.isActive ? 'fas fa-toggle-off' : 'fas fa-toggle-on'"></i></button>
            <button @click="deleteAnnouncement(announcement.id)" class="btn-icon btn-delete" title="删除"><i class="fas fa-trash-alt"></i></button>
          </td>
        </tr>
        </tbody>
      </table>
      <!-- 分页控件可以在此添加 -->
    </div>

    <!-- 公告添加/编辑模态框 -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeAnnouncementModal">
      <div class="modal-content announcement-modal-content">
        <h3>{{ modalTitle }}</h3>
        <form @submit.prevent="saveAnnouncement">
          <div class="form-group">
            <label for="announcementTitle">公告标题:</label>
            <input type="text" id="announcementTitle" v-model="currentAnnouncement.title" required>
          </div>
          <div class="form-group">
            <label for="announcementContent">公告内容 (支持HTML):</label>
            <textarea id="announcementContent" v-model="currentAnnouncement.content" rows="6" required placeholder="输入公告详细内容..."></textarea>
          </div>
          <div class="form-group-inline">
            <div class="form-group">
              <label for="announcementStartTime">显示开始时间:</label>
              <input type="datetime-local" id="announcementStartTime" v-model="currentAnnouncement.startTime">
            </div>
            <div class="form-group">
              <label for="announcementEndTime">显示结束时间 (可选):</label>
              <input type="datetime-local" id="announcementEndTime" v-model="currentAnnouncement.endTime">
            </div>
          </div>
          <div class="form-group">
            <label for="announcementImage">关联图片URL (可选):</label>
            <input type="url" id="announcementImage" v-model="currentAnnouncement.imageUrl" placeholder="https://example.com/image.jpg">
          </div>
          <div class="form-group">
            <label>
              <input type="checkbox" v-model="currentAnnouncement.isActive"> 是否立即激活 (上线)?
            </label>
          </div>
          <div class="modal-actions">
            <button type="button" @click="closeAnnouncementModal" class="btn btn-secondary">取消</button>
            <button type="submit" class="btn btn-primary">保存公告</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminAnnouncementManageView',
  data() {
    return {
      loading: true,
      searchQuery: '',
      announcements: [], // 模拟公告数据
      showModal: false,
      modalTitle: '',
      currentAnnouncement: {}, // 用于添加或编辑的对象
      isEditing: false,
    };
  },
  computed: {
    filteredAnnouncements() {
      if (!this.searchQuery) {
        return this.announcements;
      }
      const searchTerm = this.searchQuery.toLowerCase();
      return this.announcements.filter(ann => ann.title.toLowerCase().includes(searchTerm));
    },
    currentUserRole() {
      return this.$auth ? this.$auth.getUserRole() : '';
    }
  },
  methods: {
    fetchAnnouncements() {
      this.loading = true;
      setTimeout(() => {
        this.announcements = [
          { id: 1, title: '五一劳动节放假通知', content: '<p>根据学校安排，<strong>五一劳动节</strong>放假时间为...</p>', createdAt: '2024-04-28T10:00:00Z', startTime: '2024-04-29T00:00:00Z', endTime: '2024-05-05T23:59:59Z', isActive: true, imageUrl: null },
          { id: 2, title: '夏季食堂开放时间调整', content: '夏季来临，各食堂开放时间将有所调整，详情请咨询各食堂门口公告。', createdAt: '2024-05-15T14:00:00Z', startTime: '2024-06-01T00:00:00Z', endTime: null, isActive: true, imageUrl: 'https://placehold.co/300x100/007bff/white?text=食堂图片示例' },
          { id: 3, title: '关于举办校园美食节的预告', content: '敬请期待！一年一度的校园美食节即将开幕！', createdAt: '2024-05-20T09:00:00Z', startTime: '2024-05-20T09:00:00Z', endTime: '2024-06-20T23:59:59Z', isActive: false, imageUrl: null },
        ];
        this.loading = false;
      }, 800);
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A';
      return new Date(dateString).toLocaleString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
    },
    getStatusText(announcement) {
      const now = new Date();
      const start = announcement.startTime ? new Date(announcement.startTime) : null;
      const end = announcement.endTime ? new Date(announcement.endTime) : null;

      if (!announcement.isActive) return '已下线';
      if (start && now < start) return '待上线';
      if (end && now > end) return '已过期';
      return '显示中';
    },
    getStatusClass(announcement) {
      const statusText = this.getStatusText(announcement);
      if (statusText === '显示中') return 'status-active';
      if (statusText === '待上线') return 'status-pending';
      if (statusText === '已过期') return 'status-expired';
      return 'status-inactive'; // 已下线
    },
    openAnnouncementModal(announcement = null) {
      this.isEditing = !!announcement;
      this.modalTitle = announcement ? '编辑公告' : '发布新公告';
      // 将datetime-local需要的格式转换
      const formatForDateTimeLocal = (isoDateString) => {
        if (!isoDateString) return '';
        const date = new Date(isoDateString);
        // 通过减少时区偏移量来获取本地时间
        date.setMinutes(date.getMinutes() - date.getTimezoneOffset());
        return date.toISOString().slice(0, 16);
      };

      this.currentAnnouncement = announcement ?
          { ...announcement,
            startTime: formatForDateTimeLocal(announcement.startTime),
            endTime: formatForDateTimeLocal(announcement.endTime)
          } :
          { title: '', content: '', startTime: formatForDateTimeLocal(new Date().toISOString()), endTime: '', isActive: true, imageUrl: '' };
      this.showModal = true;
    },
    closeAnnouncementModal() {
      this.showModal = false;
      this.currentAnnouncement = {};
      this.isEditing = false;
    },
    saveAnnouncement() {
      // 将datetime-local的字符串转回ISOString或Timestamp存储
      const toISOStringOrNull = (localDateTimeString) => {
        if (!localDateTimeString) return null;
        return new Date(localDateTimeString).toISOString();
      };

      this.currentAnnouncement.startTime = toISOStringOrNull(this.currentAnnouncement.startTime);
      this.currentAnnouncement.endTime = toISOStringOrNull(this.currentAnnouncement.endTime);


      console.log('保存公告:', this.currentAnnouncement);
      if (this.isEditing) {
        const index = this.announcements.findIndex(a => a.id === this.currentAnnouncement.id);
        if (index !== -1) this.announcements.splice(index, 1, { ...this.currentAnnouncement });
        alert('公告已更新！');
      } else {
        this.announcements.unshift({ ...this.currentAnnouncement, id: Date.now(), createdAt: new Date().toISOString() });
        alert('新公告已发布！');
      }
      this.closeAnnouncementModal();
    },
    toggleAnnouncementStatus(announcement) {
      announcement.isActive = !announcement.isActive;
      // API call to update status
      console.log(`公告 "${announcement.title}" 状态更新为: ${announcement.isActive ? '上线' : '下线'}`);
      alert(`公告已${announcement.isActive ? '上线' : '下线'}！`);
    },
    deleteAnnouncement(id) {
      if (confirm('确定要删除这条公告吗？')) {
        console.log('删除公告 ID:', id);
        this.announcements = this.announcements.filter(a => a.id !== id);
        alert('公告已删除！');
      }
    }
  },
  created() {
    if (this.currentUserRole !== 'boss') {
      alert('您没有权限访问此页面。');
      this.$router.push({name: 'AdminDashboard'});
      return;
    }
    this.fetchAnnouncements();
  }
}
</script>

<style scoped>
/* 复用部分通用后台样式 */
.admin-announcement-manage-view { padding: 20px; }
.page-title-bar { margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid #e0e0e0; }
.page-title-bar h2 { font-size: 1.8em; color: #333; margin-top: 0; margin-bottom: 5px; display:flex; align-items:center; }
.page-title-bar h2 i { margin-right: 12px; color: #e83e8c; } /* 粉色图标 */
.page-title-bar p { font-size: 1em; color: #666; margin-bottom: 0; }

.content-card { background-color: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.08); }
.actions-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.search-input { padding: 10px 12px; border: 1px solid #ced4da; border-radius: 5px; font-size: 0.9em; min-width:250px;}
.btn { padding: 8px 16px; border: none; border-radius: 5px; font-size: 0.9em; font-weight: 500; cursor: pointer; transition: background-color 0.2s ease; display:inline-flex; align-items:center; }
.btn i { margin-right: 6px; }
.btn-primary { background-color: #007bff; color: white; }
.btn-primary:hover { background-color: #0069d9; }
.btn-secondary { background-color: #6c757d; color:white; }
.btn-secondary:hover { background-color: #5a6268; }

.loading-placeholder, .no-data-table { text-align:center; padding: 30px; color: #6c757d; font-size: 1.1em; }
.announcements-table { width: 100%; border-collapse: collapse; font-size: 0.9em; }
.announcements-table th, .announcements-table td { padding: 12px 15px; text-align: left; border: 1px solid #e9ecef; vertical-align: middle;}
.announcements-table th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
.announcements-table tbody tr:hover { background-color: #f1f3f5; }

.status-badge { padding: 4px 10px; border-radius: 12px; font-size: 0.85em; font-weight: bold; color: white; text-align:center; display:inline-block; min-width: 70px;}
.status-active { background-color: #28a745; } /* 显示中 */
.status-pending { background-color: #ffc107; color: #212529;} /* 待上线 */
.status-expired { background-color: #6c757d; } /* 已过期 */
.status-inactive { background-color: #dc3545; } /* 已下线 */

.action-buttons .btn-icon { background: none; border: none; cursor: pointer; padding: 6px; margin: 0 4px; font-size: 1.1em; transition: color 0.2s ease; }
.btn-edit { color: #007bff; }
.btn-edit:hover { color: #0056b3; }
.btn-disable { color: #ffc107; } /* 下线 */
.btn-disable:hover { color: #e0a800; }
.btn-enable { color: #28a745; } /* 上线 */
.btn-enable:hover { color: #1e7e34; }
.btn-delete { color: #dc3545; }
.btn-delete:hover { color: #c82333; }

/* Modal Styles */
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1050;}
.announcement-modal-content { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); width: 90%; max-width: 700px; }
.announcement-modal-content h3 { margin-top: 0; margin-bottom: 20px; font-size:1.4em; color:#333; }
.announcement-modal-content .form-group { margin-bottom: 15px; }
.announcement-modal-content .form-group label { display: block; font-weight: bold; margin-bottom: 6px; font-size:0.9em; }
.announcement-modal-content input[type="text"],
.announcement-modal-content input[type="url"],
.announcement-modal-content input[type="datetime-local"],
.announcement-modal-content textarea { width: 100%; padding: 10px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; font-size:0.95em; }
.announcement-modal-content textarea { min-height: 120px; }
.form-group-inline { display:flex; gap: 20px; }
.form-group-inline .form-group { flex:1; }
.announcement-modal-content input[type="checkbox"] { margin-right: 5px; vertical-align: middle;}
.modal-actions { margin-top: 25px; display: flex; justify-content: flex-end; gap: 10px; }
</style>
    