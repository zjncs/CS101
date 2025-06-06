<template>
  <div class="admin-role-manage-view">
    <div class="page-title-bar">
      <h2><i class="fas fa-users-cog"></i> 角色与用户管理</h2>
      <p>管理平台所有用户的角色和基本权限设置。</p>
    </div>

    <div class="content-card">
      <div class="table-controls">
        <input type="text" v-model="searchQuery" placeholder="搜索用户姓名、邮箱或电话..." class="search-input">
        <select v-model="filterRole" class="filter-select">
          <option value="">所有角色</option>
          <option value="user">用户 (user)</option>
          <option value="admin">后台管理员 (admin)</option>
          <option value="boss">平台管理员 (boss)</option>
        </select>
        <button @click="openAddUserModal" class="btn btn-primary" v-if="false"> <!-- 暂时隐藏添加用户功能 -->
          <i class="fas fa-user-plus"></i> 添加新用户
        </button>
      </div>

      <div class="users-table-container">
        <table class="users-table">
          <thead>
          <tr>
            <th>ID</th>
            <th>姓名/昵称</th>
            <th>邮箱</th>
            <th>电话</th>
            <th>角色</th>
            <th>状态</th>
            <th>注册日期</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="loading">
            <td colspan="8" class="text-center">
              <i class="fas fa-spinner fa-spin"></i> 加载用户列表中...
            </td>
          </tr>
          <tr v-else-if="filteredUsers.length === 0">
            <td colspan="8" class="text-center">未找到符合条件的用户。</td>
          </tr>
          <tr v-for="user in paginatedUsers" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.phone || 'N/A' }}</td>
            <td>
              <span :class="['role-badge', `role-${user.role}`]">{{ getRoleName(user.role) }}</span>
            </td>
            <td>
                    <span :class="['status-badge', user.status === 'active' ? 'status-active' : 'status-inactive']">
                      {{ user.status === 'active' ? '正常' : '禁用' }}
                    </span>
            </td>
            <td>{{ formatDate(user.registerDate) }}</td>
            <td class="action-buttons">
              <button
                  @click="openEditUserRoleModal(user)"
                  class="btn-icon btn-edit"
                  title="修改角色/权限"
                  :disabled="user.role === 'boss'"
              >
                <i class="fas fa-user-edit"></i>
              </button>
              <button
                  @click="toggleUserStatus(user)"
                  :class="['btn-icon', user.status === 'active' ? 'btn-disable' : 'btn-enable']"
                  :title="user.status === 'active' ? '禁用用户' : '启用用户'"
                  :disabled="user.role === 'boss'"
              >
                <i :class="user.status === 'active' ? 'fas fa-user-slash' : 'fas fa-user-check'"></i>
              </button>
              <button
                  @click="deleteUser(user)"
                  class="btn-icon btn-delete"
                  title="删除用户"
                  :disabled="user.role === 'boss'"
              >
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
        <span>第 {{ currentPage }} 页 / 共 {{ totalPages }} 页 (共 {{ filteredUsers.length }} 条)</span>
        <button @click="nextPage" :disabled="currentPage === totalPages" class="btn btn-pagination">
          下一页 <i class="fas fa-chevron-right"></i>
        </button>
      </div>
    </div>

    <!-- 修改用户角色模态框占位 -->
    <div v-if="showEditModal" class="modal-overlay" @click.self="closeEditUserRoleModal">
      <div class="modal-content">
        <h3>修改用户: {{ editingUser.name }}</h3>
        <div class="form-group">
          <label for="editUserRole">选择新角色:</label>
          <select id="editUserRole" v-model="editingUserNewRole" class="filter-select">
            <option value="user">用户 (user)</option>
            <option value="admin">后台管理员 (admin)</option>
            <!-- Boss角色通常不由其他Boss修改，或者需要特殊权限 -->
            <!-- <option value="boss" :disabled="currentUserRole !== 'superboss'">平台管理员 (boss)</option> -->
          </select>
        </div>
        <!-- 更多权限细分可以在此添加 -->
        <div class="modal-actions">
          <button @click="closeEditUserRoleModal" class="btn btn-secondary">取消</button>
          <button @click="saveUserRoleChange" class="btn btn-primary">保存更改</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'AdminRoleManageView',
  data() {
    return {
      loading: true,
      searchQuery: '',
      filterRole: '',
      users: [], // 模拟用户数据
      currentPage: 1,
      itemsPerPage: 10,
      showEditModal: false,
      editingUser: null,
      editingUserNewRole: '',
    };
  },
  computed: {
    filteredUsers() {
      return this.users.filter(user => {
        const searchTerm = this.searchQuery.toLowerCase();
        const matchesSearch = (
            user.name.toLowerCase().includes(searchTerm) ||
            user.email.toLowerCase().includes(searchTerm) ||
            (user.phone && user.phone.toLowerCase().includes(searchTerm))
        );
        const matchesRole = this.filterRole ? user.role === this.filterRole : true;
        return matchesSearch && matchesRole;
      });
    },
    totalPages() {
      return Math.ceil(this.filteredUsers.length / this.itemsPerPage);
    },
    paginatedUsers() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredUsers.slice(start, end);
    },
    currentUserRole() { // 获取当前操作的管理员的角色
      return this.$auth ? this.$auth.getUserRole() : '';
    }
  },
  methods: {
    fetchUsers() {
      this.loading = true;
      // 模拟API调用
      setTimeout(() => {
        this.users = [
          { id: 1, name: '张三', email: 'zhangsan@example.com', phone: '13800138000', role: 'user', status: 'active', registerDate: '2024-01-15' },
          { id: 2, name: '李四 (后台小编)', email: 'lisi_admin@example.com', phone: '13900139000', role: 'admin', status: 'active', registerDate: '2024-02-10' },
          { id: 3, name: '王五', email: 'wangwu@example.com', phone: '13700137000', role: 'user', status: 'inactive', registerDate: '2024-03-01' },
          { id: 4, name: '平台管理员Boss', email: 'boss@example.com', phone: 'N/A', role: 'boss', status: 'active', registerDate: '2023-12-01' },
          { id: 5, name: '赵六', email: 'zhaoliu@example.com', phone: '13600136000', role: 'user', status: 'active', registerDate: '2024-04-20' },
        ];
        // 确保超级管理员账号存在且角色正确
        const superAdmin = this.users.find(u => u.email === 'boss@example.com'); // 假设超级管理员用邮箱boss@example.com登录
        if (!superAdmin) {
          // this.users.push({ id: crypto.randomUUID(), name: '平台管理员 (Super)', email: 'admin', phone: 'N/A', role: 'boss', status: 'active', registerDate: '2023-01-01' });
          // 超级管理员账号在 router/index.js 中模拟，这里不需要再次添加
        } else {
          superAdmin.role = 'boss'; // 确保角色是boss
        }

        this.loading = false;
      }, 1000);
    },
    getRoleName(role) {
      const names = { user: '普通用户', admin: '后台管理员', boss: '平台管理员' };
      return names[role] || role;
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A';
      return new Date(dateString).toLocaleDateString('zh-CN');
    },
    openAddUserModal() {
      // 实际应用中，添加新用户表单会更复杂
      alert("添加新用户功能待实现。");
    },
    openEditUserRoleModal(user) {
      if (user.role === 'boss') {
        alert("平台管理员(Boss)的角色不能被修改。");
        return;
      }
      this.editingUser = { ...user }; // 创建副本以编辑
      this.editingUserNewRole = user.role;
      this.showEditModal = true;
    },
    closeEditUserRoleModal() {
      this.showEditModal = false;
      this.editingUser = null;
    },
    saveUserRoleChange() {
      if (!this.editingUser || !this.editingUserNewRole) return;
      // 模拟API调用保存更改
      console.log(`用户 ${this.editingUser.name} (ID: ${this.editingUser.id}) 角色更改为: ${this.editingUserNewRole}`);
      const userIndex = this.users.findIndex(u => u.id === this.editingUser.id);
      if (userIndex !== -1) {
        this.users[userIndex].role = this.editingUserNewRole;
        // Vue.set(this.users, userIndex, { ...this.users[userIndex], role: this.editingUserNewRole });
      }
      alert("用户角色已更新！");
      this.closeEditUserRoleModal();
    },
    toggleUserStatus(user) {
      if (user.role === 'boss') {
        alert("平台管理员(Boss)的状态不能被修改。");
        return;
      }
      const newStatus = user.status === 'active' ? 'inactive' : 'active';
      if (confirm(`确定要将用户 "${user.name}" 的状态更改为 "${newStatus === 'active' ? '正常' : '禁用'}" 吗？`)) {
        // 模拟API调用
        console.log(`用户 ${user.name} (ID: ${user.id}) 状态更改为: ${newStatus}`);
        const userIndex = this.users.findIndex(u => u.id === user.id);
        if (userIndex !== -1) {
          this.users[userIndex].status = newStatus;
          // Vue.set(this.users, userIndex, { ...this.users[userIndex], status: newStatus });
        }
        alert(`用户状态已更新为 "${newStatus === 'active' ? '正常' : '禁用'}"！`);
      }
    },
    deleteUser(user) {
      if (user.role === 'boss') {
        alert("平台管理员(Boss)不能被删除。");
        return;
      }
      if (confirm(`确定要删除用户 "${user.name}" 吗？此操作不可恢复。`)) {
        // 模拟API调用
        console.log(`删除用户: ${user.name} (ID: ${user.id})`);
        this.users = this.users.filter(u => u.id !== user.id);
        alert("用户已删除！");
      }
    },
    prevPage() {
      if (this.currentPage > 1) this.currentPage--;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) this.currentPage++;
    }
  },
  created() {
    if (this.currentUserRole !== 'boss') {
      alert('您没有权限访问此页面。');
      this.$router.push({name: 'AdminDashboard'}); // 或其他无权限提示页
      return;
    }
    this.fetchUsers();
  }
}
</script>

<style scoped>
/* 样式与 AdminBlogsManageView 类似，可复用或提取到全局 */
.admin-role-manage-view { padding: 20px; }
.page-title-bar { margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid #e0e0e0; }
.page-title-bar h2 { font-size: 1.8em; color: #333; margin-top: 0; margin-bottom: 5px; display:flex; align-items:center; }
.page-title-bar h2 i { margin-right: 12px; color: #6f42c1; } /* 紫色图标 */
.page-title-bar p { font-size: 1em; color: #666; margin-bottom: 0; }

.content-card { background-color: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.08); }
.table-controls { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; flex-wrap: wrap; gap: 15px; }
.search-input, .filter-select { padding: 10px 12px; border: 1px solid #ced4da; border-radius: 5px; font-size: 0.9em; }
.search-input { min-width: 280px; }
.filter-select { background-color: white; }
.btn { padding: 8px 16px; border: none; border-radius: 5px; font-size: 0.9em; font-weight: 500; cursor: pointer; transition: background-color 0.2s ease; display:inline-flex; align-items:center; }
.btn i { margin-right: 6px; }
.btn-primary { background-color: #007bff; color: white; }
.btn-primary:hover { background-color: #0069d9; }

.users-table-container { overflow-x: auto; }
.users-table { width: 100%; border-collapse: collapse; font-size: 0.9em; }
.users-table th, .users-table td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #e9ecef; vertical-align: middle; }
.users-table th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
.users-table tbody tr:hover { background-color: #f1f3f5; }

.role-badge, .status-badge { padding: 4px 10px; border-radius: 12px; font-size: 0.85em; font-weight: bold; color: white; text-align:center; display:inline-block; min-width: 70px;}
.role-user { background-color: #28a745; } /* 绿色 */
.role-admin { background-color: #ffc107; color: #333; } /* 黄色 */
.role-boss { background-color: #dc3545; } /* 红色 */
.status-active { background-color: #17a2b8; } /* 青色 */
.status-inactive { background-color: #6c757d; } /* 灰色 */

.action-buttons .btn-icon { background: none; border: none; cursor: pointer; padding: 6px; margin: 0 4px; font-size: 1.1em; transition: color 0.2s ease; }
.action-buttons .btn-icon:disabled { color: #ccc; cursor: not-allowed; }
.btn-edit { color: #007bff; }
.btn-disable { color: #ffc107; }
.btn-enable { color: #28a745; }
.btn-delete { color: #dc3545; }
.btn-edit:hover:not(:disabled) { color: #0056b3; }
.btn-disable:hover:not(:disabled) { color: #e0a800; }
.btn-enable:hover:not(:disabled) { color: #1e7e34; }
.btn-delete:hover:not(:disabled) { color: #c82333; }

.text-center { text-align: center !important; padding: 20px; color: #6c757d; }
.pagination-controls { display: flex; justify-content: flex-end; align-items: center; margin-top: 25px; gap: 10px; font-size: 0.9em; }
.btn-pagination { background-color: #f8f9fa; border: 1px solid #dee2e6; color: #007bff; padding: 6px 12px; border-radius:4px; }
.btn-pagination:disabled { color: #6c757d; cursor: not-allowed; background-color: #e9ecef; }
.btn-pagination:not(:disabled):hover { background-color: #e2e6ea; }

/* Modal Styles */
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1050;}
.modal-content { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); width: 90%; max-width: 500px; }
.modal-content h3 { margin-top: 0; margin-bottom: 20px; font-size:1.4em; color:#333; }
.modal-actions { margin-top: 25px; display: flex; justify-content: flex-end; gap: 10px; }
.modal-actions .btn-secondary { background-color: #6c757d; color:white; }
.modal-actions .btn-secondary:hover { background-color: #5a6268; }
</style>
    