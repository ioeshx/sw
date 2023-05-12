<script setup>
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
import ShoppingList from '../../components/ShoppingList/ShoppingList.vue'
</script>

<template>
  <Nav></Nav>
  <div>
    <h1>下单页面</h1>
    <div class="order-item" v-for="goods in selectedGoods" :key="goods.goodsId">
      <img :src="'data:image/jpeg;base64,' + goods.goodsAvatar[0]" />
      <div class="order-item-info">
        <h2>{{ goods.goodsname }}</h2>
        <p>价格：{{ goods.goodsPrice }}</p>
        <p>数量：{{ goods.num }}</p>
      </div>
    </div>
    <h3>收货地址列表：</h3>
    <div v-for="address in addressList" :key="address.addressId">
      <input
          type="radio"
          :value="address.addressId"
          v-model="selectedAddressId"
      />
      <p>
        收货人：{{ address.receiverName }} - 手机：{{ address.phone }} - 省：{{ address.province }} - 市：{{ address.municipality }} - 县：{{ address.county }} - 镇：{{ address.township }} - 详细地址：{{ address.detailAddress }}
      </p>
    </div>
    <button @click="createOrder">下单</button>
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
    };
  },
  created() {
    this.getAllReceiverAddress();
  },
  methods: {
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
      const goodsIdList = this.selectedGoods.map((goods) => goods.goodsId);
      const numList = this.selectedGoods.map((goods) => goods.num);
      this.$axios
          .post("/orderCreating", {
          username: this.username,
          addressId: this.selectedAddressId,
          goodsIdList: goodsIdList,
          numList: numList})
          .then((response) => {
            if (!response.data.success) {
              window.localStorage.setItem(
                  "orderIds",
                  response.data.data
              );
            } else {
              console.error("Error creating order");
            }
          })
          .catch((error) => {
            console.error("Error:", error);
          });
          this.$router.push({ name: "Pay" });
    },

  },
};
</script>
<style>
.order-item {
  display: flex;
  margin-bottom: 20px;
}
.order-item img {
  width: 100px;
  height: 100px;
}
.order-item-info {
  margin-left: 10px;
}
</style>
