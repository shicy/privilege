// 系统环境信息

const isDevelopment = process.env.NODE_ENV === "development";
let userInfo = null;

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
    setTimeout(() => {
      if (userInfo) {
        resolve({});
      } else {
        reject();
      }
    }, 500);
  });
}
