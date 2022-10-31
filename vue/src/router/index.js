import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/views/Layout'

Vue.use(VueRouter)

const routes = [
  /*
   *  父级：登录页面
   */
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login')
  },

  //  父级：主页(头部 + 侧边栏)
  {
    path: '/',
    name: 'home',
    component: Layout,
    redirect: '/home',   //重定向到首页！
    children : [
    //  子级会自动拼接path中的'/',因此子路由children不需要加'/'
      //  首页
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/HomeView')
      },

      //  用户管理user路由

      {
        path: 'userList',
        name: 'UserList',
        component: () => import('@/views/user/User')
      },
      {
        path: 'addUser',
        name: 'addUser',
        component: () => import('@/views/user/addUser')
      },
      {
        path: 'editUser',
        name: 'editUser',
        component: () => import('@/views/user/editUser')
      },

      //  管理员admin路由

      {
        path: 'adminList',
        name: 'AdminList',
        component: () => import('@/views/admin/Admin')
      },
      {
        path: 'Add',
        name: 'Add',
        component: () => import('@/views/admin/Add')
      },
      {
        path: 'Edit',
        name: 'Edit',
        component: () => import('@/views/admin/Edit')
      }

    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
