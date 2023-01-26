<template>
  <div>
    <h2 style="margin-top:5px">
      <i class="el-icon-s-custom"></i>
      用户管理
      <el-button class="but" type="primary" icon="el-icon-edit" circle @click="dialogVisible = true">添加新用户</el-button>
    </h2>
     <el-table
    :data="UserData"
    stripe
    style="width: 100%"
    v-loading="loading"
    :row-class-name="tableRowClassName">
    <el-table-column
      prop="userId"
      label="用户编号"
      width="130"
      >
    </el-table-column>
    <el-table-column
      prop="userName"
      label="用户姓名"
      width="150">
      <template slot-scope="scope" >
        <span v-if="scope.row.userId!=changeUser" >{{scope.row.userName}}</span>
      <el-input v-model="scope.row.userName" v-if="scope.row.userId==changeUser"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      prop="userPassword"
      label="用户密码"
      width="150">
      <template slot-scope="scope" >
        <span v-if="scope.row.userId!=changeUser">{{scope.row.userPassword}}</span>
      <el-input v-model="scope.row.userPassword" v-if="scope.row.userId==changeUser"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      prop="userPhone"
      label="用户电话"
      >
      <template slot-scope="scope" >
        <span v-if="scope.row.userId!=changeUser">{{scope.row.userPhone}}</span>
      <el-input v-model="scope.row.userPhone" v-if="scope.row.userId==changeUser"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      prop="userEmail"
      label="用户邮箱"  >
      <template slot-scope="scope" >
        <span v-if="scope.row.userId!=changeUser">{{scope.row.userEmail}}</span>
      <el-input v-model="scope.row.userEmail" v-if="scope.row.userId==changeUser"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      prop="userImg"
      label="用户头像"
      width="150"
      >
      <template slot-scope="scope">
        <el-image
        style="width: 100px; height: 100px"
      :src="scope.row.userImg"
      ></el-image>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <!-- {{scope.row}} -->
        <el-button icon="el-icon-edit" type="success" round @click="goToUpdate(scope.row.userId)" v-if="scope.row.userId !=changeUser">修改</el-button>
        <el-tooltip class="item" effect="dark" content="你别删除阿" placement="top-start">
        <el-button icon="el-icon-delete" type="warning" round @click="deleteUser(scope.row.userId)" v-if="scope.row.userId !=changeUser">删除</el-button>
      </el-tooltip>
        <el-button type="success" round @click="onUpdated(scope.row)" v-if="scope.row.userId ==changeUser">保存</el-button>
        <el-button  type="warning" round @click="changeU" v-if="scope.row.userId ==changeUser">取消 </el-button>
      </template>
    </el-table-column>
   
  </el-table>
  
  <!-- --------娣诲姞鐢ㄦ埛寮圭獥--------------娣诲姞鐢ㄦ埛寮圭獥-----------娣诲姞鐢ㄦ埛寮圭獥-----------娣诲姞鐢ㄦ埛寮圭獥-------- -->
  <el-dialog
  title="添加用户"
  :visible.sync="dialogVisible"
  width="50%"
  
  :before-close="handleClose">
  <!-- 琛ㄥ崟椤甸潰 -->
  <el-form ref="UserList" :model="UserList" label-width="100px" :rules="rules">
    <el-form-item label="用户姓名:" prop="userName">
      <el-input v-model="UserList.userName"></el-input>
    </el-form-item>
    <el-form-item label="用户密码:" prop="userPassword">
      <el-input v-model="UserList.userPassword"></el-input>
    </el-form-item>
    <el-form-item label="用户电话:" prop="userPhone">
      <el-input v-model="UserList.userPhone"></el-input>
    </el-form-item>
    <el-form-item label="用户邮箱:" prop="userEmail">
      <el-input v-model="UserList.userEmail"></el-input>
    </el-form-item>
    <el-form-item label="用户头像:">
      <el-upload
    class="avatar-uploader"
    action="http://localhost:8082/upload"
   name="a"
    :show-file-list="true"
    :on-success="handleAvatarSuccess"
   :before-upload="onBeforeUpload">
   <img v-if="imageUrl" :src="imageUrl" class="avatar">
   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
  <el-input v-model="UserList.userImg" disabled></el-input>

    </el-form-item>
    
  
  </el-form>

  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取消</el-button>
    <el-button type="primary" @click="addUser()">提交</el-button>
  </span>
</el-dialog>

<el-pagination
 class="fenye"
  background
  layout="prev, pager, next"
  page-size="3"
  @current-change="current"
  :total="this.total">
</el-pagination>
</div>
</template>

<script>
import Axios from 'axios';
export default {
    methods: {
      
     getData(){
        var api='http://localhost:8082/user/getAllUser'
        Axios.get(api).then(res=>{
            this.UserData = res.data.datas
            console.log(res);
            this.loading=false;
        })
        .catch(err=>console.log(err));
        
     },
     //分页
     current(val){
      
      this.nowpage = val
      var api='http://localhost:8082/user/admin/'+this.nowpage+'/'+this.pagesize
        Axios.get(api).then(res=>{
            this.total = res.data.count;
            console.log(this.total);
            this.UserData = res.data.datas
            console.log(res);
            this.loading=false;
        })
        .catch(err=>console.log(err));
      },
     tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      },
      handleClose(done) {
        this.$confirm('确定要退出嘛')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      goToUpdate(val){
        this.changeUser=val;
      },
      
      changeU(){
        this.changeUser=''
      },
      //  -----------更新-----------杩欎釜鏄�鏇存柊鏂规硶----------杩欎釜鏄�鏇存柊鏂规硶----------杩欎釜鏄�鏇存柊鏂规硶------
      onUpdated(val){
      console.log(val);
      var url='http://localhost:8082/user/admin'
      console.log("-----------Put----");
        console.log(val);
        Axios({
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      method: 'put',
      url,
      data: val
       }).then(res=>{
        console.log(res);
        this.$message({
          message: '修改成功',
          type: 'success'
        });
       }).catch(err=>console.log(err));
       this.changeUser='';
       this.show=!this.show;
     },
     deleteUser(val){

        var url='http://localhost:8082/user/admin/deleteUser/'+val
        Axios.delete(url).then(res=>{
          console.log(res);
        this.$message({
          message: res.data.message,
          type: 'success'
        });
        }).catch(err=>console.log(err));

      },
      //添加用户
      addUser(){
        var url='http://localhost:8082/user/admin/register'
        console.log("----------");
        console.log(this.UserList);
        Axios({
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      method: 'post',
      url,
      data: this.UserList
       }).then(res=>{
        console.log(res);
        this.$message({
          message: '添加成功',
          type: 'success'
        });
       })
        
        this.dialogVisible=false;
      },
      handleAvatarSuccess(res, file) {
        //this.imageUrl = URL.createObjectURL(file.raw);
      this.imageUrl=res;
     this.UserList.userImg=res;
        console.log(file);
        console.log(res);
      },
      onBeforeUpload(file){
		 	console.log(file)
		},
    },
    mounted(){
        //this.getData();
        this.current(1)
    },
   
    data() {
      return {
        tableData: [],
        UserData:[],  //璇诲彇鏁版嵁搴揢ser
        UserList:{
          userName:'',
          userPassword:'',
          userPhone:'',
          userEmail:'',
          userImg:''
        }, //鐢ㄤ簬娣诲姞鏂扮敤鎴�
        dialogVisible: false,
        loading: true,
        show:true,
        changeUser:'',
        imageUrl: '',
        total:'',
        nowpage:1,
        pagesize:3,
        rules:{
          userName:[{
            required: true, message: '请输入名称', trigger: 'blur'
          }],
          userPassword:[{
            required: true, message: '请输入用户密码', trigger: 'blur'
          }],
          userPhone:[{
            required: true, message: '请输入用户电话', trigger: 'blur'
          }],
          userEmail:[{
            required: true, message: '请输入用户邮箱', trigger: 'blur'
          }],
        }
      }
    }
  }

</script>

<style>
.el-table-column{
    text-align: center;
}
.el-table .warning-row {
    background: rgb(215, 226, 234);
  }

  .el-table .success-row {
    background: #f2f2f2;
  }
  .but{
    position: absolute;
    right: 20px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .fenye{
    margin-top: 10px;
    margin-left: 40%;
  }
</style>