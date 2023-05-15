<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="5">
          <el-input placeholder="请输入姓名" v-model="queryInfo.query" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-select v-model="queryInfo.sex" placeholder="请选择性别">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </el-col>
         <el-col :span="2">
          <el-button type="primary" icon="el-icon-plus" @click="addUser">新增</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表区 -->
      <el-table  v-loading="loading" :data="users" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="uname"> </el-table-column>
         <el-table-column label="用户名" prop="username"> </el-table-column>
        <el-table-column label="邮箱" prop="email"> </el-table-column>
        <el-table-column label="电话" prop="telephone"> </el-table-column>
        <el-table-column label="性别" prop="sex"> </el-table-column>
        <el-table-column label="状态" prop="ustate">
          <template slot-scope="scope">
            <el-switch @change="stateChange(scope.row.uid,scope.row.ustate)" v-model="scope.row.ustate" active-text="在线" inactive-color="#ff4949" inactive-text="冻结"> </el-switch>
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
            <el-button @click="userDelete(scope.row.uid)"
             type="danger" icon="el-icon-delete" circle ></el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[6, 12]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </el-card>
    <el-dialog title="修改用户" :visible.sync="editShow" width="50%" center @close="editClose" >
      <!-- 内容主题区域 -->
      <el-form :model="editUser" :rules="editRules" ref="editRules" label-width="80px">
        <el-form-item label="姓名" prop="uname">
          <el-input v-model="editUser.uname"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="editUser.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editUser.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="telephone">
          <el-input v-model="editUser.telephone"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <template>
            <el-radio v-model="editUser.sex" label="男">男</el-radio>
            <el-radio v-model="editUser.sex" label="女">女</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker v-model="editUser.birthday" type="date" placeholder="选择日期">
    </el-date-picker>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editClose()">取 消</el-button>
        <el-button type="primary" @click="editCommit('editRules')">确认修改</el-button>
      </span>
    </el-dialog>

    <el-dialog title="新增用户" :visible.sync="addShow" width="50%" center @close="addClose" >
      <!-- 内容主题区域 -->
      <el-form :model="editUser" :rules="editRules" ref="editRules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editUser.username"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="uname">
          <el-input v-model="editUser.uname"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="editUser.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editUser.email"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="telephone">
          <el-input v-model="editUser.telephone"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <template>
            <el-radio v-model="editUser.sex" label="男">男</el-radio>
            <el-radio v-model="editUser.sex" label="女">女</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker v-model="editUser.birthday" type="date" placeholder="选择日期">
    </el-date-picker>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addClose()">取 消</el-button>
        <el-button type="primary" @click="addCommit('editRules')">确认添加</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data() {
    // 定义邮箱校验规则
    var checkEmail = (rule,value,callback)=>{
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if(value==''){callback(new Error('请输入邮箱'));}
      else if(!regEmail.test(value)){ callback(new Error('请输入合法邮箱'));}
      else{callback();}

    }
    // 定义电话校验规则
    var checkMobie = (rule,value,callback)=>{
      const regMobie = /^((0\d{2,3}-\d{7,8})|(1[3456789]\d{9}))$/
      if(value==''){callback(new Error('请输入手机号'));}
      else if(!regMobie.test(value)){callback(new Error('请输入合法手机号'));}
      else callback();
    }

    var checkPassword =  (rule,value,callback)=>{
      const regPa = /^\w{6,16}$/
      if(value=='')callback(new Error('请输入密码'));
      else if(!regPa.test(value))callback(new Error('密码为6~16位数字、字母下划线组成'));
      else callback();
    }

    var checkUsername =  (rule,value,callback)=>{
      const regPa = /^\w{4,16}$/
      if(value=='')callback(new Error('请输入用户名'));
      else if(!regPa.test(value)){
        callback(new Error('用户名为4~16位数字、字母下划线组成'));
      }else{
        axios.post('/api/ssm/user/checkUserRegist',{'username':value}).then(res=>{
          if(res.data.flag){
            callback(new Error('用户名已存在！'));
          }else{
            callback();
          }
        })
      }
    }


    return {
      total: 0,
      loading:true,
      queryInfo: {
        query: '',
        sex:'',
        pagenum: 1,
        pageSize: 6,
      },
      users:[],
      options:[{
        value:'5',
        label:'全部'
      },
        {
        value:'男',
        label:'男'
      },{
        value:'女',
        label:'女'
      }
      ],
      editShow:false,
      addShow:false,
      editUser:{
        uid:'',
        username:'',
        uname:'',
        email:'',
        telephone:'',
        sex:'',
        birthday:'',
        password:''
      },
      // 添加表单的验证规则对象
      editRules: {
        uname: [{required: true,message: '请输入用户名', trigger: 'blur',}],
        username: [{required: true,validator:checkUsername, trigger: 'blur',}],
        email: [{required: true,validator:checkEmail,trigger:'blur'}],
        telephone: [{required: true,validator:checkMobie,trigger:'blur'}],
        password:[{required: true,validator:checkPassword,trigger:'blur'}],
        birthday: [{required: true,message: '请选择出生日期', trigger: 'blur',}],
      },
    }
  },
  methods: {
    getList() {
       if(this.queryInfo.sex==5)this.queryInfo.sex='';//查询全部性别
        axios.post('/api/ssm/user/getUsersGao',this.queryInfo).then(res=>{
          this.loading = false;
          this.total = res.data.count;
          this.users = res.data.list;
          for(let item in this.users){
            this.users[item].ustate=this.users[item].ustate==1?true:false;
          }  
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
    stateChange(uid,ustate){
      const state = ustate==true?1:0;
      axios.post('/api/ssm/user/updateUserState',{uid:uid,state:state}).then(res=>{
        if(res.data.flag){
          this.$message.success('修改成功！');
        }else{
          this.$message.error('修改失败！');
          this.getList();
        }
      })
    },
    toEdit(user){
      this.editUser.uid = user.uid;
      this.editUser.uname = user.uname;
      this.editUser.password = user.password;
      this.editUser.sex = user.sex;
      this.editUser.email = user.email;
      this.editUser.telephone = user.telephone;
      this.editUser.birthday = user.birthday;
      
      this.editShow = true;
    },
    editClose(){
      this.editShow = false;
      this.$nextTick(()=>{
        this.$refs.editRules.resetFields();
      });
    },
    editCommit(formName){
      this.$refs[formName].validate(valid => {
        if(valid){
          //日期进行转换
          this.editUser.birthday=new Date(this.editUser.birthday).getTime();
          //请求
          axios.post('/api/ssm/user/updateUserBase',this.editUser).then(res=>{
            if(res.data.flag){
              this.$message.success('修改成功！');
              this.getList();
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
    addUser(){
      for(let item in this.editUser){
        this.editUser[item] = '';
      }
      this.editUser.sex='男';
      this.addShow = true;
    },
    addClose(){
      this.addShow = false;
      this.$nextTick(()=>{
        this.$refs.editRules.resetFields();
      });
    },
    addCommit(formName){
      this.$refs[formName].validate(valid => {
        if(valid){
          //日期进行转换
          this.editUser.birthday=new Date(this.editUser.birthday).getTime();
          //请求
          axios.post('/api/ssm/user/addUser',this.editUser).then(res=>{
            if(res.data.flag){
              this.$message.success('添加成功！');
              this.getList();
              this.addShow = false;
            }else{
              this.$message.error('添加失败！');
            }
          })
        }else{
          return false;
        }
      })
    },
    async userDelete(id) {
      const re = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).catch(err=>err)
      // console.log(re)
      if(re !='confirm'){
        this.$message.info('已取消删除！')
      }else{
        axios.get('/api/ssm/user/deleteUser?uid='+id).then(res=>{
          if(res.data.flag){
            this.$message.success('删除成功！')
            this.getList();
          }else{
            this.$message.error('删除失败！')
          }
        })
      }
    },
  
  },
  mounted() {
    this.getList();
  }
}
</script>
<style scoped>
  body {
    margin: 0;
  }
</style>