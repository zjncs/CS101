<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>轮播图管理</span></div>
      <el-button type="primary" icon="el-icon-plus" @click="handleCreate" style="margin-bottom: 20px;">新增轮播图</el-button>
      <el-table :data="list" border>
        <el-table-column label="预览" width="120"><template slot-scope="{row}"><el-image style="width: 100px; height: 50px" :src="row.img" fit="cover"></el-image></template></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="link" label="跳转链接"></el-table-column>
        <el-table-column prop="time" label="定时更换时间"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default { name: 'BannerManagement', data() { return { list: [{id: 1, title: '夏日特饮', img: 'https://placehold.co/600x300/a2d2ff/FFF', link: '/promotion/1', time: '2025-06-01 00:00:00' }]}}, methods: { handleCreate(){}, handleEdit(row){ console.log('edit', row) }} }
</script>
