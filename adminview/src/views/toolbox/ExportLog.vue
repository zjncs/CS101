<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>操作日志导出</span></div>
      <el-date-picker type="daterange" placeholder="选择日期范围"></el-date-picker>
      <el-button type="primary" style="margin-left: 10px;">查询并导出</el-button>
    </el-card>
  </div>
</template>
<script>
export default { name: 'ExportLog' }
</script>