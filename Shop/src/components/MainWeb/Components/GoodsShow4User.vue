<!-- 用户可见的商品简要信息展示 -->

<template>
  <div class="goods">
    <a href="#" class="goodsLink">

      <img v-if="goods.goodsAvatar.length == 0" class="logo" src="@/assets/shop.png" alt="点击进入店铺详情页面"/>
      <img v-else :src="'data:image/jpeg;base64,'+goods.goodsAvatar[0]">

      <p class="goodsName">商品名：{{ goods.goodsname }}</p>
      <p>收藏量： {{ goods.favorites }}</p>

      <router-link :to="{ name: 'GoodsDetail4User', params: { goodsId: this.goods.goodsId } }" target="_blank">
        点击查看详细信息
      </router-link>
          
    </a>
  </div>
</template>

<script>
import "../../../constant"
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    }
}
</script>
