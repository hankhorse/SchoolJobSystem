<template>
  <div style="height:100%">

   <el-container style="height:100%">
  <el-header>
    
    <h2 class="head">
      <i class="el-icon-s-tools" style="position:absolute;left:30px;top:25px"></i>
      校园招聘系统</h2>
      <div>
      <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
    </div>
  </el-header>
  <el-container >
    
    <el-aside width="200px" style="height:900px">
        <AsideM v-on:pathName="show"/>
    </el-aside>

    <el-main >
      
      <component :is="componted"></component>
    </el-main>

  </el-container>
  </el-container>

  
  </div>
</template>

<script>
//import HelloWorld from './components/HelloWorld.vue'
import Student from './components/Student.vue';
import axios from 'axios';
import AsideM from './components/AsideM.vue';
import CompanyM from './components/CompanyM.vue';
import UserM from './components/UserM.vue';
import JobM from './components/JobM.vue';
import ImageUpload from './components/ImageUpload.vue';
import Witness from "@/components/Witness";
export default {
  name:'App',
  data(){
    return{
      componted:"UserM"
  }
  },
  components: {
    Student,AsideM,CompanyM,UserM,JobM,ImageUpload,Witness
  },
  methods:{
    show(val){
        this.componted = val;
    }
  }
}
</script>


<style>


  .el-header {
    background-color: #409EFF;
    color: #333;
    text-align: center;
   
  }
  
  .el-aside {
    background-color: #545c64;
    color: #333;
    text-align: center;
   
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    /* text-align: center; */
    line-height: 50px;
  }
  
  /* body > .el-container {
    margin-bottom: 40px;
  } */
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
  .head{
    margin-top: 10px;
    color: #202b2e;
  }
</style>
