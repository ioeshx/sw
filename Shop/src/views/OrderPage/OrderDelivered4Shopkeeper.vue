<template>
  <div class="order-container">
    <h1>已发货的订单</h1>
    <div class="order-card" v-for="order in orders" :key="order.orderId">
      <h2>用户名：{{ order.username }}</h2>
      <p>收货人：{{ order.addressDetail.receiverName }} - 手机：{{ order.addressDetail.phone }}</p>
      <p>地址：{{ order.addressDetail.province }} - {{ order.addressDetail.municipality }} - {{ order.addressDetail.county }} - {{ order.addressDetail.township }} - {{ order.addressDetail.detailAddress }}</p>
      <p>店铺名称：{{ order.shopName }}</p>
      <p>下单时间：{{ order.orderTime }}</p>
      <p>商品名称：{{ order.goodsName }}</p>
      <p>商品单价：{{ order.unitPrice }}</p>
      <p>商品数量：{{ order.goodsNum }}</p>
      <p>总价：{{ order.totalPrice }}</p>
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
      shopname: window.localStorage.getItem("shopname"),
    }
  },
  mounted() {
    axios.post('/getOrdersOfShopByStatus',{shopName : this.shopname,
      status: 2})
        .then(response => {
          if (response.data.state == 0) {
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

  }
}
</script>
<style scoped>
.order-container {
  width: 80%;
  margin: auto;
}

.order-card {
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
  border-radius: 10px;
  padding: 20px;
  margin: 20px 0;
  background-color: #fff;
}

h1 {
  text-align: center;
  margin: 30px 0;
}

h2 {
  font-size: 1.5em;
  margin: 10px 0;
  color: #333;
}

p {
  font-size: 1.2em;
  margin: 5px 0;
  color: #666;
}
</style>