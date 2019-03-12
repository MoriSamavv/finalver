<template>
  <div class="login-page">
    
    <div class="login-wrap">
      <el-col :class="translateLeft" :span="10">
        <!-- 左边那版 -->
        <div v-show="notforget">
          <div class="logo">
            <img src="../../assets/images/favicon.png" style="width: 50px;height: 50px;"/>
            <div class="title">
              <a>
                <span>漫画</span><span class="subtitle">之家</span>
              </a>
            </div>
          </div>
          <!-- 登陆版面 -->
          <div class="login-form">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
              <el-form-item prop="username">
                <el-input :placeholder="$t('login.userplaceholder')" v-model="ruleForm.username"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input :placeholder="$t('login.pwdplaceholder')" type="password" v-model="ruleForm.password"></el-input>
              </el-form-item>
              <el-form-item class="btn">
                <el-button :loading="loading" type="primary" @click="handleLogin('ruleForm')">登录</el-button>
              </el-form-item>
            </el-form>
          </div>
          <!-- 登陆版面下面2个按钮 -->
          <div class="login-footer">
            <el-col :span="12">
              <el-checkbox v-model="remember" name="type">{{$t('login.remember')}}</el-checkbox>
            </el-col>
            <el-col class="forgetpwd" :span="12">
              <span @click="wrapSwitch(false)">{{$t('login.forgetpwd')}}</span>
            </el-col>
          </div>
        </div>
        <!-- 右边那版 -->
        <div v-show="!notforget">
          <div class="title forgetwrap-title">
            <a>
              <span>用户</span><span class="subtitle">注册</span>
            </a>
          </div>
          <!-- 注册版面 -->
          <div class="forget-form">
            <el-form :model="forgetForm" :rules="forgetRules" ref="forgetForm">
              <el-form-item prop="username">
                <el-input :placeholder="$t('login.forget_email')" v-model="forgetForm.username"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input :placeholder="$t('login.forget_passwrd')" type="password" v-model="forgetForm.password"></el-input>
              </el-form-item>
              <el-form-item prop="confirmPassword">
                <el-input :placeholder="$t('login.confirm_passwrd')" type="password" v-model="forgetForm.confirmPassword"></el-input>
              </el-form-item>
              <el-form-item class="btn">
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-button @click="wrapSwitch(true)" type="primary">{{$t('login.forget_back')}}</el-button>
                  </el-col>
                  <el-col :span="12">
                    <el-button @click="forgetHandle('forgetForm')" type="primary">{{$t('login.forget_btn')}}</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
            </el-form>
          </div>
        </div>

      </el-col>

      <el-col :class="translateRight" :span="14">
        <div class="wallpaper"></div>
      </el-col>
    </div>
  </div>
</template>

<script>
import langselect from '@/components/langselect'
import storage from '@/utils/storage'
import { setToken, removeToken } from '@/utils/auth'
import axios from 'axios'
import qs from 'qs'

export default {
  name: 'login',
  components: {
    langselect
  },
  data() {
    return {
      success: false,
      lang: this.$store.state.app.language,
      ruleForm: {
        // username: storage.get('loginUser') || 'admin',
        username: storage.get('loginUser'),
        password: ''
      },
      rules: {
        username: [
          {
            required: true,
            message: "用户名为空！",
            trigger: 'change'
          }
        ],
        password: [
          {
            required: true,
            message: "密码为空！",
            trigger: 'change'
          }
        ]
      },
      remember: true,
      loading: false,
      switchLeft: false,
      switchRight: false,
      notforget: true,
      forgetForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      forgetRules:{
        username:[
          {
            required: true,
            message: "用户名为空！",
            trigger: 'change'
          }
        ],
        password:[
          {
            required: true,
            message: "密码为空！",
            trigger: 'change'
          }
        ],
        confirmPassword:[
          {
            required: true,
            message: "密码为空！",
            trigger: 'change'
          }
        ]
      }
    }
  },
  computed: {
    translateLeft() {
      return {
        'translate-left': true,
        'login-col': true,
        'switch-left': this.switchLeft
      }
    },
    translateRight() {
      return {
        'translate-right': true,
        'login-col': true,
        'switch-right': this.switchLeft
      }
    }
  },
  methods: {
    wrapSwitch(state) {
      this.switchLeft = !this.switchLeft
      this.switchRight = !this.switchRight
      setTimeout(() => {
        this.notforget = state
        this.$refs['ruleForm'].resetFields()
        // this.$refs['forgetRuleForm'].resetFields()
      }, 300)
    },
    handleLogin(formName) {
      this.loading = true
      var thiz = this;
      this.$refs[formName].validate(async valid => {
        if (valid) {
          try {
            let { username, password } = this.ruleForm
            // 点了登陆以后，记住我
            this.remember
              ? storage.set('loginUser', username)
              : storage.remove('loginUser', username)

            //开始发送登陆请求
            var param = qs.stringify({username,password})
            axios.post('http://localhost:8080/loading/login',param).then(function (response) {
              thiz.loading = false
					    var data = response.data;
					    if(data.status == "200"){
                // thiz.success = true;
                console.log(data.data);
                thiz.$message.success("登录成功");
					      thiz.$router.push({ path: '/' });
					    }else{
					      thiz.$message.error(data.message);
					    }
            }).catch(function (error) {
              thiz.loading = false
              console.log(error);
              thiz.$message.error("系统异常,请与管理员联系!");
            });

            
            // async/await 作为一个关键字放到函数前面
            // 表示函数是一个 异步/同步 函数
            const response = await this.$store.dispatch('loginbyUser', {
              username: username.trim(),
              password: password
            })
            console.log(response);
          } catch (error) {
            throw new Error(error)
          }
        } else {
          this.loading = false
          this.$message.error(this.$t('login.validfaild'))
        }
      })
    },
    forgetHandle(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('success submit!!');
          let { username, password, confirmPassword } = this.forgetForm;
          if(password != confirmPassword){
            this.$message.error("确认密码不一致！");
            return false;
          }
          var param = qs.stringify({username,password})
            axios.post('http://localhost:8080/loading/signup',param).then(function (response) {
					    var data = response.data;
					    if(data.status == "200"){
                return true;
					    }else{
					      this.$message.error(data.message);
					    }
            }).catch(function (error) {
              console.log(error);
              this.$message.error("系统异常,请与管理员联系!");
            });
          this.$message.success(this.$t('login.pwdChanged'));
          this.wrapSwitch(true);
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style lang="stylus">
.forget-form,
.login-form
  .el-form-item__content
    line-height 40px
  .el-input__inner
    padding-top: 2px;
    height 40px
    line-height 40px

.btn button
    width: 100%
    padding 12px 20px
</style>

<style lang="stylus" scoped>
@import "../../assets/styl/variables.styl"

.login-col
  height 100%

.login-page
  display flex
  justify-content center
  align-items center
  position absolute
  height 100%
  width 100%
  .lang
    position absolute
    right 59px
    top 24px
  .svg-github
    position absolute
    right 29px
    top 25px
  .translate-left
  .translate-right
    will-change auto
    transform translateX(0px)
    transition transform .6s ease-in-out
  .switch-left
    transform translateX(525px)
  .switch-right
    transform translateX(-375px)
  
.login-wrap
  overflow hidden
  width 900px
  height 400px
  background white
  border-radius 4px
  transform translateY(-10px)
  box-shadow 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04)

  .logo
    padding-top 26px
    text-align center

  .title
    font-weight bold
    color main-color
    padding-top 8px
    font-size 22px

    a
      cursor cell
    a:before
      content '['
      opacity 0
      margin-right 10px
      transform translateX(-10px)
      transition transform .2s, opacity .2s

    a:after
      content ']'
      opacity 0
      margin-left 10px
      transform translateX(10px)
      transition transform .2s, opacity .2s

    a:hover:before
    a:hover:after
      opacity 1
      transform translateX(0)

    .subtitle
      color sub-color

  .forgetwrap-title
    padding-top 30px
    padding-left 15px

  .forget-form
    padding 60px 30px 30px
    padding-bottom 0px

  .login-form
    padding 30px
    padding-bottom 0

  .login-footer
    padding 0 30px 
    .forgetpwd
      text-align right 
      span
        cursor pointer
        font-size 14px
        font-weight 500
        color #606266

  .wallpaper
    width 100%
    height 100%
    background url('../../assets/images/loginwallpaper2.jpg') -130px 0px
    background-size cover
    position relative
</style>
