<template>
  <div id="app">
    <!--  头部  -->
    <div style="height: 80px;line-height: 80px;background: white;margin-bottom: 2px">
      <img src="@/assets/logo.png" alt="logo" style="width: 50px; position: relative;top: 15px;left: 20px">
      <span style="margin-left: 28px;font-size: 24px;font-family: 黑体">青龙山图书管理系统</span>
    </div>

    <!--  侧边栏和主体  -->
    <div style="display: flex">
        <!--    侧边栏导航    -->
      <div style="width: 200px;
      min-height: calc(100vh - 82px);
      /*最小行高 100vh占满全屏，82px = 头部height80px + margin-bottom 2px */
      overflow: hidden;
      margin-right: 2px;
      background-color: white">
      <el-menu
          :default-active="$route.path" router class="el-menu-demo">

        <el-menu-item index="/">
          <i class="el-icon-s-home"></i>
          <span>首页</span>
        </el-menu-item>

        <el-submenu index="user">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span>会员管理</span>
          </template>
          <el-menu-item index="/addUser">添加会员</el-menu-item>
          <el-menu-item index="/userList">会员列表</el-menu-item>
          </el-submenu>

        <el-submenu index="admin">
          <template slot="title">
            <i class="el-icon-user-solid"></i>
            <span>管理员管理</span>
          </template>
          <el-menu-item index="/Add">添加管理员</el-menu-item>
          <el-menu-item index="/adminList">管理员列表</el-menu-item>
        </el-submenu>

        <el-menu-item index="aboutMore" disabled>
          <i class="el-icon-question"></i>
          <span>项目详情</span>
        </el-menu-item>

      </el-menu>

      </div>

        <!--   主体数据   -->
      <div style="flex: 1;background-color: white">
        <router-view/>

      </div>
    </div>
  </div>
</template>


