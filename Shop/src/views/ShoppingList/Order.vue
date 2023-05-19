<script setup>
import Nav from '../../components/Public/Nav/UserNav.vue'
import ShoppingList from '../../components/ShoppingList/ShoppingList.vue'
</script>
<template>
  <Nav></Nav>
  <div class="wrap">
  <div class="container">
    <h1 class="title"><span class="title-inner">下单页面</span></h1>
    <div class="order-item" v-for="goods in selectedGoods" :key="goods.goodsId">
      <img :src="'data:image/jpeg;base64,' + goods.goodsAvatar[0]" />
      <div class="order-item-info">
        <h2>{{ goods.goodsname }}</h2>
        <p>价格：{{ goods.goodsPrice }}</p>
        <p>数量：{{ goods.num }}</p>
      </div>
    </div>
    <div class="address-list-header">
      <h3 class="address-list-title">收货地址列表：</h3>
      <button class="submit-button2" @click = 'addAddress'>添加收货地址+</button>
    </div>
    <div class="address-item" v-for="address in addressList" :key="address.addressId">
      <input
          type="radio"
          :value="address.addressId"
          v-model="selectedAddressId"
      />
      <p>
        收货人：{{ address.receiverName }} - 手机：{{ address.phone }} - 省：{{ address.province }} - 市：{{ address.municipality }} - 县：{{ address.county }} - 镇：{{ address.township }} - 详细地址：{{ address.detailAddress }}
      </p>
    </div>
    <button class="submit-button" @click="createOrder">下单</button>
  </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addressList: [],
      username: window.localStorage.getItem("username"),
      selectedGoods: JSON.parse(localStorage.getItem("selectedGoods")),
      selectedAddressId: null,
      orderIds : [],
      orders :[],
    };
  },
  created() {
    this.getAllReceiverAddress();
  },
  methods: {
    addAddress(){
      this.$router.push({
        name: "addAddress",
      });
    },
    getAllReceiverAddress() {
      this.$axios
          .post("/getAllReceiverAddress", { username: this.username })
          .then((response) => {
            if (!response.data.success) {
              this.addressList = response.data.data;
              console.log(this.username)
              console.log(this.addressList)
            } else {
              console.error("Error fetching address list");
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
    },

    createOrder() {
      if (!this.selectedAddressId) {
        this.$message.error("请先选择一个收货地址");
        return;
      }
      const goodsIdList = this.selectedGoods.map((goods) => parseInt(goods.goodsId));
      const numList = this.selectedGoods.map((goods) => goods.num);
      this.$axios
          .post("/orderCreating", {
          username: window.localStorage.getItem("username"),
          addressId: this.selectedAddressId,
          goodsIdList: goodsIdList,
          numList: numList})
          .then((response) => {
            console.log(window.localStorage.getItem("username"));
            console.log(this.selectedAddressId);
            console.log(goodsIdList);
            console.log(numList);
            if (!response.data.success) {
              this.orderIds = response.data.data;
              window.localStorage.setItem("orderIds",this.orderIds);
              this.$router.push({ name: "Pay" });
            } else {
              console.error("Error creating order");
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });

    },

  },
};
</script>
<style>
.address-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
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
.order-item,
.address-item {
  border: 4px solid #81A18B;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}
.order-item {
  display: flex;
}
.h3{
  margin:auto;
}
.order-item img {
  width: 150px;
  height: 150px;
}
.order-item-info,
.address-item {
  margin-left: 10px;
}
.address-list-title {
  margin-top: 20px;
  margin-bottom: 20px;

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
.submit-button2 {
  display: block;
  width: 150px;
  height: 30px;
  margin-right: 0px;
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