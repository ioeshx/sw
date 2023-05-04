<!-- 商家查看商品详情 -->
<template>
    <div class="wrap">
        <h1>进度表</h1>

        <!-- 进度条 -->
        <el-card class="steps">
            <el-steps :active="activeIndex">
                <el-step title="基本信息填写"></el-step>
                <el-step title="管理员审核"></el-step>
                <el-step title="申请成功"></el-step>
                <el-step title="商品已下架"></el-step>
            </el-steps>
        </el-card>

        <el-form class="form" :model="addForm" :rules="addFormRules" ref="addForm">
            <div v-if="this.activeIndex==1">
                <div class="btns">
                    <el-button 
                        class="btn"
                        v-if="!(goods.registerStatus==2 || goods.modifyStatus == 2)"
                        type="primary" 
                        @click="this.getGoodsInfo()" 
                    >取消
                    </el-button>
                    <el-button class="btn" @click="cancelShowGoodsDetail">返回管理页</el-button>
                </div>
                <p v-if="(goods.registerStatus==2 || goods.modifyStatus == 2)">申请被拒绝，请修改信息后重新提交</p>

               <GoodsRegister class="reg" :goods='goods'/>
               
            </div>

            <div v-if="this.activeIndex==2">
                <el-space class="hint">管理员正在审核，请耐心等待...</el-space>
                <GoodsDetail :goods="this.goods"/>
                <div class="btns">
                    <el-button
                        class="btn" 
                        type="primary" 
                        @click="this.modifyGoodsInfo()" 
                        >修改商品信息
                    </el-button>
                    <el-button
                        class="btn" 
                        type="primary" 
                        @click="this.cancelApplication()" 
                        >撤销申请
                    </el-button>
                    <el-button class="btn" @click="cancelShowGoodsDetail">返回</el-button>
                </div>
            </div>

            <div v-if="this.activeIndex==3">
                <GoodsDetail :goods="this.goods"/>
                <div class="btns">
                    <el-button
                        class="btn" 
                        type="primary" 
                        @click="this.modifyGoodsInfo()" 
                        >修改商品信息
                        </el-button>
                        <el-button
                            class="btn" 
                            type="primary" 
                            @click="this.goodsOffShelve()" 
                        >下架
                    </el-button>
                    <el-button class="btn" @click="cancelShowGoodsDetail">返回</el-button>
                </div>
            </div>
            <div v-if="this.activeIndex==4">
                <GoodsDetail :goods="this.goods"/>

                <div>
                    <div class="btns">
                        <el-button
                            class="btn" 
                            type="primary" 
                            @click="this.goodsOnShelve()" 
                            >重新上架
                        </el-button>
                        <el-button class="btn" @click="cancelShowGoodsDetail">返回</el-button>
                    </div>
                </div>
                

                
            </div>
        </el-form>

        


    </div>
</template>
 
<script>
import "../../../constant";
import GoodsRegister from "./GoodsRegister.vue";
import GoodsDetail from "./GoodsDetail.vue";
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    components: {
        GoodsRegister,
        GoodsDetail
    },
    data(){
        return {
            activeIndex: 3
        }
    },
    created(){
        this.getGoodsInfo();
        console.log(this.goods.goodsAvatar)
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
                    break;
                case 2: // 提交修改请求，等待批复
                    switch(this.goods.modifyStatus) {
                        case 1: //尚未批复
                            this.activeIndex = 2;
                            break;
                        case 2: //管理员拒绝
                            this.activeIndex = 1;
                            break;
                    }
                    break;
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
                setTimeout(() => {
                    this.$router.go(0);
                }, 1000);
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
                setTimeout(() => {
                    this.$router.go(0);
                }, 1000);
                
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
        },
        cancelApplication() {
            this.$axios.post('/cancelApplication',{
                goodsId: this.goods.goodsId
            }).then(res => {
                    if(res.data.state == window.SUCCESS){
                       this.$message.success(res.data.message);
                       setTimeout(() => {
                            this.$router.go(0);
                        }, 1000);
                    }
                    else {
                        this.$message.error("提交失败，请重试");
                    }
                })
            .catch(err => {
                console.log(err);
            })
        },
        cancelShowGoodsDetail() {
            this.$emit("cancelShowGoodsDetail"); 
        }
    }
}

</script>

 
<style scoped>
.wrap{
    margin:10px;
    padding:30px;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 3px solid #ebeef5;
    transition: .3s;
}
.wrap h1{
  text-align: left;
  font-family:"Lucida Console", "Courier New", monospace;
  font-size: 30px;
  margin-bottom:20px;
  font-weight: bolder;
  color:#81A18B;
}

.steps{
    margin-bottom:30px;
}

.form{
    line-height: 30px;
}

.btns{
    display: flex;
    margin-top:20px;
    gap:20px;
}
.btn{
    width:100%;
}

.hint{
    margin-left: 20px;
    font-family: "Brush Script MT", cursive;
    font-size: 20px;

}

.reg{
    margin-top: 40px;
}
</style>