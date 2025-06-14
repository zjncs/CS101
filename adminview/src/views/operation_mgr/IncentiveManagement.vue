<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>用户激励管理</span></div>
      <el-form label-width="120px" style="width: 500px;">
        <el-form-item label="签到积分">
          <el-input-number v-model="settings.signInCredits" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="发帖积分">
          <el-input-number v-model="settings.postBlogCredits" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="评论积分">
          <el-input-number v-model="settings.commentCredits" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">保存设置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
export default { name: 'IncentiveManagement', data(){ return { settings: { signInCredits: 5, postBlogCredits: 10, commentCredits: 2 } } }}
</script>
