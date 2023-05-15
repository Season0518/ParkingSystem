<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>车辆信息管理</el-breadcrumb-item>
      <el-breadcrumb-item>车辆信息</el-breadcrumb-item>
    </el-breadcrumb>

   <!-- 卡片视图区域 -->
    <el-card>
       <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="车牌号" v-model="queryInfo.card" clearable></el-input>
        </el-col>
        <el-col :span="4">
          <el-input placeholder="用户" v-model="queryInfo.nike" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </el-col>
      </el-row>

      <el-table  v-loading="loading" :data="cars" border stripe>
        <el-table-column width="50" label="序号" align="center" type="index"></el-table-column>
        <el-table-column label="用户" align="center">
          <template slot-scope="scope">
            {{scope.row.user.nike}}
          </template>
        </el-table-column>
        <el-table-column label="车牌号" align="center">
          <template slot-scope="scope">
            {{scope.row.user.card}}
          </template>
        </el-table-column>
        <el-table-column label="联系电话" align="center">
          <template slot-scope="scope">
            {{scope.row.user.phone}}
          </template>
        </el-table-column>
        <el-table-column label="停车地点" prop="stallArea" align="center"></el-table-column>
        <el-table-column label="停车车位" prop="stallNum" align="center"></el-table-column>
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
        nike: '',
        card:'',
        pagenum: 1,
        pageSize: 5,
      },
      cars: [],
    }
  },
  methods:{
    getList(){
      axios.post('/api/car/getCar',this.queryInfo).then(res=>{
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
  },
  mounted(){
    this.getList();
  }
}
</script>