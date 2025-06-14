<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>校区管理</span></div>
      <el-button type="primary" icon="el-icon-plus" @click="handleCreate" style="margin-bottom: 20px;">新增校区</el-button>
      <el-table :data="list" border fit>
        <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
        <el-table-column prop="name" label="校区名称"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default {
  name: "CampusManagement",
  data() {
    return {
      list: [
        {id: 1, name: '卫津路校区', address: '天津市南开区卫津路92号'},
        {id: 2, name: '北洋园校区', address: '天津市津南区雅观路135号'}
      ]
    }
  },
  methods: {
    // 这个方法不需要参数
    handleCreate() {
      console.log('准备新增...');
      // 后续会在这里写弹出对话框的逻辑
    },
    // 这个方法必须接收 row 参数，才能知道要编辑哪一行
    handleEdit(row) {
      console.log('准备编辑:', row);
      // 后续会用 row.id 请求后端数据，并用 row 的内容填充表单
    },
    // 这个方法同样必须接收 row 参数，才能知道要删除哪一行
    handleDelete(row) {
      console.log('准备删除:', row);
      // 后续会用 row.id 请求后端接口进行删除
    }
  }
}
</script>
