<!--此组件为欢迎页面的登录组件-->
<template>
  <div id="login" class="login">
    <div class="wrap">
      <h1>登 录</h1>
      <el-form :model="form" ref="form" class="form">
        <el-form-item prop="username">
          <el-input 
            placeholder="账号（用户名/手机号/邮箱）" 
            type="username" 
            v-model="form.username" 
            :prefix-icon=" 1 ? 'User' : ''"
            clearable
            autocomplete="off">
          </el-input>
        </el-form-item>
        <el-form-item id="password" prop="password">
          <el-input
              placeholder="密码"
              show-password
              type="password"
              v-model="form.password"
              autocomplete="off"
              :prefix-icon=" 1 ? 'Key' : ''"
              clearable
              @keyup.enter.native="login"
          ></el-input>

        </el-form-item>
        <el-form-item class="btn_login">
          <el-button type="primary" @click="login">登&nbsp;&nbsp;录</el-button>
        </el-form-item>
        <div class="goto">
          <el-form-item>
              <a class="gotochild" @click="register">忘记密码</a>
            </el-form-item>
          <el-form-item>
            <a class="gotochild" @click="register">没有账号？去注册</a>
          </el-form-item>
        </div>
        
      </el-form>
    </div>
  </div>
</template>

<script>
import "../../constant.js"
export default {
  name: "Login",
  data() {
    return {
      form: {
        username: "",
        password: ""
      }
    }
  },
  created(){
    this.clearLocalStorage();
  },
  methods: {
    login: function () {
      if(this.form.username==""){
        this.$message.error("登录失败：请输入账号")
      }
      else if(this.form.password==""){
        this.$message.error("登录失败：请输入密码")
      }
      else{
        // 这里的username可能是用户名/邮箱/手机号，由后端判断输入的类型并检测
        this.$axios.post('/userlogin', {
          username: this.form.username,
          password: this.form.password
        })
        .then(res => {
          const user = res.data.data;
          if(res.data.state == window.SUCCESS) {
            //写入数据:在localStorage中储存用户的username和usertype
            var localStorage = window.localStorage;
            localStorage.setItem("username",this.form.username);
            localStorage.setItem("usertype",user.usertype);
            
            this.$message.success(res.data.message);
            // this.$message.success("登录成功！欢迎您：" + localStorage.getItem("username"));

            // 根据不同usertype导入不同的页面
            setTimeout(() => {
              switch (user.usertype) {
                case window.USER:
                  this.$router.push({name:'UserWeb'});
                  break;
                case window.SHOPKEEPER:
                  this.$router.push({name:'ShopkeeperWeb'});
                  break;
                case window.MANAGER:
                  this.$router.push({name:'ManagerWeb'});
                  break;
                default:
                  this.$message.error("连接错误，请重新登录！");
                  this.$router.push({name:'Login'});
              }
            }, 1000);
            
          }
          else {
            this.$message.error(res.data.message);
          }
        })
          .catch(err => {
            console.log(err);
          })
      }
    },
    register: function() {
      this.$router.push({name:'Register'});
    },
    clearLocalStorage() {
      localStorage.clear();
    }
  },
}
</script>



<style scoped>
#login {
  font-family: 'Noto Serif SC', serif;
  margin-top: 100px;
}
#login >>> .el-input__inner {
  /*font-family: "Brush Script MT", cursive;*/
  height: 35px;
  padding-top: 5px;
  padding-bottom: 5px;
}
#login .wrap {
  width: 300px;
  height: 350px;
  padding: 0 25px 0 25px;
  line-height: 100px;
  position: relative;
  display: inline-block;
  background-color: #fff;
  border-radius: 15px;
  color: #303133;
  border: 2px solid #ebeef5;
  box-shadow: 2px 2px 12px 2px rgb(0 0 0 / 10%);
  transition: .3s;
  font-family: "Brush Script MT", cursive;
}
#login .btn_login {
  margin-top: 25px;
  text-align: center;
}
#login .btn_login button{
  line-height: 100px;
  font-family: "Brush Script MT", cursive;
  width: 100%;
  height: 38px;
  background-color:#81A18B;
  border-color:#81A18B;
}

.goto{
  display: flex;
  text-align: right;
}

.gotochild{
  margin: 0px 15px 0px 13px;
  text-decoration: underline;
  text-align: right;
}



#login .suffix {
  font-size:10px;
  line-height:10px;
  color:#999;
  cursor: pointer;
  float:right;
}
</style>