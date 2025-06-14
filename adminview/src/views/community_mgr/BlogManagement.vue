<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>博客发表管理</span></div>
      <el-table :data="list" border>
        <el-table-column prop="author" label="作者"></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="content" label="内容摘要"></el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="handleApprove(scope.row)">通过</el-button>
            <el-button size="mini" type="danger" @click="handleReject(scope.row)">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'BlogManagement', data() { return { list: [ { id:1, author: 'UserC', title: '探店北洋园新开的奶茶店', content: '今天去喝了...' } ] } }, methods: { handleApprove(row){ console.log('approve', row) }, handleReject(row){ console.log('reject', row) } } }
</script>
