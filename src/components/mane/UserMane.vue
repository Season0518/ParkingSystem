<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
      <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-input placeholder="请输入用户名" v-model="queryInfo.username" clearable></el-input>
        </el-col>
        <el-col :span="4">
          <el-input placeholder="请输入昵称" v-model="queryInfo.nike" clearable></el-input>
        </el-col>
        <el-col :span="4">
          <el-input placeholder="请输入车牌号" v-model="queryInfo.card" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" icon="el-icon-plus" @click="addUser">新增</el-button>
        </el-col>
      </el-row>

        <!-- 车位信息区域 -->
      <el-table  v-loading="loading" :data="users" border stripe>
        <el-table-column width="50" label="序号" align="center" type="index"></el-table-column>
        <el-table-column label="用户名" prop="username" align="center"> </el-table-column>
        <el-table-column label="昵称" prop="nike" align="center"> </el-table-column>
        <el-table-column label="性别" prop="sex" align="center"> </el-table-column>
        <el-table-column label="联系电话" prop="phone" align="center"> </el-table-column>
        <el-table-column label="车牌号" prop="card" align="center"> </el-table-column>
        <el-table-column label="账户余额" prop="money" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.money">{{scope.row.money}}元</span>
            <span v-else>0元</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="状态" prop="ustate">
          <template slot-scope="scope">
            <el-switch @change="stateChange(scope.row.uid,scope.row.ustate)" v-model="scope.row.ustate" active-text="在线" inactive-color="#ff4949" inactive-text="冻结"> </el-switch>
          </template>
        </el-table-column> -->
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

     <el-dialog title="修改用户" :visible.sync="editShow" width="450px" center @close="editClose" >
      <!-- 内容主题区域 -->
      <el-form :model="editUser" :rules="editRules" ref="editRules" label-width="80px">
        <el-form-item label="昵称" prop="nike" class="is-required">
          <el-input v-model="editUser.nike" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" class="is-required">
          <el-input v-model="editUser.password"  show-password style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="余额" prop="money" class="is-required">
            <el-input v-model.number="editUser.money" style="width: 300px;"></el-input>
          </el-form-item>
        <el-form-item label="联系电话" prop="phone" class="is-required">
          <el-input v-model="editUser.phone" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="车牌号" prop="card" class="is-required">
          <el-input v-model="editUser.card" style="width: 300px;"></el-input>
        </el-form-item>
        <el-form-item label="性别" class="is-required">
          <template>
            <el-radio v-model="editUser.sex" label="男">男</el-radio>
            <el-radio v-model="editUser.sex" label="女">女</el-radio>
          </template>
        </el-form-item>
        <el-form-item label="年龄" prop="age" class="is-required">
          <el-input v-model.number="editUser.age" style="width: 300px;"></el-input>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editClose()">取 消</el-button>
        <el-button type="primary" @click="editCommit('editRules')">确认修改</el-button>
      </span>
    </el-dialog>
    <el-dialog title="新增用户" :visible.sync="addShow" width="500px" center @close="addClose" >
      <!-- 内容主题区域 -->
      <el-form :model="registerUser" status-icon :rules="registerRules" ref="registerRules" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username"  class="is-required">
            <el-input v-model="registerUser.username" placeholder="请输入用户名" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password"  class="is-required">
            <el-input v-model="registerUser.password" placeholder="请输入密码" show-password style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nike"  class="is-required">
            <el-input v-model="registerUser.nike" placeholder="请输入昵称" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex" class="is-required">
            <el-radio v-model="registerUser.sex" label="男">男</el-radio>
            <el-radio v-model="registerUser.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="年龄" prop="age" class="is-required">
            <el-input v-model.number="registerUser.age" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="车牌号"  prop="card" class="is-required">
            <el-input v-model="registerUser.card" placeholder="请输入车牌号" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="联系电话"  prop="phone" class="is-required">
            <el-input v-model="registerUser.phone" placeholder="请输入联系电话" style="width: 300px;"></el-input>
          </el-form-item>
        </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addClose()">取 消</el-button>
        <el-button type="primary" @click="submitForm('registerRules')">确认添加</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data(){
    var checkUsername = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('用户名不能为空'));
        }
        setTimeout(() => {
          axios.get('/api/user/checkUsername?username='+value).then(res=>{
            if(!res.data.data){
              callback(new Error('用户名已存在'));
            }else{
              callback();
            }
          })
        }, 10);
      };
     var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value < 18) {
              callback(new Error('必须年满18岁'));
            } else {
              callback();
            }
          }
        }, 1000);
      };
    return{
      total: 0,
      loading:true,
      queryInfo: {
        username: '',
        nike:'',
        card:'',
        pagenum: 1,
        pageSize: 6,
      },
      users: [],
      editUser:{
        password:'',
        nike:'',
        sex:'',
        age:'',
        phone:'',
        money:'',
        card:'',
      },
      registerUser:{
        sex:'男',
      },
      addShow: false,
      editShow: false,
      editRules: {
        niek: [  { required: true, message: '请输入昵称', trigger: 'blur' }],
        password: [ { required: true, message: '请输入密码', trigger: 'blur' }, {min: 6, max: 15, message: '长度在 6 到 15 个字符',trigger: 'blur' }],
        age: [{ validator: checkAge, trigger: 'blur' }],
        phone: [ { required: true, message: '请输入联系电话', trigger: 'blur' } ],
        card: [ { required: true, message: '请输入车牌号', trigger: 'blur' }]
      },
      registerRules:{
        username: [
          { validator: checkUsername, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 6,
            max: 15,
            message: '长度在 6 到 15 个字符',
            trigger: 'blur',
          },
        ],
        niek: [  { required: true, message: '请输入昵称', trigger: 'blur' }],
        age: [
          { validator: checkAge, trigger: 'blur' }
        ],
        phone: [
           { required: true, message: '请输入联系电话', trigger: 'blur' },
        ],
        card: [
           { required: true, message: '请输入车牌号', trigger: 'blur' },
        ]
      },
    }
  },
  methods:{
    getList(){
      axios.post('/api/user/getUsers',this.queryInfo).then(res=>{
        this.loading = false;
        this.total = res.data.data.total;
        this.users = res.data.data.records;
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
    addUser(){
      for(let item in this.registerUser){
        this.registerUser[item] = '';
      }
      this.registerUser.sex='男';
      this.addShow = true;
    },
    editClose(){
      this.editShow = false;
      this.$nextTick(()=>{
        this.$refs.editRules.resetFields();
      });
    },
    toEdit(user){
      this.editUser.uid = user.uid;
      this.editUser.password = user.password;
      this.editUser.nike = user.nike;
      this.editUser.sex = user.sex;
      this.editUser.age = user.age;
      this.editUser.phone = user.phone;
      this.editUser.money = user.money;
      this.editUser.card = user.card;
      
      this.editShow = true;
    },
    editCommit(formName){
      this.$refs[formName].validate(valid => {
        if(valid){
          //日期进行转换
          // this.editUser.birthday=new Date(this.editUser.birthday).getTime();
          //请求
          axios.post('/api/user/update',this.editUser).then(res=>{
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
    async userDelete(uid) {
      const re = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).catch(err=>err)
      // console.log(re)
      if(re !='confirm'){
        this.$message.info('已取消删除！')
      }else{
        axios.get('/api/user/del?uid='+uid).then(res=>{
          if(res.data.data){
            this.$message.success('删除成功！')
            this.getList();
          }else{
            this.$message.error('删除失败！')
          }
        })
      }
    },
    async rePassword(uid){
      const re = await this.$confirm('重置该用户的密码, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
      }).catch(err=>err)
      // console.log(re)
      if(re !='confirm'){
        this.$message.info('已取消操作！')
      }else{
        axios.get('/api/user/release?uid='+uid).then(res=>{
          if(res.data.data){
            this.$message.success('重置密码成功！')
            this.getList();
          }else{
            this.$message.error('重置密码失败！')
          }
        })
      }
    },
    addClose(){
      this.addShow = false;
      this.$nextTick(()=>{
        this.$refs.registerRules.resetFields();
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          axios.post('/api/user/register',this.registerUser).then(res=>{
            this.loading = false;
            if(res.data.data){
              this.$message.success('添加成功！');
              this.getList();
              this.addShow = false;
            }else{
              this.$message.error('添加失败,请重新尝试！');
            }
          })
        } else {
          // console.log('error submit!!');
          return false;
        }
      });
    },
  },
  mounted(){
    this.getList();
  },
}
</script>