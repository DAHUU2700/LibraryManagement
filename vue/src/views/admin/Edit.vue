<template>
  <div>
    <div style="margin: 20px;width: 300px">
      <h2 style="margin-bottom: 30px">编辑管理员</h2>
      <el-form label-width="80px" ref="form" :model="form" :rules="rules">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入地址"></el-input>
        </el-form-item>
      </el-form>

      <!--  按钮  -->
      <div style="text-align: center;margin-top: 30px">
        <el-button type="primary" @click="update">提交修改</el-button>
      </div>

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Edit",
  data() {
    //  验证联系方式
    const checkPhone = (rule, value, callback) => {
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
        callback(new Error('请输入合法的手机号'));
      }
      callback()
    };

    return {
      form: {},
      rules: {
        phone: [ { validator: checkPhone, trigger: 'blur' } ]
      }
    }
  },

  created() {
    const id = this.$route.query.id
    request.get("/admin/" + id).then(res => {
      this.form = res.data
    })
  },

  methods: {
    //  更新表单
    update() {
      request.put('/admin/update', this.form).then(
          res => {
            if (res.code === '200') {
              this.$notify.success('更新成功')
              this.$router.push("/adminList")
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