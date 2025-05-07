import { createRouter, createWebHistory } from 'vue-router'

// Views
import Home from '../views/Home.vue'
import CampusList from '../views/campus/CampusList.vue'
import CampusDetail from '../views/campus/CampusDetail.vue'
import CanteenDetail from '../views/canteen/CanteenDetail.vue'
import StallDetail from '../views/stall/StallDetail.vue'
import DishDetail from '../views/dish/DishDetail.vue'
import BlogList from '../views/blog/BlogList.vue'
import BlogDetail from '../views/blog/BlogDetail.vue'
import Login from '../views/auth/Login.vue'
import Register from '../views/auth/Register.vue'
import Profile from '../views/user/Profile.vue'
import Rankings from '../views/Rankings.vue'
import Search from '../views/Search.vue'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/campus',
        name: 'CampusList',
        component: CampusList
    },
    {
        path: '/campus/:id',
        name: 'CampusDetail',
        component: CampusDetail
    },
    {
        path: '/canteen/:id',
        name: 'CanteenDetail',
        component: CanteenDetail
    },
    {
        path: '/stall/:id',
        name: 'StallDetail',
        component: StallDetail
    },
    {
        path: '/dish/:id',
        name: 'DishDetail',
        component: DishDetail
    },
    {
        path: '/blogs',
        name: 'BlogList',
        component: BlogList
    },
    {
        path: '/blog/:id',
        name: 'BlogDetail',
        component: BlogDetail
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/profile',
        name: 'Profile',
        component: Profile
    },
    {
        path: '/rankings',
        name: 'Rankings',
        component: Rankings
    },
    {
        path: '/search',
        name: 'Search',
        component: Search
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior() {
        return { top: 0 }
    }
})

export default router
