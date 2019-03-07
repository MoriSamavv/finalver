
<!--
App.vue是主组件，所有页面都是在App.vue下进行切换的。
可以理解为所有的路由也是App.vue的子组件。
所以我将router标示为App.vue的子组件。
-->
<template>
  <div id="app">
    <!-- 路由匹配到的组件将渲染在这里 -->
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: "app",
    mounted() {}
  };
</script>
