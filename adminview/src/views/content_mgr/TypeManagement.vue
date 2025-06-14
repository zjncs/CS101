<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="食堂类型管理">
        <el-button type="primary" size="small" @click="handleAdd('canteen')" style="margin-bottom: 15px;">新增食堂类型</el-button>
        <el-table :data="canteenTypes" border>
          <el-table-column prop="name" label="类型名称"></el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.row, 'canteen')">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.row, 'canteen')">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="窗口类型管理">
        <el-button type="primary" size="small" @click="handleAdd('stall')" style="margin-bottom: 15px;">新增窗口类型</el-button>
        <el-table :data="stallTypes" border>
          <el-table-column prop="name" label="类型名称"></el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.row, 'stall')">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.row, 'stall')">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default { name: 'TypeManagement', data() { return { canteenTypes: [{ id: 1, name: '基本食堂' }, { id: 2, name: '风味食堂' }], stallTypes: [{ id: 1, name: '面食' }, { id: 2, name: '盖饭' }] } }, methods: { handleAdd(type){ console.log('add', type) }, handleEdit(row, type){ console.log('edit', row, type) }, handleDelete(row, type){ console.log('delete', row, type) } } }
</script>