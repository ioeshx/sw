<!--此组件为欢迎页面的注册组件-->
<template>
  <div id="register" class="register">
    <div class="wrap">
      <h1>注 册</h1>
      <el-form :model="form" :rules="rules" ref="form" class="form">
        <el-form-item label="用户类型选择" class="typechoose">
          <el-radio-group v-model="form.usertype">
            <el-radio label="普通用户" value=2></el-radio>
            <el-radio label="商户" value=1></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-row>
          <el-col :span="12">
            <el-form-item inline=true prop="username">
              <el-input 
                  placeholder="用户名" 
                  type="username" 
                  v-model="form.username" 
                  autocomplete="off"
                  clearable
                  :prefix-icon=" 1 ? 'User' : ''"
              >
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item prop="phone">
              <el-input 
                placeholder="手机号" 
                type="phone" 
                v-model="form.phone" 
                autocomplete="off"
                clearable
                :prefix-icon=" 1 ? 'Iphone' : ''"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item prop="idnum">
              <el-input 
                placeholder="身份证号" 
                type="idnum" 
                v-model="form.idnum" 
                autocomplete="off"
                clearable
                :prefix-icon=" 1 ? 'Postcard' : ''"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="email">
              <el-input 
                placeholder="邮箱" 
                type="email" 
                v-model="form.email" 
                autocomplete="off"
                clearable
                :prefix-icon=" 1 ? 'Message' : ''"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item prop="password" :class="{'input-error': isPasswordError}">
              <el-input 
                placeholder="密码"
                show-password 
                type="password" 
                v-model="form.password" 
                autocomplete="off"
                clearable
                :prefix-icon=" 1 ? 'Key' : ''"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="password2">
              <el-input
                  placeholder="确认密码"
                  show-password
                  type="password"
                  v-model="form.password2"
                  autocomplete="off"
                  clearable
                  :prefix-icon=" 1 ? 'Key' : ''"
                  @keyup.enter.native="register"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-form-item prop="validCode" >
            <el-input 
              v-model="form.validCode" 
              placeholder="验证码"
              :prefix-icon=" 1 ? 'MessageBox' : ''"
            ></el-input>
          </el-form-item>
            <ValidCode  class="code" @input="createValidCode" />
        </el-row>

        <el-row>
          <el-form-item class="btn_agreement">
            <el-col :span="12">
              <el-checkbox v-model="form.agreement" label="我已阅读并同意遵守" value="agreed" />
            </el-col>
            <el-col :span="12">
              <el-button text @click="open_agreements">《用户守则》</el-button>
            </el-col>
          </el-form-item>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item class="btn_register">
              <el-button type="primary" @click="register('form')" :disabled="isSubmitDisabled">注&nbsp;&nbsp;册</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item class="btn_reset">
              <el-button type="primary" @click="reset">重&nbsp;&nbsp;置</el-button>
            </el-form-item>
          </el-col>
        </el-row>

       

        <el-row>
          <el-form-item>
            <a class="goto" @click="login">已有账号？去登录</a>
          </el-form-item>
        </el-row>

      </el-form>

    </div>
  </div>

</template>

<script>
import "../../constant.js";
import { ElMessage, ElMessageBox } from 'element-plus';
import { validatePhone, validateUsername, validateIdnum, validateEmail, validatePassword } from "../../validate";
import ValidCode from "../MainWeb/Components/ValidCode.vue";

const phoneValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("电话号码不能为空"));
  } else {
    if (validatePhone(value)) {
      callback();
    } else {
      return callback(new Error('电话号码格式不正确'))
    }
  }
};
const usernameValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("用户名不能为空"));
  } else {
    if (validateUsername(value)) {
      callback();
    } else {
      return callback(new Error('用户名格式不正确：仅能出现英⽂字符、数字与下划线；用户名长度为3-10'))
    }
  }
};
const idnumValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("身份证号不能为空"));
  } else {
    if (validateIdnum(value)) {
      callback();
    } else {
      return callback(new Error('身份证号格式不正确'))
    }
  }
};
const emailValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("邮箱不能为空"));
  } else {
    if (validateEmail(value)) {
      callback();
    } else {
      return callback(new Error('邮箱格式不正确'))
    }
  }
};
const passwordValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("密码不能为空"));
  } else {
    if (validatePassword(value)) {
      callback();
    } else {
      return callback(new Error('格式不正确:密码⻓度为6-32个字符; 字⺟，数字或者特殊字符（-_）⾄少包含两种'))
    }
  }
};

export default {
  name: "Register",
  components:{
    ValidCode
  },
  data() {
    var password2Validator = (rule, value, callback) => {
      if(!value) {
        callback(new Error('确认密码不能为空!'));
      }
      if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'));
      } else {
          callback();
      }
    };
    var validCodeValidator = (rule, value, callback) => {
      if(!value) {
        callback(new Error("验证码不能为空"));
      }
      if (this.form.validCode.toLowerCase() === this.validCode.toLowerCase()) {
        callback()
      } else {
        callback(new Error('验证码错误!'));
      }
    };
    return {
      form: {
        usertype: "",
        username: "",
        password: "",
        phone: "",
        idnum: "",
        email: "",
        agreement: "",
        validCode: "",
        dialogFormVisible: false
      },
      validCode: "",
      rules: {
        username: [
          { required: true, validator: usernameValidator, trigger: 'change' },
          { required: true, validator: usernameValidator, trigger: 'blur' }
        ],
        usertype: [
          { required: true, trigger: 'change' },
          { required: true, trigger: 'blur' }
        ],
        phone: [
          { required: true, validator: phoneValidator, trigger: "change" },
          { required: true, validator: phoneValidator, trigger: "blur" }
        ],
        idnum: [
          { required: true, validator: idnumValidator, trigger: 'change' },
          { required: true, validator: idnumValidator, trigger: 'blur' }
        ],
        email: [
          { required: true, validator: emailValidator, trigger: 'change' },
          { required: true, validator: emailValidator, trigger: 'blur' }
        ],
        password: [
          { required: true, validator: passwordValidator, trigger: 'change' },
          { required: true, validator: passwordValidator, trigger: 'blur' }
        ],
        password2: [
          { required: true, validator: password2Validator, trigger: 'change' },
          { required: true, validator: password2Validator, trigger: 'blur' }
        ],
        validCode: [
          { required: true, validator: validCodeValidator, trigger: 'change' },
          { required: true, validator: validCodeValidator, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login: function() {
      this.$router.push({name:'Login'});
    },
    register: function (form) {
      // 首先判断需要填写的信息是否已经完全填入
      this.$refs[form].validate((valid=>{
        console.log(this.form.usertype);
        if(this.form.agreement==""){
          this.$message.error("必须同意用户协议");
          return;
        }
        if(this.form.usertype==""){
          this.$message.error("请选择用户类型");
          return;
        }
        // 校验通过
        if(valid){
          // 将usertype统一为和后端一致的形式
          if(this.form.usertype=="普通用户"){
            this.form.usertype = window.USER;
          } else{
            this.form.usertype = window.SHOPKEEPER;
          }
          this.$axios.post('/userRegister', {
            username: this.form.username,
            password: this.form.password,
            usertype: Number(this.form.usertype),
            phone: this.form.phone,
            idnum: this.form.idnum,
            email: this.form.email
          })
          .then(res => {
            // 注册成功
            if(res.data.state == window.SUCCESS){
              //写入数据：在localStorage中储存用户名和用户类型
              var localStorage = window.localStorage;
              localStorage.setItem("username",this.form.username);
              localStorage.setItem("usertype",this.form.usertype);
              
              this.$message.success("注册成功!   欢迎您："+ localStorage.getItem('username'));

              // 根据不同usertype导入不同的页面
              setTimeout(() => {
                if(this.form.usertype == window.USER) {
                  this.$router.push({name:'UserWeb'});
                } else {
                  this.$router.push({name:'ShopkeeperWeb'});
                }
              }, 1000);
            }
            // 注册失败：此处需要进行split是因为在多处不符合规范时，后端的提示信息是以&分割的一个字符串
            else {
              const error_msg = res.data.message.replace(/&$/, '');
              const msg = error_msg.split("&");
              for(let item of msg) {
                this.$message.error(item);
              }
            }
          })
          .catch(err => {
            console.log(err);
          })
        }
        // 校验不通过
        else {
          this.$message.error("注册失败，请按照要求填写所有信息");
        }
      }))
    },
    reset: function () {
      ElMessageBox.confirm(
        '你确定要重置输入信息吗',
        '', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
        .then(() => {
          ElMessage({
            type: 'success',
            message: '已成功重置',
          })
          this.confirmReset();
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '已取消',
          })
        })
    },
    open_agreements: function () {
      ElMessageBox({
        title: '用户守则',
        message: "我们团队竭诚为您提供服务，确保您对我们的产品更满意、工作更加高效。我们通过提供必要的应用程序努力为您缔造完美体验，确保您的信息安全，并完全公开我们的数据处理方式。同样，我们也请您尊重 Evernote Service 及其他用户，并遵守有关使用Lysw's Mall服务期间禁止事项的相关规定。请记住，当您同意服务条款（包括这些准则）时，即表示您同意为您帐户下的任何或所有行为及内容负责。",
        confirmButtonText: '确定'
      })
    },
    createValidCode(data){
      this.validCode = data;
    },
    setPreference() {
      ElMessageBox({
        title: '用户守则',
        message: "我们团队竭诚为您提供服务，确保您对我们的产品更满意、工作更加高效。我们通过提供必要的应用程序努力为您缔造完美体验，确保您的信息安全，并完全公开我们的数据处理方式。同样，我们也请您尊重 Evernote Service 及其他用户，并遵守有关使用Lysw's Mall服务期间禁止事项的相关规定。请记住，当您同意服务条款（包括这些准则）时，即表示您同意为您帐户下的任何或所有行为及内容负责。",
        confirmButtonText: '确定'
      })
    },
    handleClear(){
      this.$message.success("触发函数");
      this.form.username="";
    },
    confirmReset() {
      this.$refs.form.resetFields(); // 重置表单
      this.form.usertype="";
      this.form.agreement="";
    },

  },
}
</script>

<style scoped>
#register .el-icon-circle-close {
  content: "\ue6ac";
}

#register {
  font-family: 'Noto Serif SC', serif;
  margin-top: 10px;
}

h1{
  margin-top: 10px;
  line-height: 1.8;
  margin-bottom: -15px;
}


/*用户类型选择*/
#register .typechoose{
  bottom :-10px;
  margin-top: -5px
}

/*用户类型选项*/
#register >>> .el-radio-group{
  margin-bottom: 0px;
  font-size: 100px;
}

#register >>> .el-input__inner {
  /*font-family: "Brush Script MT", cursive;*/
  height: 35px;
  padding-top: 5px;
  padding-bottom: 5px;
}


#register >>> .el-form-item{
  margin-bottom: 25px;
  margin-right: 20px;
}

/*外包装框*/
#register .wrap {
  width: 550px;
  height: 100%;
  padding: 0 10px 0 30px;
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

/*注册按钮*/
#register .btn_register {
  margin-top: 0px;
  text-align: center;
}
#register .btn_register button{
  line-height: 0px;
  font-family: "Brush Script MT", cursive;
  width: 200%;
  height: 38px;
  background-color:#81A18B;
  border-color:#81A18B;
}

/*重置按钮*/
#register .btn_reset {
  margin-top: 0px;
  text-align: center;
}
#register .btn_reset button{
  line-height: 0px;
  font-family: "Brush Script MT", cursive;
  width: 100%;
  height: 38px;
  background-color:#81A18B;
  border-color:#81A18B;

}

/*协议按钮*/
#register .btn_agreement{
  text-align: center;
  margin-top: -2px;
  margin-bottom: 10px;
}

#register .btn_agreement button{
  margin-top: -5px;
  margin-left: 20px;
  line-height: 0px;
  font-family: "Brush Script MT", cursive;
  text-decoration: underline;
  text-align: center;

}


/*登录跳转按钮*/
.goto{
  text-align: right;
  text-decoration: underline;
  margin: -15px 10px;
}

#register .el-input__suffix-icon {
  font-size:10px;
  line-height:10px;
  color:#999;
  cursor: pointer;
  float:right;
}

.code{
  font-family: 'Courier New', Courier, monospace;
}
</style>