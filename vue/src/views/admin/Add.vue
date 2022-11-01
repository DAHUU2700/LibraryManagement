<template>
  <div>
    <!-- 新增表单 -->
    <div style="margin: 20px;width: 300px">
      <h2 style="margin-bottom: 30px">新增管理员</h2>
      <el-form label-width="80px" :model="form" :rules="rules" ref="ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
      </el-form>

      <!--  按钮  -->
      <div style="text-align: center;margin-top: 30px">
        <el-button type="primary" @click="sava">提交</el-button>
        <el-button type="warning" @click="resetForm('ruleForm')">重置</el-button>
      </div>

    </div>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "Add",
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

      ruleForm: {
        username: '',
        phone: '',
        email: ''
      },

      rules: {
        //	此处的name、age、phone和prop设置的属性对应
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],

        //  验证联系方式
        phone: [
          { validator: checkPhone, trigger: 'blur' }
        ],

        //  验证邮箱
        email: [
          // { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ]
      },

    }
  },

  methods: {
    //  添加数据（连接后台）
    sava() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/admin/sava', this.form).then(
              res => {
                if (res.code === '200') {
                  this.$notify.success('新增成功')
                  this.$refs['ruleForm'].resetFields()
                } else {
                  this.$notify.error(res.msg)
                }
              })
        }
      })
    },
    resetForm(ruleForm) {
      this.$refs['ruleForm'].resetFields();
    }
  }

}

</script>

<style scoped>

</style>