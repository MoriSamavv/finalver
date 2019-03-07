import http from "@/utils/http";

export function loginbyUser(username, password) {
  console.log("22222");
  const data = {
    username,
    password
  };
  return http({
    url: "/login/login",
    method: "post",
    data
  });
}

export function logout() {
  return http({
    url: "/login/logout",
    method: "post"
  });
}

export function getUserInfo(username) {
  return http({
    url: "/user/info",
    method: "get",
    params: { username }
  });
}
