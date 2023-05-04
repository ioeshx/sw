<template>
  <div>
    <router-view>
      
    </router-view>
  </div>

</template>

<style scoped>
header {
  line-height: 0;
  max-height: 100vh;
}

body{
  display: block;
}

.top{
  top:0px;
  width : 100%;
  height: 100%;
  object-fit: cover;
  animation-name:top;
  animation-timing-function: linear;
  animation-duration: 1s;
  
}

@keyframes top {

  0%{opacity: 0;}
  100%{opacity: 1;}
  
}



.bottom{
  margin-left : 0px;
  margin-bottom: -160px;
  margin-top :-200px;
  width : 100%;
  height: 100%;
  z-index:-1;
  object-fit: cover;
  animation-name:bottom;
  animation-timing-function: linear;
  animation-duration: 1s;
}

@keyframes bottom {
  0% {bottom: -200px;}
  25% {bottom: -150px;}
  50% {bottom: -100px}
  75% {bottom: -50px}
  100% {bottom: 0px;}
}




.wrapper{
    display: flex;
}

.logo {
  width : 15%;
  height: 15%;
  top: 120px;
  animation-name: logo;
  animation-timing-function: linear;
  animation-duration: 1s;
}
@keyframes logo {
  0% {left: -40px;}
  25% {left: -30px;}
  50% {left: -20px}
  75% {left: -10px}
  100% {left: 0px;}
}


.viewr{
  top:0px;
  left: 80px;
  right: -200px;
}


.welcome{
  top:140px;
  left:30px;
  animation-name: top;
  animation-duration: 4s;
}


nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: #195844;
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  font-size: 20px;
  border-left: 1px solid var(--color-border);
  color: #615d31;
  text-decoration: none;
  transition: 0.4s;
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    /*display: flex;*/
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
