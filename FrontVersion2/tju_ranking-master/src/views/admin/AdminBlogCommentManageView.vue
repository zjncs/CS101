<template>
  <div class="admin-blog-comment-manage-view">
    <div class="page-title-bar">
      <h2><i class="fas fa-comments-dollar"></i> 博客、评论与举报管理</h2>
      <p>审核用户发布的博客、评论，并处理相关举报信息。</p>
    </div>

    <div class="management-tabs">
      <button :class="{ active: currentTab === 'pendingBlogs' }" @click="changeTab('pendingBlogs')">
        <i class="fas fa-file-alt"></i> 待审核博客 ({{ pendingBlogs.length }})
      </button>
      <button :class="{ active: currentTab === 'pendingComments' }" @click="changeTab('pendingComments')">
        <i class="fas fa-comment-dots"></i> 待审核评论 ({{ pendingComments.length }})
      </button>
      <button :class="{ active: currentTab === 'reportedContent' }" @click="changeTab('reportedContent')">
        <i class="fas fa-flag"></i> 已举报内容 ({{ reportedItems.length }})
      </button>
      <button :class="{ active: currentTab === 'allBlogs' }" @click="changeTab('allBlogs')">
        <i class="fas fa-list-alt"></i> 所有博客管理
      </button>
    </div>

    <div class="content-card">
      <!-- 待审核博客 -->
      <div v-if="currentTab === 'pendingBlogs'" class="tab-content">
        <h3>待审核博客列表</h3>
        <div v-if="loading.pendingBlogs" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载中...</div>
        <div v-else-if="pendingBlogs.length === 0" class="no-data-table">暂无待审核的博客。</div>
        <table v-else class="data-table review-table">
          <thead>
          <tr>
            <th>标题</th>
            <th>作者</th>
            <th>提交时间</th>
            <th>预览内容</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="blog in pendingBlogs" :key="`blog-${blog.id}`">
            <td>{{ blog.title }}</td>
            <td>{{ blog.author }}</td>
            <td>{{ formatDate(blog.submittedAt) }}</td>
            <td>{{ truncateText(blog.content, 50) }} <a href="#" @click.prevent="viewContentDetails(blog, 'blog')">(查看全文)</a></td>
            <td class="action-buttons">
              <button class="btn-icon btn-approve" @click="approveItem(blog, 'blog')" title="通过"><i class="fas fa-check-circle"></i></button>
              <button class="btn-icon btn-reject" @click="rejectItem(blog, 'blog')" title="拒绝"><i class="fas fa-times-circle"></i></button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- 待审核评论 -->
      <div v-if="currentTab === 'pendingComments'" class="tab-content">
        <h3>待审核评论列表</h3>
        <div v-if="loading.pendingComments" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载中...</div>
        <div v-else-if="pendingComments.length === 0" class="no-data-table">暂无待审核的评论。</div>
        <table v-else class="data-table review-table">
          <thead>
          <tr>
            <th>评论内容</th>
            <th>评论者</th>
            <th>评论对象</th>
            <th>提交时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="comment in pendingComments" :key="`comment-${comment.id}`">
            <td>{{ truncateText(comment.text, 60) }} <a href="#" @click.prevent="viewContentDetails(comment, 'comment')">(查看全文)</a></td>
            <td>{{ comment.user }}</td>
            <td><a href="#" @click.prevent="viewTargetContent(comment.targetType, comment.targetId)">{{ comment.targetName }} ({{ comment.targetType === 'blog' ? '博客' : '菜品' }})</a></td>
            <td>{{ formatDate(comment.submittedAt) }}</td>
            <td class="action-buttons">
              <button class="btn-icon btn-approve" @click="approveItem(comment, 'comment')" title="通过"><i class="fas fa-check-circle"></i></button>
              <button class="btn-icon btn-reject" @click="rejectItem(comment, 'comment')" title="拒绝"><i class="fas fa-times-circle"></i></button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- 已举报内容 -->
      <div v-if="currentTab === 'reportedContent'" class="tab-content">
        <h3>已举报内容列表</h3>
        <div v-if="loading.reportedContent" class="loading-placeholder"><i class="fas fa-spinner fa-spin"></i> 加载中...</div>
        <div v-else-if="reportedItems.length === 0" class="no-data-table">暂无被举报的内容。</div>
        <table v-else class="data-table review-table">
          <thead>
          <tr>
            <th>举报内容</th>
            <th>类型</th>
            <th>举报理由</th>
            <th>举报人</th>
            <th>举报时间</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="report in reportedItems" :key="`report-${report.id}`">
            <td>{{ truncateText(report.reportedContentPreview, 50) }} <a href="#" @click.prevent="viewContentDetails(report.originalContent, report.contentType)">(查看详情)</a></td>
            <td>{{ report.contentType === 'blog' ? '博客' : '评论' }}</td>
            <td>{{ report.reason }}</td>
            <td>{{ report.reporter }}</td>
            <td>{{ formatDate(report.reportedAt) }}</td>
            <td class="action-buttons">
              <button class="btn-icon btn-view" @click="viewContentDetails(report.originalContent, report.contentType)" title="查看详情"><i class="fas fa-eye"></i></button>
              <button class="btn-icon btn-approve" @click="dismissReport(report.id)" title="忽略举报"><i class="fas fa-check"></i> 忽略</button>
              <button class="btn-icon btn-delete" @click="deleteReportedItem(report)" title="删除内容"><i class="fas fa-trash-alt"></i> 删除</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- 所有博客管理 (简化版，可以复用 AdminBlogsManageView 的部分逻辑) -->
      <div v-if="currentTab === 'allBlogs'" class="tab-content">
        <h3>所有博客管理</h3>
        <p class="placeholder-text">这里将展示所有博客的列表，并提供编辑、删除等操作 (类似之前的博客管理页面，但嵌入在此处)。</p>
        <!-- 实际可以引入一个博客列表组件 -->
      </div>
    </div>

    <!-- 内容详情模态框 -->
    <div v-if="showContentModal" class="modal-overlay" @click.self="closeContentModal">
      <div class="modal-content content-detail-modal">
        <h3>{{ contentModalTitle }}详情</h3>
        <div class="modal-body">
          <template v-if="viewingItemType === 'blog'">
            <h4>标题: {{ viewingItem.title }}</h4>
            <p><strong>作者:</strong> {{ viewingItem.author }}</p>
            <p><strong>提交时间:</strong> {{ formatDate(viewingItem.submittedAt) }}</p>
            <hr>
            <div class="content-preview" v-html="viewingItem.content"></div>
          </template>
          <template v-if="viewingItemType === 'comment'">
            <p><strong>评论者:</strong> {{ viewingItem.user }}</p>
            <p><strong>评论对象:</strong> {{ viewingItem.targetName }}</p>
            <p><strong>提交时间:</strong> {{ formatDate(viewingItem.submittedAt) }}</p>
            <hr>
            <p class="content-preview">{{ viewingItem.text }}</p>
          </template>
        </div>
        <div class="modal-actions">
          <button @click="closeContentModal" class="btn btn-secondary">关闭</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminBlogCommentManageView',
  data() {
    return {
      currentTab: 'pendingBlogs', //默认显示待审核博客
      loading: {
        pendingBlogs: true,
        pendingComments: true,
        reportedContent: true,
        allBlogs: true,
      },
      // 模拟数据
      allBlogsData: [ // 包含所有状态的博客
        { id: 1, title: '我的第一篇美食博客', author: '新手小红', content: '今天我去了学一食堂，尝了传说中的麻辣烫...', submittedAt: '2024-05-28T10:00:00Z', status: 'pending' },
        { id: 2, title: '校园奶茶测评大比拼！', author: '奶茶爱好者', content: '对比了校内外五家热门奶茶店的招牌产品...', submittedAt: '2024-05-27T14:30:00Z', status: 'pending' },
        { id: 3, title: '北洋园梅园食堂早餐推荐', author: '早八人', content: '梅园的豆腐脑和小笼包是绝配！', submittedAt: '2024-05-26T08:00:00Z', status: 'approved' },
      ],
      allCommentsData: [ // 包含所有状态的评论
        { id: 101, text: '这个菜品太好吃了，强烈推荐！', user: '用户A', targetType: 'dish', targetId: 10101, targetName: '红烧牛肉面', submittedAt: '2024-05-28T11:00:00Z', status: 'pending' },
        { id: 102, text: '博主写得很详细，赞一个！', user: '用户B', targetType: 'blog', targetId: 3, targetName: '北洋园梅园食堂早餐推荐', submittedAt: '2024-05-27T16:00:00Z', status: 'approved' },
        { id: 103, text: '我觉得一般般，没有说的那么好吃。', user: '用户C', targetType: 'dish', targetId: 20301, targetName: '叉烧饭', submittedAt: '2024-05-29T09:20:00Z', status: 'pending' },
      ],
      reportedItemsData: [
        { id: 201, contentType: 'blog', originalContentId: 1, reportedContentPreview: '我的第一篇美食博客...', reason: '内容不实，误导消费', reporter: '举报者X', reportedAt: '2024-05-29T10:00:00Z', originalContent: null /* 稍后填充 */ },
        { id: 202, contentType: 'comment', originalContentId: 103, reportedContentPreview: '我觉得一般般...', reason: '恶意差评', reporter: '举报者Y', reportedAt: '2024-05-29T11:30:00Z', originalContent: null },
      ],
      showContentModal: false,
      viewingItem: null,
      viewingItemType: '', // 'blog' or 'comment'
      contentModalTitle: '',
    };
  },
  computed: {
    pendingBlogs() {
      return this.allBlogsData.filter(blog => blog.status === 'pending');
    },
    pendingComments() {
      return this.allCommentsData.filter(comment => comment.status === 'pending');
    },
    reportedItems() {
      // 填充 originalContent，实际项目中可能在获取举报列表时后端就关联好了
      return this.reportedItemsData.map(report => {
        let original = null;
        if (report.contentType === 'blog') {
          original = this.allBlogsData.find(b => b.id === report.originalContentId);
        } else if (report.contentType === 'comment') {
          original = this.allCommentsData.find(c => c.id === report.originalContentId);
        }
        return { ...report, originalContent: original };
      });
    }
  },
  methods: {
    fetchDataForTab(tabName) {
      this.loading[tabName] = true;
      console.log(`Fetching data for tab: ${tabName}`);
      // 模拟API加载
      setTimeout(() => {
        this.loading[tabName] = false;
        // 实际中，这里会根据tabName调用不同API
        // 例如，this.fetchPendingBlogsFromAPI();
      }, 800);
    },
    changeTab(tabName){
      this.currentTab = tabName;
      if(!this.loading[tabName] && ( (tabName === 'pendingBlogs' && !this.pendingBlogs.length) || (tabName === 'pendingComments' && !this.pendingComments.length) || (tabName === 'reportedContent' && !this.reportedItems.length) ) ){
        // 如果数据已加载过且为空，可以考虑是否重新加载，或依赖已有数据
      }
      // 首次切换到某tab或需要刷新数据时调用
      this.fetchDataForTab(tabName);
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A';
      return new Date(dateString).toLocaleString('zh-CN');
    },
    truncateText(text, maxLength) {
      if (!text) return '';
      return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
    },
    viewContentDetails(item, type) {
      this.viewingItem = item;
      this.viewingItemType = type;
      this.contentModalTitle = type === 'blog' ? '博客' : '评论';
      this.showContentModal = true;
    },
    closeContentModal() {
      this.showContentModal = false;
      this.viewingItem = null;
      this.viewingItemType = '';
    },
    viewTargetContent(targetType, targetId){
      // 实际项目中，这里会跳转到对应博客或菜品的详情页
      alert(`查看 ${targetType === 'blog' ? '博客' : '菜品'} 详情 (ID: ${targetId}) - 功能待实现`);
    },
    approveItem(item, type) {
      if (confirm(`确定要通过这条${type === 'blog' ? '博客' : '评论'}吗？`)) {
        console.log(`通过 ${type} ID: ${item.id}`);
        item.status = 'approved'; // 模拟更新状态
        // 实际应调用API更新，并从待审核列表中移除
        if(type === 'blog') this.allBlogsData = this.allBlogsData.map(b => b.id === item.id ? item : b);
        else this.allCommentsData = this.allCommentsData.map(c => c.id === item.id ? item : c);
        alert('内容已通过审核！');
      }
    },
    rejectItem(item, type) {
      const reason = prompt(`请输入拒绝这条${type === 'blog' ? '博客' : '评论'}的理由（可选）：`);
      if (reason !== null) { // 用户点击了确定或输入了内容（包括空字符串）
        console.log(`拒绝 ${type} ID: ${item.id}, 理由: ${reason}`);
        item.status = 'rejected'; // 模拟更新状态
        if(type === 'blog') this.allBlogsData = this.allBlogsData.map(b => b.id === item.id ? item : b);
        else this.allCommentsData = this.allCommentsData.map(c => c.id === item.id ? item : c);
        alert('内容已拒绝！');
        // 实际应调用API更新，并从待审核列表中移除，可能需要记录拒绝理由
      }
    },
    dismissReport(reportId) {
      if (confirm('确定要忽略这条举报吗？该举报将不再显示。')) {
        console.log(`忽略举报 ID: ${reportId}`);
        this.reportedItemsData = this.reportedItemsData.filter(r => r.id !== reportId);
        alert('举报已忽略。');
      }
    },
    deleteReportedItem(report) {
      if (confirm(`确定要删除被举报的 ${report.contentType === 'blog' ? '博客' : '评论'} 吗？此操作不可恢复。`)) {
        console.log(`删除被举报内容: 类型-${report.contentType}, 内容ID-${report.originalContentId}`);
        // 同时从举报列表和对应的内容列表（博客/评论）中删除
        this.reportedItemsData = this.reportedItemsData.filter(r => r.id !== report.id);
        if (report.contentType === 'blog') {
          this.allBlogsData = this.allBlogsData.filter(b => b.id !== report.originalContentId);
        } else if (report.contentType === 'comment') {
          this.allCommentsData = this.allCommentsData.filter(c => c.id !== report.originalContentId);
        }
        alert('被举报的内容已删除。');
      }
    }
  },
  created() {
    // 默认加载第一个tab的数据
    this.changeTab(this.currentTab);
  }
}
</script>

<style scoped>
/* 复用 AdminRoleManageView 和 AdminFoodManagementView 的部分样式 */
.admin-blog-comment-manage-view { padding: 20px; }
.page-title-bar { margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid #e0e0e0; }
.page-title-bar h2 { font-size: 1.8em; color: #333; margin-top: 0; margin-bottom: 5px; display:flex; align-items:center;}
.page-title-bar h2 i { margin-right: 12px; color: #20c997; } /* 青色图标 */
.page-title-bar p { font-size: 1em; color: #666; margin-bottom: 0; }

.management-tabs { display: flex; margin-bottom: 0; border-bottom: 2px solid #dee2e6; } /* 与 FoodManagement 略有不同，直接连接到 content-card */
.management-tabs button { padding: 12px 20px; font-size: 1.05em; font-weight: 500; color: #495057; background-color: transparent; border: none; border-bottom: 3px solid transparent; cursor: pointer; transition: color 0.2s, border-color 0.2s; display:flex; align-items:center;}
.management-tabs button i { margin-right:8px; }
.management-tabs button:hover { color: #007bff; }
.management-tabs button.active { color: #007bff; border-bottom-color: #007bff; }

.content-card { background-color: #fff; padding: 25px; border-radius: 0 0 8px 8px; box-shadow: 0 1px 3px rgba(0,0,0,0.08); border-top: none; }
.tab-content h3 { font-size: 1.5em; color: #333; margin-top:0; margin-bottom:20px; }
.loading-placeholder, .no-data-table { text-align:center; padding: 30px; color: #6c757d; font-size: 1.1em; }

.data-table.review-table { width: 100%; border-collapse: collapse; font-size: 0.9em; }
.review-table th, .review-table td { padding: 10px 12px; text-align: left; border: 1px solid #e9ecef; vertical-align: middle; }
.review-table th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
.review-table tbody tr:hover { background-color: #f1f3f5; }
.review-table td a { color: #007bff; text-decoration: none; }
.review-table td a:hover { text-decoration: underline; }

.action-buttons .btn-icon { background: none; border: none; cursor: pointer; padding: 6px; margin: 0 4px; font-size: 1.1em; transition: color 0.2s ease; display:inline-flex; align-items:center; }
.btn-approve { color: #28a745; }
.btn-approve:hover { color: #1e7e34; }
.btn-reject { color: #dc3545; }
.btn-reject:hover { color: #c82333; }
.btn-view { color: #17a2b8; }
.btn-view:hover { color: #117a8b; }
.btn-delete { color: #dc3545; } /* 重复，但明确用作删除 */
.btn-delete:hover { color: #c82333; }
.btn-icon i { margin-right: 3px;} /* 给图标和文字之间一点点距离，如果按钮有文字 */


/* Modal Styles */
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1050;}
.content-detail-modal { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); width: 90%; max-width: 700px; max-height: 80vh; display:flex; flex-direction:column;}
.content-detail-modal h3 { margin-top: 0; margin-bottom: 15px; font-size:1.4em; color:#333; border-bottom: 1px solid #eee; padding-bottom:10px;}
.content-detail-modal .modal-body { overflow-y:auto; margin-bottom:15px; line-height:1.6; color:#444;}
.content-detail-modal .modal-body h4 { margin-top:0; color:#0056b3;}
.content-detail-modal .modal-body .content-preview { background-color: #f9f9f9; padding:10px; border-radius:4px; border:1px solid #eee; white-space: pre-wrap; }
.modal-actions { margin-top: auto; display: flex; justify-content: flex-end; gap: 10px; padding-top:15px; border-top:1px solid #eee;}
.btn { padding: 8px 16px; border: none; border-radius: 5px; font-size: 0.9em; font-weight: 500; cursor: pointer; transition: background-color 0.2s ease; display:inline-flex; align-items:center; }
.btn-secondary { background-color: #6c757d; color:white; }
.btn-secondary:hover { background-color: #5a6268; }

.placeholder-text { color: #888; font-style: italic; margin: 20px 0; text-align: center; }
</style>
    