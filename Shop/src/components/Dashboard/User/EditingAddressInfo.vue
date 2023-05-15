<!-- EditAddressInfo.vue -->

<template>
  <div>
    <form>
      <div class="input-group">
        <label for="receiverName">收件人姓名: </label>
        <input id="receiverName" type="text" v-model="editedAddress.receiverName" @blur="validateField('receiverName')" />
      </div>
      <div class="input-group">
        <label for="phone">联系电话: </label>
        <input id="phone" type="text" v-model="editedAddress.phone" @blur="validatePhone" />
      </div>
      <div class="input-group">
        <label for="province">省份: </label>
        <input id="province" type="text" v-model="editedAddress.province" @blur="validateField('province')" />
      </div>
      <div class="input-group">
        <label for="municipality">市: </label>
        <input id="municipality" type="text" v-model="editedAddress.municipality" @blur="validateField('municipality')" />
      </div>
      <div class="input-group">
        <label for="county">区/县: </label>
        <input id="county" type="text" v-model="editedAddress.county" />
      </div>
      <div class="input-group">
        <label for="township">乡/镇: </label>
        <input id="township" type="text" v-model="editedAddress.township" />
      </div>
      <div class="input-group">
        <label for="detailAddress">详细地址: </label>
        <input id="detailAddress" type="text" v-model="editedAddress.detailAddress" @blur="validateField('detailAddress')" />
      </div>
    </form>
    <button class="finish-button" @click="finishEditing" >完成</button>
  </div>
</template>

<script>

import "../../../constant.js";
export default {
  props: {
    address: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      editedAddress: {},
      isValid: false,
    };
  },
  created() {
    this.editedAddress = JSON.parse(JSON.stringify(this.address));
  },
  methods: {
    validateField(field) {
      if (!this.editedAddress[field]) {
        this.$message.error(`${field}不能为空！`);
        this.isValid = false;
      } else {
        this.isValid = true;
      }
    },
    validatePhone() {
      const phoneRegex = /^1[3456789]\d{9}$/;
      if (!phoneRegex.test(this.editedAddress.phone)) {
        this.$message.error('请输入有效的11位手机号码！');
        this.isValid = false;
      } else {
        this.isValid = true;
      }
    },
    finishEditing() {
      if (!this.isValid) {
        this.$message.error('请确保所有输入都满足要求！');
        return;
      }
      console.log(this.editedAddress);
      this.$axios.post('/modifyReceiverAddress', this.editedAddress)
          .then((response) => {
            if (!response.data.success) {
              // 假设已经成功更新了地址信息
              // 然后向父组件发送事件通知，表示编辑已完成
              this.$emit("finishEditing");
              this.$router.push({ name: "UserWeb" });
              this.$message.success("修改成功！");
            } else {
              console.log(this.editedAddress);
              // 处理错误情况，例如显示一个错误消息
              console.error("Error updating the address:", response.data.message);
            }
          })
          .catch((error) => {
            // 处理网络错误或其他异常情况
            console.error("Error:", error);
          });
    }

  }
}
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
}

label {
  margin-top: 5px;
}

input {
  margin-bottom: 5px;
}

button {
  margin-top: 10px;
}
.input-group {
  margin-bottom: 20px;
}

.input-group label {
  display: block;
  font-size: 18px;
}

.input-group input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.finish-button {
  width: 100%;
  padding: 15px 0;
  font-size: 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.finish-button:hover {
  background-color: #45a049;
}
</style>