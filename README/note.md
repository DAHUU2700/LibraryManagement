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

<img src="./pic/note-05.png" alt="image-20221029152047245" style="zoom: 67%;" />

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

# 3、分页&模糊查询(R)

## 3.1 后端

### 3.1.1 代码实现

为了统一后台返回的数据，创建`springboot.common`包，新建`Result.java`进行包装。

```java
package com.example.springboot.common;
import lombok.Data;

/**
 * 统一管理后台的返回的数据
 */
@Data
public class Result {
//    定义常量状态码
    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "-1";

//    统一封装返回信息
    private String code;
    private Object data;
    private String msg;

//    成功时调用的无参构造方法
    public static Result success() {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }

//    成功时调用的有参构造方法
    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        return result;
    }

    //    错误时调用的有参构造方法
    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }
}
```

修改`UserController`；并且将包下的`listUser`都修改为`list`。

```java
//  查询所有
@GetMapping("/list")
public Result list() {
    List<User> users = userService.list();
    return Result.success(users);
}
```

启动浏览器，访问`http://localhost:9090/user/list`

<img src="./pic/note-10.png" alt="image-20221029220206314" style="zoom: 67%;" />

==正式开始分页与模糊查询：（从前往后）==

1. 在`controller.UserController`中

   ```java
   //  分页
   @GetMapping("/page")
   public Result page(UserPageRequest userPageRequest) {
   	return Result.success(userService.page(userPageRequest));
       }
   ```

   - 会需要传递参数，创建`controller.request`来创建实体类类`BaseRequest`和`UserPageRequest`；并且UserPageRequest 继承 BaseRequest。

     ```java
     package com.example.springboot.controller.request;
     import lombok.Data;
     
     @Data
     public class BaseRequest {
         //	页数信息（通用的）
         //	设置默认值，否则报错500，需要手动传入参数
         private Integer pageNum = 1 ;
         private Integer pageSize = 10 ;
     }
     ```

     ```java
     package com.example.springboot.controller.request;
     import lombok.Data;
     
     @Data
     public class UserPageRequest extends BaseRequest{
     	//	模糊查询的字段
         private String name;	
         private String phone;
     }
     ```

2. 在`service.IUserService`中

   ```java
   //  分页
   Object page(UserPageRequest userPageRequest);
   ```

   在`service.impl.UserService`中

   ```java
   //  分页
   @Override
   public Object page(UserPageRequest userPageRequest) {
     
       // 使用插件将其包装成分页对象
      PageHelper.startPage(userPageRequest.getPageNum(),userPageRequest.getPageSize());
       
       //  条件查询listByCondition
       List<User> users = userMapper.listByCondition(userPageRequest);
       return new PageInfo<>(users);
       
   }
   ```

3. 在`mapper.UserMapper`中

   ```java
   /**
    * 条件查询
    * @param userPageRequest
    */
   List<User> listByCondition(UserPageRequest userPageRequest);
   ```

   与`User.xml`保持映射，写入sql语句。

   ```xml
   <!--  条件查询  -->
   <select id="listByCondition" resultType="com.example.springboot.entity.User">
   
   <!--
   导致传入的参数为null的原因，sql语句是静态的，应该采用动态sql语句。
   select * from user where name like concat('%', #{name}, '%') and phone like concat('%', #{phone}, '%');
   -->
   
   -- 动态sql
   select * from user 
   	<where>
   		<if test="name != null and name != ''">
               name like concat('%',#{name},'%')
   		</if>
   		
           <if test="phone != null and phone != ''">
               phone like concat('%',#{phone},'%')
   		</if>
   	</where>
   </select>
   ```

   > 如果使用静态sql，会导致传入的参数为null。
   >
   > ```sh
   > JDBC Connection [HikariProxyConnection@1851731810 wrapping com.mysql.cj.jdbc.ConnectionImpl@5bc3ea85] will not be managed by Spring
   > ==>  Preparing: select * from user where name like concat('%', ?, '%') and phone like concat('%', ?, '%');
   > ==> Parameters: null, null
   > <==      Total: 0
   > ```
   >
   > 动态sql语句的日志信息：
   >
   > ```sh
   > ==>  Preparing: select * from user where name like concat('%', ?, '%') 
   > ==> Parameters: 李四(String)
   > <==      Total: 0
   > ```
   >
   > 动态sql 的好处：**传什么查什么**

访问`http://localhost:9090/user/page`，后端功能已实现。

<img src="./pic/note-11.png" alt="image-20221030101432742" style="zoom:50%;" />

分页成功的提示：

```sh
JDBC Connection [HikariProxyConnection@695994014 wrapping com.mysql.cj.jdbc.ConnectionImpl@cdd60cf] will not be managed by Spring
==>  Preparing: SELECT count(0) FROM user
==> Parameters: 
<==    Columns: count(0)
<==        Row: 1
<==      Total: 1
==>  Preparing: -- 动态sql select * from user LIMIT ?
==> Parameters: 10(Integer)
<==    Columns: id, name, username, age, sex, phone, address
<==        Row: 1, 张三, zhangsan, 18, 男, 19111966688, 家里
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1cbbcb99]
```

### 3.1.2 pagehelper 分页对象

在`service.impl.UserService`中，为了包装分页对象；导入依赖pagehelper。

```xml
<!--    pagehelper    -->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.4.5</version>
        </dependency>
```

在启动srpingboot时pagehelper成功生效会有提示：

```sh

,------.                           ,--.  ,--.         ,--.                         
|  .--. '  ,--,--.  ,---.   ,---.  |  '--'  |  ,---.  |  |  ,---.   ,---.  ,--.--. 
|  '--' | ' ,-.  | | .-. | | .-. : |  .--.  | | .-. : |  | | .-. | | .-. : |  .--' 
|  | --'  \ '-'  | ' '-' ' \   --. |  |  |  | \   --. |  | | '-' ' \   --. |  |    
`--'       `--`--' .`-  /   `----' `--'  `--'  `----' `--' |  |-'   `----' `--'    
                   `---'                                   `--'                        
is intercepting.
```

### 3.1.3 设置日志

```properties
# 配置Mybatis 绑定
mybatis:
  mapper-locations: classpath:com/example/springboot/mapper/*.xml
  # 打印日志
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

## 3.2 前端

之前使用`fetch()`去实现的，需要自己去拼接地址，比较麻烦。因此使用`axios`插件，来处理前后端交互的数据。

### 3.2.1 axios的安装

不知道是什么bug、在Terminal终端进入项目的vue目录后，执行安装语句，会报错；经过解决后使用`cmd（管理员）`完成插件的安装。

- 通过cmd，进入到项目中`vue`目录（其实和终端一样）

  ```sh
  C:\Windows\system32>cd H:\GIt-Note\LibraryManagement\vue
  C:\Windows\system32>H:
  H:\GIt-Note\LibraryManagement\vue> 
  ```

- 清除npm缓存：

  ```sh
  npm cache clean --force
  ```

- 安装：

  ```sh
  npm install axios -S
  ```

- 安装成功后，在`vue\package.json`中会添加`axios`依赖：

  ```json
  "dependencies": {
  	"axios": "^1.1.3",
      "core-js": "^3.8.3",
      "element-ui": "^2.15.10",
      "vue": "^2.6.14",
      "vue-router": "^3.5.1"
  }
  ```

- axios封装`request.js`工具类：新建`vue\src\utils`目录。

  ```js
  import axios from 'axios'
  
  const request = axios.create({
      //	设置url
      baseURL: 'http://localhost:9090',  
      timeout: 5000
  })
  
  // request 拦截器
  // 可以自请求发送前对请求做一些处理
  // 比如统一加token，对请求参数统一加密
  request.interceptors.request.use(config => {
      config.headers['Content-Type'] = 'application/json;charset=utf-8';
  
      // config.headers['token'] = user.token;  // 设置请求头
      return config
  }, error => {
      return Promise.reject(error)
  });
  
  // response 拦截器
  // 可以在接口响应后统一处理结果
  request.interceptors.response.use(
      response => {
          let res = response.data;
          // 兼容服务端返回的字符串数据;res中存放的是code,data,msg
          if (typeof res === 'string') {
              res = res ? JSON.parse(res) : res
          }
          return res;
      },
      error => {
          console.log('err' + error) // for debug
          return Promise.reject(error)
      }
  )
  export default request
  ```

### 3.2.2 代码实现

需要对`HomeView.vue`进行增修，以完善功能。

```vue
<template>
  <div>
    <!--    搜索表单    -->
      <div style="margin-bottom: 20px">
        <el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
        <el-input style="width: 240px; margin: 5px" placeholder="请输入联系方式" v-model="params.phone" ></el-input>
        <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
        <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
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
          :current-page="params.pageNum"
          :page-size="params.pagesize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total=total>
      </el-pagination>
    </div>

  </div>
</template>

<script>

//  导入request包，类似于导入axios.js
import request from "@/utils/request";

export default {
  name: 'HomeView',
  data(){
    return {
      tableData: [],
      //  绑定total，默认为0
      total:0,
      //  传入参数
      params: {
        pageNum: 1,
        pagesize: 10,
        name: '',
        phone: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods:{
    //  加载
    load() {
      request.get(
          '/user/page',
          //  传递参数
          {params:this.params})
          .then(res => {
            //  进行判断200再赋值
            if(res.code === '200'){
              //  data.list 才是数据库中的数据
              this.tableData = res.data.list
              //  绑定total
              this.total = res.data.total
            }
          }
      )
    },
    //  重置按钮功能
    reset() {
      this.params = {
        pageNum: 1,
        pagesize: 10,
        name: '',
        phone: ''
      }
      this.load()
    },
    //  点击触发分页效果
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    }

  }
}
</script>
```

注意点：

1. 搜索表单中：

   ```html
   <!--    搜索表单    -->
   <div style="margin-bottom: 20px">
   	<el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
       -- 需要绑定参数v-model，才能输入！
   	<el-input style="width: 240px; margin: 5px" placeholder="请输入联系方式" v-model="params.phone" ></el-input>
       -- 同上
       
   	<el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
       -- 当点击搜索时，无反应，需要查看是否绑定【事件@click】
       
   <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
       
   </div>
   ```

2. 分页中：

   ```html
   <!-- 分页 -->
   <div style="margin-top: 20px">
   	<el-pagination>
   		background
           <!--  分页相关的参数pageNum、pagesize -->
   		:current-page="params.pageNum"
   		:page-size="params.pagesize"
   		layout="prev, pager, next"
           <!-- currentPage 改变时会触发 -->
   		@current-change="handleCurrentChange"
   		:total=total>
   	</el-pagination>
   </div>
   ```

# 4、后台增删改(CDU)

调整前端页面；新建`views\user\User.vue`，将之前的HomeView.vue移植过来。

```vue
<template>
  <div>
    <!--    搜索表单    -->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入姓名" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin: 5px" placeholder="请输入联系方式" v-model="params.phone" ></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <!--  表头  -->
    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <!--   操作（编辑&删除）   -->
      <el-table-column label="操作">
        <template v-slot="scope">
        <!--     scope.row 就是当前行数据     -->
          <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm
              style="margin-left: 8px"
              title="您确定删除吗？"
              @confirm = "del(scope.row.id)">
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pagesize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total=total>
      </el-pagination>
    </div>

  </div>
</template>

<script>

//  导入request包，类似于导入axios.js
import request from "@/utils/request";

export default {
  name: 'User',
    //	此处保持一致
  data(){
    return {
      tableData: [],
      //  绑定total，默认为0
      total:0,
      //  传入参数
      params: {
        pageNum: 1,
        pagesize: 10,
        name: '',
        phone: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods:{
    //  加载
    load() {
      request.get(
          '/user/page',
          //  传递参数
          {params:this.params})
          .then(res => {
                //  进行判断200再赋值
                if(res.code === '200'){
                  //  data.list 才是数据库中的数据
                  this.tableData = res.data.list
                  //  绑定total
                  this.total = res.data.total
                }
              }
          )
    },
    //  重置按钮功能
    reset() {
      this.params = {
        pageNum: 1,
        pagesize: 10,
        name: '',
        phone: ''
      }
      this.load()
    },
    //  点击触发分页效果
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
      // console.log(pageNum)
    },
    //  删除按钮功能
    del(id) {
      request.delete("user/delete/" + id).then(res => {
        if(res.code === '200') {
          this.$notify.success('删除成功')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
          })
    }

  }
}
</script>
<style scoped>
</style>
```

1. 表头新增`id`(编号)、`createtime`(创建时间)、`updatetime`(更新时间)；
2. 新增==操作==（编辑和删除按钮）；
3. 当实现删除按钮时，使用`methods:{}`中的`del()`方法。

在`index.js`中添加路由：

```js
  {
    path: '/user',
    name: 'User',
    component: () => import('@/views/user/User')
  },
```

在`App.vue`中对侧边导航进行微调：

```vue
<!--    侧边栏导航    -->
      <div style="width: 200px;
      min-height: calc(100vh - 82px);
      /*最小行高 100vh占满全屏，82px = 头部height80px + margin-bottom 2px */
      overflow: hidden;
      margin-right: 2px;
      background-color: white">
      <el-menu
          :default-active="$route.path === '/'? $route.path : $route.path.substring(1)"
          :default-openeds="['/']"
          router class="el-menu-demo">

        <el-menu-item index="/">
          <i class="el-icon-s-platform"></i>
          <span>首页</span>
        </el-menu-item>


        <el-submenu index="/">
          <template slot="title">
            <i class="el-icon-more"></i>
            <span>会员管理</span>
          </template>
          <el-menu-item index="addUser">添加会员</el-menu-item>
          <el-menu-item index="user">会员列表</el-menu-item>
          </el-submenu>

        <el-menu-item index="3" disabled>消息中心</el-menu-item>
      </el-menu>
      </div>
```

## 4.1 添加

### 4.1.1 前端

在`index.js`中添加`addUser`路由：

```js
  {
    path: '/addUser',
    name: 'addUser',
    component: () => import('@/views/user/addUser')
  },
```

新建`user\addUser.vue`页面：

```vue
<template>
    <!-- 新增表单 -->
  <div style="margin: 20px;width: 300px">
    <h2 style="margin-bottom: 30px">新增用户</h2>
  <el-form label-width="80px" ref="form" :model="form">
    <el-form-item label="姓名" prop="name">
      <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="sex">
      <el-input v-model="form.sex" placeholder="请输入性别"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="form.address" placeholder="请输入姓名"></el-input>
    </el-form-item>
  </el-form>

      <!--  按钮  -->
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="sava">提交</el-button>
    </div>

  </div>
</template>

<script>

//	导入request
import request from "@/utils/request";

export default {
  name: "addUser",
  data() {
    return {
      form: {}
    }
  },
  methods: {
    //  新增表单（关联后端）
    sava() {
      request.post('/user/sava', this.form).then(
          res => {
            if (res.code === '200') {
              this.$notify.success('新增成功')
              this.form = {}
            } else {
              this.$notify.error(res.msg)
            }
          })
    },

  }
}
</script>
<style scoped>
</style>
```

### 4.1.2 后端

1. `controller`，通过post请求；

   ```java
   //  添加
   @PostMapping("/sava")   
   //这里不加/sava，也可以直接通过"/"访问
   public Result save(@RequestBody User user) {
   	userService.sava(user);
   	return Result.success();
   }
   ```

2. `service`包下

   在`IUserService`中

   ```java
       //  添加
       void sava(User user);
   ```

   在`UserService`中，通过导入依赖实现`username`成为唯一的号码——`hutool`

   ```java
   //  添加
   @Override
   public void sava(User user) {
   	Date date = new Date();
           //  生成卡号（当前时间 + 随机6位数字）
           user.setUsername(DateUtil.format(date,"yyyMMdd") + RandomUtil.randomNumbers(6));
           userMapper.sava(user);
       }
   ```

   - `pom.xml`添加依赖

     ```xml
     <!--    UID-hutool    -->
     	<dependency>
     		<groupId>cn.hutool</groupId>
     		<artifactId>hutool-all</artifactId>
     		<version>5.8.0</version>
     	</dependency>
     ```

3. `UserMapper`中

   ```java
       /**
        * 添加
        * @param user
        */
       void sava(User user);
   ```

   `User.xml`中

   ```xml
   <!--  添加  -->
   <insert id="sava">
           insert into user(name,username,age,sex,phone,address)
           values (#{name},#{username},#{age},#{sex},#{phone},#{address})
    </insert>
   ```

### 4.1.3 SQL表

新增两个属性`reatetime`和`updatetime`；设置`reatetime`特性为`CURRENT_TIMESTAMP`，字段默认值为当前时间。

![image-20221030172828383](./../../../Typora/typora-pic/image-20221030172828383.png)

添加新属性后，实体类`entity\User`中的代码页需要添加：

```java
@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
private Date createtime;
@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
private Date updatetime;
```

## 4.2 更新

### 4.2.1 前端

在`index.js`中添加`editUser`路由：

```js
  {
    path: '/editUser',
    name: 'editUser',
    component: () => import('@/views/user/editUser')
  }
```

创建`editUser.vue`，类似于添加页面，基本相同。

```vue
<template>
  <div style="margin: 20px;width: 300px">
    <h2 style="margin-bottom: 30px">编辑用户</h2>
  <el-form label-width="80px" ref="form" :model="form">
    <el-form-item label="姓名" prop="name">
      <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="sex">
      <el-input v-model="form.sex" placeholder="请输入性别"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="form.address" placeholder="请输入姓名"></el-input>
    </el-form-item>
  </el-form>

      <!--  按钮  -->
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="update">提交</el-button>
<!--      <el-button type="warning" @click="resetForm">重置</el-button>-->
    </div>

  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "addUser",
  data() {
    return {
      form: {}
    }
  },
    //	根据id查询的页面
  created() {
    const id = this.$route.query.id
    request.get("/user/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    //  更新
    update() {
      request.put('/user/update', this.form).then(
          res => {
            if (res.code === '200') {
              this.$notify.success('更新成功')
              this.$router.push("/user")
            } else {
              this.$notify.error(res.msg)
            }
          })
    },
  }
}
</script>
<style scoped>
</style>
```

### 4.2.2 后端

方便写笔记，根据==id查询==与更新写在一起。

1. `controller`包

   ```java
       //  修改（根据id查询）
       @GetMapping("/{id}")
       public Result getById(@PathVariable Integer id) {
           User user = userService.getById(id);
           return Result.success(user);
       }
   
       //  更新
       @PutMapping("/update")
       public Result update(@RequestBody User user) {
           userService.update(user);
           return Result.success();
       }
   ```

2. `service`包下

   在`IUserService`中：

   ```java
       //  修改（根据id查询）
       User getById(Integer id);
   
       //  更新
       void update(User user);
   ```

   在`UserService`中：

   ```java
       //  修改（根据id查询）
       @Override
       public User getById(Integer id) {
           return userMapper.getById(id);
       }
   
       //  更新
       @Override
       public void update(User user) {
           user.setUpdatetime(new Date());
           userMapper.updateById(user);
       }
   ```

3. `UserMapper`中

   ```java
       /**
        * 根据id查询
        * @param id
        * @return
        */
       User getById(Integer id);
   
       /**
        * 更新
        * @param user
        */
       void updateById(User user);
   ```

   `User.xml`中

   ```xml
       <!--  修改（根据id查询）  -->
       <select id="getById" resultType="com.example.springboot.entity.User">
           select * from user where id = #{id}
       </select>
   
       <!--  更新  -->
       <update id="updateById">
           update user set name = #{name},age = #{age},sex = #{sex},phone = #{phone}, address = #{address}, updatetime = #{updatetime}
           where id = #{id}
       </update>
   ```

## 4.3 删除

### 4.3.1 前端

因为删除在操作中，位于`User.vue`中：

- 实现删除按钮：

```vue
      <!--   操作（编辑&删除）   -->
      <el-table-column label="操作">
        <template v-slot="scope">
        <!--     scope.row 就是当前行数据     -->
          <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm
              style="margin-left: 8px"
              title="您确定删除吗？"
              @confirm = "del(scope.row.id)">
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
```

- 关联后端：

```javascript
    //  删除按钮功能
    del(id) {
      request.delete("user/delete/" + id).then(res => {
        if(res.code === '200') {
          this.$notify.success('删除成功')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
          })
    }
```

### 4.3.2 后端

1. `controller`包，删除和更新相同，根据id进行删除。

   ```java
       //  删除
       @DeleteMapping("/delete/{id}")
       public Result delete(@PathVariable Integer id) {
           userService.deleteById(id);
           return Result.success();
       }
   ```

2. `service`包下

   在`IUserService`中：

   ```java
   	//  删除
       void deleteById(Integer id);
   ```

   在`UserService`中：

   ```java
       //  删除
       @Override
       public void deleteById(Integer id) {
           userMapper.deleteById(id);
       }
   ```

3. `UserMapper`中

   ```java
       /**
        * 删除
        * @param id
        */
       void deleteById(Integer id);
   ```

   `User.xml`中

   ```xml
       <!--  删除  -->
       <delete id="deleteById">
           delete from user where id = #{id}
       </delete>
   ```

## 4.4 小结

```java
//	增
@PostMapping

//	删
@DeleteMapping

//	改
@PutMapping

//	查
@GetMapping
```

==_**400就是前端的问题，500就是后端的问题**_==

# 5、表单校验

为了贴合实际，将会对表单数据进行验证，对代码进行优化。（包括：新增用户时对年龄进行判断、联系方式手机号的规定、性别采用单选框）

[https://element.eleme.cn/#/zh-CN/component/form](表单验证)

- 在`editUser`中，表头添加会员卡号`username`，并且设置为不可修改。

  ```vue
  <el-form-item label="会员卡号" prop="name">
  	<el-input v-model="form.username" disabled></el-input>
  </el-form-item>
  ```

  <img src="./pic/note-12.png" alt="image-20221030212035255" style="zoom:50%;" />

- 修改性别`sex`为单选框，并且设置_男_为默认选中。

  ```vue
  <el-form-item label="性别" prop="sex">
  	<el-radio v-model="form.sex" aria-checked="男">男</el-radio>
  	<el-radio v-model="form.sex" label="女">女</el-radio>
  </el-form-item>
  ```

  `aria-checked="男"`相当于`label="男"`吧，后者存在，前者不生效。`addUser`和`editUser`类似相同修改。

- 在`addUser`中，进行表单验证：

  >Form 组件提供了表单验证的功能，只需要通过 `rules` 属性传入约定的验证规则，并将 Form-Item 的 `prop` 属性设置为需校验的字段名即可。

1. 姓名验证：

   ```vue
   <el-form-item label="姓名" prop="name">
   	<el-input v-model="form.name" placeholder="请输入姓名"></el-input>
   </el-form-item>
   ```

   ```javascript
   data() {
       return {
         form: {},
   
         rules: {
   //	此处的name和prop设置的属性对应
           name: [
             { required: true, message: '请输入姓名', trigger: 'blur' },
           ]
           //age : [{...}]       
         },
   
         ruleForm: {
           name: ''
         },
       }
     },
   ```

2. 年龄验证：

   ```vue
   <el-form-item label="年龄" prop="age">
   	<el-input v-model="form.age" placeholder="请输入年龄"></el-input>
   </el-form-item>
   ```

   ```javascript
   data {
   	//  验证年龄数据
       const checkAge = (rule,value,callback) => {
         if (!value) {
           return callback(new Error('年龄不能为空'));
         }
         if (!/^[0-9]+$/.test(value)) {
           callback(new Error('请输入数字'));
         }
         if (parseInt(value) > 120 || parseInt(value) <= 0) {
           callback(new Error('请输入合理的年龄'));
         }
         callback()
       };
   ...
   }
   ```

   ```javascript
   age: [
   		{ validator: checkAge, trigger: 'blur' }
   	 ],
   ```

3. 验证联系方式

   ```vue
   <el-form-item label="联系方式" prop="phone">
         <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
   </el-form-item>
   ```

   ```javascript
   //  验证联系方式
       const checkPhone = (rule, value, callback) => {
         if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
           callback(new Error('请输入合法的手机号'));
         }
         callback()
   	};
   ```

   ```javascript
   phone: [
   		{ validator: checkPhone, trigger: 'blur' }
   	   ]
   ```

- 修改代码——清空表单数据

  ```javascript
      sava() {
        this.$refs['ruleForm'].validate((valid) => {
          if (valid) {
            request.post('/user/sava', this.form).then(
                res => {
                  if (res.code === '200') {
                    this.$notify.success('新增成功')
                    this.$refs['ruleForm'].resetFields()
                      //	this.form={}
                  } else {
                    this.$notify.error(res.msg)
                  }
                })
          }
        })
      },
  ```

- 在新增界面实现了重置按钮（解决了我早上没有实现的功能！！简直是重大突破🙂）

  ```vue
  <el-button type="warning" @click="resetForm('ruleForm')">重置</el-button>
  ```

  ```javascript
  resetForm(ruleForm) {
  	this.$refs['ruleForm'].resetFields();
  }
  ```

  > ​	一定要修改`formName`的值，即在最上面定义的`ref="ruleForm"`的值！

# 6、克隆模块