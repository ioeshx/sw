<template>
  <div>
    <h1>地址列表</h1>
    <div v-if="!isEditingAddress">
    <ul>
      <li v-for="address in addressList" :key="address.addressId">
        <div>
          <strong>收货人：</strong>{{ address.receiverName }}
        </div>
        <div>
          <strong>电话：</strong>{{ address.phone }}
        </div>
        <div>
          <strong>省份：</strong>{{ address.province }}
        </div>
        <div>
          <strong>城市：</strong>{{ address.municipality }}
        </div>
        <div>
          <strong>区县：</strong>{{ address.county }}
        </div>
        <div>
          <strong>乡镇：</strong>{{ address.township }}
        </div>
        <div>
          <strong>详细地址：</strong>{{ address.detailAddress }}
        </div>
        <div>
          <el-button type="primary" @click="editAddress(address)">修改</el-button>
          <el-button type="danger" @click="deleteAddress(address.addressId)">删除</el-button>
        </div>
        <hr />
      </li>
    </ul>
    </div>
    <component
        v-else
        :is="currentComponent"
        :address="selectedAddress"
        @finishEditing="finishEditing"
    ></component>
    <EditAddressInfo
        v-if="editingAddress"
        :address="editingAddress"
        @finishEditing="handleFinishEditing"
    ></EditAddressInfo>
  </div>
</template>

<script>
import EditAddressInfo from "./EditingAddressInfo.vue";
export default {
  components: {
    EditAddressInfo,
  },
  data() {
    return {
      addressList: [],
      isEditingAddress: false,
      selectedAddress: null,
      currentComponent: "",
    };
  },
  created() {
    this.getAllReceiverAddress();
  },
  methods: {
    getAllReceiverAddress() {
      var localStorage = window.localStorage;
      this.$axios
          .post("/getAllReceiverAddress", {
            username: localStorage.getItem("username"),
          })
          .then((res) => {
            this.addressList = res.data.data;
          });
    },
    deleteAddress(addressId) {
      this.$axios
          .post("/deleteReceiverAddress", {
            addressId: addressId,
          })
          .then(res => {
            console.log(res.data.state)
            console.log(window.SUCCESS)
            if (res.data.state == window.SUCCESS) {
              this.$message.success(res.data.message);
              this.$router.go(0);
            } else {
              const error_msg = res.data.message.replace(/&$/, '');
              const msg = error_msg.split("&");
              for (let item of msg) {
                this.$message.error(item);
              }
            }
          });
    },
    editAddress(address) {
      this.isEditingAddress = true;
      this.selectedAddress = address;
      this.currentComponent = "EditAddressInfo";
    },
    finishEditing() {
      this.isEditingAddress = false;
      this.selectedAddress = null;
      this.currentComponent = "";
      // 在这里重新获取地址列表以更新显示的信息
    },
    handleFinishEditing() {
      this.editingAddress = null;
      this.getAllReceiverAddress();
    },
  },
};
</script>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 16px;
}
</style>
