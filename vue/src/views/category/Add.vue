<template>
  <div style="margin: 20px;width: 300px">
    <h2 style="margin-bottom: 30px">新增分类</h2>
    <el-form :rules="rules" ref="ruleForm" :model="form" label-width="80px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
      </el-form-item>
    </el-form>

    <div style="text-align: center; margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">提交</el-button>
      <el-button type="warning" @click="resetForm('ruleForm')">重置</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'AddCategory',
  data() {
    return {
      form: {},
      ruleForm: {
        username: '',
        phone: '',
        email: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/category/save', this.form).then(res => {
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

