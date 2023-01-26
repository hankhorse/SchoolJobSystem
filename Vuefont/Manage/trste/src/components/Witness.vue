<template>
  <div>
    <el-table
        :data="noticeData"
        stripe
        v-loading="false"
        style="width: 100%">
      <el-table-column
          prop="noticeId"
          label="通知编号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="noticeMsg"
          label="通知内容"
          width="400">
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="发起时间">
      </el-table-column>
      <el-table-column
          label="查看凭证">
        <template slot-scope="scope">
        <el-popover
            placement="right"
            width="400"
            trigger="click">
          <el-image
              style="width: 400px; height: 400px"
              :src="img"
              ></el-image>

          <el-button  icon="el-icon-search" circle slot="reference" @click="getpoof(scope.row.noticeId)">点击查看</el-button>
        </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- {{scope.row}} -->
<!--                                                          //传入 noticeId -->
          <el-button icon="el-icon-s-check"  type="success" round @click="pass(scope.row.noticeId)" v-if="scope.row.noticeResult==-1">审核</el-button>
          <el-button icon="el-icon-s-release" type="warning" round @click="refuse(scope.row.noticeId)" v-if="scope.row.noticeResult==-1">拒绝</el-button>
          <el-result icon="success" title="通过审核" v-if="scope.row.noticeResult==0" style="padding: 0px"></el-result>
            <el-result icon="error" title="未通过审核" v-if="scope.row.noticeResult==1" style="padding: 0px"></el-result>
        </template>
      </el-table-column>
    </el-table>
<!--&lt;!&ndash;分页&ndash;&gt;-->
<!--    <el-pagination-->
<!--        class="fenye"-->
<!--        background-->
<!--        layout="prev, pager, next"-->
<!--        page-size="3"-->
<!--        @current-change="current"-->
<!--        :total="this.total">-->
<!--    </el-pagination>-->
  </div>
</template>

<script>
import Axios from "axios";

export default {
  name: "Witness",
  data(){
    return{
    noticeData:[

    ],
      company:{},
        total:'',
        nowpage:1,
        pagesize:3,
        loading:true,
        img:''
    }

  },
  mounted(){
    //this.current(1);
    this.getData();
  },
  methods:{
    getData(){
      var api='http://localhost:8082/admin/getAllNotice'
      Axios.get(api).then(res=>{
        this.noticeData = res.data.data
        console.log(res);
        this.loading = false;
      })
          .catch(err=>console.log(err));
    },
    //查看凭证
    getpoof(val){
      this.img=''
      var api='http://localhost:8082/admin/getCompanyByNotice/'+val
      Axios.get(api).then(res=>{
        console.log(res)
        this.company = res.data.data
        this.img=res.data.data.proofImg
      })
    },

    // //分页
    // current(val){
    //   this.nowpage = val
    //   var api='http://localhost:8082/notice/'+this.nowpage+'/'+this.pagesize
    //   Axios.get(api).then(res=>{
    //         this.total = res.data.count;
    //         this.noticeData = res.data.datas
    //         console.log(res);
    //         //加载关闭
    //         this.loading=false;
    // })
    // },
    //通过
    pass(val){
      var url='http://localhost:8082/admin/passAddCompany/'+val
      console.log("-----------Put请求发起-");
      console.log(val)
      Axios({
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: 'put',
        url,
      }).then(res=>{
        console.log(res);
        this.$message({
          message: '通过成功',
          type: 'success'
        });
      }).catch(err=>console.log(err));
    },
    show(val){

    },
    //拒绝
    refuse(val){

      var url='http://localhost:8082/admin/refuseAddCompany/'+val
      Axios.put(url).then(res=>{
        console.log(res);
        this.$message({
          message: '拒绝成功',
          type: 'success'
        });
      }).catch(err=>console.log(err));
    },

  }
}
</script>

<style scoped>

</style>