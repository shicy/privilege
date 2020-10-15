// 系统环境信息

import { $get } from "@scyui/vue-base";

const isDevelopment = process.env.NODE_ENV === "development";

let userInfo = null;
let accessToken = null;
let accessTokenTime = 0;

// 判断是否开发环境
export function isDev() {
  return isDevelopment;
}

// API转换
export function api(url) {
  if (!/^http/.test(url)) {
    if (isDevelopment) {
      url = "/api" + url;
    }
  }
  return url;
}

// ==============================================
// export function showWaiting(context, text) {
//   // body...
// }

// ==============================================
export function doLogin() {
  // .
}

export function doLogout() {
  userInfo = null;
  return new Promise(resolve => {
    setTimeout(() => {
      resolve();
    }, 500);
  });
}

export function setUser(user) {
  userInfo = user;
  console.log("===>", userInfo);
}

// 验证用户是否登录，或者登录是否已过期
export function checkUserSession() {
  return new Promise((resolve, reject) => {
    let params = { needUser: !userInfo };
    $get(api("/account/valid"), params, (err, ret) => {
      // console.log("==>", err, ret);
      if (!err) {
        if (ret) {
          setUser(ret);
        }
        resolve();
      } else {
        reject();
      }
      return false;
    });
  });
}

// 获取 AccessToken 信息
export function getAccessToken() {
  if (accessToken && Date.now() - accessTokenTime < 15 * 60 * 1000) {
    return Promise.resolve(accessToken);
  }
  return new Promise(resolve => {
    $get(api("/account/access/token"), null, (err, ret) => {
      accessToken = !err ? ret : "";
      accessTokenTime = Date.now();
      resolve(accessToken);
    });
  });
}
