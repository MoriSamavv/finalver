import Vue from "vue";
import VueRouter from "vue-router";
import _import from "@/utils/import";
const Layout = _import("layout/Layout");

// 根据文档，路由的流程
// 1、定义模版 template ，在这里就 views 里面的每个文件
// 2、定义路由表，就是下面的这个
// 3、const router = new VueRouter 把路由表赋值进去
// 4、在最外面的 new Vue(); 那把路由对象赋值进去

// 另外，除了使用 <router-link> 创建 a 标签来定义导航链接，
// 还可以借助 router 的实例方法，通过编写代码来实现
// router.push(...)


Vue.use(VueRouter);

export const constantRouterMap = [
  {
    path: "/401",
    component: _import("errorPage/401"),
    hidden: true
  },
  {
    path: "/404",
    component: _import("errorPage/404"),
    hidden: true
  },
  {
    path: "/login",
    component: _import("login/index"),
    hidden: true
  },
  {
    path: "/lock",
    component: _import("lock/index"),
    name: "lock",
    hidden: true
  },
  {
    path: "",
    component: Layout,
    redirect: "dashboard",
    children: [
      {
        path: "dashboard",
        name: "dashboard",
        component: _import("dashboard/index"),
        meta: {
          title: "dashboard",
          icon: "dashboard",
          noCache: true
        }
      }
    ]
  }
];

export const router = new VueRouter({
  routes: constantRouterMap,
  scrollBehavior: () => ({ y: 0 })
});
