<template>
  <div class="app-container">
    <el-card><div slot="header"><span>系统公告管理</span></div>
      <el-button type="primary" icon="el-icon-plus" @click="handleCreate" style="margin-bottom: 20px;">发布新公告</el-button>
      <el-table :data="list" border>
        <el-table-column prop="title" label="公告标题"></el-table-column>
        <el-table-column prop="publishTime" label="定时发送时间"></el-table-column>
        <el-table-column label="操作"><template slot-scope="scope"><el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button></template></el-table-column>
      </el-table>
      <el-dialog title="编辑公告" :visible.sync="dialogVisible"><el-form :model="temp"><el-form-item label="标题"><el-input v-model="temp.title"></el-input></el-form-item><el-form-item label="内容"><el-input type="textarea" :rows="5" v-model="temp.content"></el-input></el-form-item><el-form-item label="定时发送"><el-date-picker v-model="temp.publishTime" type="datetime"></el-date-picker></el-form-item></el-form><div slot="footer"><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="submit">确认</el-button></div></el-dialog>
    </el-card>
  </div>
</template>
<script>
export default { name: 'NoticeManagement', data() { return { list: [{id: 1, title: '关于五一假期食堂开放时间的通知', publishTime: '2025-04-28 10:00:00'}], dialogVisible: false, temp: {} } }, methods: { handleCreate(){ this.temp={}; this.dialogVisible=true; }, handleEdit(row){ this.temp={...row}; this.dialogVisible=true; }, submit(){} } }
</script>
