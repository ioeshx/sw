<script setup>
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
</script>

<template>
  <Nav></Nav>
  <div class="wrap">
    <div class="container">
      <h1 class="title"><span class="title-inner">订单页面</span></h1>
      <div class="order-item" v-for="order in orders" :key="order.orderId">
        <div class="order-item-info">
          <h2>订单ID：{{ order.orderId }}</h2>
          <p>收货地址ID: {{ order.addressId }}</p>
          <p>用户名: {{ order.username }}</p>
          <p>创建时间: {{ order.createTime }}</p>
          <p>商品ID: {{ order.goodsId }}</p>
          <p>数量: {{ order.goodsNum }}</p>
        </div>
      </div>
      <button class="submit-button" @click="pay">支付</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      orders: [],
      orderIdList :JSON.parse( window.localStorage.getItem("orderIdList"))
    };
  },
  created() {
  },
  mounted() {
    console.log("1");
    console.log(window.localStorage.getItem("orderIds"));
    this.getOrders();
  },
  methods: {
    getOrders() {
      let orderIds = window.localStorage.getItem("orderIds");
      let orderIdList = orderIds.split(',');
      window.localStorage.setItem("orderIdsTest2",orderIdList);
      for (var i = 0;i<orderIdList.length;i++){
        console.log(orderIdList[i]);
      }
      orderIdList.forEach((orderId) => {

        this.$axios.post("/getOrderByOrderId", { OrderId: parseInt(orderId) })
            .then((response) => {
              if (!response.data.success) {
                this.orders.push(response.data.data);
              } else {
                console.error("Error fetching order");
              }
            })
            .catch((error) => {
              console.error("Error:", error);
            });
      });
    },
    pay() {
      // 从localStorage获取orderIdList
      const orderIds = JSON.parse(window.localStorage.getItem("orderIds"));
      // 调用/payment方法
      this.$axios
          .post('/payment', { orderIds })
          .then(response => {
            // 处理响应
            if (!response.data.success) {
              // 支付成功，可以进行相关操作，例如显示成功消息，清空localStorage中的orderIdList等
              console.log("支付成功");
              localStorage.removeItem('orderIds');
            } else {
              // 支付失败，可以进行相关操作，例如显示错误消息等
              console.error("支付失败");
            }
          })
          .catch(error => {
            // 处理网络错误或其他异常情况
            console.error("Error:", error);
          });
    },
  },
};
</script>
<style>
/* your current styles here */

.wrap{
  width: 85%;
  height: 100%;
  margin:20px auto;
  padding: 10px 0 30px;
  background-color: #fff;
  border-radius: 15px;
  color: #303133;
  border: 2px solid #ebeef5;
  box-shadow: 2px 2px 12px 2px rgb(0 0 0 / 10%);
}
.container {
  width: 80%;
  margin: 0 auto;
  color: darkgreen;
}
.title {
  text-align: center;
  margin-bottom: 50px;
  position: relative;
  padding: 10px;
}
.order-item {
  border: 4px solid #81A18B;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}
.h3{
  margin:auto;
}
.order-item-info {
  margin-left: 10px;
}
.submit-button {
  display: block;
  width: 200px;
  height: 50px;
  margin: 20px auto;
  background-color: #4F7942;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 20px;
  cursor: pointer;
}
.submit-button:hover {
  background-color: #6B8E23;
}
</style>