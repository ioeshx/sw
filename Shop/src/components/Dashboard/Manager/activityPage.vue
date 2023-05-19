<script setup>
</script>
<template>
  <div class="container">
    <div v-if="status == 0">
      <form @submit.prevent="startPromotion">
        <div class="demo-date-picker">
          <div class="block">
            <span class="demonstration">选择结束的时间</span>
            <el-date-picker
                v-model="value2"
                type="date"
                placeholder="Pick a day"
                :disabled-date="disabledDate"
                :shortcuts="shortcuts"
                :size="size"
            />
          </div>
        </div>
        <div class="form-group">
          <label>促销的商品类型</label>
          <input type="text" v-model="request.goodsTypeInPromotion" required>
        </div>
        <div class="form-group">
          <label>月销售收入限制</label>
          <input type="text" v-model="request.monthlySaleIncomeLimit" required>
        </div>
        <div class="form-group">
          <label>月销售额收入限制</label>
          <input type="text" v-model="request.monthlySalesLimit" required>
        </div>
        <div class="form-group">
          <label>活动资金</label>
          <input type="text" v-model="request.promotionFund" required>
        </div>
        <div class="form-group">
          <label>活动名</label>
          <input type="text" v-model="request.promotionName" required>
        </div>
        <div class="form-group">
          <label>满：</label>
          <input type="text" v-model="request.promotionStartLine" required>
        </div>
        <div class="form-group">
          <label>减：</label>
          <input type="text" v-model="request.promotionPaymentReduce" required>
        </div>
        <div class="form-group">
          <label>注册资金限制</label>
          <input type="text" v-model="request.registrationCapitalLimit" required>
        </div>
        <!-- 其他字段类似 -->
        <button class="btn" @click="1">开启活动！</button>
      </form>
    </div>
    <div v-if="status == 1">
      <button class="btn" @click="endThePromotion">关闭活动！</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
export default {
  setup() {
    const size = ref('default');
    const value2 = ref('');

    const shortcuts = [
      {
        text: 'Today',
        value: new Date(),
      },
      {
        text: 'Yesterday',
        value: () => {
          const date = new Date();
          date.setTime(date.getTime() - 3600 * 1000 * 24);
          return date;
        },
      },
      {
        text: 'A week ago',
        value: () => {
          const date = new Date();
          date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
          return date;
        },
      },
    ];

    const disabledDate = (time) => {
      return time.getTime() < Date.now();
    }

    return {
      size,
      value2,
      shortcuts,
      disabledDate,
    };
  },
  mounted() {
    axios.post('/getPromotions', {type :1})
        .then((response) => {
          // handle success
          if(response.data.data!=null) this.status = 1;
        })
        .catch((error) => {
          // handle error
          console.error(error);
        });
  },
  data() {
    return {
      request: {
        endTime: '',
        goodsTypeInPromotion: '',
        monthlySaleIncomeLimit: '',
        monthlySalesLimit: '',
        promotionFund: '',
        promotionName: '',
        promotionPaymentReduce: '',
        promotionStartLine: '',
        registrationCapitalLimit: '',
      },
      date : null,
      status : 0
    };
  },
  methods: {
    endThePromotion(){
      this.status = 0;
      axios.post('/adminCloseCurrentPromotion')
          .then((response) => {
            // handle success
            this.$message.success("关闭活动成功！");
          })
          .catch((error) => {
            // handle error
            console.error(error);
          });
    },
    startPromotion() {
      this.request.endTime = this.value2;
      console.log(this.value2);
      for (const key in this.request) {
        if (!this.request[key]) {
          alert(`请填写${key}`);
          return;
        }
      }
      axios.post('/adminStartPromotion', this.request)
          .then((response) => {
            // handle success
            console.log(response);
            this.$message.success("成功开启活动！");
            this.status = 1;
          })
          .catch((error) => {
            // handle error
            console.error(error);
          });
    },
  },
};
</script>


<style scoped>
.demo-date-picker {
  display: flex;
  width: 100%;
  padding: 0;
  flex-wrap: wrap;
}

.demo-date-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}

.demo-date-picker .block:last-child {
  border-right: none;
}

.demo-date-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
.container {
  width: 50%;
  margin: auto;
}

form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 1.0em;
}

input {
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  font-size: 1.2em;
  background-color: #81A18B;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #618c75;
}
</style>