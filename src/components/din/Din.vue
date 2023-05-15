<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>
     <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="5">
          <el-input placeholder="请输入姓名" v-model="queryInfo.uname" clearable></el-input>
        </el-col>
        <el-col :span="5">
          <el-input placeholder="请输入商品名" v-model="queryInfo.gname" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        </el-col>
      </el-row>
      <!-- 订单列表区 -->
      <el-table  v-loading="loading" :data="orders" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名">
          <template slot-scope="scope">
            {{scope.row.user.uname}}
          </template>
        </el-table-column>
         <el-table-column label="购买商品">
            <template slot-scope="scope">
            {{scope.row.goods.gname}}
          </template>
         </el-table-column>
        <el-table-column label="数量" align="center" prop="ocount"> </el-table-column>
        <el-table-column label="商品状态" width="140px">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.ostate==0" type="info">未发货</el-tag>
            <el-tag v-if="scope.row.ostate==1">已发货</el-tag>
            <el-tag v-if="scope.row.ostate==2" type="warning">运输中</el-tag>
            <el-tag v-if="scope.row.ostate==3" type="warning">待收货</el-tag>
            <el-tag v-if="scope.row.ostate==4" type="success">已签收</el-tag>
            <el-tag v-if="scope.row.ostate==4" type="danger">未评论</el-tag>
            <el-tag v-if="scope.row.ostate==5" type="success">已签收</el-tag>
            <el-tag v-if="scope.row.ostate==5" type="success">已评论</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="单价">
           <template slot-scope="scope">
            ￥{{scope.row.oprice.toFixed(2)}}
          </template>
        </el-table-column>
         <el-table-column label="总价">
            <template slot-scope="scope">
            ￥{{scope.row.tprice.toFixed(2)}}
          </template>
         </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              @click="toEdit(scope.row)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button type="warning" @click="toDetail(scope.row)" icon="el-icon-tickets" circle >

            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageIndex"
        :page-sizes="[5, 10]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </el-card> 
    <el-dialog title="修改订单信息" :visible.sync="editOrderShow" width="50%" center @close="editOrderShow=false" >
      <!-- 内容主题区域 -->
      <el-form :model="editOrderInfo" label-width="120px">
        <el-form-item label="收货人姓名：" width="100px">
          <el-input v-model="editOrderInfo.recipient.rname"></el-input>
        </el-form-item>
        <el-form-item label="收货人电话：" width="100px">
          <el-input v-model="editOrderInfo.recipient.rtelephone"></el-input>
        </el-form-item>
        <el-form-item label="收货人地址：" width="100px">
          <el-input v-model="editOrderInfo.recipient.radress"></el-input>
        </el-form-item>
        <el-form-item label="商品状态：" width="100px" prop="ostate">
           <el-select v-model="checkedState" placeholder="请选择">
            <el-option
              v-for="item in states"
              :key="item.ostate"
              :label="item.value"
              :value="item.ostate">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editClose()">取 消</el-button>
        <el-button type="primary" @click="editCommit()">提交</el-button>
      </span>
    </el-dialog>

    <el-dialog title="订单详细信息" :visible.sync="detailShow" width="50%" center @close="detailShow=false" >
      <!-- 内容主题区域 -->
      <div class="order-detail">
        <p>
          <span>订单编号:</span> 
          {{detailOrder.oid}}------- 创建时间：{{detailOrder.otime|timeflow}}
           ------------<span>订单状态：</span>
           <el-tag v-if="detailOrder.ostate==0" type="info">未发货</el-tag>
          <el-tag v-if="detailOrder.ostate==1">已发货</el-tag>
          <el-tag v-if="detailOrder.ostate==2" type="warning">运输中</el-tag>
          <el-tag v-if="detailOrder.ostate==3" type="warning">待收货</el-tag>
          <el-tag v-if="detailOrder.ostate==4" type="success">已签收</el-tag>
          <el-tag v-if="detailOrder.ostate==4" type="danger">未评论</el-tag>
           <el-tag v-if="detailOrder.ostate==5" type="success">已签收</el-tag>
          <el-tag v-if="detailOrder.ostate==5" type="success">已评论</el-tag>
        </p>
        <p>
         <span>商品名称：</span>{{detailOrder.goods.gname}}-------
         <span>购买单价：</span>{{detailOrder.oprice.toFixed(2)}}----
         <span>购买数量：</span>{{detailOrder.ocount}}----
         <span>付款：</span>{{detailOrder.tprice.toFixed(2)}}
        </p>
        <p>
          <span>购买姓名：</span>{{detailOrder.user.uname}}----
          <span>购买人电话：</span>{{detailOrder.user.telephone}}----
          <span>购买人邮箱：</span>{{detailOrder.user.email}}
        </p>
        <p>
          <span>收货人姓名：</span>{{detailOrder.recipient.rname}}-----
          <span>收货人电话：</span>{{detailOrder.recipient.rtelephone}}<br/><br/>
          <span>收货人地址：</span>{{detailOrder.recipient.radress}}
        </p>
      </div>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="detailShow=false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
import Vue from 'vue'

Vue.filter('timeflow',date=>{
  if(!date) return ''
  const da = new Date(date);
  return da.getFullYear()+'-'+(da.getMonth()+1)+'-'+da.getDate();
})
export default {
   data() {
    return {
      loading:false,
      orders:[],
      total:0,
      editOrderShow:false,
      checkedState:'',
      detailShow:false,
      detailOrder:{
        oprice:0,
        tprice:0,
        goods:{},
        user:{},
        recipient:{}
      },
      recipient:{
        rname:'',
        rtelephone:'',
        radress:''
      },
      editOrderInfo:{
        recipient:{
          rname:'',
          rtelephone:'',
          radress:''
        },
        ostate:'',
        oid:''
      },
      states:[
        {ostate:'0',value:'未发货'},
        {ostate:'1',value:'已发货'},
        {ostate:'2',value:'运输中'},
        {ostate:'3',value:'待签收'},
        {ostate:'4',value:'已签收'},
        {ostate:'5',value:'已评价'},
      ],
      queryInfo:{
        uname:'',
        gname:'',
        pageIndex:1,
        pageSize:5
      }
    }
  },
  methods: {
    getOrders(){
      axios.post('/api/ssm/orders/getOrdersByFenye',this.queryInfo).then(res=>{
        this.orders = res.data.list;
        this.total = res.data.count;
      })
    },
    handleSizeChange(val){
      this.queryInfo.pageSize = val;
      this.getOrders()
    },
    handleCurrentChange(val){
      this.queryInfo.pageIndex = val;
      this.getOrders()
    },
    search(){

      this.getOrders()
    },
    toEdit(order){
      this.checkedState = order.ostate;
      this.recipient.rname = order.recipient.rname;
      this.recipient.rtelephone = order.recipient.rtelephone;
      this.recipient.radress = order.recipient.radress;
      this.editOrderInfo.recipient = order.recipient;
      this.editOrderInfo.oid = order.oid;
      this.editOrderShow = true;
    },
    toDetail(order){
      // console.log(order);
      this.detailOrder = order;
      this.detailShow = true;
    },
    editClose(){
      this.editOrderShow = false;
    },
    editCommit(){
      let result=true;
      if(this.editOrderInfo.recipient.rname!=this.recipient.rname){result=false; }
      if(this.editOrderInfo.recipient.rtelephone!=this.recipient.rtelephone){result=false; }
      if(this.editOrderInfo.recipient.radress!=this.recipient.radress){ result=false;}
        
      if(result){
        axios.get('/api/ssm/orders/updateOrderState?oid='+this.editOrderInfo.oid+'&state='+this.checkedState).then(res=>{
          if(res.data.flag){
            this.getOrders();
            this.$message.success('修改成功！');
            this.editOrderShow = false;
          }else{
            this.$message.error('修改失败！');
          }
        })
      }else{
        axios.post('/api/ssm/orders/updateOrderBase',{
          oid:this.editOrderInfo.oid,
          ostate:this.checkedState,
          rid:this.editOrderInfo.recipient.rid,
          rname:this.editOrderInfo.recipient.rname,
          radress:this.editOrderInfo.recipient.radress,
          rtelephone:this.editOrderInfo.recipient.rtelephone
        }).then(res=>{
          if(res.data.flag){
             this.getOrders();
            this.$message.success('修改成功！');
            this.editOrderShow = false;
          }else{
            this.$message.error('修改失败！');
          }
        })
      }
      
    },
  },
  mounted(){
    this.getOrders();
  },
}
</script>
<style scoped>

</style>