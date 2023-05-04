<!--商家后台界面-->
<script setup>
import ShopRegister from '../../../components/MainWeb/Components/ShopRegister.vue'
import MyShopInfo from '../../../components/MainWeb/Components/MyShopInfo.vue'
import Nav from '../../../components/Public/Nav/ShopkeeperNav.vue'
import Goods4Shopkeeper from '../../DashBoard/Goods/Goods4Shopkeeper.vue'
import Transaction4Shopkeeper from '../../../components/Dashboard/Shop/Transactions4Shopkeeper.vue'
import ShopAccountInfo from '../../../components/Dashboard/Shop/ShopAccountInfo.vue'
//import SearchTop from '../../../components/Dashboard/Goods/SearchG.vue'
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>

    <Nav></Nav>

    <section>
        <el-tabs :tab-position=tabPosition class="tabs">
            <el-tab-pane label="店铺基本信息">
                <MyShopInfo class="info" v-if="isregistered && (!isToModify)" :shop="shop"/>
                <ShopRegister class="cont" v-else :shop="shop"/>
            </el-tab-pane>

            <el-tab-pane v-if="shop.is_admitted==1" label="商品信息">
                <Goods4Shopkeeper/>
            </el-tab-pane>

            <el-tab-pane v-if="shop.is_admitted==1" label="店铺流水">
                <ShopAccountInfo />
                <Transaction4Shopkeeper />
            </el-tab-pane>
        </el-tabs>
    </section>

</template>


<script>
import "../../../constant.js";
import { interceptor, shopkeeperInterceptor } from "../../../interceptor";
import { ref } from 'vue'
const tabPosition = ref('left')
export default {
    props: ["shop_id"],
    components: {
        MyShopInfo,
        ShopRegister,
        Goods4Shopkeeper
    },
    
    data() {
        return {
            isregistered: "",
            isToModify: "",
            shop: {
                goodstype: [],
                shopname: "",
                introduction: "",
                address: "",
                idnum: "",
                capital: "",
                date: "",
                shopid: "",
                is_admitted: "",
                avatar: ""
            }
        };
    },
    created() {
        interceptor(this);
        shopkeeperInterceptor(this);
        this.getMyShopInfo();
        this.toModify();
    },
    methods: {
        // 根据当前username取店铺信息
        getMyShopInfo() {
            var localStorage = window.localStorage;
            this.$axios.post("/getShopInfoByUsername", {
                username: localStorage.getItem("username")
            })
                .then(res => {
                // 如果用户已经注册过，则在localStorage中储存此商户的shopname，便于后续在数据库中的操作
                if (res.data.state == window.SUCCESS) {
                    this.isregistered = 1;
                    this.shop = res.data.data;
                    var localStorage = window.localStorage;
                    localStorage.setItem("shopname", this.shop.shopname);
                    this.getShopAvatar();
                }
                else {
                    this.isregistered = 0;
                }
            });
        },
        getShopAvatar: function () {
            var localStorage = window.localStorage;
            this.$axios.post("/getShopAvatarByShopname", {
                shopname: localStorage.getItem("shopname")
            }).then(res => {
                if (res.data.state == window.SUCCESS) {
                    this.shop.avatar = res.data.data;
                }
            }).catch((err) => {
                console.log(err);
                this.$message.error("图片获取失败！");
              });
        },
        // 如果用户需要修改注册信息重新提交注册，也需要导入ShopRegister页面   
        toModify() {
            var localStorage = window.localStorage;
            if (localStorage.getItem("toModify")) {
                this.isToModify = 1;
            }
            else {
                this.isToModify = 0;
            }
        }
    }
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