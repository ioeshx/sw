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
    };
  },
  created() {
    this.getOrders();
  },
  methods: {
    getOrders() {
      const orderIds = JSON.parse(window.localStorage.getItem("orderIds"));
      orderIds.forEach((orderId) => {
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
      // TODO: 实现支付逻辑
      console.log("支付功能尚未实现");
    },
  },
};
</script>
