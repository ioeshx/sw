<!-- EditAddressInfo.vue -->

<template>
  <div>
    <form>
      <label>收件人姓名: </label>
      <input type="text" v-model="editedAddress.receiverName" />
      <br />
      <label>联系电话: </label>
      <input type="text" v-model="editedAddress.phone" />
      <br />
      <label>省份: </label>
      <input type="text" v-model="editedAddress.province" />
      <br />
      <label>市: </label>
      <input type="text" v-model="editedAddress.municipality" />
      <br />
      <label>区/县: </label>
      <input type="text" v-model="editedAddress.county" />
      <br />
      <label>乡/镇: </label>
      <input type="text" v-model="editedAddress.township" />
      <br />
      <label>详细地址: </label>
      <input type="text" v-model="editedAddress.detailAddress" />
      <br />
    </form>
    <button @click="finishEditing">完成</button>
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
    };
  },
  created() {
    this.editedAddress = JSON.parse(JSON.stringify(this.address));
  },
  methods: {
    finishEditing() {
      console.log(this.editedAddress);
      this.$axios.post('/modifyReceiverAddress', this.editedAddress)
          .then((response) => {
            if (!response.data.success) {
              // 假设已经成功更新了地址信息
              // 然后向父组件发送事件通知，表示编辑已完成
              this.$emit("finishEditing");
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
</style>