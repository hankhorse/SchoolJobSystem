<template>
  <div>
    <h2 style="margin-top:5px">
      <i class="el-icon-s-custom"></i>
      公司管理
      <el-button style="position:absolute;right:20px" type="primary" icon="el-icon-edit" circle @click="dialogVisible = true" v-show="show">添加新公司</el-button>
   </h2>
    <el-table
    :data="companyData"
    style="width: 100%"
    v-loading = "loading"
    v-show="show">
    <el-table-column 
      prop="companyId"
      label="公司编号"
      width="150">
      
    </el-table-column>
    <el-table-column
      prop="companyName"
      label="公司名字"
      width="150">
    </el-table-column>
    <el-table-column
      prop="companyAddress"
      label="公司地址"
      width="150">
    </el-table-column>
    <el-table-column
      prop="companyNumber"
      label="公司规模"
      width="150">
    </el-table-column>
    <el-table-column
      prop="companyPhone"
      label="公司联系方式">
    </el-table-column>
    <el-table-column
      prop="companyImg"
      label="公司图片">
    <template slot-scope="scope">
        <el-image
        style="width: 100px; height: 100px"
      :src="scope.row.companyImg"
      ></el-image>
      </template>
    </el-table-column>
    <el-table-column label="操作" >
      <template slot-scope="scope">
        <!-- {{scope.row}} -->
      <el-button icon="el-icon-edit" type="success" round @click="goToUpdate(scope.row.companyId)">修改</el-button>
      <el-button icon="el-icon-delete" type="warning" round @click="deleteCom(scope.row.companyId)">删除</el-button>
      </template>
      
    </el-table-column>
  </el-table>
  
<!-- 以下是修改表格----------------以下是修改表格------------以下是修改表格------------以下是修改表格--------------- -->

<el-table
    :data="companyData"
    style="width: 100%"
    v-show="!show">
    <el-table-column 
      prop="companyId"
      label="公司编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="companyName"
      label="公司名字"
      width="180">
      <template slot-scope="scope" >
        <span v-if="scope.row.companyId!=changeCom">{{scope.row.companyName}}</span>
      <el-input v-model="scope.row.companyName" v-if="scope.row.companyId==changeCom"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      prop="companyAddress"
      label="公司地址">
      <template slot-scope="scope" >
        <span v-if="scope.row.companyId!=changeCom">{{scope.row.companyAddress}}</span>
      <el-input v-model="scope.row.companyAddress" v-if="scope.row.companyId==changeCom"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      prop="companyNumber"
      label="公司规模">
      <template slot-scope="scope" >
        <span v-if="scope.row.companyId!=changeCom">{{scope.row.companyNumber}}</span>
      <el-input v-model="scope.row.companyNumber" v-if="scope.row.companyId==changeCom"></el-input>
      </template>
    </el-table-column>
    <el-table-column
      prop="companyPhone"
      label="公司联系方式">
      <template slot-scope="scope" >
        <span v-if="scope.row.companyId!=changeCom">{{scope.row.companyPhone}}</span>
      <el-input v-model="scope.row.companyPhone" v-if="scope.row.companyId==changeCom"></el-input>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <!-- {{scope.row}} -->
        <el-button type="success" round @click="update(scope.row)">保存</el-button>
      <el-button type="warning" round @click="changeShow">取消</el-button>
      </template>
      
    </el-table-column>
  </el-table>

  <!-- ------------对话框部分------------------对话框部分-------------对话框部分------------------- -->

  <el-dialog
  title="添加公司"
  :visible.sync="dialogVisible"
  width="60%"
  :before-close="handleClose">
  <!-- 表单页面 -->
  <el-form ref="Company" :model="Company" label-width="90px" :rules="rules">
    <el-form-item label="公司姓名:" prop="companyName">
      <el-input v-model="Company.companyName"></el-input>
    </el-form-item>
    <el-form-item label="公司地址:" prop="companyAddress">
      <el-input v-model="Company.companyAddress"></el-input>
    </el-form-item>
    <el-form-item label="公司规模:" prop="companyNumber">
      <el-input v-model="Company.companyNumber"></el-input>
    </el-form-item>
    <el-form-item label="公司电话:" prop="companyPhone">
      <el-input v-model="Company.companyPhone"></el-input>
    </el-form-item>

    <el-form-item label="公司图片:">
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

      <el-input v-model="Company.companyImg" placeholder="请上传图片" disabled></el-input>
    </el-form-item>


    <el-form-item label="公司详情:" prop="companyComtext">
      <el-input type="textarea" autosize v-model="Company.companyContext"></el-input>
    </el-form-item>
  
  </el-form>

  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addCompany()">确 定</el-button>
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
      tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      },
      getData(){
        var api='http://localhost:8082/company/getAll'
        Axios.get(api).then(res=>{
            this.companyData = res.data.datas
            console.log(res);
        })
        .catch(err=>console.log(err));
     },
    //分页
     current(val){
      this.nowpage = val
      var api='http://localhost:8082/company/admin/page/'+this.nowpage+'/3'
        Axios.get(api).then(res=>{
            this.total = res.data.count;
            console.log(this.total);
            this.companyData = res.data.datas
            console.log(res);
            this.loading=false;
        })
        .catch(err=>console.log(err));
      },

     goToUpdate(val){
        this.changeCom=val;
        this.show=!this.show;
     },
     changeShow(){
      this.show=!this.show;
     },
    //  -----------这个是更新方法------------这个是更新方法----------这个是更新方法----------这个是更新方法------
     update(val){
      console.log(val);
      var url='http://localhost:8082/company/admin'
      console.log("-----------Put请求发起-");
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
          message: res.data.message,
          type: 'success'
        });
       }).catch(err=>console.log(err));
       this.changeCom='';
       this.show=!this.show;
     },
     //---------这个是删除---------------这个是删除-------------这个是删除-------------这个是删除------
     deleteCom(val){

        var url='http://localhost:8082/company/admin/'+val
        Axios.delete(url).then(res=>{
          console.log(res);
        this.$message({
          message: res.data.message,
          type: 'success'
        });
        }).catch(err=>console.log(err));
     },
     handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      // ---------这个是添加------------------这个是添加------------这个是添加------------这个是添加--------
      addCompany(){
        var url='http://localhost:8082/company/admin/create'
        console.log("----------");
        console.log(this.Company);
        Axios({
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      method: 'post',
      url,
      data: this.Company
       }).then(res=>{
        console.log(res);
        this.$message({
          message: res.data.message,
          type: 'success'
        });
       })
        
        this.dialogVisible=false;
      },
      handleAvatarSuccess(res, file) {
        //this.imageUrl = URL.createObjectURL(file.raw);
      this.imageUrl=res;
     this.Company.companyImg=res;
        console.log(file);
        console.log(res);
      },
      onBeforeUpload(file){
		 	console.log(file)
		},
     
      
    
    },
    mounted(){
        
        this.current(1)
    },
    data(){
      return{
        companyData:[],
        dialogVisible: false,
        show:true,
        changeCom:'',
        Company:{
          companyName:'',
          companyAddress:'',
          companyNumber:'',
          companyPhone:'',
          companyContext:'',
          companyImg:'',
          total:'',
          nowpage:1,
          loading:true
        },
        imageUrl: '',
        rules:{
          companyName:[{
            required: true, message: '请输入名称', trigger: 'blur'
          }],
          companyAddress:[{
            required: true, message: '请输入地址', trigger: 'blur'
          }],
          companyNumber:[{
            required: true, message: '请输入规模', trigger: 'blur'
          }],
          companyPhone:[{
            required: true, message: '请输入电话', trigger: 'blur'
          }],
          companyContext:[{
            required: true, message: '请输入介绍', trigger: 'blur'
          }],
        }
      }
    }
}
</script>

<style>
.el-table .warning-row {
    background: rgb(239, 216, 172);
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>