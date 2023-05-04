<!--此组件为商家看到的本店铺的注册信息展示-->
<template>
  <div class="wrap">
    <div :shop="shop" class="shop">
        <img v-if="shop.avatar" :src="'data:image/jpeg;base64,' + shop.avatar" class="logo">
        <img v-else class="logo" src="@/assets/shop.png"/>

        <div class="info1">
          <p>店名：{{shop.shopname}}</p>
          <p>类别：{{shop.goodstype}}</p>
          <p>简介：{{shop.introduction}}</p>
          <p>注册资金：{{shop.capital}}</p>
        </div>

        <div class="info2">
          <el-row>
            <el-col :span="12">
              <p>注册人身份证号：{{shop.idnum}}</p>
            </el-col>
            <el-col :span="12">
              <p>备案地址：{{shop.address}}</p>
            </el-col>
          </el-row>
        </div>

        <div class="info3">
          <el-row>
            <el-col :span="12">
              <p>注册日期：{{shop.date}}</p>
            </el-col>
            <el-col :span="12">
              <p v-if="shop.is_admitted == 1">店铺状态：已注册</p>
              <p v-if="shop.is_admitted == 0">店铺状态：管理员审核注册申请中</p>
              <p v-if="shop.is_admitted == 2">店铺状态：注册申请已拒绝，请修改店铺信息后重新提交注册申请</p>
              <p v-if="shop.is_admitted == 3">店铺状态：管理员审核删除申请中</p>
              <p v-if="shop.is_admitted == 4">店铺状态：删除申请已拒绝，请撤销申请并修改信息后重新提交申请</p>
            </el-col>
          </el-row>
        </div>

        <div class="info4">
          <!-- 撤销注册申请：注册申请中 -->
          <el-form-item v-if="shop.is_admitted==0||shop.is_admitted==2" class="re_btn">
              <el-button type="submit" @click="dialogTableVisible = true">撤销注册申请</el-button>
              <el-dialog v-model="dialogTableVisible" title="撤销注册申请确认" class="deleteConfirm">
                <p>你确定要撤销注册店铺申请吗？</p>
                <div class="deleteConfirmBtn">
                  <el-button class="delete" type="primary" @click="cancelRegister">确认</el-button>
                  <el-button class="delete" @click="dialogTableVisible = false">我再想想</el-button>
                </div>
              </el-dialog>
            </el-form-item>

          <!-- 修改注册信息btn：注册申请中/正常状态/注册失败 -->
          <el-form-item v-if="shop.is_admitted<3" class="re_btn">
            <el-button type="submit" @click="modifyShopInfo">修改注册信息</el-button>
          </el-form-item>

          <!-- 删除店铺btn：正常状态 -->
          <el-form-item v-if="shop.is_admitted==1" class="re_btn">
              <!--点击后弹框，需要根据弹框中点击的内容决定是否给后端发送删除申请-->
              <el-button type="submit" @click="dialogTableVisible = true">申请删除</el-button>
              <el-dialog  class="deleteConfirm" v-model="dialogTableVisible" title="删除确认">
                <p>你确定要删除你的店铺吗？删除后，店铺下的商品也将被清除！</p>
                <div class="deleteConfirmBtn">
                  <el-button class="delete" type="primary" @click="unregisterConfirmed">确认</el-button>
                  <el-button class="delete" @click="dialogTableVisible = false">我再想想</el-button>
                </div>
              </el-dialog>
            </el-form-item>

            <!-- 撤销删除申请btn：删除申请审核中/拒绝删除申请 -->
            <el-form-item v-if="shop.is_admitted==3||shop.is_admitted==4" class="re_btn">
              <el-button type="submit" @click="dialogTableVisible = true">撤销删除申请</el-button>
              <el-dialog v-model="dialogTableVisible" title="撤销删除申请确认" class="deleteConfirm">
                <p>你确定要撤销删除店铺申请吗？</p>
                <div class="deleteConfirmBtn">
                  <el-button class="delete" type="primary" @click="cancelRegister">确认</el-button>
                  <el-button class="delete" @click="dialogTableVisible = false">我再想想</el-button>
                </div>
              </el-dialog>
            </el-form-item>
          </div>

        

    </div>
  </div>
</template>
  
<script>
import '../../../constant'
export default {
  props: {
    shop: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      dialogTableVisible: false
    }
  },
  methods: {
    modifyShopInfo() {
      var localStorage = window.localStorage;
      localStorage.setItem("toModify",1);
      localStorage.setItem("shopname",this.shop.shopname);
      localStorage.setItem("goodstype",this.shop.goodstype);
      localStorage.setItem("introduction",this.shop.introduction);
      localStorage.setItem("address",this.shop.address);
      localStorage.setItem("idnum",this.shop.idnum);
      localStorage.setItem("capital",this.shop.capital);
      localStorage.setItem("date",this.shop.date);
      this.$router.go(0);
    },
    unregisterConfirmed() {
      this.$axios.post('/shopUnregister',{
        shopname: localStorage.getItem("shopname")
      }).then(res=>{
        console.log("shopUregister")
        console.log(res.data)
        if(res.data.state==window.SUCCESS){
          this.$message.success(res.data.message);
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        } else {
          this.$message.error(res.data.message);
        }
      })
      this.dialogTableVisible = false;
    },
    cancelRegister() {
      this.$axios.post('/cancelRegister',{
        shopname: localStorage.getItem("shopname"),
        cancelType: this.shop.is_admitted
      }).then(res=>{
        if(res.data.state==window.SUCCESS){
          this.$message.success(res.data.message);
        } else {
          this.$message.error(res.data.message);
        }
      })
      //this.$router.go(0);
    }
  }
}
</script>


<style scoped>
.wrap{
  padding: 20px 30px 10px 30px;
  margin: 10px 20px 20px 20px;
  line-height: 100px;
  align-items: center;
  display: flex;
  flex-wrap: wrap;
  overflow: auto;
  background-color: #fff;
  border-radius: 15px;
  color: #303133;
  border: 3px solid #ebeef5;
  transition: .3s;
}

.shop{
  width: 100%;
  height: 100%;
  margin: 50px 40px 40px 60px;

  display: grid;
  grid-template-columns: repeat(50px, 6);
  grid-template-rows: repeat(10%, 10);
  grid-column-gap:0px;
  grid-row-gap:10px;
  font-size: 20px;
/*
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    transition: .3s;
  */
}

.shop .logo{
    grid-row: 1/3;
    grid-column: 1/4;
    width: 70%;
    height: 70%;
    background-color: #303133;
}


.shop .info1{
  margin-left: 20px;
  grid-row: 1/3;
  grid-column: 4/11;
  margin-top: -15px;
}
.shop .info1 p{
  margin-bottom: -10px;
}



.shop .info2{
  padding-left: 25px;
  grid-row: 4/5;
  grid-column: 1/11;
  line-height: 30px;
}


.shop .info3{
  padding-left: 25px;
  grid-row: 5/6;
  grid-column: 1/11;
  margin-bottom: 50px;
}

.shop .info4{
  grid-row: 6/7;
  grid-column: 1/11;
  display: flex;
  justify-content: center;
  align-items: center
}

.re_btn{
  margin-bottom: 25px;
  margin-right: 20px;
  margin-top: 0px;
  border-color:#81A18B;
}
.re_btn button{
  line-height: 4px;
  width: auto;
  height: 38px;
  border-color:#81A18B;
  font-size: 20px;
  padding: 30px;
}




.deleteConfirm p{
  font-size:20px;
  margin-bottom: 30px;
  font-family: "Brush Script MT", cursive;
}

.deleteConfirm .deleteConfirmBtn{
  display: flex;
  margin: 20px auto;
}

.deleteConfirm .deleteConfirmBtn .delete{
  font-family: "Brush Script MT", cursive;
  border-color:gray;
  padding: 10px;
  font-size: large;
  font-size: 20px;
  margin: 20px auto;
  width: 40%;
}


</style>