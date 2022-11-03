<template>
  <div style="height: 100vh ; overflow: hidden;position: relative">

    <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :l="42"
                    :r="10"
                    :w="310"
                    :h="155"
                    :imgs="imgs"
                    :accuracy="6"
                    slider-text="向右滑动"
                    @success="onSuccess"
                    @fail="onFail"
                    @refresh="onRefresh"
      ></slide-verify>
    </el-card>>

    <div style="width: 500px; height: 400px;background-color: white;border-radius: 10px;margin: 150px auto;padding: 50px">
      <div style="margin: 30px;text-align: center;  font-size: 30px; font-weight: bold;color: dodgerblue">
        <span>登　录</span>
        <el-form :model="admin" :rules="rules" ref="loginForm"  >

          <el-form-item prop="username">
            <el-input style="margin-top: 40px" placeholder="请输入用户名" prefix-icon="el-icon-user" seiz="medium" v-model="admin.username"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" seiz="medium" v-model="admin.password"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button style="width: 100%" size="medium" type="primary" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";
import Cookies from 'js-cookie';
import img1 from '@/assets/slide/img1.jpg';
import img2 from '@/assets/slide/img2.jpg';
import img3 from '@/assets/slide/img3.jpg';

export default {
  name: "LOGIN",
  data() {
    return {
      //  滑块相关
      loginAdmin: {},
      imgs: [img1, img2, img3,],
      admin: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ]
      }
    }
   },
      methods: {
        login() {
          this.$refs['loginForm'].validate((valid) => {
            if (valid) {
              request.post('/admin/login', this.admin).then(res => {
                if (res.code === '200') {
                  //  滑块组件
                  this.loginAdmin = res.data
                } else {
                  this.$notify.error(res.msg)
                }
              })
            }
          })
        },
        //  滑块
        onSuccess(){
          //  先存放Cookie数据
          Cookies.set('admin',JSON.stringify(this.loginAdmin))
          this.$notify.success("登录成功")
          //  再跳转
          this.$router.push('/')
        },
        onFail(){
          console.log('onFail')
        },
        onRefresh(){
          console.log('refresh')
        }
      },
    }
</script>

<style scoped>
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}

</style>