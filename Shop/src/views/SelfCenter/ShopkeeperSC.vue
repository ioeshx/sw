<!--商家个人中心主页面-->
<script setup>
import InfoShow from '../../components/SelfCenter/InfoShow.vue'
import OrderShow from '../../components/SelfCenter/OrderShow.vue'
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
import SetUserInfo from '../../components/Dashboard/User/SetUserInfo.vue'
import SetAddress from "@/components/Dashboard/User/SetAddress.vue";
import ShowAllAddress from "@/components/Dashboard/User/ShowAllAddress.vue";
</script>

<template>
        
    <Nav></Nav>
    <section>
        <div v-if="state==0">
            <h1 class="titleShow">Self Center</h1>
            <div class="main">
                <div class="infoShow">
                    <InfoShow></InfoShow>
                  <div class="buttonContainer">
                    <div class="buttonRow">
                      <el-button class="change" @click="this.state=1">个人信息修改</el-button>
                      <el-button class="change" @click="orderPage">订单管理</el-button>
                    </div>
                    <div class="buttonRow">
                      <el-button class="change" @click="shop">商店流水</el-button>
                      <el-button class="change" @click="privateAccount">个人流水</el-button>
                    </div>
                  </div>
                </div>
            </div>
        </div>
        <div v-if="state==1">
            <h1 class="titleChange">个人信息修改</h1>
            <SetUserInfo class="changeWrap"/>
        </div>

        
    </section>


</template>

<script>
import { interceptor, shopkeeperInterceptor } from "../../interceptor";
import axios from "axios";
export default{
    data() {
        return{
            state:0,
            shopname :""
        }
    },
    mounted() {
      axios.post('/getShopInfoByUsername',{username : window.localStorage.getItem("username")})
          .then(response => {
            if (response.data.state == 0) {
              this.shopname = response.data.data.shopname;
              window.localStorage.setItem("shopname",this.shopname)}
              }
              )
    },

  created(){
        interceptor(this);
        shopkeeperInterceptor(this);
    },methods:{
      orderPage(){
          this.$router.push({ name: "OrderPageForShopkeeper" });
      },
      shop(){
        this.$router.push({ name: "shopAccount" });
    },
    privateAccount(){
      this.$router.push({ name: "privateAccount" });
    }
  }
}
</script>

<style scoped>
.titleShow{
    font-family: 'Courier New', Courier, monospace;
    margin: 30px 0 30px 50px;
    color:#81A18B;
    text-shadow: 2px 2px 2px #195844;
    font-size: 45px;
    text-align: center;
    font-weight: bolder;
}

.main{
    display: grid;
    grid-template-columns: 35% 55%;
    grid-template-rows: 100%;
    grid-column-gap: 50px;
    margin-top: 20px;
    margin-left: 20px;
}

.infoShow{
    margin-left: 20px;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    box-shadow: 2px 2px 12px 2px (0 0 0 / 10%);
}
.buttonContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.buttonRow {
  display: flex;
  justify-content: space-around;
  width: 100%;
}

.change {
  width: 60%; /* 调整宽度 */
  margin: 10px;
}


.titleChange{
  text-align: center;
  font-family:"Lucida Console", "Courier New", monospace;
  font-size: 40px;
  margin:40px;
  font-weight: bolder;
  color:#81A18B;
}
.changeWrap{
    padding: 20px 30px 10px 30px;
    margin: 20px 180px 40px 180px;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    gap:20px;
}
</style>
