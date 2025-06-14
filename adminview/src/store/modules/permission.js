import { asyncRoutes, constantRoutes } from '@/router'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles the user's roles
 * @param route the route to check
 */
function hasPermission(roles, route) {
    if (route.meta && route.meta.roles) {
        // Check if the user's role array has at least one role that is included in the route's roles array
        return roles.some(role => route.meta.roles.includes(role))
    } else {
        // If the route does not have meta.roles, it means it's accessible to everyone
        return true
    }
}

/**
 * Filter asynchronous routing tables recursively
 * @param routes asyncRoutes
 * @param roles user's roles
 */
export function filterAsyncRoutes(routes, roles) {
    const res = []

    routes.forEach(route => {
        const tmp = { ...route }
        if (hasPermission(roles, tmp)) {
            if (tmp.children) {
                tmp.children = filterAsyncRoutes(tmp.children, roles)
            }
            res.push(tmp)
        }
    })

    return res
}

const state = {
    routes: [],
    addRoutes: []
}

const mutations = {
    SET_ROUTES: (state, routes) => {
        state.addRoutes = routes
        state.routes = constantRoutes.concat(routes) // Combine static and dynamic routes
    }
}

const actions = {
    generateRoutes({ commit }, roles) {
        return new Promise(resolve => {
            let accessedRoutes
            // If the user is a '系统管理员', grant access to all async routes
            if (roles.includes('系统管理员')) {
                accessedRoutes = asyncRoutes || []
            } else {
                // Otherwise, filter routes based on roles
                accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
            }
            commit('SET_ROUTES', accessedRoutes)
            resolve(accessedRoutes)
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
