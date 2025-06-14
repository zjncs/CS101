import Vue from 'vue'
import VueRouter from 'vue-router'

const Layout = () => import('@/views/layout/index.vue');
const Login = () => import('@/views/login/index.vue');
const Dashboard = () => import('@/views/dashboard/index.vue');
const NotFound = () => import('@/views/404.vue');

// 引入所有业务页面组件
const PersonnelManagement = () => import('@/views/system_mgr/PersonnelManagement.vue');
const RoleManagement = () => import('@/views/system_mgr/RoleManagement.vue');
const CampusManagement = () => import('@/views/content_mgr/Campus.vue');
const CanteenManagement = () => import('@/views/content_mgr/Canteen.vue');
const StallManagement = () => import('@/views/content_mgr/Stall.vue');
const DishManagement = () => import('@/views/content_mgr/Dish.vue');
const TypeManagement = () => import('@/views/content_mgr/TypeManagement.vue');
const ReportManagement = () => import('@/views/community_mgr/ReportManagement.vue');
const BlogManagement = () => import('@/views/community_mgr/BlogManagement.vue');
const SensitiveWordsManagement = () => import('@/views/community_mgr/SensitiveWordsManagement.vue');
const UserManagement = () => import('@/views/community_mgr/UserManagement.vue');
const NoticeManagement = () => import('@/views/operation_mgr/NoticeManagement.vue');
const LogManagement = () => import('@/views/operation_mgr/LogManagement.vue');
const BannerManagement = () => import('@/views/operation_mgr/BannerManagement.vue');
const IncentiveManagement = () => import('@/views/operation_mgr/IncentiveManagement.vue');
const HelpDoc = () => import('@/views/toolbox/HelpDoc.vue');
const ExportLog = () => import('@/views/toolbox/ExportLog.vue');
const ExportDb = () => import('@/views/toolbox/ExportDb.vue');
const StatisticsDashboard = () => import('@/views/toolbox/StatisticsDashboard.vue');
const ApiDoc = () => import('@/views/toolbox/ApiDoc.vue');
const TaskManagement = () => import('@/views/toolbox/TaskManagement.vue');

Vue.use(VueRouter)

// 静态路由
export const constantRoutes = [
  { path: '/login', component: Login, hidden: true },
  { path: '/404', component: NotFound, hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: Dashboard,
      meta: { title: '首页', icon: 'el-icon-s-home' }
    }]
  }
]

// 动态路由
export const asyncRoutes = [
  {
    path: '/system-management',
    component: Layout,
    redirect: '/system-management/content/campus',
    name: 'SystemManagement',
    meta: { title: '系统管理', icon: 'el-icon-s-management', roles: ['系统管理员', '内容管理员', '社区管理员', '运营管理员'] },
    children: [
      {
        path: 'content', name: 'ContentManagement', component: { render: h => h('router-view') },
        meta: { title: '内容管理', roles: ['内容管理员', '系统管理员'] },
        children: [
          { path: 'campus', name: 'CampusManagement', component: CampusManagement, meta: { title: '校区管理' } },
          { path: 'canteen-type', name: 'CanteenTypeManagement', component: TypeManagement, meta: { title: '食堂类型管理' } },
          { path: 'canteen', name: 'CanteenManagement', component: CanteenManagement, meta: { title: '食堂管理' } },
          { path: 'stall-type', name: 'StallTypeManagement', component: TypeManagement, meta: { title: '窗口类型管理' } },
          { path: 'stall', name: 'StallManagement', component: StallManagement, meta: { title: '窗口管理' } },
          { path: 'dish', name: 'DishManagement', component: DishManagement, meta: { title: '菜品管理' } },
        ]
      },
      {
        path: 'community', name: 'CommunityManagement', component: { render: h => h('router-view') },
        meta: { title: '社区管理', roles: ['社区管理员', '系统管理员'] },
        children: [
          { path: 'report', name: 'ReportManagement', component: ReportManagement, meta: { title: '用户举报管理' } },
          { path: 'blog', name: 'BlogManagement', component: BlogManagement, meta: { title: '博客发表管理' } },
          { path: 'sensitive-words', name: 'SensitiveWordsManagement', component: SensitiveWordsManagement, meta: { title: '敏感词管理' } },
          { path: 'user', name: 'UserManagement', component: UserManagement, meta: { title: '普通用户管理' } },
        ]
      },
      {
        path: 'operation', name: 'OperationManagement', component: { render: h => h('router-view') },
        meta: { title: '运营管理', roles: ['运营管理员', '系统管理员'] },
        children: [
          { path: 'notice', name: 'NoticeManagement', component: NoticeManagement, meta: { title: '系统公告管理' } },
          { path: 'log', name: 'LogManagement', component: LogManagement, meta: { title: '系统日志管理' } },
          { path: 'banner', name: 'BannerManagement', component: BannerManagement, meta: { title: '轮播图管理' } },
          { path: 'incentive', name: 'IncentiveManagement', component: IncentiveManagement, meta: { title: '用户激励管理' } },
        ]
      },
      {
        path: 'platform', name: 'PlatformSettings', component: { render: h => h('router-view') },
        meta: { title: '平台设置', roles: ['系统管理员'] },
        children: [
          { path: 'personnel', name: 'PersonnelManagement', component: PersonnelManagement, meta: { title: '人员管理' } },
          { path: 'role', name: 'RoleManagement', component: RoleManagement, meta: { title: '角色管理' } },
        ]
      },
    ]
  },
  {
    path: '/toolbox',
    component: Layout,
    redirect: '/toolbox/help-doc',
    name: 'Toolbox',
    meta: { title: '工具箱', icon: 'el-icon-s-cooperation', roles: ['系统管理员', '内容管理员', '社区管理员', '运营管理员'] },
    children: [
      { path: 'help-doc', name: 'HelpDoc', component: HelpDoc, meta: { title: '在线帮助文档' } },
      { path: 'export-log', name: 'ExportLog', component: ExportLog, meta: { title: '操作日志导出', roles: ['系统管理员', '运营管理员'] } },
      { path: 'export-db', name: 'ExportDb', component: ExportDb, meta: { title: '数据库数据导出', roles: ['系统管理员'] } },
      { path: 'statistics', name: 'StatisticsDashboard', component: StatisticsDashboard, meta: { title: '数据大屏', roles: ['系统管理员'] } },
      { path: 'api-doc', name: 'ApiDoc', component: ApiDoc, meta: { title: '接口文档', roles: ['系统管理员'] } },
      { path: 'task', name: 'TaskManagement', component: TaskManagement, meta: { title: '定时任务管理', roles: ['系统管理员'] } },
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
];

const createRouter = () => new VueRouter({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
const router = createRouter()
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}
export default router