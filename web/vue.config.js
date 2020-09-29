const { CleanWebpackPlugin } = require("clean-webpack-plugin");

const isDevelopment = process.env.NODE_ENV == "development";

module.exports = {
  publicPath: "/web",
  outputDir: "../priv_service/src/main/resources/static/web",
  productionSourceMap: false,

  configureWebpack: config => {
    config.plugins.push(new CleanWebpackPlugin());
    if (isDevelopment) {
      config.watch = true;
    } else {
      config.performance = { hints: false };

      let minimizer = config.optimization.minimizer[0];
      minimizer.options.terserOptions.compress.warnings = false;
      minimizer.options.terserOptions.compress.drop_console = true;
      minimizer.options.terserOptions.compress.drop_debugger = true;
      minimizer.options.terserOptions.compress.pure_funcs = ["console.log"];

      // css大文件分割，IE9限制每个文件最大css个数为4000个，否则将被忽略
      // config.plugins.push(new CSSSplitWebpackPlugin({
      //   size: 4000,
      //   filename: "css/[name]-[part].[ext]"
      // }));
      config.plugins.push(new MyWarningsFilterPlugin());
    }
  },

  devServer: {
    disableHostCheck: true,
    host: "0.0.0.0",
    port: 3000,
    proxy: {
      "^/api": {
        target: "http://localhost:12103",
        pathRewrite: { "^/api": "" }
      }
    }
  }
};

class MyWarningsFilterPlugin {
  apply(compiler) {
    compiler.hooks.afterEmit.tap('MyWarningsFilterPlugin', compilation => {
      compilation.warnings = compilation.warnings.filter(warning => {
        let message = warning.message || warning;
        if (/mini-css-extract-plugin/.test(message)) return false;
        if (/size limit:/.test(message)) return false;
        return true;
      });
    });
  }
};
