<template>
  <div class="wrap">
    <div class="merchant-container">
      <h1>商家申请列表</h1>
      <div class="merchant-card" v-for="merchant in merchants" :key="merchant.id" >
        <div v-if="merchant.status === 0">
          <h2>{{ merchant.shopName }}</h2>
          <p>店铺名: {{ merchant.username }}</p>
          <div class="approve-button">
            <button @click="approveMerchant(merchant.username)">批准</button>
            <button @click="refuseMerchant(merchant.username)">拒绝</button>
          </div>
        </div>
        <!-- 展示更多商家信息... -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      merchants: [],
    };
  },
  mounted() {
    this.getAllApplications();
  },
  methods: {
    getAllApplications() {
      axios.get('/adminGetAllApplication')
          .then(response => {
            if(response.data.state === 0) {
              this.merchants = response.data.data;
            }
          })
          .catch(error => {
            console.error(error);
          });
    },
    approveMerchant(username) {
      axios.post('/adminCheckPromotionApplication', { username: username, checkType :2 })
          .then(response => {
            if(response.data.state === 0) {
              alert("批准成功！");
              this.getAllApplications(); // 批准后重新获取列表
            } else {
              alert("批准失败，请稍后再试！");
            }
          })
          .catch(error => {
            console.error(error);
          });
    },
    refuseMerchant(username) {
      axios.post('/adminCheckPromotionApplication', { username: username, checkType :1})
          .then(response => {
            if(response.data.state === 0) {
              alert("拒绝成功！");
              this.getAllApplications(); // 批准后重新获取列表
            } else {
              alert("拒绝失败，请稍后再试！");
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
  line-height: 1.6;
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
.merchant-container {
  width: 80%;
  margin: auto;
}

.merchant-card {
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

.approve-button {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.approve-button button {
  padding: 10px 20px;
  font-size: 1.2em;
  color: #fff;
  background-color: #409EFF;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.approve-button button:hover {
  background-color: #66b1ff;
}
</style>