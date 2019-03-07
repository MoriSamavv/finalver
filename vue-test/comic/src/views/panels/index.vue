<template>
  <div>
    <!-- <el-button class="btn" @click="lockhandle">查看</el-button> -->
    <el-table
    :data="tableData"
    style="width: 100%"
    :show-header= "false"
    empty-text="您还没有收藏过任何漫画哦！">
      <el-table-column
        prop="title"
        width="250">
      </el-table-column>
      <el-table-column
        prop="latest"
        width="220">
      </el-table-column>
      <el-table-column
        prop="read">
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="lockhandle(scope.row)">继续阅读</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)">取消收藏</el-button>
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
  name: "panels",
  data() {
    return {
      tableData: []
    };
  },
  mounted() {
  	this.updateTable();
  },
  methods: {
    lockhandle(row) {
      this.$store.state.user.token = row.chapterId;
      this.$store.dispatch("setLockState", "lock");
      this.$router.push({
        path: "/lock"
      });
      // this.$message.success(this.$t("lock.locked"));
    },
    handleEdit(index, row) {
      console.log(index, row);
      console.log(index, row.name);
    },
    handleDelete(row) {
      console.log(row);
      var thiz = this;
      var params = {
        cid: row.cid,
        like: false,
        username: this.$store.state.user.name
      }
      axios.get('http://localhost:8080/star/setLike',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.$message.success("取消收藏成功！");
          thiz.updateTable();
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
      });
    },
    updateTable(){
      var thiz = this;
      var params = {
    		username: this.$store.state.user.name
      }
      console.log(this.$store.state.user);
      axios.get('http://localhost:8080/star/getStarList',{
        params: params
      }).then(function (response) {
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
    }
  }
};
</script>

<style lang="stylus" scoped>

</style>
