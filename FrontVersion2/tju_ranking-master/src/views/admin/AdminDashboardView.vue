<template>
  <div class="admin-dashboard-view">
    <div class="page-title-bar">
      <h2><i class="fas fa-chart-line"></i> 统计分析 (仪表盘)</h2>
      <p>欢迎来到管理后台，以下是今日平台关键数据概览。</p>
    </div>

    <div class="dashboard-stats-grid">
      <div class="stat-card">
        <div class="stat-icon users">
          <i class="fas fa-users"></i>
        </div>
        <div class="stat-info">
          <h4>新增用户 (今日)</h4>
          <p class="stat-value">{{ stats.newUsersToday || 'N/A' }}</p>
          <!-- <small>较昨日 <span class="text-success">+5%</span></small> -->
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon likes">
          <i class="fas fa-thumbs-up"></i>
        </div>
        <div class="stat-info">
          <h4>新增点赞 (今日)</h4>
          <p class="stat-value">{{ stats.newLikesToday || 'N/A' }}</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon favorites">
          <i class="fas fa-heart"></i>
        </div>
        <div class="stat-info">
          <h4>新增收藏 (今日)</h4>
          <p class="stat-value">{{ stats.newFavoritesToday || 'N/A' }}</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon blogs">
          <i class="fas fa-newspaper"></i>
        </div>
        <div class="stat-info">
          <h4>新增博客 (今日)</h4>
          <p class="stat-value">{{ stats.newBlogsToday || 'N/A' }}</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon comments">
          <i class="fas fa-comments"></i>
        </div>
        <div class="stat-info">
          <h4>新增评论 (今日)</h4>
          <p class="stat-value">{{ stats.newCommentsToday || 'N/A' }}</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon active-users">
          <i class="fas fa-user-clock"></i>
        </div>
        <div class="stat-info">
          <h4>活跃用户 (今日)</h4>
          <p class="stat-value">{{ stats.activeUsersToday || 'N/A' }}</p>
        </div>
      </div>
    </div>

    <div class="dashboard-charts-section">
      <div class="chart-container card">
        <h3><i class="fas fa-chart-bar"></i> 用户活跃度趋势 (占位)</h3>
        <div class="chart-placeholder">
          <p>此处为用户活跃度图表区域</p>
          <small>(例如：最近7日每日活跃用户数)</small>
        </div>
      </div>
      <div class="chart-container card">
        <h3><i class="fas fa-chart-pie"></i> 内容互动分布 (占位)</h3>
        <div class="chart-placeholder">
          <p>此处为内容互动图表区域</p>
          <small>(例如：点赞、收藏、评论的比例)</small>
        </div>
      </div>
    </div>

    <div class="quick-actions-section card">
      <h3><i class="fas fa-bolt"></i> 快速操作</h3>
      <div class="actions-grid">
        <button class="action-button" @click="goTo('AdminBlogCommentManage')"><i class="fas fa-tasks"></i> 审核待处理内容</button>
        <button class="action-button" @click="goTo('AdminFoodManagement')"><i class="fas fa-plus-circle"></i> 添加食堂/菜品</button>
        <button class="action-button" v-if="canAccess(['boss'])" @click="goTo('AdminAnnouncementManage')"><i class="fas fa-bullhorn"></i> 发布新公告</button>
        <button class="action-button" v-if="canAccess(['boss'])" @click="goTo('AdminRoleManage')"><i class="fas fa-users-cog"></i> 管理用户角色</button>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'AdminDashboardView',
  data() {
    return {
      loadingStats: true,
      stats: {
        newUsersToday: 0,
        newLikesToday: 0,
        newFavoritesToday: 0,
        newBlogsToday: 0,
        newCommentsToday: 0,
        activeUsersToday: 0,
      }
    };
  },
  computed: {
    adminUserRole() {
      return this.$auth ? this.$auth.getUserRole() : '';
    }
  },
  methods: {
    fetchDashboardStats() {
      this.loadingStats = true;
      // 模拟API调用获取统计数据
      console.log("Fetching dashboard statistics...");
      setTimeout(() => {
        this.stats = {
          newUsersToday: Math.floor(Math.random() * 50) + 5,
          newLikesToday: Math.floor(Math.random() * 200) + 50,
          newFavoritesToday: Math.floor(Math.random() * 150) + 30,
          newBlogsToday: Math.floor(Math.random() * 20) + 2,
          newCommentsToday: Math.floor(Math.random() * 100) + 10,
          activeUsersToday: Math.floor(Math.random() * 300) + 100,
        };
        this.loadingStats = false;
      }, 1200);
    },
    goTo(routeName) {
      this.$router.push({ name: routeName });
    },
    canAccess(requiredRoles) {
      const userRole = this.adminUserRole;
      return userRole && requiredRoles.includes(userRole);
    }
  },
  created() {
    this.fetchDashboardStats();
  }
}
</script>

<style scoped>
.admin-dashboard-view {
  padding: 20px;
}

.page-title-bar {
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
}
.page-title-bar h2 {
  font-size: 1.8em;
  color: #333;
  margin-top: 0;
  margin-bottom: 5px;
  display: flex;
  align-items: center;
}
.page-title-bar h2 i {
  margin-right: 12px;
  color: #007bff;
}
.page-title-bar p {
  font-size: 1em;
  color: #666;
  margin-bottom: 0;
}

.dashboard-stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.06);
  display: flex;
  align-items: center;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.stat-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}

.stat-icon {
  font-size: 2.5em; /* 图标大小 */
  padding: 15px;
  border-radius: 50%;
  margin-right: 20px;
  color: #fff;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.stat-icon.users { background-color: #17a2b8; } /* Info Blue */
.stat-icon.likes { background-color: #007bff; } /* Primary Blue */
.stat-icon.favorites { background-color: #e83e8c; } /* Pink */
.stat-icon.blogs { background-color: #fd7e14; } /* Orange */
.stat-icon.comments { background-color: #20c997; } /* Teal */
.stat-icon.active-users { background-color: #6f42c1; } /* Indigo */


.stat-info h4 {
  font-size: 0.95em;
  color: #6c757d;
  margin-top: 0;
  margin-bottom: 5px;
  font-weight: 500;
}
.stat-value {
  font-size: 1.8em;
  font-weight: bold;
  color: #343a40;
  margin: 0;
}
.stat-info small {
  font-size: 0.8em;
  color: #6c757d;
}
.text-success { color: #28a745 !important; }
.text-danger { color: #dc3545 !important; }

.dashboard-charts-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.chart-container.card,
.quick-actions-section.card {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 1px 5px rgba(0,0,0,0.05);
}
.chart-container h3,
.quick-actions-section h3 {
  font-size: 1.3em;
  color: #333;
  margin-top: 0;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
  display: flex;
  align-items: center;
}
.chart-container h3 i,
.quick-actions-section h3 i{
  margin-right: 10px;
  color: #6c757d;
}


.chart-placeholder {
  min-height: 250px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #f8f9fa;
  border: 1px dashed #e0e0e0;
  border-radius: 5px;
  color: #adb5bd;
  text-align: center;
}
.chart-placeholder p {
  font-size: 1.1em;
  margin-bottom: 5px;
}
.chart-placeholder small {
  font-size: 0.9em;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 15px;
}
.action-button {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  color: #007bff;
  padding: 12px 15px;
  border-radius: 5px;
  text-align: left;
  font-size: 0.95em;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s, border-color 0.2s, color 0.2s;
  display: flex;
  align-items: center;
}
.action-button i {
  margin-right: 10px;
  color: #6c757d;
  transition: color 0.2s;
}
.action-button:hover {
  background-color: #e9ecef;
  border-color: #adb5bd;
  color: #0056b3;
}
.action-button:hover i {
  color: #0056b3;
}

</style>
