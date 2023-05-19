<!--商家个人中心主页面-->
<script setup>
import InfoShow from '../../components/SelfCenter/InfoShow.vue'
import OrderShow from '../../components/SelfCenter/OrderShow.vue'
import Nav from '../../components/Public/Nav/UserNav.vue'
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
                <el-button class="change" @click="this.state=1">修改个人信息</el-button>
              </div>
              <div class="buttonRow">
                <el-button class="change" @click="this.state=2">添加地址</el-button>
                <el-button class="change" @click="this.state=3">我的地址</el-button>
              </div>
              <div class="buttonRow">
                <el-button class="change" @click="orderPage">订单管理</el-button>
                <el-button class="change" @click="privite">个人流水</el-button>
              </div>
            </div>
          </div>
          <OrderShow />
        </div>
      </div>
      <div v-if="state==1">
        <h1 class="titleChange">个人信息修改</h1>
        <SetUserInfo class="changeWrap"/>
      </div>
      <div v-if="state==2">
        <h1 class="titleChange">添加地址</h1>
        <SetAddress class="changeWrap"/>
      </div>
      <div v-if="state==3">
        <h1 class="titleChange">我的地址</h1>
        <ShowAllAddress class="changeWrap"/>
      </div>
    </section>


</template>

<script>
import { interceptor, userInterceptor } from "../../interceptor";
export default{
    data() {
        return{
            state:0
        }
    },
    created(){
        interceptor(this);
        userInterceptor(this);
    },methods: {
    orderPage() {
      this.$router.push({name: "OrderPage"});
    },
    privite(){
      this.$router.push({name: "privateAccount"});
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
