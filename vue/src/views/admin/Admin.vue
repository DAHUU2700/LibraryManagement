<template>
  <div>
    <!--    搜索表单    -->
    <div style="margin-bottom: 10px;margin-top: 10px;margin-left: 10px">
      <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
      <el-input style="width: 240px; margin: 6px" placeholder="请输入联系方式" v-model="params.phone" ></el-input>
      <el-input style="width: 240px;" placeholder="请输入邮箱" v-model="params.email" ></el-input>
      <el-button style="margin-left: 8px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 6px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <!--  表头  -->
    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="编号" width="60"></el-table-column>
      <el-table-column prop="username" label="用户名" width="150"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <!--   操作（编辑&删除）   -->
      <el-table-column label="操作">
        <template v-slot="scope">
          <!--     scope.row 就是当前行数据     -->
          <el-button type="primary" @click="$router.push('/Edit?id=' + scope.row.id)" class="el-icon-setting">编辑</el-button>
          <el-popconfirm
              style="margin-left: 8px"
              title="您确定删除吗？"
              @confirm = "del(scope.row.id)">
            <el-button type="danger" slot="reference" class="el-icon-delete">删除</el-button>
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
//  导入request包
import request from "@/utils/request";

export default {
  name: "Admin",
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
        phone: '',
        email: ''
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
          '/admin/page',
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
        phone: '',
        email: ''
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
      request.delete("admin/delete/" + id).then(res => {
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