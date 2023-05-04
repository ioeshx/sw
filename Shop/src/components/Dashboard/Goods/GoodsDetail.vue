<!--公共组件，展示商品详细信息-->
<template>
    <div class="wrapGD">
        
            <!--img src="@/assets/logo.png"-->
            <!--el-pagination class="pages" background layout="prev, pager, next" :total="50" size="small"/>
            <div v-for="avatar in goods.goodsAvatar">
                <img :src="'data:image/jpeg;base64,' + avatar">
            </div-->
            <ShowPics :goodsAvatar="goods.goodsAvatar" class="image"/>
        
        <div class="infoGD">
            <p>商品名称：{{ goods.goodsname }}</p>
            <p>商品类别：{{ goods.goodsCategory }}</p>
            <p>商品简介：{{ goods.introduction }}</p>
            <p>商品库存：{{ goods.goodsStock }}</p>
            <p>商品价格：{{ goods.goodsPrice }}</p>
        </div>        

    </div>
</template>
 
<script>
import "../../../constant";
import ShowPics from "../../Public/Upload/ShowPics.vue"
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    components:{
        ShowPics
    }
}
</script>

 
<style scoped>
.wrapGD{
    display: grid;
    grid-template-columns: 50% 50%;
    margin-top: 20px;
}

.wrapGD .image{
    margin-top: 20px;
    margin-left: 20px;
    width:70%;
    height: 70%;
}

.wrapGD .image img{
    width:100%;
    height: 100%;
    background: rgba(128,128,128,0.1);
}
.infoGD{
    padding: 0px;
    line-height: 50px;
    margin-left: -20px;
}
.pages{
    margin-top:10px;
}

</style>