<template>
  <div class="transaction-container">
    <h1>交易流水</h1>
    <div class="transaction-card" v-for="transaction in transactions" :key="transaction.transactionId">
      <h2>交易ID：{{ transaction.transactionId }}</h2>
      <div class="transaction-detail">
        <div>
          <p><strong>日期：</strong>{{ transaction.transactionDate }}</p>
          <p><strong>交易类型：</strong>{{ getTransactionTypeName(transaction.transactionType) }}</p>
        </div>
        <div>
          <p><strong>转出方：</strong>{{ transaction.outPart }}</p>
          <p><strong>转入方：</strong>{{ transaction.inPart }}</p>
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
      username :window.localStorage.getItem("username")
    };
  },
  created() {
    console.log(this.username)
    axios.post('/getUserTransactions',{username : this.username, accountType : 3})
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
  },
  methods: {
    getTransactionTypeName(type) {
      switch(type) {
        case 1:
          return '个人账户充值';
        case 2:
          return '商店账户充值';
        case 3:
          return '中间账户充值';
        case 4:
          return '利润账户充值';
        case 5:
          return '申请商店注册，商店注册资金向中间账户转账';
        case 6:
          return '管理员同意商店注册，商店注册资金从中间账户向利润账户转账';
        case 7:
          return '删除商店后，商店账户转入个人账户';
        case 8:
          return '支付订单后，金额转入中间账户';
        case 9:
          return '完成订单后，佣金从中间账户转入商城利润账户';
        case 10:
          return '完成订单后，订单利润从中间账户转入商店账户';
        case 11:
          return '同意退款后，订单金额从中间账户转回个人账户';
        case 12:
          return '管理员拒绝商店注册，商店注册资金从中间账户向个人账户转账';
        default:
          return '未知的交易类型';
      }
    }
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