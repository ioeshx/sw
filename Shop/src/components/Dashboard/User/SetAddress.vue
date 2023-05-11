<template>

  <el-form :model="form" :rules="rules" ref="form" class="form">
    <el-row>
      <el-col :span="12">
        <el-form-item label="收件人姓名" prop="receiverName">
          <el-input
              placeholder="请输入用户名"
              type="receiverName"
              v-model="form.receiverName"
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
        <el-form-item label="省份" prop="province">
          <el-input
              placeholder="请输入省份"
              type="province"
              v-model="form.province"
              autocomplete="off"
              clearable
              :prefix-icon=" 1 ? 'province' : ''"
          ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="市" prop="municipality">
          <el-input
              placeholder="请输入市"
              type="municipality"
              v-model="form.municipality"
              autocomplete="off"
              clearable
              :prefix-icon=" 1 ? 'municipality' : ''"
          ></el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="县" prop="county">
          <el-input
              placeholder="请输入县"
              type="county"
              v-model="form.county"
              autocomplete="off"
              clearable
              :prefix-icon=" 1 ? 'county' : ''"
          ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="镇" prop="township">
          <el-input
              placeholder="确认输入镇"
              type="township"
              v-model="form.township"
              autocomplete="off"
              clearable
              :prefix-icon=" 1 ? 'township' : ''"
          ></el-input>
        </el-form-item>
      </el-col>
    </el-row>


    <el-row>
      <el-form-item label="具体地址" prop="detailAddress" >
        <el-input
            v-model="form.detailAddress"
            type="detailAddress"
            placeholder="请输入具体的地址"
            autocomplete="off"
            clearable
            :prefix-icon=" 1 ? 'detailAddress' : ''"
        ></el-input>
      </el-form-item>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item>
          <el-button class="btn"  type="primary" @click="setAddress('form')">提交</el-button>
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
    import {validatePhone} from "../../../validate";
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
    };  const receiverNameValidator = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("收件人姓名不能为空"));
      } else {callback();

      }
    };const provinceValidator = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("省份不能为空"));
      } else {
          callback();
      }
    };const municipalityValidator = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("市不能为空"));
      } else {
        callback();
      }
    };const detailAddressValidator = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("具体地址不能为空"));
      } else {
        callback();
      }
    };
    export default {
      name: "SetAddress",
      components: {
      },
      data() {

        return {
          form: {
            username: "",
            receiverName: "",
            phone: "",
            province: "",
            municipality: "",
            county: "",
            township: "",
            detailAddress: ""
          },
          validCode: "",
          rules: {
            username: [
              {required: true, trigger: 'change'},
              {required: true, trigger: 'blur'}
            ],
            receiverName: [
              {required: true, validator:receiverNameValidator, trigger: 'change'},
              {required: true, validator:receiverNameValidator, trigger: 'blur'}
            ],
            phone: [
              {required: true, validator: phoneValidator, trigger: "change"},
              {required: true, validator: phoneValidator, trigger: "blur"}
            ],
            province: [
              {required: true, validator: provinceValidator,trigger: 'change'},
              {required: true, validator: provinceValidator,trigger: 'blur'}
            ],
            municipality: [
              {required: true, validator: municipalityValidator,trigger: 'change'},
              {required: true, validator: municipalityValidator,trigger: 'blur'}
            ],
            county: [
              {required: false, trigger: 'change'},
              {required: false, trigger: 'blur'}
            ],
            township: [
              {required: false, trigger: 'change'},
              {required: false, trigger: 'blur'}
            ],
            detailAddress: [
              {required: true, validator: detailAddressValidator,trigger: 'change'},
              {required: true, validator: detailAddressValidator,trigger: 'blur'}
            ]
          }
        }
      },
      methods: {
        setAddress: function (form) {
          // 首先判断需要填写的信息是否已经完全填入
          this.$refs[form].validate((valid => {
            // 校验通过
            if (valid) {
              // 将usertype统一为和后端一致的形式
              this.$axios.post('/addReceiverAddress', {
                username: localStorage.getItem("username"),
                receiverName: this.form.receiverName,
                phone: this.form.phone,
                province: this.form.province,
                municipality: this.form.municipality,
                county: this.form.county,
                township: this.form.township,
                detailAddress: this.form.detailAddress,
              })
                  .then(res => {
                    console.log(res.data.state)
                    console.log(window.SUCCESS)
                    if (res.data.state == window.SUCCESS) {
                      this.$message.success(res.data.message);
                      this.$router.go(0);
                    } else {
                      const error_msg = res.data.message.replace(/&$/, '');
                      const msg = error_msg.split("&");
                      for (let item of msg) {
                        this.$message.error(item);
                      }
                    }
                  })
            }
            // 校验不通过
            else {
              this.$message.error("地址填写失败，请按照要求填写所有信息");
            }
          }))
        },     cancel() {
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