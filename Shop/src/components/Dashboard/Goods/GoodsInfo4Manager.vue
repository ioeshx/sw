<template>
    <div>
        <GoodsDetail :goods="this.goods"></GoodsDetail>
        <p>店铺名：{{ goods.shopname }}</p>
        
        <el-button 
            type="primary" 
            @click="this.approveGoods()" 
        >批准
        </el-button>
            <el-button 
                type="primary" 
                @click="this.rejectGoods()" 
            >拒绝
        </el-button>

        
    </div>
</template>
 
<script>
import "../../../constant";
import GoodsDetail from "./GoodsDetail.vue";
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    components: {
        GoodsDetail
    },
    methods:{
        approveGoods() {
            this.$axios.post('/approveGoods',{
                goodsId: this.goods.goodsId
            }).then(res => {
                    if(res.data.state == window.SUCCESS){
                       this.$message.success(res.data.message);
                        setTimeout(() => {
                            this.$router.go(0);
                        }, 1000);
                    }
                    else {
                        this.$message.error("操作失败，请重试");
                    }
                })
            .catch(err => {
                console.log(err);
            })
        },
        rejectGoods() {
            this.$axios.post('/approveGoods',{
                goodsId: this.goods.goodsId
            }).then(res => {
                    if(res.data.state == window.SUCCESS){
                       this.$message.success(res.data.message);
                       this.$router.go(0);
                    }
                    else {
                        this.$message.error("操作失败，请重试");
                    }
                })
            .catch(err => {
                console.log(err);
            })
        }
    }
}

</script>

 
<style scoped>
 .el-steps{
  margin: 15px 0;
}
.el-step__title{
  font-size: 13px;
}

</style>