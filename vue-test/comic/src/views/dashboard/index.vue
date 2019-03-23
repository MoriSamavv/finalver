<!-- 漫画中心 -->
<template>

  <div>
  <div v-if="isFirstPage">
    <div class="dashboard-wrap">
      <el-row :gutter="20">
        <el-col :span="8" v-for="item in outerCover" :key="item.cid">
          <div class="my box widget-card">
            <img class="pic" :src="item.outsideurl"/>
            <div class="downp">
              <a class="mya" @click="gointo(item.cid)">【{{item.title}}】{{item.author}}</a>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
  <div v-if="!isFirstPage">
    <div>
			<i class="el-icon-back" style="font-size: 16px;cursor:pointer;" @click="goback">返回</i>
		</div>
    <div class="profile-container">
      <el-card shadow="never" class = "detail padd">
          <div class="header-avatar">
            <img :src="innerCover.insideurl" />
          </div>
          <div class="header-content">
            <div class="title">
              {{innerCover.title}}
              <img src="../../assets/images/star_on.gif"  title="取消收藏" class="star" v-if="innerCover.star" @click="starfunc" />
              <img src="../../assets/images/star_off.gif" title="点击收藏" class="star" v-else @click="starfunc" />
            </div>
            <div class="sub">
              作者：{{innerCover.author}}
            </div>
            <div class="sub">
              {{innerCover.description}}
            </div>
          </div>
      </el-card>
      <el-card shadow="never" class="padd">
          <div slot="header">
            <span class="card-title">正篇章节</span>
          </div>
            <el-row :gutter="20">
              <el-col :span="6" v-for="item in chapterList" :key="item.chapterId">
                <div class="chapter">
                  <a @click="readComic(item.chapterId)">{{item.section}}  {{item.title}}</a>
                </div>
              </el-col>
            </el-row>
      </el-card>
      <el-card shadow="never" class="commentWrap padd">
          <div slot="header">
            <div class="commentTitle">
              <span>话题互动</span>
            </div>
            <el-input
              type="textarea" 
              :rows="4"
              :maxlength=150
              placeholder="请输入内容"
              v-model="textarea">
            </el-input>
            <div class="commentbtn">
              <el-button type="primary" @click="comment">发送</el-button>
            </div>
            <div class="clear"></div>
          </div>
          <el-col :span="24" v-for="item in commentList" :key="item.commentid">
            <el-card shadow="never" :body-style="{ margin: '20px' }">
              <span class="commentSub commentNickname">{{item.nickname}}</span>
              <span class="commentSub">{{item.content}}</span>
              <span class="commentSub commentTime">{{item.posttime}}</span>
            </el-card>
        
          </el-col>
      </el-card>
    </div>
  </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import TWEEN from "@tweenjs/tween.js";
import lineChart from "@/components/linechart";
import pieChart from "@/components/piechart";
import { randomIntegerInRange } from "@/utils/tools";
import axios from 'axios'
import qs from 'qs'

const chartData = {
  newGamer: {
    PS4PlayerData: [4352, 3120, 2561, 3234, 4305, 3170, 2165],
    NSPlayerData: [2120, 3282, 5291, 2554, 2162, 3140, 3145]
  },
  fpsGame: [
    { value: 320, name: "Battlefield" },
    { value: 240, name: "Call Of Duty" },
    { value: 149, name: "Counter Strike" },
    { value: 400, name: "Titanfall" },
    { value: 524, name: "Splatoon" }
  ],
  fpsLegend: [
    "Battlefield",
    "Call Of Duty",
    "Counter Strike",
    "Splatoon",
    "Titanfall"
  ]
};

const tweenTransition = (newValue, oldValue, onUpdateFn) => {
  function animate() {
    if (TWEEN.update()) {
      requestAnimationFrame(animate);
    }
  }
  new TWEEN.Tween({ number: oldValue })
    .to({ number: newValue }, 1000)
    .onUpdate(onUpdateFn)
    .start();
  animate();
};

export default {
  name: "dashboard",
  components: {
    lineChart,
    pieChart
  },
  data() {
    return {
      star: false,
      temurl: "/incover2.jpg",
      avatarUrl: "/incover1.jpg",
      isFirstPage: true,
      newUsers: 18273,
      tweenedNewUsers: 18273,
      pageVisits: 4824,
      tweenedPageVisits: 4824,
      download: 7921,
      tweenDownload: 7921,
      switchPlayer: 69281,
      tweenSwitchPlayer: 69281,
      randomfn: null,
      lineChartData: chartData.newGamer,
      pieChartData: chartData.fpsGame,
      pieLegendData: chartData.fpsLegend,
      newTasks: "",
      todolist: [],
      outerCover: [],
      username: this.$store.state.user.name,
      innerCover: {},
      chapterList: [],
      nowCid: "",
      topicList: [],
      textarea: "",
      commentList: []
    };
  },
  watch: {
    newUsers(newValue, oldValue) {
      tweenTransition(newValue, oldValue, tween => {
        this.tweenedNewUsers = tween.number.toFixed(0);
      });
    },
    pageVisits(newValue, oldValue) {
      tweenTransition(newValue, oldValue, tween => {
        this.tweenedPageVisits = tween.number.toFixed(0);
      });
    },
    download(newValue, oldValue) {
      tweenTransition(newValue, oldValue, tween => {
        this.tweenDownload = tween.number.toFixed(0);
      });
    },
    switchPlayer(newValue, oldValue) {
      tweenTransition(newValue, oldValue, tween => {
        this.tweenSwitchPlayer = tween.number.toFixed(0);
      });
    }
  },
  mounted() {
    this.firstLoginnotify();
    this.randomNum();
    this.loadOuterCover();
  },
  beforeDestroy() {
    clearInterval(this.randomfn);
  },
  methods: {
    loadComment(){
      var thiz = this;
      var params = {
        cid: this.nowCid
      }
      axios.get('http://localhost:8080/comment/getCommentList',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.commentList = data.data;
          console.log(thiz.commentList);
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
      });
    },
    comment(){
      if(this.textarea == ""){
        this.$message.error("评论不能为空！");
        return
      }
      var thiz = this;
      var params = {
        cid: this.nowCid,
        username: this.username,
        content: this.textarea
      }
      axios.get('http://localhost:8080/comment/createComment',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.$message.success("评论成功！");
          thiz.textarea = "";
          thiz.loadComment();
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
      });
    },
    firstLoginnotify() {
      const USERNAME = Cookies.get("username") || "Sakuya";
      if (this.$store.state.app.firstLogin === "yep") {
        this.$notify.info({
          title: `Welcome, ${USERNAME}`,
          message: "First login system, You can view eden system documentation",
          duration: 6000,
          offset: 60
        });
        this.$store.dispatch("setFirstLogin");
      }
    },
    randomNum() {
      this.randomfn = setInterval(() => {
        this.newUsers = randomIntegerInRange(100, 10000);
        this.pageVisits = randomIntegerInRange(100, 100000);
        this.download = randomIntegerInRange(100, 1000);
        this.switchPlayer = randomIntegerInRange(1000, 10000);
      }, 3000);
    },
    addTasks() {
      this.todolist.push({
        state: false,
        task: this.newTasks
      });
      this.newTasks = "";
    },
    gointo(cid) {
      this.isFirstPage = false;
      this.nowCid = cid;
      var thiz = this;
      var params = {
        cid: cid,
        username: this.username
      }
      axios.get('http://localhost:8080/comic/getInnerCover',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.innerCover = data.data;
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
      });
      this.loadChapterList(cid);
      this.loadComment();
    },
    goback(){
      this.isFirstPage = true;
    },
    starfunc(){
      this.innerCover.star = !this.innerCover.star;
      var thiz = this;
      var params = {
        cid: this.nowCid,
        like: this.innerCover.star,
        username: this.username
      }
      // console.log(username)
      axios.get('http://localhost:8080/star/setLike',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          console.log(data.data);
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
      });
      if(this.innerCover.star){
        this.$message.success("收藏成功！");
      }else{
        this.$message.success("取消收藏成功！");
      }
    },
    loadOuterCover(){
      var thiz = this;
      axios.get('http://localhost:8080/comic/getOuterCover').then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.outerCover = data.data;
          console.log(thiz.outerCover);
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    loadChapterList(cid){
      var thiz = this;
      var params = {
        cid: cid
      }
      axios.get('http://localhost:8080/chapter/getChapterVOList',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.chapterList = data.data;
          console.log(thiz.chapterList);
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    readComic(chapterId){
      console.log(chapterId);
      this.setChapter(chapterId);
      this.$store.state.user.token = chapterId;
      this.$store.dispatch("setLockState", "lock");
      this.$router.push({
        path: "/lock"
      });
    },
    setChapter(chapterId){
      var thiz = this;
      var params = {
        cid: this.nowCid,
        chapterId: chapterId,
        username: this.username
      }
      // console.log(username)
      axios.get('http://localhost:8080/star/setChapter',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          console.log(data.data);
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
@import '../../assets/styl/variables.styl'

.padd
  margin-bottom 10px

.star:hover
  cursor pointer
  
.commentTitle
  margin-bottom 5px

.commentbtn
  margin-top 5px
  float right
.clear
  clear both

.commentWrap
  padding-bottom 15px

.chapter
  height 40px
  text-align center
  a:hover
    color #e6511e

.commentSub
  display block
.commentNickname
  color #ec5245
.commentTime
  float right
  color #939393

.detail
  margin-top 15px
  .imgwrap 
    height 200px
    width  160px
    clear both
    img 
      height 100%
      width  100%
      clear both
  p
    display block
    float left 
    clear both


.downp{
  margin-top: 15px;
  margin-left: 15px;
}

.mya {
  color: white
}
.mya:hover {
  color: red
}

.downp{
  height: 100%;
  background-color: #556d84;
}

.my{
  margin-top: 30px;
  background-color: #556d84;
  height: 200px
}

.pic{
  width: 100%
}

.pic:hover {
  opacity: 0.6
}

.flex-center
  display flex
  justify-content center
  align-items center

box-style(bg)
  width 100%
  height 130px
  background white
  display flex
  margin-top 15px
  .leftPart
    background bg
    width 130px
    @extend .flex-center
  .rightPart
    flex 1
    color #99a9c0
    @extend .flex-center
    flex-direction column
    .number
      font-size 30px
      padding-bottom 6px
    .font-center
      text-align center

.dashboard-row
  width 100%
  height 400px
  margin-top 15px
  background white

.dashboard-wrap
  margin-left: 10px
  .box1
    box-style(#41b883)
  .box2
    box-style(#9ab7e0)
  .box3
    box-style(#f7c94d)
  .box4
    box-style(#e45f5f)

  .line-chart
    @extend .dashboard-row
    padding 15px
    box-sizing border-box

  .todo-wrap
    @extend .dashboard-row
    .todo-header
      span
        font-weight bold
        color #6fb998
      .enterTodo
        float right
        width 140px
        position relative
        top -8px
        right -11px
    .todo-list
      div:not(:first-child)
        margin-top 15px
  
  .system-report
    @extend .dashboard-row
    height 520px
    .report-header
      span
        font-weight bold
        color #6fb998
    .report-content
      .el-alert:not(:first-child)
        margin-top 15px
      .progress-wrap
        margin-top 20px
        text-align center
        padding 20px
        .el-progress:not(:first-child)
          margin-left 20px
  .pie-wrap
    @extend .dashboard-row
    height 520px
    .pie-header
      span
        font-weight bold
        color #6fb998



.profile-container
  .el-card
    font-size 14px

  @extend .edenfont-content
  width 100%
  .header
    padding 15px
    margin -15px
    box-sizing unset
    background white
    height 130px
    &-left
      height 100%
      display flex
      box-sizing border-box
      align-items center
    &-avatar
      margin-left 20px
      display inline-block
      vertical-align middle
      width 100px
      img 
        height 100%
        width  100%
    &-content
      flex 1
      margin-left 20px
      height 85px
      display inline-block
      vertical-align middle
      .title
        font-size 18px
        font-weight bold
        color title-color
        span
          cursor pointer
          color main-color
          &:hover
            text-decoration-line underline
      .sub
        margin-top 20px
        color title-sub
        font-size 14px
      .sub:last-child
        margin-top 10px

  .profile-content
    margin-top 30px
    &__left
      margin-bottom 20px
      .el-alert
        margin-bottom 20px
      .feed
        height 63px
        border-bottom 1px solid #e0e0e0
        .line
          position relative
        .time
          color #a7a7a7
          font-size 14px
          margin-top 10px
        .details
          position absolute
          right 0
          top 0
      .feed:not(:first-child)
        margin-top 25px
      .feed:last-child
        border-bottom 0px

    &__right
      .el-card:not(:first-child)
        margin-top 20px
    .card-title
      color #585858   
</style>
