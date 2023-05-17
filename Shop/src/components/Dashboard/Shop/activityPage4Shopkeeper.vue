<template>
  <div class="wrap">
    <div class="promotion-container">
      <h1>正在进行的活动</h1>
      <div class="promotion-card" v-for="promotion in promotions" :key="promotion.promotionId">
        <h2>{{ promotion.promotionName }}</h2>
        <div class="promotion-info">
          <div class="info-row">
            <p>结束时间: {{ promotion.endTime }}</p>
            <p>促销的商品类型: {{ promotion.goodsTypeInPromotion }}</p>
            <p><strong>注册资金限制:</strong> {{ promotion.registrationCapitalLimit }}</p>
          </div>
          <div class="info-row">
            <p>活动资金: {{ promotion.promotionFundUsed }}</p>
            <p>满： {{ promotion.promotionStartLine }} 减: {{ promotion.promotionPaymentReduce }}</p>
            <p><strong>月销售额收入限制:</strong> {{ promotion.monthlySalesLimit }}</p>
          </div>
        </div>
        <p><strong>月销售收入限制:</strong> {{ promotion.monthlySaleIncomeLimit }}</p>
        <div class="apply-button">
          <button class="btn" @click="applyForPromotion()">申请</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      promotions: [],
    };
  },
  mounted() {
    this.getPromotions();
  },
  methods: {
    getPromotions() {
      axios.post('/getPromotions',{type : 1})
          .then(response => {
            if(response.data.state === 0) {
              this.promotions = response.data.data;
            }
          })
          .catch(error => {
            console.error(error);
          });
    },
    applyForPromotion() {
      axios.post('/applyForPromotion')
          .then(response => {
            if(response.data.state === 0) {
              this.$message.success("申请已送达，请耐心等待管理员审核")
            } else {
              alert("申请失败，请稍后再试！");
            }
          })
          .catch(error => {
            console.error(error);
          });
    },
  },
};
</script>

<style scoped>
.wrap{
  padding: 20px 30px 10px 30px;
  margin: 10px 20px 20px 20px;
  line-height: 100px;
  align-items: center;
  display: flex;
  flex-wrap: wrap;
  overflow: auto;
  background-color: #fff;
  border-radius: 15px;
  color: #303133;
  border: 3px solid #ebeef5;
  transition: .3s;
}
.promotion-container {
  width: 80%;
  margin: auto;
}

.promotion-card {
  box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
  border-radius: 10px;
  padding: 20px;
  margin: 20px 0;
  background-color: #fff;
}

.promotion-info {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.info-row {
  display: flex;
  flex-direction: column;
  margin-bottom: 5px;
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

p strong {
  color: #333;
}
.btn {
  background-color: #81A18B;
  color: white;
  font-size: 18px;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  margin: 0 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #618c75;
}
</style>