<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="30">
        <el-col :span="6">
          <el-input v-model="queryInfo.query" placeholder="请输入商品名称" class="input-serch"></el-input>
        </el-col>
        <el-col :span="4">
            <el-input v-model="queryInfo.min" placeholder="最低价格"></el-input>
        </el-col>
        <el-col :span="4">
             <el-input v-model="queryInfo.max" placeholder="最高价格"></el-input>
        </el-col>
        <el-col :span="3">
            <el-button type="primary" icon="el-icon-search" @click="searchGoods">搜索</el-button>
        </el-col>
        <el-col :span="4">
            <el-button type="primary" icon="el-icon-plus" @click="toAdd">添加</el-button>
        </el-col>
        <!-- <el-col :span="16">
          <el-button type="primary" @click="addShop">添加</el-button>
        </el-col> -->
      </el-row>
      <!-- 列表形式 -->
      <el-table v-loading="loading" :data="goods" stripe border style="width: 100%">
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="gname" label="商品名称"></el-table-column>
        <el-table-column prop="mprice" label="市场价格"></el-table-column>
        <el-table-column prop="gprice" label="出售价格"></el-table-column>
        <el-table-column prop="num" label="数量"></el-table-column>
        <el-table-column prop="hot" label="热度">
          <template slot-scope="scope">
            <el-switch @change="hotChange(scope.row.gid,scope.row.hot)" v-model="scope.row.hot" active-text="热门" inactive-text="普通"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="上架">
          <template slot-scope="scope">
            <el-switch @change="sellChange(scope.row.gid,scope.row.sell)" v-model="scope.row.sell" active-text="上架" inactive-text="下架"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="toEdit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
            <el-button @click="deleteGoods(scope.row.gid)" type="danger" icon="el-icon-delete" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页底部 -->
       <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageIndex"
        :page-sizes="[5,10]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </el-card>
    <!-- 修改商品的对话框 -->
    <el-dialog
      title="修改商品"
      :visible.sync="editShow"
      width="50%"
      center
      @close="editClose"
    >
      <!-- 内容主题区域 -->
      <el-form :model="editGoods" :rules="editRules" ref="editRules" label-width="150px">
        <el-form-item label="商品名称" prop="gname">
          <el-input v-model="editGoods.gname"></el-input>
        </el-form-item>
        <el-form-item label="市场价格" prop="mprice">
          <el-input type="number" v-model="editGoods.mprice"></el-input>
        </el-form-item>
        <el-form-item label="出售价格" prop="gprice">
          <el-input type="number" v-model="editGoods.gprice"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="num">
          <el-input type="number" v-model="editGoods.num"></el-input>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editClose('editRules')">取 消</el-button>
        <el-button type="primary" @click="editCommit('editRules')">确认修改</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data() {
     var valiNumberPass1 = (rule, value, callback) => {//正整数
            if (value === '') {
                callback(new Error('请输入市场价格'));
            } else if (isNaN(parseFloat(value))) {
                callback(new Error('请输正确的数值'));
            } else {
                callback();
            }
     };
    return {
      loading:true,
      searchValue:'',
      goods:[],
      editGoods:{
        gid:'',
        gname:'',
        mprice:'',
        gprice:'',
        num:'',
        color:'',
        weight:'',
        gadress:''
      },
      editShow:false,
      queryInfo:{
        query:'',
        min:'',
        max:'',
        pageIndex:1,
        pageSize:5
      },
      total:0,
      editRules:{
        gname:[{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        mprice:[{ required: true, validator:valiNumberPass1, trigger: "blur" }],
        gprice:[{required: true, message: '请输入出售价格', trigger: 'blur'}],
        num:[{ required: true, message: '请输入商品数量', trigger: 'blur'}],
      }
      
    }
  },
  methods: {
    searchGoods(){
      this.getGoods();
    },
    hotChange(gid,hot){
     const hot2 = hot==true?1:0;
      axios.get('api/ssm/goods/updateGoodsHot?gid='+gid+'&hot='+hot2).then(res=>{
        if(res.data.flag){
          this.$message.success('修改成功！');
        }else{
          this.$message.error('修改失败！');
          this.getGoods();
        }
      })
    },
    sellChange(gid,sell){
      const sell2 = sell==true?1:0;
      axios.get('api/ssm/goods/updateGoodsSell?gid='+gid+'&sell='+sell2).then(res=>{
        if(res.data.flag){
          this.$message.success('修改成功！');
        }else{
          this.$message.error('修改失败！');
          this.getGoods();
        }
      })
    },
    toEdit(val){
      //拷贝
      this.editGoods.gid = val.gid;
      this.editGoods.gname = val.gname;
      this.editGoods.mprice = val.mprice;
      this.editGoods.gprice = val.gprice;
      this.editGoods.num = val.num;
      this.editGoods.gadress = val.gadress;
      this.editGoods.color = val.color;
      this.editGoods.weight = val.weight;
      this.editGoods.sid = val.sid;

      this.editShow = true;
    },
    editClose(){
      this.editShow = false;
      //重置规则
      // this.$refs[formName].resetFields(); 
     //会报错
     //原因是：mouted加载数据以后，隐藏的弹出框并没有编译渲染进dom里面
     //。所以click弹出的时候$refs并没有获取到dom元素导致 ‘resetFields’ of undefined
      this.$nextTick(()=>{
        this.$refs.editRules.resetFields();
      });
    },
    toAdd(){
      this.$router.push('/addshop');
    },
    checkGname(){
      this.editGoods.gname
    },
    editCommit(formName){
      this.$refs[formName].validate(valid => {
        if(valid){
          //请求
            axios.post('/api/ssm/goods/updateGoodsBase',this.editGoods).then(res=>{
            if(res.data.flag){
              this.$message.success('修改成功！');
              this.getGoods();
            }else{
              this.$message.error('修改失败！');
            }
            this.editShow = false;
          })
        }else{
          return false;
        }
      })

      
    },
    deleteGoods(gid){
       this.$confirm('此操作将永久删除该商品信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.get('/api/ssm/goods/deleteGoodsById?gid='+gid).then(res=>{
            if(res.data.flag){
              this.getGoods();
              this.$message.success("删除成功！");
            }else{
              this.$message.error("删除失败！")
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
     
    },
    handleSizeChange(val){
      this.queryInfo.pageSize = val;
      this.getGoods();
    },
    handleCurrentChange(val){
      this.queryInfo.pageIndex = val;
      this.getGoods();
    },
    getGoods(){
      axios.post("api/ssm/goods/getGoodsByFenye",this.queryInfo).then(res=>{
            this.loading = false;
            this.goods = res.data.list;
            this.total = res.data.count;
            for(let item in this.goods){
              this.goods[item].sell=this.goods[item].sell==1?true:false;
              this.goods[item].hot=this.goods[item].hot==1?true:false;
            }   
      })
    },
  },
  mounted() {
    //获取商品数据
    this.getGoods();
  },
}
</script>
<style scoped>
/* .input-serch{
  width: 130px;
} */
</style>