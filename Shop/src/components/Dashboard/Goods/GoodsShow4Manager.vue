<!--管理员界面的商品展示组件-->
<script setup>
import { reactive, ref } from 'vue'
const dialogTableVisible = ref(false)
</script>


<template>
  <div class="goods">
    <a href="#" class="goodsLink">

      <img v-if="goods.goodsAvatar.length==0" class="logo" src="@/assets/shop.png" alt="点击进入店铺详情页面"/>
      <img v-else :src="'data:image/jpeg;base64,'+goods.goodsAvatar[0]" class="logo">

      <p>{{ goods.goodsname }}</p>
      <p v-if="goods.status==0">申请类型：注册申请</p>
      <p v-if="goods.status==2">申请类型：修改申请</p>
      
      <el-button class="btnReg"  @click="dialogTableVisible = true">
        <p>点击查看详细信息</p>
      </el-button>

      <el-dialog class="information" v-model="dialogTableVisible" title="商品信息">
        <p class="infoTop">所属店铺：{{ goods.shopname }}</p>
        <GoodsDetail :goods="this.goods"></GoodsDetail>

        <template #footer>
          <span class="dialog-footer">
            <el-button 
                type="primary" 
                @click="this.goodsApplicationApproved()" 
            >批准
            </el-button>

            <el-button 
                type="primary" 
                @click="this.goodsApplicationRejected()" 
            >拒绝
            </el-button>
          </span>
        </template>
      </el-dialog>   
    </a>
  </div>
</template>

<script>
import "../../../constant"
import GoodsDetail from './GoodsDetail.vue';
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    data() {
      return {
        editingGoods: {
          goodsname: "",
          goodsPrice :"",
          goodsStock : "",
          goodsCategory : "",
          introduction : "",
          goodsAvatar:[]
        }
      }
    },
    components: { 
      GoodsDetail
    },
    methods: {
        goodsApplicationApproved() {
            this.$axios.post('/goodsApplicationApproved',{
                goodsId: this.goods.goodsId,
                status: this.goods.status
            }).then(res => {
                    if(res.data.state == window.SUCCESS){
                      this.$message.success(res.data.message);
                      this.$emit('reload');
                    }
                    else {
                        this.$message.error("操作失败，请重试");
                    }
                })
            .catch(err => {
                console.log(err);
            })
        },
        goodsApplicationRejected() {
            this.$axios.post('/goodsApplicationRejected',{
                goodsId: this.goods.goodsId,
                status: this.goods.status
            }).then(res => {
                    if(res.data.state == window.SUCCESS){
                       this.$message.success(res.data.message);
                       this.$emit('reload');
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

<style>
.goodsLink{
    width: 90%;
    height: 220px;
    padding: 20px 30px 10px 20px;
    margin: 10px 20px 20px 20px;
    line-height: 100px;
    align-items: center;

    display: grid;
    grid-template-columns: 60% 40%;
    grid-template-rows: 20% 50% 30%;
    grid-column-gap:20px;

    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    text-decoration: none;  
}
.goodsLink .logo{
    grid-row: 1/3;
    grid-column: 1/2;
    width: 80%;
    height: 80%;
}
.goodsLink > p{
  padding-top: 5px;
  margin-left: -20px;
  line-height: 30px;
}


.goodsLink .btnReg{
    grid-row: 3/4;
    grid-column: 1/3;
}

.goodsLink .information .infoTop{
  margin-top:-40px;
}

.goodsLink .information p{
  font-size:20px;
  margin:0px 0px 0px 50px;
  line-height: 40px;
  font-family: "Brush Script MT", cursive;
  height: auto;
}


.dialog-footer{
  margin-right: 10px;
}


</style>