import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: () => import('../views/WelcomeWeb/LoginWeb.vue')
    },
    {
      path: '/Register',
      name: 'Register',
      component: () => import('../views/WelcomeWeb/RegisterWeb.vue')
    },
    {
      path: '/UserWeb',
      name: 'UserWeb',
      component: () => import('../views/MainWeb/User/UserWeb.vue')
    },
    {
      path: '/ShopkeeperWeb',
      name: 'ShopkeeperWeb',
      component: () => import('../views/MainWeb/Shopkeeper/ShopkeeperWeb.vue')
    },
    {
      path: '/ManagerWeb',
      name: 'ManagerWeb',
      component: () => import('../views/MainWeb/Manager/ManagerWeb.vue')
    },
    {
      path: '/ManagerWebBlank',
      name: 'ManagerWebBlank',
      component: () => import('../views/MainWeb/Manager/ManagerWebBlank.vue')
    },
    {
      path: '/ShopDashboard',
      name: 'ShopDashboard',
      component: () => import('../views/MainWeb/Shopkeeper/ShopDashboard.vue')
    },
    {
      path: '/ShopDashboardBlank',
      name: 'ShopDashboardBlank',
      component: () => import('../views/MainWeb/Shopkeeper/ShopkeeperDashboardBlank.vue')
    },
    // 上述页面已添加拦截器


    // 新增拦截器，需要测试
    {
      path: '/userSelfCenter',
      name: 'userSelfCenter',
      component: () => import('../views/SelfCenter/UserSC.vue')
    },
    {
      path: '/shopkeeperSelfCenter',
      name: 'shopkeeperSelfCenter',
      component: () => import('../views/SelfCenter/ShopkeeperSC.vue')
    },
    {
      path: '/userSL',
      name: 'userSL',
      component: () => import('../views/ShoppingList/UserSL.vue')
    },
    {
      path: '/shopkeeperSL',
      name: 'shopkeeperShoppingList',
      component: () => import('../views/ShoppingList/ShopkeeperSL.vue')
    },
    {
      path: '/Shop4User/:shopname',
      name: 'Shop4User',
      component: () => import('../views/ShopShow/UserSS.vue')
    },
    {
      path: '/GoodsDetail/:goodsId',
      name: 'GoodsDetail',
      component: () => import('../views/ShopShow/GoodsDetail.vue')
    },
    {
      path: '/GoodsDetail4User/:goodsId',
      name: 'GoodsDetail4User',
      component: () => import('../components/MainWeb/Components/GoodsDetail4User.vue')
    },
    {
      path: '/ShopDetail4User/:shopname',
      name: 'ShopDetail4User',
      component: () => import('../components/MainWeb/Components/ShopDetail4User.vue')
    },
    {
      path: '/GoodsRegister',
      name: 'GoodsRegister',
      component: () => import('../components/Dashboard/Goods/GoodsRegister.vue')
    },
    {
      path: "/order",
      name: "Order",
      component: ()=> import('../views/ShoppingList/Order.vue'),
      props: true, // 使用 props 将参数传递给组件
    },
    {
      path: '/Pay',
      name: 'Pay',
      component: ()=> import('../views/ShoppingList/Pay.vue'),
      param : 'orderIds'
    },
    {
      path: '/OrderPage',
      name: 'OrderPage',
      component: ()=> import('../views/OrderPage/OrderPage.vue'),
    },
    {
      path: '/OrderPageForShopkeeper',
      name: 'OrderPageForShopkeeper',
      component: ()=> import('../views/OrderPage/OrderPageForShopkeeper.vue'),
    },

    {
      path: '/addAddress',
      name: 'addAddress',
      component: ()=> import('../views/ShoppingList/addAddress.vue'),
    },
    {
      path: '/adminIntermediaryAccount',
      name: 'adminIntermediaryAccount',
      component: ()=> import('../views/transactionPage/adminIntermediaryAccount.vue'),
    },
    {
      path: '/adminProfitAccount',
      name: 'adminProfitAccount',
      component: ()=> import('../views/transactionPage/adminProfitAccount.vue'),
    },
    {
      path: '/privateAccount',
      name: 'privateAccount',
      component: ()=> import('../views/transactionPage/privateAccount.vue'),
    },
    {
      path: '/shopAccount',
      name: 'shopAccount',
      component: ()=> import('../views/transactionPage/shopAccount.vue'),
    },
    // 下述页面是为了调试方便的组件路径，最终需要封装到views中，因此不需要额外添加拦截器
    // 提交项目前保证无组件路径
    
  ]
})
router.beforeEach((to, from, next) => {
  console.log(`Navigating from ${from.path} to ${to.path}`);
  next();
});
export default router
