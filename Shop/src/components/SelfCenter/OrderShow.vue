<template>
    <div class="goodsInfo">
        <div class="goodsList">
            <h3 class="goodsTitle">已购买</h3>
            <!-- 购物记录将在此处通过 JavaScript 动态加载  -->
        </div>
    </div>
</template>

<style scoped>
.goodsInfo{
    grid-column: 2/3;
    border-radius: 5px;
    padding: 10px;
    display: flex;
    flex-direction: column;
}
  
.goodsList {
    background-color:#81A18B;
    display: flex;
    flex-direction: column;
    padding: 15px;
    max-height: 300px;
    margin-bottom: 20px;
}
.goodsTitle {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
    color:white;
}
</style>