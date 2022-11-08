<template>
  <div>
    <!--    搜索表单    -->
    <div style="margin-bottom: 20px;margin-top: 10px;margin-left: 10px">
      <el-input style="width: 240px" placeholder="请输入姓名" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
      <el-button style="margin-left: 8px" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>

    <!--  表头  -->
    <el-table :data="tableData" stripe
              :header-cell-style="{
              'background-color': '#f0f3ff',
              'color': 'rgb(0,0,0)',
              'border-top': '1px rgb(64, 158, 255) solid',
              'border-bottom': '1px rgb(64, 158, 255) solid',
               }">
    <el-table-column prop="id" label="编号" width="50" align="center"></el-table-column>
      <el-table-column prop="username" label="会员卡号" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100" align="center"></el-table-column>
      <el-table-column prop="age" label="年龄" width="50" align="center"></el-table-column>
      <el-table-column prop="phone" label="联系方式" align="center"></el-table-column>
      <el-table-column prop="sex" label="性别" width="50" align="center"></el-table-column>
      <el-table-column prop="address" label="地址" align="center"></el-table-column>
      <el-table-column prop="account" label="账号积分" align="center"></el-table-column>

      <el-table-column label="状态" align="center">
        <template v-slot="scope">
          <el-switch
              v-model="scope.row.status"
              @change="changeStatus(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <!--   操作（编辑&删除）   -->
      <el-table-column label="操作" width="300">
        <template v-slot="scope">
          <!--     scope.row 就是当前行数据     -->
          <el-button type="success" @click="handleAccoutAdd(scope.row)">充值</el-button>
          <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)" class="el-icon-setting">编辑
          </el-button>
          <el-popconfirm
              style="margin-left: 8px"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)">
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

    <!--  充值  -->
    <el-dialog title="充值" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="ruleForm" :rules="rules" style="width: 85%">

        <el-form-item label="当前用户积分" prop="account">
          <el-input disabled v-model="form.account" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="充值积分" prop="score">
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAccount">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

//  导入request包，类似于导入axios.js
import request from "@/utils/request";

export default {
  name: 'User',
  data() {
    const checkAccount = (rule, value, callback) => {
      value = parseInt(value)
      if (value < 0 || value > 500) {
        callback(new Error('充值金额必须大于10或小于500'));
      }
      callback()
    };
    return {
      tableData: [],
      //  绑定total，默认为0
      total: 0,
      //  传入参数
      params: {
        pageNum: 1,
        pagesize: 10,
        name: '',
        phone: ''
      },
      dialogFormVisible: false,
      form: {
        account:0
      },
      rules: {
        score: [
          {required: true, message: '请输入充值积分', trigger: 'blur'},
          {validator: checkAccount, trigger: 'blur'}
        ]
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    //  加载
    load() {
      request.get(
          '/user/page',
          //  传递参数
          {params: this.params})
          .then(res => {
                //  进行判断200再赋值
                if (res.code === '200') {
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
        if (res.code === '200') {
          this.$notify.success('删除成功')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    //  添加账号余额
    handleAccoutAdd(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    //  提交充值
    addAccount() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/user/account', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('充值成功')
              this.dialogFormVisible=false
              this.load()
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
    //  用户状态
    changeStatus(row) {
      request.put('/user/update', row).then(res => {
        if (res.code === '200') {
          this.$notify.success('操作成功')
          this.load()
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