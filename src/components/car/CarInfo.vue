<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>车位管理</el-breadcrumb-item>
      <el-breadcrumb-item>车位信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-select v-model="queryInfo.carArea" placeholder="请选择停车区域">
            <el-option v-for="item in carAreas" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="queryInfo.carType" placeholder="请选择车位类型">
            <el-option v-for="item in carTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="queryInfo.carState" placeholder="请选择车位状态">
            <el-option v-for="item in carStates" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </el-col>
      </el-row>

        <!-- 车位信息区域 -->
      <el-table  v-loading="loading" :data="cars" border stripe>
        <el-table-column width="50" label="序号" align="center" type="index"></el-table-column>
        <el-table-column label="车位号" prop="stallNum" align="center"> </el-table-column>
        <el-table-column label="车位区域" prop="stallArea" align="center"> </el-table-column>
        <el-table-column label="车位类型" prop="stallType" align="center"> </el-table-column>
        <el-table-column label="车位状态" prop="stallState" align="center"> </el-table-column>
        <el-table-column label="车位收费" prop="stallMoney" align="center">
          <template slot-scope="scope">
            {{scope.row.stallMoney}}元/时
          </template>
        </el-table-column>
        <!-- <el-table-column label="状态" prop="ustate">
          <template slot-scope="scope">
            <el-switch @change="stateChange(scope.row.uid,scope.row.ustate)" v-model="scope.row.ustate" active-text="在线" inactive-color="#ff4949" inactive-text="冻结"> </el-switch>
          </template>
        </el-table-column> -->
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button v-if="!scope.row.userId" @click="useStall(scope.row.sid)"
             type="danger" icon="el-icon-thumb" >预定</el-button>
             <el-button v-else @click="useStall(scope.row.sid)"
             type="info" icon="el-icon-thumb" disabled>预定</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[5, 10, 15 ,20]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </el-card>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data(){
    return{
      total: 0,
      loading:true,
      queryInfo: {
        carArea: '',
        carType:'',
        carState:'',
        pagenum: 1,
        pageSize: 6,
      },
      cars:[],
      uid:undefined,
      carAreas:[{
        value: null,
        label:'全部'
      },{
        value:'青山镇南小区',
        label:'青山镇南小区'
      },{
        value:'永安小区',
        label:'永安小区'
      }],
      carTypes:[{
        value:null,
        label:'全部'
      },{
        value:'临时车位',
        label:'临时车位'
      },{
        value:'固定车位',
        label:'固定车位'
      }],
      carStates:[{
        value:null,
        label:'全部'
      },{
        value:'已停车',
        label:'已停车'
      },{
        value:'空闲中',
        label:'空闲中'
      }]
    }
  },
  methods:{
    getList(){
      axios.post('/api/stall/getStallList',this.queryInfo).then(res=>{
        this.loading = false;
        this.total = res.data.data.total;
        this.cars = res.data.data.records;
      })
    },
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize
      this.getList()
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage
      this.getList()
    },
    async useStall(sid){ 
      const re = await this.$confirm('此操作将预定该车位, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
      }).catch(err=>err)
      // console.log(re)
      if(re !='confirm'){
        this.$message.info('已取消预定！')
      }else{
        axios.get('/api/stall/orderStall?uid='+this.uid+'&sid='+sid).then(res=>{
          if(res.data.data){
            this.$message.success('预定成功！')
            this.getList();
          }else{
            this.$message.error('预定失败！')
          }
        })
      }
    },
  },
  mounted(){
    this.getList();
    this.uid = window.sessionStorage.getItem('token');
  },
}
</script>