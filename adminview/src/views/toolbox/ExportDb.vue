<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>数据库数据导出</span></div>
      <p>点击按钮将导出数据库备份。</p>
      <el-button type="danger">立即导出</el-button>
    </el-card>
  </div>
</template>
<script>
export default { name: 'ExportDb' }
</script>