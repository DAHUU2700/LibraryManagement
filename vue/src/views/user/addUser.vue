<template>
    <!-- 新增表单 -->
  <div style="margin: 20px;width: 300px">
    <h2 style="margin-bottom: 30px">新增用户</h2>
  <el-form label-width="80px" :model="form" :rules="rules" ref="ruleForm">
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
      <el-radio v-model="form.sex" aria-checked="男">男</el-radio>
      <el-radio v-model="form.sex" label="女">女</el-radio>
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="form.address" placeholder="请输入地址"></el-input>
    </el-form-item>
  </el-form>

      <!--  按钮  -->
    <div style="text-align: center;margin-top: 30px">
      <el-button type="primary" @click="sava">提交</el-button>
      <el-button type="warning" @click="resetForm('ruleForm')">重置</el-button>
    </div>

  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "addUser",
  data() {
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
        name: '',
        age: '',
        phone: '',
        sex: '男',
        address: '',
      },

      rules: {
        //	此处的name、age、phone和prop设置的属性对应
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        age: [
          { validator: checkAge, trigger: 'blur' }
        ],
        phone: [
          { validator: checkPhone, trigger: 'blur' }
        ]
      },

    }
  },

  methods: {
    //  添加数据（连接后台）
    sava() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/user/sava', this.form).then(
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