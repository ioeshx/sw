<!--此组件将商家注册信息打包，并用于给管理员展示注册信息-->
<script setup>
import { reactive, ref } from 'vue'
const dialogTableVisible = ref(false)
</script>


<template>
  <div class="shop" :shop="shop">
    <a href="#" class="shop_link">
      <img v-if="!shop.avatar" class="logo" src="@/assets/shop.png" alt="点击进入店铺详情页面"/>
      <img v-else :src="'data:image/jpeg;base64,' + shop.avatar" class="logo">

      <p class="shopName">{{shop.shopname}}</p>
      <p class="status" v-if="shop.is_admitted==0">申请类型：注册申请</p>
      <p class="status" v-if="shop.is_admitted==3">申请类型：删除申请</p>
      <p class="status" v-if="shop.is_admitted==1">批复状态：已同意注册</p>
      <p class="status" v-if="shop.is_admitted==2||shop.is_admitted==4">批复状态：已拒绝</p>

      <el-button class="btn_approval" v-if="isregistered=1" @click="dialogTableVisible = true">
        <p>点击查看详细信息</p>
      </el-button>

      <el-dialog  class="information" v-model="dialogTableVisible" title="商家信息">
        <div class="infoTop">
          <p v-if="shop.is_admitted==0">申请类型：注册申请</p>
          <p v-if="shop.is_admitted==3">申请类型：删除申请</p>
          <p v-if="shop.is_admitted==1">批复状态：已同意注册</p>
          <p v-if="shop.is_admitted==2||shop.is_admitted==4">批复状态：已拒绝</p>
        </div>
        <p>店名：{{shop.shopname}}</p>
        <p>店铺id：{{shop.shopid}}</p>
        <p>类别：{{shop.goodstype}}</p>
        <p>启动资金：{{shop.capital}}</p>
        <p>注册日期：{{shop.date}}</p>
        <p>备份地址：{{shop.address}}</p>
        <p>负责人用户id：{{shop.uid}}</p>
        <p>负责人身份证号码：{{shop.idnum}}</p>
        <p class="bottom">简介：{{shop.introduction}}</p>
        <template #footer v-if="shop.is_admitted==0||shop.is_admitted==3">
          <span class="dialog-footer">
            <el-button type="primary" @click="shopApplicationApproved">批准</el-button>
            <el-button  @click="shopApplicationRejected">拒绝</el-button>
          </span>
        </template>
      </el-dialog>
          
          
    </a>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus';
import "../../../constant.js";
export default {
  props: {
    shop: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      shopid: this.shop.shopid
    }
  },
  methods: {
    shopApplicationApproved: function() {
      console.log(this.shop.shopname)
      console.log(this.shop.is_admitted)
      this.$axios.post('/shopApplicationApproved', {
          shopname: this.shop.shopname,
          is_admitted: this.shop.is_admitted
        })
      .then(res=>{
        console.log(res.data.state)
        if(res.data.state == window.SUCCESS){
          this.$message.success("已同意店铺申请！");
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        }
        else {
          this.$message.error(res.data.message);
        }
      })
    },
    shopApplicationRejected: function() {
      this.$axios.post('/shopApplicationRejected', {
          shopname: this.shop.shopname,
          is_admitted: this.shop.is_admitted
        })
      .then(res=>{
        if(res.data.state == window.SUCCESS){
          this.$message.success(res.data.message);
          setTimeout(() => {
            this.$router.push({name:'ManagerWebBlank'});
          }, 1000);
        }
        else {
          this.$message.error(res.data.message);
        }
      })
    }
  }
}
</script>

<style scoped>
.shop_link{
    width: 90%;
    height: 220px;
    padding: 20px 30px 10px 20px;
    margin: 10px 20px 20px 20px;
    line-height: 100px;
    align-items: center;

    display: grid;
    grid-template-columns: 60% 40%;
    grid-template-rows: 20% 50% 30%;
    grid-column-gap:20px;

    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    text-decoration: none;  
}
.shop_link .logo{
    grid-row: 1/3;
    grid-column: 1/2;
    width: 80%;
    height: 80%;
}
.shop_link > p{
  align-items: left;
  padding-top: 20px;
  margin-left: -20px;
  line-height: 30px;
  
}
.shop_link .shopName{
  margin-bottom: 10px;
  font-size: 16px;
  grid-column: 2/3;
  grid-row: 1/2;
  white-space: pre-wrap;
}


.shop_link .btn_approval{
    grid-row: 3/4;
    grid-column: 1/3;
}


.shop_link .information .infoTop{
  margin-top:-40px;
}
.shop_link .information p{
  font-size:20px;
  margin:0px 0px 0px 50px;
  line-height: 40px;
  font-family: "Brush Script MT", cursive;
  height: auto;
}
.shop_link .information .bottom{
  margin:0px 0px 0px 50px;
}

.dialog-footer{
  margin-right: 10px;
}


</style>