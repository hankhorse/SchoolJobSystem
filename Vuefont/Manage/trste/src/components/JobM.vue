<template>
  <div>
    <h2 style="margin-top:5px">
      <i class="el-icon-s-cooperation"></i>
      工作管理
      </h2>
  <el-table
  v-loading="loading"
    stripe
    style="width: 100%"  :data="JobList"
    >
    <el-table-column
      label="工作编号"
      width="180"
      prop="jobId">
    </el-table-column>
    <el-table-column
      prop="jobName"
      label="职位名称"
      width="180">
      <template slot-scope="scope" >
        <span v-if="scope.row.jobId!=changeJob">{{scope.row.jobName}}</span>
      <el-input v-model="scope.row.jobName" v-if="scope.row.jobId==changeJob"></el-input>
      </template>
    </el-table-column>
    <el-table-column
    prop="jobSalary"
      label="职位薪资"
      width="180">
      <template slot-scope="scope" >
        <span v-if="scope.row.jobId!=changeJob">{{scope.row.jobSalary}}</span>
      <el-input v-model="scope.row.jobSalary" v-if="scope.row.jobId==changeJob"></el-input>
      </template>
    </el-table-column>
    <el-table-column
    prop="companyName"
      label="公司名称">
    </el-table-column>
<!--    <el-table-column-->
<!--    prop="companyAddress"-->
<!--      label="公司地址">-->
<!--    </el-table-column>-->
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
    <el-table-column label="操作">
      <template slot-scope="scope">
        <!-- {{scope.row}} -->
        <el-button icon="el-icon-edit" type="success" round @click="goToUpdate(scope.row.jobId)" v-if="scope.row.jobId !=changeJob">修改</el-button>
        <el-button icon="el-icon-delete" type="warning" round @click="deleteJob(scope.row.jobId)" v-if="scope.row.jobId !=changeJob">删除</el-button>

        <el-button  type="success" round @click="onUpdated(scope.row)" v-if="scope.row.jobId ==changeJob">保存</el-button>
        <el-button type="warning" round @click="change" v-if="scope.row.jobId ==changeJob">取消 </el-button>
      </template>
    </el-table-column>
  </el-table>

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
        var api='http://localhost:8082/job/getAll'
        Axios.get(api).then(res=>{
            this.JobList = res.data.datas
            console.log(res);
            this.loading = false;
        })
        .catch(err=>console.log(err));
     },
      current(val){

        this.nowpage = val
        var api='http://localhost:8082/job/admin/'+this.nowpage+'/'+this.pagesize
        Axios.get(api).then(res=>{
          this.total = res.data.count;
          console.log(this.total);
          this.JobList = res.data.datas
          console.log(res);
          this.loading=false;
        })
            .catch(err=>console.log(err));
      },
     //-----------这个是删除-------------这个是删除-------------这个是删除-------------这个是删除---------
     deleteJob(val){
      var url='http://localhost:8082/job/admin/deleteByJobId/'+val
        Axios.delete(url).then(res=>{
          console.log(res);
        this.$message({
          message: res.data.message,
          type: 'success'
        });
        }).catch(err=>console.log(err));

        this.getData();
     },
     goToUpdate(val){
        this.changeJob=val
     },
     // -------------这个是修改------------------这个是修改--------------这个是修改--------------这个是修改-------
     onUpdated(val){
      console.log("--------scope.row-->");
        console.log(val)
        console.log(val.jobId);
        console.log(val.jobName);
        console.log(val.jobSalary);
        this.Job.jobId = val.jobId
        this.Job.jobName = val.jobName
        this.Job.jobSalary = val.jobSalary
        console.log(this.Job)
        var url='http://localhost:8082/job/admin'
      console.log("-----------Put请求发起-");
        
        Axios({
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      method: 'put',
      url,
      data: this.Job
       }).then(res=>{
        console.log(res);
        this.$message({
          message: res.data.message,
          type: 'success'
        });
       }).catch(err=>console.log(err));
       this.change()

       this.getData();
     
     },
     update(val){
        console.log(val);
     },
     change(){
      this.changeJob=''
     }
    },
    mounted(){
      this.current(1)
    },
    data(){
    return{
        JobList:[],
        loading: true,
        changeJob:'',
        Job:{
          jobName:'',
          jobId:'',
          jobSalary:''
        },
        total:'',
        nowpage:1,
        pagesize:3,
    }
}
}
</script>

<style>

</style>