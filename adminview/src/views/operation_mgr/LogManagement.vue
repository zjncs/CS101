<template>
  <div class="app-container">
    <el-card><div slot="header"><span>系统日志管理</span></div>
      <el-table :data="logs" height="600" border><el-table-column type="index" width="50"></el-table-column><el-table-column prop="time" label="时间" width="200"></el-table-column><el-table-column prop="level" label="级别" width="100"><template slot-scope="{row}"><el-tag :type="row.level === 'ERROR' ? 'danger' : 'info'">{{row.level}}</el-tag></template></el-table-column><el-table-column prop="message" label="日志信息"></el-table-column><el-table-column label="操作" width="150"><template slot-scope="{row}"><el-button v-if="row.level==='ERROR'" type="warning" size="mini">反馈给开发者</el-button></template></el-table-column></el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'LogManagement', data() { return { logs: [{time: '2025-06-15 11:00:05', level: 'ERROR', message: 'NullPointerException at a.b.c.Service:123'}, {time: '2025-06-15 11:00:01', level: 'INFO', message: 'User admin logged in successfully.'}]}}}
</script>