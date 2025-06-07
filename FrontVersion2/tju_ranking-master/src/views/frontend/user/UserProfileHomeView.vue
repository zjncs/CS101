<template>
  <div class="user-profile-home-view">
    <h3 class="section-title">最近动态</h3>
    <div v-if="loading" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载动态...</div>
    <ul v-else-if="activities.length > 0" class="activity-list">
      <li v-for="activity in activities" :key="activity.id" class="activity-item">
        <i :class="['activity-icon', activity.type]"></i>
        <div class="activity-text">
          <p>
            {{ activity.actionText }}
            <router-link :to="activity.targetLink">{{ activity.targetName }}</router-link>
          </p>
          <span class="activity-time">{{ formatDate(activity.time) }}</span>
        </div>
      </li>
    </ul>
    <div v-else class="no-data">暂无最新动态。</div>
  </div>
</template>

<script>
export default {
  name: 'UserProfileHomeView',
  data() {
    return {
      loading: true,
      activities: [], // 模拟动态数据
    };
  },
  methods: {
    formatDate(dateString) {
      // 更友好的时间格式，例如 "几分钟前", "几小时前"
      // 此处简化为toLocaleString
      return new Date(dateString).toLocaleString('zh-CN');
    },
    fetchActivities() {
      this.loading = true;
      // 模拟API调用
      setTimeout(() => {
        this.activities = [
          { id: 1, type: 'comment', actionText: '评论了菜品', targetName: '红烧牛肉面', targetLink: { name: 'DishDetail', params: { id: 10101 } }, time: '2025-06-05T14:30:00Z' },
          { id: 2, type: 'favorite', actionText: '收藏了菜品', targetName: '叉烧饭', targetLink: { name: 'DishDetail', params: { id: 20301 } }, time: '2025-06-05T11:00:00Z' },
          { id: 3, type: 'comment', actionText: '评论了博客', targetName: '北洋园梅园食堂早餐攻略', targetLink: { name: 'BlogDetail', params: { id: 102 } }, time: '2025-06-04T22:15:00Z' },
          { id: 4, type: 'favorite', actionText: '收藏了菜品', targetName: '鱼香肉丝', targetLink: { name: 'DishDetail', params: { id: 10201 } }, time: '2025-06-04T12:05:00Z' },
        ];
        this.loading = false;
      }, 800);
    }
  },
  created() {
    this.fetchActivities();
  }
}
</script>

<style scoped>
.section-title { font-size: 1.4em; color: #333; margin-top: 0; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid #f0f0f0; }
.loading-placeholder, .no-data { text-align: center; padding: 30px; color: #888; }
.activity-list { list-style: none; padding: 0; margin: 0; }
.activity-item { display: flex; align-items: flex-start; padding: 15px 0; border-bottom: 1px solid #f5f5f5; }
.activity-item:last-child { border-bottom: none; }
.activity-icon {
  width: 36px; height: 36px; border-radius: 50%;
  display: flex; align-items: center; justify-content: center;
  color: white; margin-right: 15px; flex-shrink: 0; font-size: 1.1em;
}
.activity-icon.comment { background-color: #28a745; } /* 绿色 */
.activity-icon.favorite { background-color: #fd7e14; } /* 橙色 */
.activity-icon.like { background-color: #007bff; } /* 蓝色 */
.activity-text p { margin: 0 0 5px 0; font-size: 1em; color: #555; }
.activity-text p a { color: #007bff; text-decoration: none; font-weight: 500; }
.activity-text p a:hover { text-decoration: underline; }
.activity-time { font-size: 0.85em; color: #999; }
</style>
