<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>食堂管理</span></div>
      <el-button type="primary" icon="el-icon-plus" @click="handleCreate" style="margin-bottom: 20px;">新增食堂</el-button>
      <el-table :data="list" border fit>
        <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
        <el-table-column prop="name" label="食堂名称"></el-table-column>
        <el-table-column prop="campusName" label="所属校区"></el-table-column>
        <el-table-column prop="typeName" label="食堂类型"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope"><el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button><el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button></template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: "CanteenManagement", data() { return { list: [ {id: 1, name: '学一食堂', campusName: '卫津路校区', typeName: '基本食堂'}, {id: 2, name: '留学生食堂', campusName: '北洋园校区', typeName: '风味食堂'} ] } }, methods: { handleCreate(){ console.log('create') }, handleEdit(row){ console.log('edit', row) }, handleDelete(row){ console.log('delete', row) } } }
</script>
