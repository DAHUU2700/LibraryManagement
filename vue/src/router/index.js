import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/views/Layout'
import Cookies from "js-cookie";

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
        path: 'userList', name: 'UserList', component: () => import('@/views/user/User')
      },
      {
        path: 'addUser', name: 'addUser', component: () => import('@/views/user/addUser')
      },
      {
        path: 'editUser', name: 'editUser', component: () => import('@/views/user/editUser')
      },

      //  管理员admin路由
      {
        path: 'adminList', name: 'AdminList', component: () => import('@/views/admin/Admin')
      },
      {
        path: 'Add', name: 'Add', component: () => import('@/views/admin/Add')
      },
      {
        path: 'Edit', name: 'Edit', component: () => import('@/views/admin/Edit')
      },

      //  分类category路由
      {
        path: 'categoryList', name: 'CategoryList', component: () => import('@/views/category/List')
      },
      {
        path: 'categoryAdd', name: 'CategoryAdd', component: () => import('@/views/category/Add')
      },
      {
        path: 'categoryEdit', name: 'CategoryEdit', component: () => import('@/views/category/Edit')
      },

      //  图书book路由
      {
        path: 'bookList', name: 'BookList', component: () => import('@/views/book/List')
      },
      {
        path: 'bookAdd', name: 'BookAdd', component: () => import('@/views/book/Add')
      },
      {
        path: 'bookEdit', name: 'BookEdit', component: () => import('@/views/book/Edit')
      },


    ]
  },

    //  404页面
  {
    path: '*',
    component: () => import('@/views/404')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//  路由守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login') next()
  const admin = Cookies.get("admin")
  //  强制退回到登录界面
  if (!admin && to.path !== '/login') return next("/login")
  //  访问/home的时候，里面存在合法的Cookie，才能放行
  next()
})

export default router
