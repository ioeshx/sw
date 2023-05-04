<!--个人信息界面-->

<template>
  
    <el-form :model="form" :rules="rules" ref="form" class="form">
        <Avatar :type="2"></Avatar>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
                <el-input 
                    placeholder="请输入用户名"
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
            <el-form-item label="手机号" prop="phone">
                <el-input 
                    placeholder="请输入手机号码"
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
            <el-form-item label="身份证号" prop="idnum">
                <el-input 
                    placeholder="请输入身份证号"
                    type="idnum" 
                    v-model="form.idnum" 
                    autocomplete="off"
                    clearable
                    :prefix-icon=" 1 ? 'Postcard' : ''"
                    :disabled="true"
                ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
                <el-input 
                    placeholder="请输入邮箱"
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
            <el-form-item label="密码" prop="password">
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
            <el-form-item label="确认密码" prop="password2">
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
            <el-form-item label="验证码" prop="validCode" >
                <el-input 
                v-model="form.validCode" 
                placeholder="验证码"
                :prefix-icon=" 1 ? 'MessageBox' : ''"
                ></el-input>
            </el-form-item>
            <ValidCode @input="createValidCode" />
        </el-row>
  
        <el-row>
          <el-col :span="12">
            <el-form-item>
                <el-button class="btn"  type="primary" @click="setUserInfo('form')">提交</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item>
                <el-button class="btn"  type="primary" @click="cancel">取消</el-button>
            </el-form-item>
          </el-col>
        </el-row>
    </el-form>
  </template>
  
  <script>
  import "../../../constant.js";
  import { ElMessage, ElMessageBox } from 'element-plus';
  import { validatePhone, validateUsername, validateEmail, validatePassword } from "../../../validate";
  import ValidCode from "../../MainWeb/Components/ValidCode.vue";
  import Avatar from "../../Public/Upload/Avatar.vue";
  
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
  
  export default {
    name: "SetUserInfo",
    components:{
      ValidCode,
      Avatar
    },
    data() {
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
      return {
        form: {
          username: "",
          phone: "",
          idnum: "",
          email: "",
          validCode: "",
          password: "",
          password2: ""
        },
        user: {
          oldusername: "",
          oldphone: "",
          oldemail: "",
          oldpassword: ""
        },
        validCode: "",
        idnumDisabled: true,
        rules: {
          username: [
            { required: true, validator: usernameValidator, trigger: 'change' },
            { required: true, validator: usernameValidator, trigger: 'blur' }
          ],
          phone: [
            { required: true, validator: phoneValidator, trigger: "change" },
            { required: true, validator: phoneValidator, trigger: "blur" }
          ],
          email: [
            { required: true, validator: emailValidator, trigger: 'change' },
            { required: true, validator: emailValidator, trigger: 'blur' }
          ],
          validCode: [
            { required: true, validator: validCodeValidator, trigger: 'change' },
            { required: true, validator: validCodeValidator, trigger: 'blur' }
          ],
          password: [
            { required: true, validator: passwordValidator, trigger: 'change' },
            { required: true, validator: passwordValidator, trigger: 'blur' }
          ],
          password2: [
            { required: true, validator: password2Validator, trigger: 'change' },
            { required: true, validator: password2Validator, trigger: 'blur' }
          ]
        }
      }
    },
    created() {
        this.getUserInfo();
    },
    methods: {
      getUserInfo() {
        var localStorage = window.localStorage;
        this.$axios.post('/getUserInfo', {
            username: localStorage.getItem("username")
        })
        .then(res => {
            const user = res.data.data;
            this.form.username = user.username;
            this.form.phone = user.phone;
            this.form.idnum = user.idnum;
            this.form.email = user.email;
            this.form.password = user.password;
            this.form.password2 = user.password;
            this.user.oldusername = user.username;
            this.user.oldphone= user.phone;
            this.user.oldemail= user.email;
            this.user.oldpassword= user.password;
        })
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
      setUserInfo: function(form) {
        this.$refs[form].validate((valid=>{
            if(valid) {
                var localStorage = window.localStorage;
                if(this.user.oldusername == this.form.username && this.user.oldphone == this.form.phone && this.user.newemail == this.form.email && this.user.password == this.user.newpassword) {
                  this.$message.error("您没有修改任何信息，请修改信息后重新提交");
                  return;
                }
                this.$axios.post('/setUserInfo', {
                    // idnum不传输
                    oldusername: localStorage.getItem("username"),
                    newusername: this.form.username,
                    newphone: this.form.phone,
                    newemail: this.form.email,
                    newpassword: this.form.password
                })
                .then(res => {
                  console.log(res.data.state)
                  console.log(window.SUCCESS)
                    if(res.data.state == window.SUCCESS){
                        this.$message.success(res.data.message);
                        var localStorage = window.localStorage;
                        localStorage.setItem("username", this.form.username);
                        this.$router.go(0);
                    } else {
                        const error_msg = res.data.message.replace(/&$/, '');
                        const msg = error_msg.split("&");
                        for(let item of msg) {
                            this.$message.error(item);
                        }
                    }
                })
            } else {
                this.$message.error("修改失败，请按照要求填写所有信息");
            }
        }))
      },
     createValidCode(data){
        this.validCode = data;
    },
     confirmReset() {
        this.$refs.form.resetFields(); // 重置表单
        this.form.usertype="";
        this.form.agreement="";
     },
     cancel() {
        this.$router.go(0);
     }
    }
  }
  </script>
  
<style scoped>
.form{
  margin:50px;
  gap:20px;
}

.el-form-item{
  margin:20px;
}



.form .btn {
  line-height: 100px;
  width: 100%;
  height: 38px;
  background-color:#81A18B;
  border-color:#81A18B;
  justify-items: center;

}
</style>