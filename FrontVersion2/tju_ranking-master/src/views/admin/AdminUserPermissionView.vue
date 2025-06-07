<template>
  <div class="admin-user-permission-view">
    <div class="page-title-bar">
      <h2><i class="fas fa-user-shield"></i> 用户权限精细管理</h2>
      <p>配置不同角色可访问的功能模块与具体操作权限 (此页面为高级功能，主要由平台管理员操作)。</p>
    </div>

    <div class="content-card">
      <div class="permission-management-area">
        <div class="role-selection-area">
          <h3>选择角色以配置权限:</h3>
          <select v-model="selectedRoleForPermissions" @change="loadPermissionsForRole" class="filter-select">
            <option value="user">普通用户 (user)</option>
            <option value="admin">后台管理员 (admin)</option>
            <option value="boss">平台管理员 (boss) - (通常拥有所有权限)</option>
            <!-- 更多自定义角色可在此添加 -->
          </select>
        </div>

        <div v-if="selectedRoleForPermissions" class="permissions-config-area">
          <h4>正在为角色 “{{ getRoleName(selectedRoleForPermissions) }}” 配置权限:</h4>
          <div v-if="loadingPermissions" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载权限数据...</div>
          <div v-else class="permissions-grid">
            <div v-for="permissionGroup in availablePermissions" :key="permissionGroup.groupName" class="permission-group card">
              <h5>{{ permissionGroup.groupName }}</h5>
              <ul>
                <li v-for="permission in permissionGroup.permissions" :key="permission.key">
                  <label>
                    <input
                        type="checkbox"
                        :value="permission.key"
                        v-model="rolePermissions[selectedRoleForPermissions]"
                        :disabled="selectedRoleForPermissions === 'boss'"
                    >
                    {{ permission.description }}
                  </label>
                </li>
              </ul>
            </div>
            <p v-if="availablePermissions.length === 0" class="no-data">暂无可配置的权限项。</p>
          </div>
          <div class="form-actions" v-if="selectedRoleForPermissions !== 'boss' && availablePermissions.length > 0">
            <button @click="saveRolePermissions" class="btn btn-primary">
              <i class="fas fa-save"></i> 保存对 “{{ getRoleName(selectedRoleForPermissions) }}” 角色的权限更改
            </button>
          </div>
          <div v-if="selectedRoleForPermissions === 'boss'" class="boss-permission-note">
            <i class="fas fa-info-circle"></i> 平台管理员 (Boss) 默认拥有所有权限，不可在此修改。
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminUserPermissionView',
  data() {
    return {
      selectedRoleForPermissions: 'user', // 默认选中用户角色
      loadingPermissions: false,
      // 模拟可用的权限点，实际应从后端获取
      availablePermissions: [
        {
          groupName: '前台用户功能',
          permissions: [
            { key: 'view_dishes', description: '查看菜品列表' },
            { key: 'view_canteens', description: '查看食堂列表' },
            { key: 'post_blog', description: '发表博客' },
            { key: 'post_comment', description: '发表评论' },
            { key: 'edit_own_blog', description: '编辑自己的博客' },
            { key: 'delete_own_comment', description: '删除自己的评论' },
            { key: 'favorite_item', description: '收藏内容' },
            { key: 'like_item', description: '点赞内容' },
          ]
        },
        {
          groupName: '后台管理 - 内容审核',
          permissions: [
            { key: 'review_blogs', description: '审核博客' },
            { key: 'review_comments', description: '审核评论' },
            { key: 'manage_reports', description: '处理举报' },
          ]
        },
        {
          groupName: '后台管理 - 数据管理',
          permissions: [
            { key: 'manage_canteens', description: '管理食堂信息' },
            { key: 'manage_windows', description: '管理窗口信息' },
            { key: 'manage_dishes', description: '管理菜品信息' },
          ]
        },
        // 更多权限组...
      ],
      // 模拟各角色已有的权限，实际应从后端获取
      rolePermissions: {
        user: ['view_dishes', 'view_canteens', 'post_blog', 'post_comment', 'edit_own_blog', 'favorite_item', 'like_item'],
        admin: ['view_dishes', 'view_canteens', 'review_blogs', 'review_comments', 'manage_reports', 'manage_canteens', 'manage_windows', 'manage_dishes'],
        boss: [] // Boss 角色在前端通常表现为拥有所有权限，或由后端硬编码
      }
    };
  },
  computed: {
    currentUserRole() {
      return this.$auth ? this.$auth.getUserRole() : '';
    }
  },
  methods: {
    getRoleName(roleKey) {
      const names = { user: '普通用户', admin: '后台管理员', boss: '平台管理员' };
      return names[roleKey] || roleKey;
    },
    loadPermissionsForRole() {
      this.loadingPermissions = true;
      console.log(`加载角色 ${this.selectedRoleForPermissions} 的权限...`);
      // 模拟API调用获取该角色的权限数据，并更新 this.rolePermissions[this.selectedRoleForPermissions]
      // 以及 this.availablePermissions (如果权限项也是动态的)
      setTimeout(() => {
        if (!this.rolePermissions[this.selectedRoleForPermissions]) {
          // 如果是新角色或未初始化的角色，给一个空数组
          this.$set(this.rolePermissions, this.selectedRoleForPermissions, []);
        }
        this.loadingPermissions = false;
      }, 500);
    },
    saveRolePermissions() {
      if (this.selectedRoleForPermissions === 'boss') {
        alert("平台管理员(Boss)的权限不能通过此页面修改。");
        return;
      }
      this.loadingPermissions = true; // 可以用一个独立的 saving 状态
      const permissionsToSave = this.rolePermissions[this.selectedRoleForPermissions];
      console.log(`保存角色 ${this.selectedRoleForPermissions} 的权限:`, permissionsToSave);
      // 模拟API调用
      setTimeout(() => {
        this.loadingPermissions = false;
        alert(`角色 “${this.getRoleName(this.selectedRoleForPermissions)}” 的权限已更新！`);
      }, 1000);
    }
  },
  created() {
    if (this.currentUserRole !== 'boss') {
      alert('您沒有權限訪問此頁面。');
      this.$router.push({name: 'AdminDashboard'});
      return;
    }
    this.loadPermissionsForRole(); // 加载默认选中角色的权限
  }
}
</script>

<style scoped>
.admin-user-permission-view { padding: 20px; }
.page-title-bar { margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid #e0e0e0; }
.page-title-bar h2 { font-size: 1.8em; color: #333; margin-top: 0; margin-bottom: 5px; display:flex; align-items:center; }
.page-title-bar h2 i { margin-right: 12px; color: #9b59b6; } /* 紫色图标，与角色管理区分 */
.page-title-bar p { font-size: 1em; color: #666; margin-bottom: 0; }

.content-card { background-color: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.08); }

.role-selection-area { margin-bottom: 25px; }
.role-selection-area h3 { font-size: 1.3em; margin-bottom:10px; color:#444;}
.filter-select { padding: 10px 12px; border: 1px solid #ced4da; border-radius: 5px; font-size: 1em; min-width: 250px; background-color:white;}

.permissions-config-area h4 { font-size: 1.2em; margin-bottom: 15px; color:#333; }
.loading-placeholder { text-align:center; padding:20px; color:#6c757d; }
.permissions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
  margin-bottom: 25px;
}
.permission-group.card {
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 6px;
  border: 1px solid #e9ecef;
}
.permission-group h5 {
  font-size: 1.1em;
  color: #007bff;
  margin-top: 0;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 1px solid #dee2e6;
}
.permission-group ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
.permission-group li {
  margin-bottom: 8px;
}
.permission-group li label {
  font-size: 0.95em;
  color: #343a40;
  cursor: pointer;
  display: flex;
  align-items: center;
}
.permission-group li input[type="checkbox"] {
  margin-right: 8px;
  accent-color: #007bff;
  transform: scale(1.1);
}
.permission-group li input[type="checkbox"]:disabled {
  accent-color: #ccc;
  cursor: not-allowed;
}
.permission-group li label:has(input[type="checkbox"]:disabled) { /* 给禁用的标签一些不同样式 */
  color: #888;
}


.form-actions { text-align: right; margin-top: 20px; }
.btn { padding: 10px 20px; border: none; border-radius: 5px; font-size: 1em; font-weight: 500; cursor: pointer; transition: background-color 0.2s ease; display:inline-flex; align-items:center; }
.btn i { margin-right: 8px; }
.btn-primary { background-color: #007bff; color: white; }
.btn-primary:hover { background-color: #0069d9; }

.no-data {
  text-align: center;
  padding: 20px;
  color: #6c757d;
  font-style: italic;
}
.boss-permission-note {
  background-color: #e9f7fd;
  border: 1px solid #bce8f1;
  color: #31708f;
  padding: 15px;
  border-radius: 5px;
  margin-top: 15px;
  font-size: 0.95em;
}
.boss-permission-note i { margin-right: 8px;}
</style>
    