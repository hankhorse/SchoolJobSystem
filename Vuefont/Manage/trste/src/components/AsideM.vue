<template>
  <div>
    <el-row class="tac">
    <el-col >
   
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      @select="selected">
      
      <el-menu-item index="UserM" >
        <i class="el-icon-user"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-menu-item index="CompanyM">
        <i class="el-icon-s-platform"></i>
        <span slot="title">公司管理</span>
      </el-menu-item>
      <el-menu-item index="JobM">
        <i class="el-icon-s-claim"></i>
        <span slot="title">工作管理</span>
      </el-menu-item>
      <el-menu-item index="Witness"  >
        <i class="el-icon-message-solid"></i>
        <span slot="title">审批公司</span>
      </el-menu-item>
      <el-menu-item index="ImageUpload"  >
        <i class="el-icon-message-solid"></i>
        <span slot="title">图片上传</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</el-row>
  </div>
</template>

<script>
export default {
    methods:{
        
      selected(key,keyPath){
        console.log(key);
        this.$emit('pathName',key)
      }
    }
}
</script>

<style>

</style>