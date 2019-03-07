// https://cli.vuejs.org/

//注意这里的都是关乎node的
//Node.js path 模块提供了一些用于处理文件路径的工具，我们可以通过以下方式引入该模块：
const path = require("path");

//__dirname 表示当前文件所在的目录的绝对路径
function resolve(dir) {
  console.log("__dirname  " + __dirname);
  return path.join(__dirname, dir);
}

//process 对象是一个全局变量，它提供有关当前 Node.js 进程的信息并对其进行控制。
//作为一个全局变量，它始终可供 Node.js 应用程序使用，无需使用 require()。
module.exports = {
  baseUrl: process.env.baseUrl,
  outputDir: process.env.outputDir,
  lintOnSave: true,

  configureWebpack: () => {
    if (process.env.NODE_ENV === "production") {
      // 为生产环境修改配置...
    } else {
      // 为开发环境修改配置...
    }
  },
  chainWebpack: config => {
    config.resolve.alias
      .set("vue$", "vue/dist/vue.esm.js")
      .set("@", resolve("src"))
      .set("assets", resolve("src/assets"))
      .set("components", resolve("src/components"))
      .set("api", resolve("src/api"))
      .set("utils", resolve("src/utils"))
      .set("store", resolve("src/store"))
      .set("router", resolve("src/router"));

    config.resolve.extensions
      .add(".js")
      .add(".vue")
      .add(".styl");

    config.module.rule("svg").uses.clear();
    config.module
      .rule("svg")
      .use("raw-loader")
      .loader("raw-loader");
  },

  productionSourceMap: false,
  css: {
    sourceMap: false
  },

  devServer: {
    open: process.platform === "darwin",
    host: "0.0.0.0",
    port: 8863,
    https: false,
    hotOnly: false,
    proxy: null
  }
};
