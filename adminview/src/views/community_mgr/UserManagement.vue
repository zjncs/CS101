<template>
  <div class="app-container">
    <el-card><div slot="header"><span>普通用户管理</span></div>
      <el-table :data="list" border>
        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column prop="nickname" label="昵称"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column label="状态"><template slot-scope="{row}"><el-tag :type="row.status === 0 ? 'success' : 'danger'">{{ row.status === 0 ? '正常' : '禁用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作"><template slot-scope="{row}"><el-button size="mini" :type="row.status===0 ? 'danger' : 'success'" @click="toggleStatus(row)">{{ row.status === 0 ? '禁用' : '启用' }}</el-button></template></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'UserManagement', data() { return { list: [{id: 101, nickname: 'TJU_Foodie', email: 'test@tju.edu.cn', status: 0}]} }, methods: { toggleStatus(row) { row.status = row.status === 0 ? 1 : 0 } } }
</script>