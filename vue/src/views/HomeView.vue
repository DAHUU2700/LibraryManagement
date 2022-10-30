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
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="phone" label="联系方式"></el-table-column>
        <el-table-column prop="sex" label="性别"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
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
      // console.log(pageNum)
    }

  }
}
</script>
