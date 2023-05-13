<template>
  <div>
    <h2>待支付订单</h2>
    <div v-for="order in orders" :key="order.orderId">
      <h3>订单编号：{{ order.orderId }}</h3>
      <p>用户名：{{ order.username }}</p>
      <p>收货地址：{{ order.addressId }}</p>
      <p>店铺名称：{{ order.shopName }}</p>
      <p>下单时间：{{ order.orderTime }}</p>
      <p>商品编号：{{ order.goodsId }}</p>
      <p>商品名称：{{ order.goodsName }}</p>
      <p>商品单价：{{ order.unitPrice }}</p>
      <p>商品数量：{{ order.goodsNum }}</p>
      <p>总价：{{ order.totalPrice }}</p>
      <p>实际支付金额：{{ order.actualPayment }}</p>
      <p>订单状态：{{ order.status }}</p>
      <el-button type="danger" @click="cancelOrder(order.orderId)">撤销</el-button>
      <el-button type="primary" @click="payOrder(order.orderId)">支付</el-button>
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
    axios.post('/getAllOrders',{username : this.username})
        .then(response => {
          if (response.data.state === 0) {
            this.orders = response.data.data;
          } else {
            console.error('Error fetching orders');
          }
        })
        .catch(error => {
          console.error('Error:', error);
        });
  },
  methods:{
    async cancelOrder(orderId) {
      let orderIdList = [];
      orderIdList.push(orderId);
      this.$axios
          .post("/cancelOrder", { orderIdList: orderIdList })
          .then((response) => {
            if (!response.data.success) {
              this.$message.success("撤销订单成功");
              this.$router.push({ name: "shopkeeperSelfCenter" });
            } else {
              this.$message.error("撤销订单失败");
              this.$router.push({ name: "shopkeeperSelfCenter" });
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
    },
    async payOrder(orderId){
      let orderIdList = [];
      orderIdList.push(orderId);
      this.$axios
          .post("/payment", { orderIdList: orderIdList })
          .then((response) => {
            if (response.data.state==0) {
              this.$message.success("支付订单成功");
              this.$router.push({ name: "shopkeeperSelfCenter" });
            } else {
              this.$message.error("余额不足");
              this.$router.push({ name: "shopkeeperSelfCenter" });
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
    }
  }
}
</script>
