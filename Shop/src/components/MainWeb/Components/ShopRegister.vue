<!--此组件用于为未注册店铺的商家提供注册UI-->
<template>
    <div id="register" class="register">
        <h1 v-if="this.form.idnumDisabled">店铺信息修改</h1>
        <h1 v-else>店 铺 注 册</h1>

        <Avatar :type="1" ref="avatar" ></Avatar>
        <el-form :model="form" :rules="rules" ref="form" class="form">
            <el-form-item prop="shopname">
                <el-input 
                    placeholder="店名（不超过12个字）" 
                    type="shopname" 
                    v-model="form.shopname" 
                    autocomplete="off"
                    :prefix-icon=" 1 ? 'House' : ''"
                    Clearable
                >
                </el-input>
            </el-form-item>

            <el-form-item label="商品类别" prop="goodstype">
                <el-checkbox-group v-model="form.goodstype">
                    <el-checkbox label="电脑数码" name="digitalproduct" />
                    <el-checkbox label="家用电器" name="householdappliances" />
                    <el-checkbox label="运动户外" name="sports" />
                    <el-checkbox label="服饰鞋包" name="clothing" />
                    <el-checkbox label="个护化妆" name="makeups" />
                    <el-checkbox label="母婴用品" name="babyproduct" />
                    <el-checkbox label="日用百货" name="dailynecessities" />
                    <el-checkbox label="食品生鲜" name="food" />
                    <el-checkbox label="配饰腕表" name="accessory" />
                    <el-checkbox label="图书音像" name="book" />
                    <el-checkbox label="玩模乐器" name="instrument" />
                    <el-checkbox label="办公设备" name="officeequipment" />
                    <el-checkbox label="家居家装" name="furniture" />
                    <el-checkbox label="汽车消费" name="car" />
                    <el-checkbox label="艺术收藏" name="art" />
                    <el-checkbox label="其他" name="others" />
                </el-checkbox-group>
            </el-form-item>

            <el-form-item prop="idnum">
                <el-input 
                placeholder="身份证号" 
                type="idnum" 
                v-model="form.idnum" 
                autocomplete="off"
                :prefix-icon=" 1 ? 'Postcard' : ''"
                Clearable
                :disabled="this.form.idnumDisabled"
                ></el-input>
            </el-form-item>
        
            <el-form-item prop="introduction">
                <el-input 
                    placeholder="店铺简介" 
                    type="introduction" 
                    v-model="form.introduction" 
                    autocomplete="off"
                    :prefix-icon=" 1 ? 'House' : ''"
                    Clearable
                ></el-input>
            </el-form-item>

            <el-form-item prop="address">
                <el-input 
                    placeholder="备案地址" 
                    type="address" 
                    v-model="form.address" 
                    autocomplete="off"
                    :prefix-icon=" 1 ? 'Position' : ''"
                    Clearable
                ></el-input>
            </el-form-item>

            <el-form-item label="启动资金" prop="capital">
                <el-input-number
                    label="启动资金"
                    v-model="form.capital"
                    :controls="false"
                    :precision="2"
                    :min="1000"
                    :prefix-icon=" 1 ? 'Money' : ''"
                    Clearable
                >
                </el-input-number>
                <span class="input-requirement">启动资金不能少于1000，且保留2位小数 </span>
            </el-form-item>

            <el-form-item label="注册日期" prop="date" v-if="!this.form.idnumDisabled">
                <el-date-picker 
                    v-model="form.date" 
                    type="date" 
                    readonly 
                    :default-value="new Date()"
                ></el-date-picker>
            </el-form-item>
            <el-form-item label="注册日期" prop="date" v-else>
                <el-date-picker 
                    v-model="form.date" 
                    type="date" 
                    readonly 
                ></el-date-picker>
            </el-form-item>

            <el-row>
              <el-form-item prop="validCode" >
                <el-input 
                  v-model="form.validCode" 
                  placeholder="验证码"
                  :prefix-icon=" 1 ? 'MessageBox' : ''"
                ></el-input>
              </el-form-item>
                <ValidCode class="validcode" @input="createValidCode" />
            </el-row>

            <el-row>
              <el-col :span="12">
                <el-form-item class="btn_agreement">
                  <el-checkbox v-model="form.agreement" prop="agreement" label="我已阅读并同意遵守" value="agreed" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item class="btn_agreement">
                  <el-button @click="shop_agreements">《店铺守则》</el-button>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row v-if="!this.form.idnumDisabled">
              <el-col :span="12">
                <el-form-item class="btn">
                  <el-button type="primary" @click="register('form')">注&nbsp;&nbsp;册</el-button>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item class="btn">
                  <el-button type="primary" @click="reset">重&nbsp;&nbsp;置</el-button>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row v-else>
              <el-col :span="8">
                <el-form-item class="btn">
                  <el-button type="primary" @click="modifyShopInfo('form')">提交</el-button>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item class="btn">
                  <el-button type="primary" @click="reset">重&nbsp;&nbsp;置</el-button>
                </el-form-item>
              </el-col>
              <el-col v-if="toModify" :span="8">
                <el-form-item class="btn">
                  <el-button type="primary" @click="cancel">返&nbsp;&nbsp;回</el-button>
                </el-form-item>
              </el-col>
            </el-row>
  
        </el-form>
    </div>
  
  </template>
  
<script>
import { ElMessage, ElMessageBox } from 'element-plus';
import { validateIdnum } from "../../../validate";
import "../../../constant.js";
import ValidCode from "./ValidCode.vue";
import ShopDashboard from '../../../views/MainWeb/Shopkeeper/ShopDashboard.vue';
import Avatar from '../../Public/Upload/Avatar.vue';

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
  export default {
    name: "Register",
    props: {
      shop: {
        type: Object
      }
    },
    components:{
      ValidCode,
      Avatar
    },
    created() {
      this.modify();
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
      return {
        form: {
          goodstype: [],
          shopname: "",
          introduction: "",
          address: "",
          idnum: "",
          capital: "",
          date: new Date(),
          username: "",
          agreement: "",
          validCode: "",
          idnumDisabled: false
        },
        toModify: false,
        validCode: "",
        rules: {
          shopname: [
            { required: true, message: '店铺名不能为空', trigger: 'blur' },
            { required: true, message: '店铺名不能为空', trigger: 'change' },
            { required: true, max: 12, message: '店铺名不能超过12个字', trigger: 'blur' },
            { required: true, max: 12, message: '店铺名不能超过12个字', trigger: 'change' }
          ],
          goodstype: [
            { required: true, type: 'array', min: 1, message: '请至少选择一个选项', trigger: 'blur' },
          ],
          idnum: [
            { required: true, validator: idnumValidator, trigger: 'change' },
            { required: true, validator: idnumValidator, trigger: 'blur' }
          ],
          introduction: [
            { required: true, message: '店铺简介不能为空', trigger: 'blur' },
            { required: true, message: '店铺简介不能为空', trigger: 'change' },
            { required: true, max: 128, message: '店铺简介不能超过128个字', trigger: 'blur' },
            { required: true, max: 128, message: '店铺简介不能超过128个字', trigger: 'change' }
          ],
          address: [
            { required: true, message: '备案地址不能为空', trigger: 'blur' },
            { required: true, message: '备案地址不能为空', trigger: 'change' },
            { required: true, max: 32, message: '备案地址不能超过32个字', trigger: 'blur' },
            { required: true, max: 32, message: '备案地址不能超过32个字', trigger: 'change' }
          ],
          captial: [
            { type: 'number', min: 1000, message: '注册资金小于1000', trigger: 'change' }
          ],
          validCode: [
            { required: true, validator: validCodeValidator, trigger: 'change' },
            { required: true, validator: validCodeValidator, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      modify:function() {
        var localStorage = window.localStorage;
        if(localStorage.getItem("toModify")) {
          this.form.shopname = localStorage.getItem('shopname');
          this.form.goodstype = [localStorage.getItem('goodstype')];
          this.form.introduction = localStorage.getItem('introduction');
          this.form.address = localStorage.getItem('address');
          this.form.idnum = localStorage.getItem('idnum');
          this.form.idnumDisabled = true;
          this.form.capital = localStorage.getItem('capital');
          this.form.date = new Date(localStorage.getItem('date'));
          this.toModify = true;
          console.log(this.form.goodstype)
        }
        
      },
      register: function (form) {
        // 首先判断需要填写的信息是否已经完全填入
        this.$refs[form].validate((valid=>{
          if(this.form.agreement==""){
            this.$message.error("必须同意店铺守则");
            return;
          }
          // 校验通过
          if(valid){
            var localStorage = window.localStorage;
            console.log(this.form);
            this.$axios.post('/shopRegister', {
              shopname: this.form.shopname,
              goodstype: this.form.goodstype.join(';'),
              idnum: this.form.idnum,
              introduction: this.form.introduction,
              address: this.form.address,
              capital: this.form.capital.toString(),
              date: this.formatDate(),
              username: localStorage.getItem('username')
            })
            .then(res => {
              // 注册成功
              if(res.data.state == window.SUCCESS){

                // 对于商家，还需要在localStorage中额外储存商店名
                var localStorage = window.localStorage;
                localStorage.setItem("shopname",res.data.data.shopname);
                
                const avatarComponent = this.$refs.avatar;
                avatarComponent.setShopAvatar();

                this.$message.success("注册成功，请耐心等待管理员审批");

                // 店铺注册成功后，导向ShopkeeperWeb主页面
                setTimeout(() => {
                  this.$router.push({name:'ShopDashboardBlank'});
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
          else {
            this.$message.error("注册失败，请按照要求填写所有信息");
          }
        }))
      },
      modifyShopInfo: function (form) {
        // 首先判断需要填写的信息是否已经完全填入
        this.$refs[form].validate((valid=>{
          if(this.form.agreement==""){
            this.$message.error("必须同意店铺守则");
            return;
          }
          // 校验通过
          if(valid){
            var localStorage = window.localStorage;
            console.log(this.form);
            this.$axios.post('/modifyShopInfo', {
              newshopname: this.form.shopname,
              goodstype: this.form.goodstype.join(';'),
              introduction: this.form.introduction,
              address: this.form.address,
              capital: this.form.capital.toString(),
              shopname: localStorage.getItem('shopname')
            })
            .then(res => {
              if(res.data.state == window.SUCCESS){
                this.$message.success("提交成功，请耐心等待管理员审批");

                var localStorage = window.localStorage;
                localStorage.setItem("shopname",res.data.data.shopname);

                localStorage.removeItem('goodstype');
                localStorage.removeItem('introduction');
                localStorage.removeItem('address');
                localStorage.removeItem('idnum');
                localStorage.removeItem('capital');
                localStorage.removeItem('date');

                // 店铺注册成功后，导向ShopkeeperWeb主页面
                setTimeout(() => {
                  this.$router.push({name:'ShopDashboardBlank'});
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
          else {
            this.$message.error("注册失败，请按照要求填写所有信息");
          }
        }))
      },
      reset: function () {
        ElMessageBox.confirm(
          '你确定要重置输入信息吗，将恢复店铺原信息',
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
      confirmReset() {
        this.$refs.form.resetFields(); // 重置表单
        this.form.agreement="";
      },
      shop_agreements: function () {
        ElMessageBox({
          title: '店铺守则',
          message: "我们团队竭诚为您提供服务，确保您对我们的产品更满意、工作更加高效。我们通过提供必要的应用程序努力为您缔造完美体验，确保您的信息安全，并完全公开我们的数据处理方式。同样，我们也请您尊重 Evernote Service 及其他商家，并按照法律规定合法经营，禁止在本平台恶意竞争、倒卖非法物资等。请记住，当您同意服务条款（包括这些准则）时，即表示您同意为您帐户下的任何或所有行为及内容负责。",
          confirmButtonText: '确定'
        })
      },
      createValidCode(data){
        this.validCode = data;
      },
      formatDate() {
        const year = this.form.date.getFullYear(); // 获取年份
        const month = this.addZero(this.form.date.getMonth() + 1); // 获取月份，因为月份从0开始，所以需要+1
        const date = this.addZero(this.form.date.getDate()); // 获取日期
        return `${year}-${month}-${date}`; // 拼接年月日
      },
      addZero(value) {
        return value < 10 ? `0${value}` : value; // 如果数字小于10，就在前面加0，比如9月就变成09月
      },
      cancel() {
        localStorage.removeItem("toModify");
        this.$router.go(0)
      }
    }
}
</script>



<style scoped>
.register{
  margin:20px 60px;
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  color: #303133;
  border: 3px solid #ebeef5;
}

.register h1{
  text-align: center;
  font-family:"Lucida Console", "Courier New", monospace;
  font-size: 45px;
  margin-bottom:20px;
  font-weight: bolder;
  color:#81A18B;
}

.register .el-input {
  font-family: "Brush Script MT", cursive;
  height: 50px;
  padding-top: 5px;
  padding-bottom: 5px;
}

.register .el-form-item{
  margin: 25px 10px 10px 10px;
  text-emphasis:initial;
}


.register .btn button{
  line-height: 100px;
  width: 80%;
  height: 38px;
  background-color:#81A18B;
  border-color:#81A18B;
  justify-items: center;

}

.input-requirement{
  margin-left:50px;
  text-decoration:underline;
}
.validcode{
  margin-top:30px;
}


</style>