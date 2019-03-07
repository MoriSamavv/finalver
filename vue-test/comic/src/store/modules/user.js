import { loginbyUser, logout, getUserInfo } from "@/api/login";
import { setToken, removeToken } from "@/utils/auth";
import Cookies from "js-cookie";
import * as types from "../mutaion-types";

const user = {
  state: {
    token: "",
    roles: [],
    name: "",
    avatar: "",
    introduction: ""
  },
  //更改 Vuex 的 store 中的状态的唯一方法是提交 mutation。
  //你不能直接调用一个 mutation handler。这个选项更像是事件注册：“当触发一个类型为 increment 的 mutation 时，调用此函数。”
  //要唤醒一个 mutation handler，你需要以相应的 type 调用 store.commit 方法：store.commit('increment')
  //以下采用的就是官方文档中使用常量替代 Mutation 事件类型
  //mutation 都是同步事务
  mutations: {
    [types.SET_TOKEN]: (state, token) => {
      state.token = token;
    },
    [types.SET_ROLES]: (state, roles) => {
      state.roles = roles;
    },
    [types.SET_INTRODUCTION]: (state, introduction) => {
      state.introduction = introduction;
    },
    [types.SET_NAME]: (state, name) => {
      state.name = name;
    },
    [types.SET_AVATAR]: (state, avatar) => {
      state.avatar = avatar;
    }
  },
  // Action 类似于 mutation，不同在于：
  //    Action 提交的是 mutation，而不是直接变更状态。
  //    Action 可以包含任意异步操作。
  // Action 通过 store.dispatch 方法触发：
  actions: {
    // 参数解构
    // resolve解析  reject拒绝
    loginbyUser({ commit }, { username, password }) {
      return new Promise(async (resolve, reject) => {
        try {
          // 这里的 loginbyUser 是import { loginbyUser, logout, getUserInfo } from "@/api/login" 这里来的
          console.log("11111");
          const response = await loginbyUser(username, password);
          if (response.data) {
            commit(types.SET_TOKEN, response.data.token);
            setToken(response.data.token);
            Cookies.set("user", username);
          }
          resolve(response);
        } catch (error) {
          reject(error);
        }
      });
    },
    logout({ commit, state }) {
      return new Promise(async (resolve, reject) => {
        try {
          await logout(state.token);
          commit(types.SET_ROLES, "");
          commit(types.SET_ROLES, []);
          Cookies.remove("user");
          removeToken();
          resolve();
        } catch (error) {
          reject(error);
        }
      });
    },
    felogout({ commit }) {
      return new Promise(resolve => {
        commit(types.SET_TOKEN, "");
        removeToken();
        resolve();
      });
    },
    getUserInfo({ commit }) {
      return new Promise(async (resolve, reject) => {
        try {
          const response = await getUserInfo(Cookies.get("user"));
          const { roles, name, avatar, introduction } = response.data;

          console.log(roles+"---"+name+"---"+avatar+"---"+introduction);
          commit(types.SET_ROLES, roles);
          commit(types.SET_NAME, name);
          commit(types.SET_AVATAR, avatar);
          commit(types.SET_INTRODUCTION, introduction);
          // commit(types.SET_UID, uid);

          resolve(response);
        } catch (error) {
          reject(error);
        }
      });
    }
  }
};

export default user;
