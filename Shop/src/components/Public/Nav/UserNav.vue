<template>
    <header>
            <p>Welcome!</p>
            <nav>
                <RouterLink to="/UserWeb">首页</RouterLink>
                <RouterLink to="/userSL">购物车</RouterLink>
                <RouterLink to="#">收藏夹</RouterLink>
                <RouterLink to="/userSelfCenter">个人中心</RouterLink>
                <RouterLink to="/">退出登录</RouterLink>
            </nav>
    </header>
</template>

<style scoped>
header {
    background-color:#81A18B;
    width: 100%;
    padding:10px;
    text-align: center;
    display: grid;
    grid-template-columns: 50%, 50%;
    grid-row: 100%;
}
header p{
    grid-column: 1/2;
    text-align: left; 
    margin-left: 20px;
    padding: 0 1rem;
    font-size: 20px;
    color:white;
    transition: 0.4s;

}
header nav{
    grid-column: 2/3;
    text-align: right; 
}
header nav a {
    display: inline-block;
    padding: 0 1rem;
    font-size: 20px;
    border-left: 1px solid var(--color-border);
    color:white;
    text-decoration: underline;
    transition: 0.4s;
    font-family: "Brush Script MT", cursive;
}
</style>