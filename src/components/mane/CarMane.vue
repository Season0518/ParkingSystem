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
          <el-input placeholder="请输入车牌号" v-model="queryInfo.card" clearable></el-input>
        </el-col>
        <el-col :span="4">
          <el-input placeholder="请输入用户名" v-model="queryInfo.person" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </el-col>
      </el-row>

      <el-table  v-loading="loading" :data="cars" border stripe>
        <el-table-column width="50" label="序号" align="center" type="index"></el-table-column>
        <el-table-column label="车牌号" prop="card" align="center"> </el-table-column>
        <el-table-column label="车类别" prop="carType" align="center"> </el-table-column>
        <el-table-column label="用户名" prop="person" align="center"> </el-table-column>
        <el-table-column label="创建时间" prop="xtime" align="center"> </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.username!='admin'"
              @click="toEdit(scope.row)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
            <el-button
              v-if="scope.row.username!='admin'"
              @click="rePassword(scope.row.uid)"
              type="warning"
              icon="el-icon-refresh"
              circle
            ></el-button>
            <el-button v-if="scope.row.username!='admin'" @click="userDelete(scope.row.uid)"
             type="danger" icon="el-icon-delete" circle ></el-button>
            <el-button
              v-if="scope.row.username=='admin'"
              @click="toEdit(scope.row)"
              type="primary"
              icon="el-icon-edit"
              circle
              disabled
            ></el-button>
            <el-button disabled v-if="scope.row.username=='admin'" @click="userDelete(scope.row.uid)"
             type="danger" icon="el-icon-delete" circle ></el-button>
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
        person: '',
        carType:'',
        card:'',
        pagenum: 1,
        pageSize: 6,
      },
      cars: [],

    }
  },
  methods:{
    getList(){
      axios.post('/api/car/getList',this.queryInfo).then(res=>{
        this.loading = false;
        this.total = res.data.data.total;
        this.cats = res.data.data.records;
      })
    },
  },
  mounted(){
    this.getList();
  }
}
</script>