<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>敏感词管理</span></div>
      <el-input type="textarea" :rows="15" placeholder="请输入敏感词，每行一个" v-model="words"></el-input>
      <el-button type="primary" @click="saveWords" style="margin-top: 20px;">保存设置</el-button>
    </el-card>
  </div>
</template>
<script>
export default { name: 'SensitiveWordsManagement', data(){ return { words: '广告\n涉政\n辱骂\n...'} }, methods: { saveWords() { this.$message.success('保存成功！') } } }
</script>