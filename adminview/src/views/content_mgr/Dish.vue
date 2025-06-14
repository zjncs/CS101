<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>菜品管理</span></div>
      <el-button type="primary" icon="el-icon-plus" @click="handleCreate" style="margin-bottom: 20px;">新增菜品</el-button>
      <el-table :data="list" border fit>
        <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
        <el-table-column prop="name" label="菜品名称"></el-table-column>
        <el-table-column prop="stallName" label="所属窗口"></el-table-column>
        <el-table-column prop="price" label="价格(元)" width="120" align="center"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope"><el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button><el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button></template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: "DishManagement", data() { return { list: [ {id: 101, name: '油泼面', stallName: '山西面馆', price: '12.00'}, {id: 102, name: '自选香锅', stallName: '麻辣香锅', price: '25.50'} ] } }, methods: { handleCreate(){ console.log('create') }, handleEdit(row){ console.log('edit', row) }, handleDelete(row){ console.log('delete', row) } } }
</script>
