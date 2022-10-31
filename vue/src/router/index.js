import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },

  /**
   *  用户管理user路由
   */

  {
    path: '/userList',
    name: 'UserList',
    component: () => import('@/views/user/User')
  },
  {
    path: '/addUser',
    name: 'addUser',
    component: () => import('@/views/user/addUser')
  },
  {
    path: '/editUser',
    name: 'editUser',
    component: () => import('@/views/user/editUser')
  },

  /**
   *  管理员admin路由
   */

  {
    path: '/adminList',
    name: 'AdminList',
    component: () => import('@/views/admin/Admin')
  },
  {
    path: '/Add',
    name: 'Add',
    component: () => import('@/views/admin/Add')
  },
  {
    path: '/Edit',
    name: 'Edit',
    component: () => import('@/views/admin/Edit')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
