<template>
  <div>
    <h1>瀛︾敓绠＄悊绯荤粺</h1>
    <h1>{{msg}}</h1>
    <button @click="test">寮圭獥</button>
    <el-button type="success" round @click="test">鎴愬姛鎸夐挳</el-button>
    <input v-model="mode"/>
    <div>
  <el-input placeholder="璇疯緭鍏ュ唴瀹�" v-model="input1">
    <template slot="prepend">Http://</template>
  </el-input>
</div>
    <div v-show="true">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae molestiae laborum veritatis, sunt ex distinctio laboriosam fuga quam at. Rem, eius? Reprehenderit quam tempora harum consectetur quo modi magni maxime.
    </div>
    <div v-for=" arr in srrays" v-bind:key="arr.id">
        {{arr.name}}
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
    
data(){
    return{
        msg:'灏忔槑',
        mode:'123',
        input1:'',
        srrays:[
                 {
                    id:1,
                    name:'鏅撴瘏',
                    age:42
                 },{
                    id:2,
                    name:'鏈卞畞',
                    age:43
                 },{
                    id:3,
                    name:'锟�??锟斤拷',
                    age:12
                 },
                ]
    }
    
},
methods:{
    test(){
        alert(this.mode);
    },
    aj(){
        axios({
            url:'http://localhost:8082',
            method:'GET'
        }).then
    },
    tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      },
    
},

}
</script>

<style>


</style>