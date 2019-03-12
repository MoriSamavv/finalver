<template>
  <div>
    <el-table
      :data="tableData"
      style="width: 100%; margin-top: 15px"
      empty-text="审核列表为空"
      key='firstTable'>
        <el-table-column
          prop="comicTitle"
          label="漫画"
          width="220">
        </el-table-column>
        <el-table-column
          prop="section"
          label="章节"
          width="220">
        </el-table-column>
        <el-table-column
          prop="chapterTitle"
          label="标题"
          width="220">
        </el-table-column>
        <el-table-column
          prop="posttime"
          label="发布时间"
          width="220">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <div>
              <div v-if="scope.row.verify == 0">
                <el-button
                  size="mini"
                  @click="read(scope.row)">审核</el-button>
                <el-button
                  size="mini"
                  type="success"
                  @click="check(scope.row, 1)">审核通过</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  @click="check(scope.row, 2)">审核拒绝</el-button>
              </div>
              <div v-if="scope.row.verify == 1" style="color:GREEN">
                <span>通过审核</span>
              </div>
              <div v-if="scope.row.verify == 2" style="color:RED">
                <span>审核拒绝</span>
              </div>
            </div>
          </template>
        </el-table-column>
    </el-table>
  </div>
</template>

<script>

import storage from '@/utils/storage'
import axios from 'axios'
import qs from 'qs'

export default {
  name: "widget",
  data() {
    return {
      tableData: []
    }
  },
  mounted() {
  	this.updateTable();
  },
  methods: {
    updateTable(){
      var thiz = this;
      axios.get('http://localhost:8080/chapter/getChapterVerifyVOList').then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.tableData = data.data;
          console.log(thiz.tableData);
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    check(row, verify){
      var thiz = this;
      var params = {
        chapterId: row.chapterId,
        verify: verify
      }
      axios.get('http://localhost:8080/chapter/setChapterVerify',{
        params: params
      }).then(function (response) {
        var data = response.data;
		    if(data.status == "200"){
          thiz.updateTable();
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    read(row){
      this.$store.state.user.token = row.chapterId;
      this.$store.dispatch("setLockState", "lock");
      this.$router.push({
        path: "/lock"
      });
    }
  }
};
</script>

<style lang="stylus" scoped>

</style>
