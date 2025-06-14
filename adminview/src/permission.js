import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // 进度条
import 'nprogress/nprogress.css' // 进度条样式

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // 无需重定向的白名单

router.beforeEach(async(to, from, next) => {
    NProgress.start()

    const hasToken = store.getters.token

    if (hasToken) {
        if (to.path === '/login') {
            // if is logged in, redirect to the home page
            next({ path: '/' })
            NProgress.done()
        } else {
            const hasRoles = store.getters.roles && store.getters.roles.length > 0
            if (hasRoles) {
                next()
            } else {
                try {
                    // get user info
                    // note: roles must be a non-empty array! e.g: ['admin'] or ,['developer','editor']
                    const { roles } = await store.dispatch('user/getInfo')

                    // generate accessible routes map based on roles
                    const accessRoutes = await store.dispatch('permission/generateRoutes', roles)

                    // dynamically add accessible routes
                    router.addRoutes(accessRoutes)

                    // ★★★★★ 核心修复 ★★★★★
                    // hack method to ensure that addRoutes is complete
                    // set the replace: true, so the navigation will not leave a history record
                    // 这个 next(to) 会继续之前被中断的导航，而不是像 next({ ...to }) 那样发起一个新的导航
                    // 这就从根本上避免了 "Redirected" 错误。
                    next({ ...to, replace: true })

                } catch (error) {
                    // remove token and go to login page to re-login
                    await store.dispatch('user/resetToken')
                    Message.error(error.message || 'Has Error')
                    next(`/login?redirect=${to.path}`)
                    NProgress.done()
                }
            }
        }
    } else {
        /* has no token*/
        if (whiteList.indexOf(to.path) !== -1) {
            // in the free login whitelist, go directly
            next()
        } else {
            // other pages that do not have permission to access are redirected to the login page.
            next(`/login?redirect=${to.path}`)
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    NProgress.done()
})
