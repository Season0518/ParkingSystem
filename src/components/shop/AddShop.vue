<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品添加</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-button type="primary" @click="toShop">返回商品列表</el-button>
      <el-form :model="addGoods" :rules="addRules" ref="addRules" label-width="150px">
        <el-form-item label="商品类型" prop="sid">
          <el-select v-model="shopTypeSid" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.sid"
              :label="item.sname"
              :value="item.sid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="gname">
          <el-input v-model="addGoods.gname"></el-input>
        </el-form-item>
        <el-form-item label="市场价格" prop="mprice">
          <el-input type="number" v-model="addGoods.mprice"></el-input>
        </el-form-item>
        <el-form-item label="出售价格" prop="gprice">
          <el-input type="number" v-model="addGoods.gprice"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="num">
          <el-input type="number" v-model="addGoods.num"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <el-upload
            class="upload-demo"
            action="/api/ssm/goods/uploadImg"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="successUpload"
            :on-error="errorUpload"
            :file-list="fileList"
            :multiple="false"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品描述" prop="gdesc">
          <el-input v-model="addGoods.gdesc"></el-input>
        </el-form-item>
        <el-form-item label="生产地址" prop="gadress">
          <el-input v-model="addGoods.gadress"></el-input>
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="addGoods.color"></el-input>
        </el-form-item>
        <el-form-item label="重量" prop="weight">
          <el-input type="number" v-model="addGoods.weight"></el-input>
        </el-form-item>
        <el-form-item label="添加">
           <el-button type="primary" @click="onAdd">添加商品</el-button>
        </el-form-item>
      </el-form>
      
    </el-card>
  </div>
</template>
<script>
import axios from 'axios';
export default {
  data() {
    return {
     addGoods:{
       gname:'',
       mprice:'',
       gprice:'',
       num:'',
       color:'',
       gdesc:'',
       picture:'',
       gadress:'',
       weight:'',
       sid:'',
     },
     fileList:[],
     imgName:'',
     options:[],
     shopTypeSid:'',
     addRules:{
        gname:[{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        mprice:[{ required: true, message:'请输入商品市场价格', trigger: "blur" }],
        gprice:[{required: true, message: '请输入出售价格', trigger: 'blur'}],
        num:[{ required: true, message: '请输入商品数量', trigger: 'blur'}],
        sid:[{ required: true, message: '请选择商品类型', trigger: 'blur'}],
        gadress:[{ required: true, message: '请输入盛产地址', trigger: 'blur'}],
        color:[{ required: true, message: '请输入商品颜色', trigger: 'blur'}],
        gdesc:[{ required: true, message: '请输入商品描述', trigger: 'blur'}],
     },
     
    }
  },
  methods: {
      toShop(){
        this.$router.push('/shop');
      },
      handlePreview(file, fileList){
       
     },
     handleRemove(file, fileList){
       axios.get('/api/ssm/goods/deleteUpload?img='+this.imgName).then(res=>{
          this.$message.success('移除成功！');
       })
     }, 
     successUpload(response, file, fileList){
      if(response.flag){
        this.imgName = response.img;
        this.$message.success('添加成功！');
      }
     },
     errorUpload(err, file, fileList){
        this.$message.console.error('添加失败！');
     },
     getShopType(){
       axios.get('/api/ssm/shopType/getShopTypes').then(res=>{
          for(let item in res.data){
            this.options = res.data;
          }
       })
     },
     onAdd(){
      //  console.log(this.shopTypeSid);
       if(this.shopTypeSid!=''){
         if(this.imgName!=''){
           this.addGoods.picture = this.imgName;
           this.addGoods.sid = this.shopTypeSid;
           axios.post('/api/ssm/goods/addGoods',this.addGoods).then(res=>{
             if(res.data.flag){
               this.$message.success("添加成功！");
               this.$router.push('/shop');
             }else{
               this.$message.error("添加失败！")
             }
           })
         }else{
           this.$message("请上传商品");
         }
       }else{
         this.$message("请选择类型");
       }
       
     },
  },
  mounted() {
    this.getShopType();
  },
}
</script>
<style lang="less" scoped>
.el-steps {
  margin: 15px 0;
  padding: 5px;
}
.el-step__title {
  font-size: 12px;
}
.el-tab-pane {
  border-bottom: black;
}
.el-form{
  width: 60%;
  margin-top: 20px;
  // /* border: 1px solid red; */
  // margin: 5px auto;
  // .el-form-item{
  //   margin: 50px 0px;
  // }
}
</style>