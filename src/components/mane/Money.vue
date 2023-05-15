<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>费用管理</el-breadcrumb-item>
      <el-breadcrumb-item>费用信息</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="请输入车位类型" v-model="carType" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-magic-stick" @click="feeUserShow=true">充值</el-button>
        </el-col>
      </el-row>

      <el-table  v-loading="loading" :data="fees" border stripe>
        <el-table-column width="50" label="序号" align="center" type="index"></el-table-column>
        <el-table-column label="用户名" prop="carType" align="center"> </el-table-column>
        <el-table-column label="价位" prop="money" align="center"></el-table-column>
        <el-table-column label="价位说明" prop="moneyDesc" align="center"></el-table-column>
        <el-table-column label="创建时间" prop="feeTime" align="center">
          <template slot-scope="scope">
            {{dateFormat(scope.row.feeTime,3)}}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              @click="toEdit(scope.row)"
              type="primary"
              icon="el-icon-edit"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="用户充值"  :visible.sync="feeUserShow" width="50%" center @close="feeUserShow=false" >
      <el-form :model="feeUser" :rules="feeUserRules" ref="feeUserRules"  label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="feeUser.username"></el-input>
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input v-model="feeUser.money"></el-input>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="feeUserShow=false">取 消</el-button>
        <el-button type="primary" @click="feeUserCon('feeUserRules')">确认充值</el-button>
      </span>
    </el-dialog>
    <el-dialog title="车位类型价格修改"  :visible.sync="feeShow" width="50%" center @close="editClose" >
      <el-form :model="feeUser"  label-width="80px">
        <el-form-item label="车位类型">
          <el-input v-model="feeEdit.carType" disabled></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="feeEdit.money"></el-input>
        </el-form-item>
         <el-form-item label="价格说明">
          <el-input v-model="feeEdit.moneyDesc"></el-input>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editClose">取 消</el-button>
        <el-button type="primary" @click="editCon">确认修改</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data(){
    return{
      total: 0,
      loading:true,
      carType:'',
      fees:[],
      feeShow:false,
      feeEdit:{
        carType:'',
        money:'',
        moneyDesc:'',
      },
      feeUserShow:false,
      feeUserRules:{
        username: [
           { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
         money: [
           { required: true, message: '请输入充值金额', trigger: 'blur' },
        ],
      },
      feeUser:{
        username:'',
        money:'',
      },
    }
  },
  methods:{
    getList(){
      axios.get('/api/fee/list?carType='+this.carType).then(res=>{
        this.loading = false;
        this.fees = res.data.data;
      })
    },
    toEdit(row){
      this.feeEdit.carType = row.carType;
      this.feeEdit.money = row.money;
      this.feeEdit.moneyDesc = row.moneyDesc;
      this.feeShow = true;
      console.log(row);
    },
    editClose(){
      this.feeEdit.carType = '';
      this.feeEdit.money = '';
      this.feeEdit.moneyDesc = '';
      this.feeShow = false;
    },
    tofeeUser(){
     
    },
    feeUserCon(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('/api/fee/userFee',this.feeUser).then(res=>{
            if(res.data.data.flag){
              this.$message.success('充值成功！');
              this.feeUserShow = false;
            }else{
              this.$message.error(res.data.data.msg);
            }
          })
        } else {
          return false;
        }
      });
    },
    editCon(){
      axios.post('/api/fee/update',this.feeEdit).then(res=>{
        this.loading = false;
        if(res.data.data.flag){
          this.$message.success('修改成功！');
          this.getList();
          this.editClose();
        }else{
          this.$message.error(res.data.data.msg);
        }
      })
    },
    //时间转换函数
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
  },
}
</script>