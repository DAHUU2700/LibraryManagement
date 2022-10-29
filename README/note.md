 [README.md](..\README.md) 准备阶段！

# 1、完成主页布局

图标网站：https://www.iconfont.cn/

ElementUI官方文档：https://element.eleme.cn/#/zh-CN/component

- 在`main.js`中导入ElemetUI；

  ```js
  //导入ElemetUI
  import ElementUI from 'element-ui';
  import 'element-ui/lib/theme-chalk/index.css';
  ```

- 导入`src`中的`global.css`文件；

  ```js
  // 导入src下的css文件
  import '@/assets/global.css'
  ```

```js
import Vue from 'vue'
import App from './App.vue'
import router from './router'

//导入ElemetUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// 导入src下的css文件
import '@/assets/global.css'

Vue.config.productionTip = false
Vue.use(ElementUI);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

```

- 在`global.css`中清除所有的样式，设置背景颜色为`#eee`；

  ```css
  body{
      margin: 0;
      padding: 0;
      background-color: #eee;
  }
  
  *{
      box-sizing: border-box;
  }
  ```

## 1.1头部

头部内容基本不会发生变动，因此在`App.vue`中进行书写。

```vue
<!--  头部  -->
<div style="height: 80px;line-height: 80px;background: white;margin-bottom: 2px">
//	设置行高为80px，背景颜色为白色，下外边距2px
    
<img src="@/assets/logo.png" alt="logo" style="width: 50px; position: relative;top: 15px;left: 20px">
//	图片logo
    
<span style="margin-left: 28px;font-size: 24px;font-family: 黑体">图书管理系统</span>
//	左边距为28px

</div>
```

## 1.2左侧菜单

左侧数据内容也是基本不会变动，同样书写在`App.vue`中。

```vue
<!--  侧边栏和主体  -->
<div style="display: flex">
    //	弹性布局
    <!--    侧边栏导航    -->
    ...
	<!--   主体数据   -->
    ...
</div>
```

```vue
 <!--    侧边栏导航    -->
<div style="width: 200px;
      min-height: calc(100vh - 82px);
            // 最小行高 100vh占满全屏，82px = 头部height(80px) + margin-bottom(2px)
      overflow: hidden;
      margin-right: 2px;
      background-color: white">
      <el-menu
          :default-active="$route.path"
          :default-openeds="['/']"	
               //设置默认打开页面为index索引为'/'的页面
          router class="el-menu-demo">

        <el-menu-item index="/">
          <i class="el-icon-s-platform"></i>
          <span>首页</span>
        </el-menu-item>


        <el-submenu>
          <template slot="title">
            <i class="el-icon-more"></i>
            <span>关于页面</span>
          </template>

          <el-menu-item index="/about">详情about</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
          </el-submenu>
        <el-menu-item index="3" disabled>消息中心</el-menu-item>
      </el-menu>
</div>
```

## 1.3主体

```vue
<!--   主体数据   -->
<div style="flex: 1;background-color: white">
<router-view/>
    //	当路由path 与访问的地址相符时，会将指定的组件替换该 router-view
    //	在index.js 文件中 path: '/'
    
</div>
```

对于实际显示的（表格等）页面，将在`HomeView.vue`中实现。

```vue
<template>
  <div>
    <!--    搜索表单    -->
      <div style="margin-bottom: 20px">
        <el-input style="width: 240px" placeholder="请输入名称"></el-input>
        <el-input style="width: 240px; margin: 5px" placeholder="请输入联系方式"></el-input>
        <el-button style="margin-left: 5px" type="primary"><i class="el-icon-search"></i>搜索</el-button>
      </div>

    <!--  表头  -->
      <el-table :data="tableData" stripe>
        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="phone" label="联系方式"></el-table-column>
        <el-table-column prop="sex" label="性别"></el-table-column>
      </el-table>

    <!-- 分页 -->
    <div style="margin-top: 20px">
      <el-pagination
          background
          layout="prev, pager, next"
          :total="100">
      </el-pagination>
    </div>

  </div>
</template>

<script>

export default {
  name: 'HomeView',
    
  data(){
    return {
      tableData: [
        {name: '王小虎', age: '20', address: '北京市', phone: '181119518',sex:'男'},
        {name: '王大虎', age: '18', address: '上海市', phone: '181119528',sex:'男'},
        {name: '荙拗', age: '20', address: '北京市', phone: '181119518',sex:'女'},
      ]
    }
  }
    
}
</script>
```

# 2、搭建后台服务

## 2.1 创建Springboot项目

File → New → Module → `Spring Initializr`

![image-20221029145147025](./pic/note-01.png)

- `Service URL` 报错；

  ```sh
  Initialization failed for 'https://start.spring.io'    
  Please check URL, network and proxy settings.Error message:Cannot download 'https://start.spring.io': connect timed out
  ```

  将默认地址修改为阿里云的地址：`https://start.aliyun.com/`

  ![image-20221029145605098](./pic/note-02.png)

- 设置`Name`；

- 选择`Java` 为 8。

![image-20221029150057608](./pic/note-03.png)

- 选择`Spring Boot` 的版本；
- 添加依赖组件；
  - `Lombok`通过注解`@Data`简化实体类的JavaBean书写，
  - `Spring Web`架构，
  - `MySQL`数据库驱动，
  - `MyBatis`框架，简化JDBC书写。

![image-20221029150916654](./pic/note-04.png)

> 当创建完成后，需要等待Maven依赖。在右侧没有出现Maven导航栏：在新版本IDEA中，可以直接添加——打开`pom.xml`文件 → 在文件上右键 → `Add as Maven Project`即可。

## 2.2 连接MySQL数据库

① 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS library-management
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;
```

<img src="./../../../Typora/typora-pic/note-05.png" alt="image-20221029152047245" style="zoom: 67%;" />

② 创建表

```sql
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    -- id自增
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '住址',
  PRIMARY KEY (`id`)
    -- id 主键
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

③ 添加一条数据

<img src="./pic/note-06.png" alt="image-20221029152442107" style="zoom: 80%;" />

### 2.2.1 配置

一般将`application.properties`改为`application.yml`。

```properties
# 设置springboot的端口号为9090，vue的端口号为8080，不设置会冲突
server:
  port: 9090

spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/library-management?serverTimezone=GMT%2b8
    # GMT%2b8 东八区 —— 北京时间
    username: root
    password: 123456
```

配置完成后即可启动SpringBoot，出现spring标识即启动成功。

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.7.RELEASE)
```

## 2.3 大体结构

![image-20221029154246337](./pic/note-07.png)

> 1. `entity`包：每一个Java类对应于数据库中的一个表
> 2. `mapper`包：dao数据库访问层
> 3. `service`包：业务逻辑层
> 4. `controller`包：界面层

### 2.3.1 entity包

存放数据库相关的实体类文件。`User`

```java
package com.example.springboot.entity;
import lombok.Data;

//  简化JavaBean的书写
@Data
public class User {
    private Integer id;
    private String name;
    private String username;
    private Integer age;
    private String sex;
    private String phone;
    private String address;
}
```

### 2.3.2 mapper包

创建实体后，需要一个接口来操作实体；mapper包提供数据库访问的方法。`UserMapper`

```java
package com.example.springboot.mapper;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询所有
     * @return
     */
    
//    @Select("select * from user")
//	通过注解的方式
    List<User> listUser();
}
```

### 2.3.3 service包

业务处理层区操作数据库；一般是通过【接口`IUserService`】+【实现类`impl.UserService`】的方法去实现的。

`IUserService`

```java
package com.example.springboot.service;
import com.example.springboot.entity.User;
import java.util.List;

public interface IUserService {
    List<User> listUser();
}
```

继承接口`IUserService`的实现类`UserService`：

```java
package com.example.springboot.service.impl;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service	//	标识为一个spring的组件，才可以去调用
public class UserService implements IUserService {
    @Autowired
    UserMapper userMapper;

    //	继承接口，必须重写接口中的方法
    @Override
    public List<User> listUser() {
        return userMapper.listUser();
        // 不安全，需要使用try...catch处理
    }
}
```

### 2.3.4 controller包

实现数据传输给浏览器。`UserController`

```java
package com.example.springboot.controller;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController		// 标识——会以json的数据进行返回
@RequestMapping("/user")	//路由 —— 一级目录
//	@CrossOrigin	解决跨界错误
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/list")	//定义路由 —— 二级目录
    public List<User> listUsers() {
        return userService.listUser();
    }
}
```

从后往前的大体结构如此；通过`Ctrl + Alt + B`可以前往后controller → service → Mapper → entity ，可以更好的理解。

访问`http://localhost:9090/user/list`，可以查询到MySQL数据库中的一条数据，以JSON数据返回。

<img src="./pic/note-08.png" alt="image-20221029161138514" style="zoom: 50%;" />

## 2.4 前后端交互

修改`vue/src/HomeView.vue`的代码，通过fetch进行完成。

```vue
<script>
export default {
  name: 'HomeView',
  data(){
    return {
      tableData: []
    }
  },
  created() {
    this.load()
  },
  methods:{
    load() {
      fetch('http://localhost:9090/user/list')
          .then(res => res.json())
          .then(res => {
            console.log(res)
            this.tableData = res
          })
    }
  }
}
</script>
```

### 2.4.1  跨域错误

启动vue前端，发现显示暂无数据，并报错：

```
Access to fetch at 'http://localhost:9090/user/list' from origin 'http://localhost:8080' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource. If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled.
```

解决方法：在`UserController`中添加注解`@CrossOrigin`

```java
@RestController				// 标识——会以json的数据进行返回
@RequestMapping("/user")	//路由 —— 一级目录
@CrossOrigin				//解决跨界错误
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/list")	//定义路由 —— 二级目录
    public List<User> listUsers() {
        return userService.listUser();
    }
}
```

其他解决方法，参考：[https://cloud.tencent.com/developer/article/1911678](解决跨域问题的 5 种方法)

### 2.4.2 通过XML交互

Mybatis官网：[https://mybatis.net.cn/getting-started.html](MyBatis官网)

在mapper中，是通过书写注解的方式来实现交互的。

```java
@Mapper
public interface UserMapper {
    /**
     * 查询所有
     * @return
     */  
@Select("select * from user")	//	通过注解的方式
    List<User> listUser();
}
```

1. 在`resources`目录中新建`/com/example/springboot/mapper/User.xml`；

   在spring中并不需要一一对应，因为需要配置，直接创建`mapper/User.xml`即可。

   ![image-20221029163915055](./pic/note-09.png)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.springboot.mapper.UserMapper">
    //	设置namespace目录的【全限定名称】，与Mapper对应！

<!--  查询所有  -->
    <select id="listUser" resultType="com.example.springboot.entity.User">
        //	设置id与UserMapper中方法名！
        //	设置resultType的实体类User！
        select * from user
    </select>

</mapper>
```

2. 取消`UserMapper`中的注解；

   ```java
   @Mapper
   public interface UserMapper {
       /**
        * 查询所有
        * @return
        */  
       
   //	@Select("select * from user")
       List<User> listUser();
   }
   ```

3. 配置`application.yml`

   ```properties
   #配置Mybatis 绑定
   mybatis:
     mapper-locations: classpath:com/example/springboot/mapper/*.xml
   ```

   如果直接创建mapper，只用写`mapper-locations: classpath:mapper/*.xml`

如果没有配置，网页会报错：

```
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.example.springboot.mapper.UserMapper.listUsers
//绑定异常:无效的绑定语句(未找到)
```

# 3、后台增删改查
