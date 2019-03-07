<!-- 看漫画 -->
<template>
  <div class="lock-wrap">
    <el-carousel :height="carouselHeight" indicator-position="none" :autoplay="false" :loop="false">
      <el-carousel-item v-for="item in comicList" :key="item.pid">
          <img :src="item.pageurl" :height="carouselHeight" />
      </el-carousel-item>

    </el-carousel>
    <div class="lock-container">
      <div class="lock-container__box">
        <!-- <h1><span>{{$t('app.unlock')}} {{$t('login.edenPart1')}}</span><span class="subtitle">{{$t('login.edenPart2')}}</span></h1> -->
        <!-- <el-input v-model="pwd" placeholder="Enter Password"></el-input> -->
        <el-button class="btn" @click="unlock">返回</el-button>
      </div>
    </div>
  </div>
  
</template>

<script>
import storage from '@/utils/storage'
import axios from 'axios'
import qs from 'qs'

export default {
  name: "lock",
  data() {
    return {
      carouselHeight: "",
      pwd: "",
      token: this.$store.state.user.token,
      comicList: []
    };
  },
  mounted() {
  	this.loadComic();
  },
  created() {
    console.log("token:"+this.token);
    this.$message.success("按F11全屏浏览");
    this.carouselHeight = `${window.innerHeight * 0.9}px`;
  },
  methods: {
    unlock() {
      // if (this.pwd === "") {
      //   this.$message.error("Please Enter Password!");
      // } else {
        // this.$message.success(this.$t("lock.unlock"));
        this.$store.dispatch("setLockState", "unlock");
        this.$router.go(-1);
        // this.$router.push("/dashboard");
      // }
    },
    loadComic(){
      var thiz = this;
      var params = {
    		chapterId: this.token
      }
      axios.get('http://localhost:8080/page/readComic',{
        params: params
      }).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.comicList = data.data;
          console.log(thiz.comicList);
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    }
  }
};
</script>

<style lang="stylus" scoped>
.lock-wrap
  width 100%
  height 100%
  overflow hidden

.lock-container
  width 100%
  // height 50%
  display flex
  align-items center
  justify-content center
  &__box
    color #659c84
    h1
      text-align center
    .btn
      margin-top 20px
      width 100%
    .subtitle
      color #374b63
  
.el-carousel__item
  display flex
  align-items center
  justify-content center
  div
    font-size 35px
    color white
.el-carousel__item:nth-child(2n)
  background-color rgb(51,51,51)
.el-carousel__item:nth-child(2n+1)
  background-color rgb(51,51,51)
</style>
