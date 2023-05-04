<!-- 添加商品的组件页面-->
<template>
    <div>
        <!-- 进度条 -->
        <el-card>
            <el-steps :active="activeIndex">
                <el-step title="基本信息填写"></el-step>
                <el-step title="管理员审核"></el-step>
                <el-step title="申请成功"></el-step>
                <el-step title="商品已下架"></el-step>
            </el-steps>

        </el-card>

        <el-form :model="addForm" :rules="addFormRules" ref="addForm" label-width="100px" label-position="top">
            <div v-if="this.activeIndex==1">
               <GoodsRegister :goods='goods'/>
            </div>

            <div v-if="this.activeIndex==2">
                管理员正在审核，请耐心等待...
                <div v-for="avatar in goods.goodsAvatar">
                    <img :src="'data:image/jpeg;base64,' + avatar">
                </div>
                
                <p>商品类型：{{ goods.goodsname }}</p>
                <p>商品类别：{{ goods.goodsCategory }}</p>
                <p>商品简介：{{ goods.introduction }}</p>
                <p>商品库存：{{ goods.goodsStock }}</p>
                <p>商品价格：{{ goods.goodsPrice }}</p>
                <el-button 
                    type="primary" 
                    @click="this.modifyGoodsInfo()" 
                >修改商品信息
                </el-button>
            </div>

            <div v-if="this.activeIndex==3">
                <div v-for="avatar in goods.goodsAvatar">
                    <img :src="'data:image/jpeg;base64,' + avatar">
                </div>
                
                    <p>商品类型：{{ goods.goodsname }}</p>
                    <p>商品类别：{{ goods.goodsCategory }}</p>
                    <p>商品简介：{{ goods.introduction }}</p>
                    <p>商品库存：{{ goods.goodsStock }}</p>
                    <p>商品价格：{{ goods.goodsPrice }}</p>

                <el-button 
                    type="primary" 
                    @click="this.modifyGoodsInfo()" 
                >修改商品信息
                </el-button>
                <el-button 
                    type="primary" 
                    @click="this.goodsOffShelve()" 
                >下架
                </el-button>
            </div>
            
            <div v-if="this.activeIndex==4">
                <el-button 
                    type="primary" 
                    @click="this.goodsOnShelve()" 
                >重新上架
                </el-button>
            </div>
        </el-form>


    </div>
</template>
 
<script>
import "../../../constant";
import GoodsRegister from "./GoodsRegister.vue"
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    components: {
        GoodsRegister
    },
    data(){
        return {
            activeIndex: 3
        }
    },
    created(){
        this.getGoodsInfo();
    },
    methods:{
        getGoodsInfo() {
            switch(this.goods.status) {
                case 0: // 注册中
                    switch(this.goods.registerStatus) {
                        case 0: //尚未批复
                            this.activeIndex = 2;
                            break;
                        case 2: //管理员拒绝
                            this.activeIndex = 1;
                            break;
                    }
                case 2: // 提交修改请求，等待批复
                    switch(this.goods.modifyStatus) {
                        case 1: //尚未批复
                            this.activeIndex = 2;
                            break;
                        case 2: //管理员拒绝
                            this.activeIndex = 1;
                            break;
                    }
                case 1:
                    this.activeIndex = 3;
                    break;
                case 3: 
                    this.activeIndex = 4;
                    break;
            }
        },
        modifyGoodsInfo() {
            localStorage.setItem("goodsId", this.goods.goodsId);
            this.activeIndex = 1;
        },
        goodsOffShelve() {
            this.$axios.post("/goodsOffShelve", {
                goodsId: this.goods.goodsId
            })
            .then((res) => {
                console.log(res.data);
                this.$message.success("下架成功！页面将自动刷新...");
                this.$router.go(0);
            })
            .catch((err) => {
                console.log(err);
                this.$message.error("下架失败！请重试...");
            });
        },
        goodsOnShelve() {
            this.$axios.post("/goodsOnShelve", {
                goodsId: this.goods.goodsId
            })
            .then((res) => {
                console.log(res.data);
                this.$message.success("上架成功！页面将自动刷新...");
                this.$router.go(0);
            })
            .catch((err) => {
                console.log(err);
                this.$message.error("上架失败！请重试...");
            });
        },
        next() {
            this.activeIndex++;
        },
        prev() {
            this.activeIndex--;
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