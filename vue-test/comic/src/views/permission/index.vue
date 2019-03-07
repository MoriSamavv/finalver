<template>
  <div>
    <div v-if="firstpage">
      <el-button type="primary" @click="dialogVisible = true">新建漫画</el-button>
      <el-table
      :data="tableData"
      style="width: 100%; margin-top: 15px"
      empty-text="漫画列表为空"
      key='firstTable'>
        <el-table-column
          prop="title"
          label="标题"
          width="250">
        </el-table-column>
        <el-table-column
          prop="author"
          label="作者"
          width="220">
        </el-table-column>
        <el-table-column
          prop="latest"
          label="最新"
          width="180">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.row)">编辑章节</el-button>
            <el-button
              size="mini"
              type="success"
              @click="uploadImage2(scope.row,'first')">编辑引导图片</el-button>
            <el-button
              size="mini"
              type="success"
              @click="uploadImage2(scope.row,'second')">编辑封面</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>


      <el-dialog title="新建漫画" :visible.sync="dialogVisible" width="30%">
        <table style="margin-left: 60px">
          <tr>
            <td>
              <span>名称</span>
            </td>
            <td>
              <el-input v-model="name1" type="text" style="width: 217px;"></el-input>
            </td>
          </tr>
          <tr>
            <td>
              <span>作者</span>
            </td>
            <td>
              <el-input v-model="name2" type="text" style="width: 217px;"></el-input>
            </td>
          </tr>
          <tr>
            <td>
              <span>描述</span>
            </td>
            <td>
              <el-input v-model="name3" type="textarea" style="width: 217px;"></el-input>
            </td>
          </tr>
        </table>
        <div style="margin-top:30px; width:100%; text-align:center;">
          <el-button @click="create">新建</el-button>
        </div>
      </el-dialog>

       <el-dialog title="上传图片" :visible.sync="uploadDialog2" width="30%">
        <el-upload
          class="upload-demo"
          ref="upload2"
          action="http://localhost:8080/comic/uploadCover"
          :on-preview="handlePreview"
          :data = "tempara2"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false"
          :limit=1>
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload2">上传到服务器</el-button>
        </el-upload>
      </el-dialog>

    </div>
    <div v-if="!firstpage">
      <div>
        <i class="el-icon-back" style="font-size: 16px;cursor:pointer; margin-bottom:10px" @click="goback">返回 当前位置</i>
      </div>
      <el-button type="primary" @click="dialogVisible2 = true">新建篇章</el-button>
      <el-table
      :data="tableData2"
      style="width: 100%; margin-top: 15px"
      empty-text="漫画列表为空"
      key='secondTable'>
        <el-table-column
          prop="section"
          label="篇章"
          width="250">
        </el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          width="220">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- <el-button
              size="mini"
              @click="dialogVisible2 = true">编辑信息</el-button> -->
            <el-button
              size="mini"
              type="success"
              @click="uploadImage1(scope.row)">上传图片</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete2(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog title="新建篇章" :visible.sync="dialogVisible2" width="30%">
        <table style="margin-left: 60px">
          <tr>
            <td>
              <span>篇章</span>
            </td>
            <td>
              <el-input v-model="section" type="number" style="width: 217px;"></el-input>
            </td>
          </tr>
          <tr>
            <td>
              <span>标题</span>
            </td>
            <td>
              <el-input v-model="title" type="text" style="width: 217px;"></el-input>
            </td>
          </tr>
        </table>
        <div style="margin-top:30px; width:100%; text-align:center;">
          <el-button @click="createChapter">新建</el-button>
        </div>
      </el-dialog>


      <el-dialog title="上传图片" :visible.sync="uploadDialog" width="30%">
        <el-upload
          class="upload-demo"
          ref="upload"
          action="http://localhost:8080/page/uploadPage"
          :on-preview="handlePreview"
          :data = "tempara"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false"
          :limit=5>
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb，一次最多上传5张</div>
        </el-upload>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import storage from '@/utils/storage'
import axios from 'axios'
import qs from 'qs'

export default {
  name: "permission",
  data() {
    return {
      firstpage: true,
      dialogVisible: false,
      uploadDialog: false,
      uploadDialog2: false,
      dialogVisible2: false,
      tableData: [],
      tableData2: [],
      name1: "",
      name2: "",
      name3: "",
      nowupload: "",
      fileList: [],
      nowCid: "",
      nowChapterId: "",
      section: "",
      title: "",
      tempara: {
        chapterId: ""
      },
      tempara2: {
        cid: "",
        type: ""
      },
      uploadType: ""
    };
  },
  mounted() {
  	this.updateTable();
  },
  methods: {
    handleEdit(row) {
      this.firstpage = !this.firstpage;
      this.nowCid = row.cid;
      console.log(row);
      this.updateTable2();
    },
    handleDelete(index, row) {
      console.log(row.cid);
      var thiz = this;
      var params = {
    		cid: row.cid
      }
      axios.get('http://localhost:8080/comic/delComic',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          if(data.data == true){
            thiz.$message.success("删除成功！");
            thiz.updateTable();
          }else{
            thiz.$message.error("网络波动了？删除失败");
          }
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    handleDelete2(row) {
      var thiz = this;
      var params = {
    		chapterId: row.chapterId
      }
      axios.get('http://localhost:8080/chapter/delChapter',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          if(data.data == true){
            thiz.$message.success("删除成功！");
            thiz.updateTable2();
          }else{
            thiz.$message.error("网络波动了？删除失败");
          }
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    uploadpic(){
      console.log("上传图片");
    },
    create(){
      var thiz = this;
      var params = {
    		title: this.name1,
  			author: this.name2,
  			description: this.name3
      }
      axios.get('http://localhost:8080/comic/createComic',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          if(data.data == true){
            thiz.$message.success("创建成功！");
            thiz.dialogVisible = false;
            thiz.updateTable();
          }else{
            thiz.$message.error("网络波动了？创建失败");
          }
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    createChapter(){
      var thiz = this;
      var params = {
        cid: this.nowCid,
        section: this.section,
        title: this.title
      }
      axios.get('http://localhost:8080/chapter/createChapter',{
  			params: params
  		}).then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          if(data.data == true){
            thiz.$message.success("创建成功！");
            thiz.dialogVisible2 = false;
            thiz.updateTable2();
          }else{
            thiz.$message.error("网络波动了？创建失败");
          }
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
      axios.get('http://localhost:8080/comic/getAllComic').then(function (response) {
		    var data = response.data;
		    if(data.status == "200"){
          thiz.tableData = data.data;
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },
    updateTable2(){
      var thiz = this;
      var params = {
    		cid: this.nowCid
      }
      axios.get('http://localhost:8080/chapter/getChapterList',{
        params: params
      }).then(function (response) {
        var data = response.data;
        console.log(data);
		    if(data.status == "200"){
          thiz.tableData2 = data.data;
		    }else{
		    	thiz.$message.error(data.message);
		    }
		  }).catch(function (error) {
		    console.log(error);
		    thiz.$message.error("系统异常!请与管理员联系!");
		  });
    },

    uploadImage1(row){
      this.nowChapterId = row.chapterId;
      console.log(this.nowChapterId);
      this.uploadDialog = true;
    },
    uploadImage2(row, type){
      this.nowCid = row.cid;
      this.uploadType = type;
      this.uploadDialog2 = true;
    },

    submitUpload() {
      this.tempara.chapterId = this.nowChapterId;
      console.log(this.tempara);
      this.$refs.upload.submit();
    },
    submitUpload2() {
      this.tempara2.cid = this.nowCid;
      this.tempara2.type = this.uploadType;
      console.log(this.tempara2);
      this.$refs.upload2.submit();
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    goback(){
      this.firstpage = true;
      this.updateTable();
    }
  }
};
</script>

<style lang="stylus" scoped>

</style>
