<template>
  <div class="app-container">
    <el-card><div slot="header"><span>用户举报管理</span></div>
      <el-table :data="list" border>
        <el-table-column prop="reporter" label="举报人"></el-table-column>
        <el-table-column prop="reported" label="被举报对象"></el-table-column>
        <el-table-column prop="reason" label="举报原因"></el-table-column>
        <el-table-column prop="time" label="举报时间"></el-table-column>
        <el-table-column label="状态"><template slot-scope="{row}"><el-tag :type="row.status === '待办' ? 'danger' : 'success'">{{ row.status }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="150" align="center"><template slot-scope="{row}"><el-button size="mini" v-if="row.status==='待办'" @click="handleView(row)">标记为已查看</el-button><span v-else>--</span></template></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'ReportManagement', data() { return { list: [ { id:1, reporter: 'UserA', reported: 'UserB的评论', reason: '发布不当言论', time: '2025-06-15 10:30', status: '待办' }, { id:2, reporter: 'UserC', reported: 'UserD的博客', reason: '广告骚扰', time: '2025-06-14 11:00', status: '已查看' }] } }, methods: { handleView(row) { row.status = '已查看' } } }
</script>