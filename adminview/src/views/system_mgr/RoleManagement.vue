<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>角色管理</span></div>
      <el-table :data="rolesList" border>
        <el-table-column prop="name" label="角色名称"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑权限</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'RoleManagement', data() { return { rolesList: [{name: '内容管理员', description: '管理校区、食堂、菜品等核心数据'}, {name: '社区管理员', description: '审核用户发布的内容，管理用户'}, {name: '运营管理员', description: '负责平台活动、公告等运营工作'} ] } }, methods: { handleEdit(row){ console.log('edit', row) } } }
</script>