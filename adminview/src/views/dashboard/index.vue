<template>
  <div class="dashboard-container">
    <!-- 顶部欢迎和统计 -->
    <el-card shadow="never">
      <el-row type="flex" justify="space-between" align="middle">
        <div class="welcome-section">
          <h2>下午好, {{ name }}, 祝您开心每一天！</h2>
          <p>您当前的角色是: <el-tag v-for="role in roles" :key="role" size="small" style="margin-right: 5px;">{{ role }}</el-tag></p>
        </div>
        <div class="stats-section">
          <div class="stat-item">
            <div class="stat-title">待办事项</div>
            <div class="stat-value">3 / 10</div>
          </div>
          <div class="stat-item">
            <div class="stat-title">项目总数</div>
            <div class="stat-value">8</div>
          </div>
        </div>
      </el-row>
    </el-card>

    <!-- 快捷操作和最近活动 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card>
          <div slot="header"><span>快捷操作</span></div>
          <div class="quick-access">
            <el-button @click="$router.push('/system-management/content/canteen')">食堂管理</el-button>
            <el-button @click="$router.push('/system-management/community/audit')">审核中心</el-button>
            <el-button @click="$router.push('/system-management/operation/notice')">发布公告</el-button>
            <el-button @click="$router.push('/system-management/platform/personnel')">人员管理</el-button>
            <el-button @click="$router.push('/toolbox/statistics')">数据大屏</el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <div slot="header"><span>最近操作</span></div>
          <el-timeline :reverse="false" style="padding-left: 10px;">
            <el-timeline-item
                v-for="(activity, index) in activities"
                :key="index"
                :timestamp="activity.timestamp"
                :color="activity.color">
              {{activity.content}}
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  data() {
    return {
      activities: [
        { content: '审核通过了用户 "TJU_Foodie" 的博客', timestamp: '2小时前', color: '#0bbd87' },
        { content: '更新了 "夏日特饮" 轮播图的上线时间', timestamp: '昨天', color: '' },
        { content: '禁用了违规用户 "小广告君"', timestamp: '2025-06-12', color: '' },
      ]
    }
  },
  computed: {
    ...mapGetters(['name', 'roles'])
  }
}
</script>
<style lang="scss" scoped>
.dashboard-container { padding: 20px; }
.welcome-section {
  h2 { margin: 0; font-size: 20px; font-weight: normal; }
  p { margin-top: 10px; color: #888; }
}
.stats-section { display: flex; }
.stat-item {
  margin-left: 40px; text-align: right;
  .stat-title { color: #888; font-size: 14px; }
  .stat-value { font-size: 24px; font-weight: bold; }
}
.quick-access .el-button { margin: 0 10px 10px 0; }
</style>