<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>人员管理</span></div>
      <el-button type="primary" icon="el-icon-plus" @click="handleCreate" style="margin-bottom: 20px;">新增人员</el-button>
      <el-table :data="list" border fit>
        <el-table-column prop="username" label="管理员账号"></el-table-column>
        <el-table-column prop="name" label="姓名/职位"></el-table-column>
        <el-table-column label="角色">
          <template slot-scope="{row}">
            <el-tag v-for="role in row.roles" :key="role" style="margin-right: 5px;">{{ role }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleUpdate(scope.row)">修改角色</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'PersonnelManagement', data() { return { list: [ {id: 2, username: 'content_admin', name: '张三', roles: ['内容管理员']} ] } }, methods: { handleCreate(){ console.log('create') }, handleUpdate(row){ console.log('update', row) }, handleDelete(row){ console.log('delete', row) } } }
</script>