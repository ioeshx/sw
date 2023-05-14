<script setup>
import OrderToPay from './OrderToPay.vue'// 请将路径替换为orderToPay.vue的实际路径
import OrderToSendOut4User from "@/views/OrderPage/OrderToSendOut4User.vue";
import OrderDelivered4User from "@/views/OrderPage/OrderDelivered4User.vue";
import OrderDone4User from "@/views/OrderPage/OrderDone4User.vue";
import OrderToRefund4User from "@/views/OrderPage/OrderToRefund4User.vue";
import OrderRefundDone4User from "@/views/OrderPage/OrderRefundDone4User.vue";
</script>

<template>
  <section>
    <el-tabs :tab-position=tabPosition class="tabs">
      <el-tab-pane label="待发货">
        <OrderToSendOut4User/>
        <!--        <MyShopInfo class="info" v-if="isregistered && (!isToModify)" :shop="shop"/>
                <ShopRegister class="cont" v-else :shop="shop"/>
                 -->
      </el-tab-pane>

      <el-tab-pane  label="已发货">
        <OrderDelivered4User/>
        <!--        <Goods4Shopkeeper/>
        -->
      </el-tab-pane>

      <el-tab-pane label="已完成">
        <OrderDone4User/>
        <!--        <ShopAccountInfo />
                <Transaction4Shopkeeper />
                -->
      </el-tab-pane>
      <el-tab-pane label="待退款">
        <OrderToRefund4User/>
      </el-tab-pane>
      <el-tab-pane label="已退款">
        <OrderRefundDone4User/>
      </el-tab-pane>

    </el-tabs>
  </section>

</template>

<script>
import "../../constant.js";
import { interceptor, shopkeeperInterceptor } from "../../interceptor";
import { ref } from 'vue'
const tabPosition = ref('left')
export default {
  name: "OrderPage"
}
</script>
<style scoped>
.tabs{
  margin-top:20px
}

.info{
  margin: 30px auto;
  width: 90%;
}
</style>