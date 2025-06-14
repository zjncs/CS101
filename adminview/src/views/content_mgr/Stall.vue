<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>窗口管理</span></div>
      <el-button type="primary" icon="el-icon-plus" @click="handleCreate" style="margin-bottom: 20px;">新增窗口</el-button>
      <el-table :data="list" border fit>
        <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
        <el-table-column prop="name" label="窗口名称"></el-table-column>
        <el-table-column prop="canteenName" label="所属食堂"></el-table-column>
        <el-table-column prop="typeName" label="窗口类型"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope"><el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button><el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button></template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: "StallManagement", data() { return { list: [ {id: 1, name: '山西面馆', canteenName: '学一食堂', typeName: '面食'}, {id: 2, name: '麻辣香锅', canteenName: '学一食堂', typeName: '麻辣烫/香锅'} ] } }, methods: { handleCreate(){ console.log('create') }, handleEdit(row){ console.log('edit', row) }, handleDelete(row){ console.log('delete', row) } } }
</script>
