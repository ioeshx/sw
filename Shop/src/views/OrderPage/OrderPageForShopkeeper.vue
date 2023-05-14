<script setup>
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
import OrderToSendOut4Shopkeeper from "@/views/OrderPage/OrderToSendOut4Shopkeeper.vue";
import OrderToRefund4Shopkeeper from "@/views/OrderPage/OrderToRefund4Shopkeeper.vue";
import OrderDone4Shopkeeper from "@/views/OrderPage/OrderDone4Shopkeeper.vue";
import OrderToPay from './OrderToPay.vue'  // 请将路径替换为orderToPay.vue的实际路径
</script>

<template>

  <Nav></Nav>

  <section>
    <el-tabs :tab-position=tabPosition class="tabs">
      <el-tab-pane label="待发货">
        <OrderToSendOut4Shopkeeper/>
        <!--        <MyShopInfo class="info" v-if="isregistered && (!isToModify)" :shop="shop"/>
                <ShopRegister class="cont" v-else :shop="shop"/>
                 -->
      </el-tab-pane>

      <el-tab-pane  label="待退款">
        <OrderToRefund4Shopkeeper/>
        <!--        <Goods4Shopkeeper/>
        -->
      </el-tab-pane>

      <el-tab-pane label="已完成">
        <OrderDone4Shopkeeper/>
        <!--        <ShopAccountInfo />
                <Transaction4Shopkeeper />
                -->
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