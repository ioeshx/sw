import "./constant.js"

// 未登录用户不可以进入任何页面
export function interceptor(Vue) {
    var localStorage = window.localStorage;

    if(!localStorage.getItem('username')) {
        Vue.$message.error("访问失败：请先登录！");
        Vue.$router.push({name:'Login'});
    }
}

// 用户和商户复用的页面
export function userorshopInterceptor(Vue) {
    var localStorage = window.localStorage;

    if(localStorage.getItem('usertype') != window.USER && localStorage.getItem('usertype') != window.SHOPKEEPER) {
        Vue.$message.error("访问失败：请登入普通用户或商户账号！");
        Vue.$router.push({name:'Login'});
    }
}


// 不同类型用户之间不可互相访问页面
export function userInterceptor(Vue) {
    var localStorage = window.localStorage;

    if(localStorage.getItem('usertype') != window.USER) {
        Vue.$message.error("访问失败：请登入普通用户账号！");
        Vue.$router.push({name:'Login'});
    }
}
export function managerInterceptor(Vue) {
    var localStorage = window.localStorage;

    if(localStorage.getItem('usertype') != window.MANAGER) {
        Vue.$message.error("访问失败：请登入管理员账号！");
        Vue.$router.push({name:'Login'});
    }
}
export function shopkeeperInterceptor(Vue) {
    var localStorage = window.localStorage;

    if(localStorage.getItem('usertype') != window.SHOPKEEPER) {
        Vue.$message.error("访问失败：请登入商家账号！");
        Vue.$router.push({name:'Login'});
    }
}