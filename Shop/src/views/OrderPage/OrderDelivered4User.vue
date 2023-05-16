<template>
  <div class="orders-container">
    <h2 class="title">待收货</h2>
    <div v-for="order in orders" :key="order.orderId" class="order-card">
      <p>用户名：{{ order.username }}</p>
      <p>
        收货人：{{ order.addressDetail.receiverName }} -
        手机：{{ order.addressDetail.phone }} -
        {{ order.addressDetail.province }} -
        {{ order.addressDetail.municipality }} -
        <span v-if="order.addressDetail.county">{{ order.addressDetail.county }} - </span>
        <span v-if="order.addressDetail.township">{{ order.addressDetail.township }} - </span>
        详细地址：{{ order.addressDetail.detailAddress }}
      </p>

      <p>店铺名称：{{ order.shopName }}</p>
      <p>下单时间：{{ order.orderTime }}</p>
      <p>商品名称：{{ order.goodsName }}</p>
      <p>商品单价：{{ order.unitPrice }}</p>
      <p>商品数量：{{ order.goodsNum }}</p>
      <p>总价：{{ order.totalPrice }}</p>
      <p>实际支付金额：{{ order.actualPayment }}</p>
      <div class="button-group">
        <el-button type="danger" size="large" @click="refund(order.orderId)">申请退款</el-button>
        <el-button type="primary" size="large" @click="Done(order.orderId)">确认收货</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';  // 请根据实际情况导入axios

export default {
  data() {
    return {
      orders: [],
      username: window.localStorage.getItem("username"),
    }
  },
  mounted() {
    axios.post('/getOrdersByStatus',{username : this.username,
      status: 2})
        .then(response => {
          if (response.data.state === 0) {
            this.orders = response.data.data;
            this.orders.forEach(order =>{
              axios.post('/getReceiverAddressByAddressId', {addressId: order.addressId})
                  .then(res => {
                    if (res.data.state === 0) {
                      // 将获取到的地址详情添加到订单对象中
                      order.addressDetail = res.data.data;
                    }else {
                      console.error('获取地址详情失败');
                    }
                  })
            })
          } else {
            console.error('没有订单');
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });
  },
  methods:{
    async refund(orderId) {
      let orderIdList = [];
      orderIdList.push(orderId);
      this.$axios
          .post("/refundOrder", { orderIdList: orderIdList })
          .then((response) => {
            if (!response.data.success) {
              this.$message.success("退款受理中");
              this.$router.push({ name: "userSelfCenter" });
            } else {
              this.$message.error("退款申请失败");
              this.$router.push({ name: "userSelfCenter" });
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
    },
    async Done(orderId) {
      let orderIdList = [];
      orderIdList.push(orderId);
      this.$axios
          .post("/confirmReceipt", { orderIdList: orderIdList })
          .then((response) => {
            if (!response.data.success) {
              this.$message.success("已收货！");
              this.$router.push({ name: "userSelfCenter" });
            } else {
              this.$message.error("收货失败");
              this.$router.push({ name: "userSelfCenter" });
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
    },
  }
}
</script>
<style scoped>
.orders-container {
  width: 80%;
  margin: auto;
}

.title {
  color: #4CAF50; /* 主题绿色 */
  text-align: center;
  margin-bottom: 20px;
}

.order-card {
  background-color: #f0f0f0; /* 背景色 */
  border: 2px solid #4CAF50; /* 主题绿色 */
  border-radius: 10px; /* 圆角 */
  padding: 20px;
  margin-bottom: 20px;
}

.order-card p {
  margin: 5px 0;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 10px;
}

</style>