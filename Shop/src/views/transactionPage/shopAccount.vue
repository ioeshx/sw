<template>
  <div class="transaction-container">
    <h1>交易流水</h1>
    <div class="transaction-card" v-for="transaction in transactions" :key="transaction.transactionId">
      <h2>交易ID：{{ transaction.transactionId }}</h2>
      <div class="transaction-detail">
        <div>
          <p><strong>日期：</strong>{{ transaction.transactionDate }}</p>
          <p><strong>交易类型：</strong>{{ transaction.transactionType }}</p>
          <p><strong>转出方：</strong>{{ transaction.outPart }}</p>
          <p><strong>转入方：</strong>{{ transaction.inPart }}</p>
        </div>
        <div>
          <p><strong>转出方的账户类型：</strong>{{ transaction.outAccountType }}</p>
          <p><strong>转入方的账户类型：</strong>{{ transaction.inAccountType }}</p>
          <p><strong>金额数量：</strong>{{ transaction.amount }}</p>
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
      transactions: [],
      shopname :window.localStorage.getItem("shopname")
    };
  },
  created() {
    axios.post('/getUserTransactions',{username : this.shopname, account : 2})
        .then(response => {
          if(response.data.state == 0) {
            this.transactions = response.data.data;
          }else{
            console.error("未获取到流水")
          }
        })
        .catch(error => {
          console.log(error);
        });
  }
};
</script>

<style scoped>
.transaction-container {
  width: 80%;
  margin: auto;
  font-size: 1.2em;
}

h1 {
  color: #006400;
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: 2rem;
}

.transaction-card {
  border: 1px solid #006400;
  border-radius: 10px;
  padding: 1rem;
  margin-bottom: 1rem;
  background-color: #f0fff0;
}

.transaction-detail {
  display: flex;
  justify-content: space-between;
}

.transaction-detail p {
  margin-bottom: 0.5rem;
}

h2 {
  color: #006400;
  font-size: 1.5rem;
}

p {
  color: #006400;
  font-size: 1rem;
}
</style>