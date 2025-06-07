<template>
  <div class="edit-profile-view container">
    <div class="page-header">
      <h1><i class="fas fa-user-edit"></i> 编辑个人资料</h1>
    </div>

    <form @submit.prevent="saveProfile" class="profile-form card">
      <div class="form-row">
        <div class="form-group avatar-group">
          <label>头像:</label>
          <div class="avatar-edit-container">
            <img :src="profileData.avatarUrl || 'https://placehold.co/150x150/EFEFEF/AAAAAA?text=Avatar'" alt="Avatar" class="form-avatar-preview">
            <input type="file" @change="onAvatarChange" id="avatarUpload" hidden>
            <label for="avatarUpload" class="btn btn-sm">更换头像</label>
          </div>
        </div>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label for="username">用户名:</label>
          <input type="text" id="username" v-model="profileData.name" required>
        </div>
        <div class="form-group">
          <label for="email">邮箱:</label>
          <input type="email" id="email" v-model="profileData.email" required>
        </div>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label for="phone">手机号:</label>
          <input type="tel" id="phone" v-model="profileData.phone">
        </div>
        <div class="form-group">
          <label for="campus">所在校区:</label>
          <select id="campus" v-model="profileData.campus">
            <option value="weijinlu">卫津路校区</option>
            <option value="beiyangyuan">北洋园校区</option>
          </select>
        </div>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label for="gender">性别:</label>
          <select id="gender" v-model="profileData.gender">
            <option value="male">男</option>
            <option value="female">女</option>
            <option value="secret">保密</option>
          </select>
        </div>
        <div class="form-group">
          <label for="birthday">生日:</label>
          <input type="date" id="birthday" v-model="profileData.birthday">
        </div>
      </div>

      <div class="form-group">
        <label for="bio">个人简介:</label>
        <textarea id="bio" v-model="profileData.bio" rows="4" placeholder="一句话介绍一下自己吧"></textarea>
      </div>

      <hr>

      <h3 class="password-change-title">修改密码</h3>
      <div class="form-row">
        <div class="form-group">
          <label for="currentPassword">当前密码:</label>
          <input type="password" id="currentPassword" v-model="passwordData.current" placeholder="如需修改密码，请填写">
        </div>
        <div class="form-group">
          <label for="newPassword">新密码:</label>
          <input type="password" id="newPassword" v-model="passwordData.new" placeholder="输入新密码">
        </div>
      </div>

      <div class="form-actions">
        <router-link :to="{ name: 'UserProfileHome' }" class="btn btn-secondary">取消</router-link>
        <button type="submit" class="btn btn-primary" :disabled="saving">
          <i class="fas fa-spinner fa-spin" v-if="saving"></i>
          <span v-else>保存更改</span>
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'EditProfileView',
  data() {
    return {
      saving: false,
      // 模拟从API获取的当前用户信息
      profileData: {
        name: '',
        email: 'user@example.com',
        phone: '13812345678',
        avatarUrl: 'https://placehold.co/150x150/007BFF/FFFFFF?text=TJU',
        campus: 'weijinlu',
        gender: 'secret',
        birthday: '2002-08-15',
        bio: '一个热爱美食和生活的天大人。'
      },
      passwordData: {
        current: '',
        new: ''
      }
    };
  },
  methods: {
    onAvatarChange(event) {
      const file = event.target.files[0];
      if (file) {
        // 创建本地预览URL，实际应用中需要上传到服务器
        this.profileData.avatarUrl = URL.createObjectURL(file);
        console.log("选择了新头像:", file);
      }
    },
    saveProfile() {
      this.saving = true;
      console.log('保存个人信息:', this.profileData);
      if (this.passwordData.current && this.passwordData.new) {
        console.log('同时尝试修改密码');
        if (this.passwordData.new.length < 6) {
          alert('新密码长度不能少于6位！');
          this.saving = false;
          return;
        }
        // 此处应有API验证旧密码和更新新密码的逻辑
      }
      // 模拟API调用
      setTimeout(() => {
        alert('个人资料已更新！');
        localStorage.setItem('username', this.profileData.name); // 更新本地存储的用户名
        this.saving = false;
        this.$router.push({ name: 'UserProfileHome' });
      }, 1500);
    }
  },
  created() {
    // 实际项目中，在这里通过API获取当前登录用户的完整信息
    this.profileData.name = localStorage.getItem('username') || '天大美食家';
  }
}
</script>

<style scoped>
.edit-profile-view { padding-top: 20px; padding-bottom: 40px; }
.page-header { text-align: center; margin-bottom: 30px; }
.page-header h1 { font-size: 2.2em; color: #333; }
.page-header h1 i { margin-right: 12px; color: #007bff; }

.profile-form.card { max-width: 800px; margin: 0 auto; background-color: #fff; padding: 35px; border-radius: 8px; box-shadow: 0 3px 15px rgba(0,0,0,0.08); }
.form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 25px; }
@media (max-width: 768px) { .form-row { grid-template-columns: 1fr; } }
.form-group { margin-bottom: 20px; }
.form-group label { display: block; font-weight: bold; margin-bottom: 8px; color: #495057; font-size: 0.9em; }
.profile-form input, .profile-form select, .profile-form textarea { width: 100%; padding: 12px 15px; border: 1px solid #ced4da; border-radius: 5px; box-sizing: border-box; font-size: 1em; }
.profile-form textarea { resize: vertical; }

.avatar-group label { text-align: center; width: 100%;}
.avatar-edit-container { display: flex; flex-direction: column; align-items: center; gap: 15px; }
.form-avatar-preview { width: 150px; height: 150px; border-radius: 50%; object-fit: cover; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
.btn.btn-sm { padding: 6px 12px; font-size: 0.9em; border-radius: 4px; border: 1px solid #ced4da; background: #fff; cursor:pointer; }
hr { border: 0; border-top: 1px solid #eee; margin: 30px 0; }
.password-change-title { font-size: 1.3em; margin-bottom: 15px; }

.form-actions { display: flex; justify-content: flex-end; gap: 15px; margin-top: 30px; }
.btn { padding: 10px 25px; border-radius: 5px; font-size: 1em; font-weight: 500; cursor: pointer; text-decoration: none; display: inline-flex; align-items: center; justify-content: center; }
.btn-primary { background-color: #007bff; color: white; border: none; }
.btn-primary:hover:not(:disabled) { background-color: #0056b3; }
.btn-primary:disabled { opacity: 0.7; cursor: not-allowed; }
.btn-secondary { background-color: transparent; color: #6c757d; border: 1px solid #6c757d; }
.btn-secondary:hover { background-color: #f8f9fa; }
</style>
