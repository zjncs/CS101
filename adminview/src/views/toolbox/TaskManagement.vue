<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>定时任务管理</span></div>
      <el-table :data="tasks" border>
        <el-table-column prop="name" label="任务名称"></el-table-column>
        <el-table-column prop="cron" label="CRON表达式"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template>
            <el-button type="primary" size="mini">立即执行</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'TaskManagement', data() { return { tasks: [{name: '每日数据统计', cron: '0 0 1 * * ?'}] } } }
</script>
