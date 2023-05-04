<template>
    <div class="wrap">
        <div class="info">
            <img :src="'data:image/jpeg;base64,'+goods.goodsAvatar[0]">

            <div class="intro">
                <span>商品名称: {{ goods.goodsname }}</span>
                <!--span>商品简介 </span-->
            </div>
            <div class="category">
                <p>{{ goods.goodsCategory }}</p>
                <!--p>分类2</p-->
            </div>
        </div>
        <div class="price">
            <p>￥{{ goods.goodsPrice }}</p>
        </div>
        <div class="amount">
            {{ goods.num }}
        </div>
        <div class="count">
            <p>￥{{ Number(goods.goodsPrice)*Number(goods.num) }}</p>
        </div>
        <div class="btn">
            <el-button @click="deleteGoodsFromCart">删除</el-button>
        </div>


    </div>
    
</template>

<script>
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    methods: {
        deleteGoodsFromCart() {
            this.$axios.post("/deleteGoodsFromCart", {
                username: localStorage.getItem("username"),
                goodsId: this.goods.goodsId
            })
            .then((res) => {
                this.$message.success("删除成功");
                this.$emit('reloadPage')
            })
            .catch((err) => {
                this.$message.error("删除失败！请重试...");
            });
        }
    }
}
</script>

<style scoped>
.wrap{
    display:grid;
    grid-template-columns: 45% 13% 17% 11% 15%;
    background-color: transparent;
    line-height: 30px;
}
.info{
    grid-column: 1/2;
    display: grid;
    grid-template-columns: 30% 25% 25%;
    gap:20px;
}
.intro{
    display: flex;
    flex-direction: column;
}
.category{
    display: flex;
    flex-direction: column;
}
.btn{
    margin:0 auto;
}
img{
    margin-left:-40px;
    width: 100%;
    height: 100%;
    align-items: end;
    background-color: white;
}
</style>