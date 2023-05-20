<script setup>
import WrapperUser from '../../../components/MainWeb/Wrapper/WrapperUser.vue'
import SearchTop from '../../../components/MainWeb/Components/SearchU.vue'
import Nav from '../../../components/Public/Nav/UserNav.vue'
import { RouterLink, RouterView } from 'vue-router'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
const input = ref('')
const select = ref('')
</script>
<template>
  <Nav></Nav>>
  <div>
    <h1 class="promo-title">大促销！！</h1>
    <div class="product-list">
      <div class="product" v-for="product in products" :key="product.goodsId">
        <h2>{{ product.goodsname }}</h2>
        <p>价格: {{ product.goodsPrice }}</p>
        <p>类别: {{ product.goodsCategory }}</p>
        <p>库存: {{ product.goodsStock }}</p>
        <p>介绍: {{ product.introduction }}</p>
        <p>店铺: {{ product.shopname }}</p>
        <div class="product-actions">
          <el-input-number v-model="product.num" :min="1" :max="product.goodsStock"></el-input-number>
          <el-button type="primary" @click="buyNow(product)">立即购买</el-button>
          <el-button type="success" @click="addToCart(product)">加入购物车</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      products: []
    }
  },
  created() {
    this.fetchProducts();
  },
  methods: {
    async fetchProducts() {
      axios.post('/getAllGoodsInPromotion')
          .then(response => {
            if (response.data.state == 0) {
              this.products = response.data.data;
            }
          })
    },
    buyNow(product){
      const selectedGoods = [];
      selectedGoods.push(product);
      localStorage.setItem("selectedGoods", JSON.stringify(selectedGoods));
      console.log(selectedGoods)
      // 导航到下单页面，不需要传递参数
      this.$router.push({
        name: "Order",
      });
    },
    addToCart(product) {
      this.$axios.post('/addToCart',{
        goodsId: parseInt(product.goodsId),
        username: localStorage.getItem("username"),
        num: parseInt(product.num)
      }).then(res => {
        if(res.data.state == window.SUCCESS) {
          this.$message.success("已成功添加至购物车！");
        } else {
          this.$message.error("出现异常，请重试！");
        }})
    },
  }
}
</script>


<style scoped>
.promo-title {
  text-align: center;
  color: #195844;
  font-size: 3em;
  margin-bottom: 20px;
}
.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
.product {
  border: 3px solid #81A18B;
  padding: 20px;
  margin: 20px;
  width: 200px;
  text-align: center;
  background-color: #E3FCEF;
}
.product h2 {
  color: #195844;
}
.product-actions {
  margin-top: 20px;
}
</style>