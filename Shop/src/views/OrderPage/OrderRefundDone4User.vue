<template>
  <div>
    <h2>已退款订单</h2>
    <div v-for="order in orders" :key="order.orderId">
      <p>用户名：{{ order.username }}</p>
      <p>收货人：{{ order.addressDetail.receiverName }} - 手机：{{ order.addressDetail.phone }} - 省：{{ order.addressDetail.province }} - 市：{{ order.addressDetail.municipality }} - 县：{{ order.addressDetail.county }} - 镇：{{ order.addressDetail.township }} - 详细地址：{{ order.addressDetail.detailAddress }}</p>

      <p>店铺名称：{{ order.shopName }}</p>
      <p>下单时间：{{ order.orderTime }}</p>
      <p>商品名称：{{ order.goodsName }}</p>
      <p>商品单价：{{ order.unitPrice }}</p>
      <p>商品数量：{{ order.goodsNum }}</p>
      <p>总价：{{ order.totalPrice }}</p>
      <p>实际支付金额：{{ order.actualPayment }}</p>
      <el-button type="danger" @click="deleteOrder(order.orderId)">删除订单</el-button>
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
      status: 6})
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
    async deleteOrder(orderId) {
      let orderIdList = [];
      orderIdList.push(orderId);
      this.$axios
          .post("/deleteOrder", { orderIdList: orderIdList })
          .then((response) => {
            if (!response.data.success) {
              this.$message.success("删除订单成功");
              this.$router.push({ name: "userSelfCenter" });
            } else {
              this.$message.error("删除订单失败");
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
