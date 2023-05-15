<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>车辆进出管理</el-breadcrumb-item>
      <el-breadcrumb-item>车辆进出</el-breadcrumb-item>
    </el-breadcrumb>
    
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="请输入用户名" v-model="queryInfo.person" clearable></el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="queryInfo.stallArea" placeholder="请选择区域">
            <el-option v-for="item in carAreas" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-col>
        <el-col :span="10">
            <el-date-picker
              v-model="times"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间">
            </el-date-picker>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </el-col>
      </el-row>

      <el-table  v-loading="loading" :data="stallRes" border stripe>
        <el-table-column width="50" label="序号" align="center" type="index"></el-table-column>
        <el-table-column label="用户名" prop="person" align="center"> </el-table-column>
        <el-table-column label="驶入时间" prop="createTime" align="center">
          <template slot-scope="scope">
             {{dateFormat(scope.row.createTime,3)}}
          </template>
        </el-table-column>
        <el-table-column label="驶出时间" prop="overTime" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.overTime">{{dateFormat(scope.row.overTime,3)}}</span>
            <el-tag type="success" v-else>未驶出</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="车位区域" align="center">   
          <template slot-scope="scope">
           {{scope.row.stall.stallArea}}
          </template>
        </el-table-column>
        <el-table-column label="车位号" align="center">
         <template slot-scope="scope">
           {{scope.row.stall.stallNum}}
          </template>
        </el-table-column>
        <el-table-column label="收费" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.overTime">{{scope.row.money}}元</span>
            <span v-else class="fee-in">{{feeChange(scope.row)}}元</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="驶出扣费" placement="top-start">
             <el-button v-if="scope.row.overTime" @click="opare(scope.row)"
             type="info" icon="el-icon-finished" circle disabled></el-button>
             <el-button v-else @click="opare(scope.row)"
             type="warning" icon="el-icon-finished" circle ></el-button>
            </el-tooltip> 
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
      times:'',
      queryInfo: {
        person: '',
        stallArea:'',
        inTime:'',
        outTime:'',
        pagenum: 1,
        pageSize: 6,
      },
      carAreas:[],
      stallRes:[],
    }
  },
  methods:{
    getList(){
      if(this.times!=null && this.times!=''){
        this.queryInfo.inTime = this.times[0];
        this.queryInfo.outTime = this.times[1];
      }
      axios.post('/api/stall/allList',this.queryInfo).then(res=>{
        this.loading = false;
        this.total = res.data.data.total;
        this.stallRes = res.data.data.records;
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
    getCommunityList(){
      axios.get('/api/community/list').then(res=>{
        this.carAreas = [];
        this.carAreas.push({value:null,label:'全部'})
        for(let i=0;i<res.data.data.length;i++){
          let val = {value:res.data.data[i].zname,label: res.data.data[i].zname}
          this.carAreas.push(val);
        }
      })
    },
    feeChange(stallRes){
      let startTime = new Date(stallRes.createTime);
      let endTime = new Date();
      let hours = ((endTime.getTime() - startTime.getTime())/3600000);
      return Math.floor(hours*10)/10*stallRes.stall.stallMoney;
    },
    async opare(stallRes){
      const re = await this.$confirm('此操作将'+stallRes.person+'车辆驶出, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
      }).catch(err=>err)
      // console.log(re)
      if(re !='confirm'){
        this.$message.info('已取消驶出扣费！')
      }else{
        let oa = {};
        oa.pid = stallRes.pid;
        oa.person = stallRes.person;
        oa.stallId = stallRes.stallId;
        oa.money = this.feeChange(stallRes);
        axios.post('/api/stall/payMoney',oa).then(res=>{
          if(res.data.data){
            this.$message.success('驶出扣费成功！')
            this.getList();
          }else{
            this.$message.error('驶出扣费失败！')
          }
          
        })
      }
    },
    dateFormat(val,timeType){
      //将字符串或长整型转为时间类型
      let date = new Date(val);
      //默认格式为'年月日 时:分:秒'
      let resultFormat = 'Y年M月D日 h:m:s'
      switch (timeType) {
          case 1: resultFormat = 'Y年M月D日';break;
          case 2: resultFormat = 'Y-M-D';break;
          case 3: resultFormat = 'Y-M-D h:m:s';break;
      }
      let result = formatTime(resultFormat);

      // 格式化日期，如月、日、时、分、秒保证为2位数
      function formatNumber (n) {
          n = n.toString()
          return n[1] ? n : '0' + n;
      }
      // 参数number为毫秒时间戳，format为需要转换成的日期格式
      function formatTime (format) {
          let newArr = []
          let formatArr = ['Y', 'M', 'D', 'h', 'm', 's']
          newArr.push(date.getFullYear())
          newArr.push(formatNumber(date.getMonth() + 1))
          newArr.push(formatNumber(date.getDate()))

          newArr.push(formatNumber(date.getHours()))
          newArr.push(formatNumber(date.getMinutes()))
          newArr.push(formatNumber(date.getSeconds()))

          for (let i in newArr) {
              format = format.replace(formatArr[i], newArr[i])
          }
          return format;
      }

      return result;
    },
  },
  mounted(){
    this.getList();
    this.getCommunityList();
  }
}
</script>
<style scoped>
.fee-in{
  color: red;
}
</style>