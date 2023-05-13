<script setup>
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
</script>

<template>
  <Nav></Nav>
  <div>
    <div v-for="order in orders" :key="order.orderId">
      <h3>订单ID：{{ order.orderId }}</h3>
      <p>收货地址ID: {{ order.addressId }}</p>
      <p>用户名: {{ order.username }}</p>
      <p>创建时间: {{ order.createTime }}</p>
      <p>商品ID: {{ order.goodsId }}</p>
      <p>数量: {{ order.num }}</p>
    </div>
    <button @click="pay">支付</button>
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
    this.getOrders();
  },
  methods: {
    getOrders() {
      let orderIdList = window.localStorage.getItem("orderIds");
      orderIdList = JSON.parse(orderIdList);
      orderIdList.forEach((orderId) => {
        this.$axios
            .post("/getOrderByOrderId", { orderId: orderId })
            .then((response) => {
              if (response.data.success) {
                this.orders.push(response.data.order);
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
