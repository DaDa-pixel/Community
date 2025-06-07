<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-left">
        <h1>校园社团管理系统</h1>
        <p>连接学生、社团和管理员的高效管理平台，提供活动组织、成员管理、资源分配等全方位解决方案。</p>

        <div class="features">
          <div class="feature">
            <h3><i class="el-icon-user-solid"></i> 学生用户</h3>
            <p>浏览社团活动、申请加入社团、管理个人日程、查看通知公告</p>
          </div>
          <div class="feature">
            <h3><i class="el-icon-s-flag"></i> 社长用户</h3>
            <p>管理社团成员、创建社团活动、审批入社申请、发布社团公告</p>
          </div>
          <div class="feature">
            <h3><i class="el-icon-s-custom"></i> 管理员</h3>
            <p>管理系统用户、审核社团申请、管理平台资源、查看系统数据</p>
          </div>
          <div class="feature">
            <h3><i class="el-icon-s-data"></i> 数据分析</h3>
            <p>提供社团活动数据统计、成员参与度分析、资源使用情况报告</p>
          </div>
        </div>
      </div>

      <div class="login-body">
        <div class="login-title">
          用户登录
        </div>

        <div class="role-selector">
          <div
              :class="['role-btn', activeRole === 'student' ? 'active' : '']"
              @click="activeRole = 'student'">
            <i class="el-icon-user"></i>
            学生
          </div>
          <div
              :class="['role-btn', activeRole === 'leader' ? 'active' : '']"
              @click="activeRole = 'leader'">
            <i class="el-icon-s-flag"></i>
            社长
          </div>
          <div
              :class="['role-btn', activeRole === 'admin' ? 'active' : '']"
              @click="activeRole = 'admin'">
            <i class="el-icon-s-custom"></i>
            管理员
          </div>
        </div>

        <div class="login-form">
          <el-form :model="loginForm" :rules="rules" ref="loginForm">
            <el-form-item prop="userName">
              <el-input
                  type="text"
                  v-model="loginForm.userName"
                  prefix-icon="el-icon-user"
                  placeholder="请输入您的账号">
              </el-input>
            </el-form-item>
            <el-form-item prop="passWord">
              <el-input
                  type="password"
                  v-model="loginForm.passWord"
                  prefix-icon="el-icon-lock"
                  placeholder="请输入您的密码"
                  show-password>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                  style="margin-top: 15px; width: 100%;"
                  @click="submitForm('loginForm')"
                  type="primary">
                立即登录
              </el-button>
              <el-link
                  style="text-align:center;width: 100%;"
                  @click="showAddWin()"
                  :underline="false"
                  type="info">
                没有账号？注册一个
              </el-link>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>

    <el-dialog title="用户注册" width="700px" :modal="false" :visible.sync="showAddFlag">
      <el-form label-width="90px" :model="usersForm">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户账号">
              <el-input v-model="usersForm.userName"
                        placeholder="请输入用户账号…" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户密码">
              <el-input v-model="usersForm.passWord" type="password"
                        placeholder="请输入用户密码…" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户姓名">
              <el-input v-model="usersForm.name"
                        placeholder="请输入用户姓名…" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户年龄">
              <el-input v-model="usersForm.age"
                        placeholder="请输入用户年龄…" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-radio-group v-model="usersForm.gender">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input v-model="usersForm.phone"
                        placeholder="请输入联系电话…" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="联系地址">
          <el-input rows="4" type="textarea" v-model="usersForm.address"
                    placeholder="请输入联系地址…" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showAddFlag = false">取 消</el-button>
        <el-button type="primary" @click="addInfo()">确 定</el-button>
      </div>
    </el-dialog>

    <div class="footer-links">
      <a href="#">关于我们</a>
      <a href="#">帮助中心</a>
      <a href="#">隐私政策</a>
      <a href="#">服务条款</a>
    </div>
    <div class="copyright">
      © 2023 校园社团管理系统 版权所有
    </div>
  </div>
</template>

<script>
import initMenu from "../utils/menus.js";
import { login, addUsers } from '../api/index.js'

export default {
  data() {
    return {
      activeRole: 'student', // 默认选中学生角色
      showAddFlag: false,
      usersForm: {
        id: "",
        userName: "",
        passWord: "",
        name: "",
        gender: "",
        age: "",
        phone: "",
        address: "",
        type: 2,
        status: 1
      },
      loginForm: {
        userName: '',
        passWord: '',
        role: 'student' // 默认学生角色
      },
      rules: {
        userName: [{
          required: true,
          message: '用户账号必须输入',
          trigger: 'blur'
        }],
        passWord: [{
          required: true,
          message: '用户密码必须输入',
          trigger: 'blur'
        }],
      }
    }
  },
  watch: {
    activeRole(newVal) {
      this.loginForm.role = newVal;
      // 可以根据不同角色设置不同的placeholder提示
      switch(newVal) {
        case 'student':
          this.loginForm.userName = '';
          this.loginForm.passWord = '';
          break;
        case 'leader':
          this.loginForm.userName = '';
          this.loginForm.passWord = '';
          break;
        case 'admin':
          this.loginForm.userName = '';
          this.loginForm.passWord = '';
          break;
      }
    }
  },
  methods: {
    showAddWin() {
      this.showAddFlag = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 根据选择的角色设置不同的type
          let postData = {
            ...this.loginForm,
            type: this.getRoleType(this.activeRole)
          };

          login(postData).then(res => {
            this.$store.commit('setToken', res.data);
            sessionStorage.setItem("token", res.data);
            initMenu(this.$router, this.$store);
            this.$router.push('/index');
          }).catch(err => {
            this.$message.error('登录失败: ' + (err.message || '未知错误'));
          });

        } else {
          return false;
        }
      });
    },
    getRoleType(role) {
      // 根据角色返回对应的type值
      switch(role) {
        case 'student': return 1;
        case 'leader': return 2;
        case 'admin': return 3;
        default: return 1;
      }
    },
    addInfo() {
      // 注册时默认设置为学生角色
      this.usersForm.type = 1;

      addUsers(this.usersForm).then(resp => {
        if(resp.code == 0) {
          this.$confirm('注册成功, 立即登陆?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.loginForm.userName = this.usersForm.userName;
            this.loginForm.passWord = this.usersForm.passWord;
            this.submitForm('loginForm');
          }).catch(() => {
            this.showAddFlag = false;
          });
        } else {
          this.$message({
            message: resp.msg,
            type: 'warning'
          });
        }
      }).catch(err => {
        this.$message.error('注册失败: ' + (err.message || '未知错误'));
      });
    }
  }
}
</script>

<style scoped>
.login-page {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", Arial, sans-serif;
  background: linear-gradient(135deg, #1a8cff, #00cccc);
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  position: relative;
}

.login-container {
  position: relative;
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  z-index: 1;
}

.login-left {
  flex: 1;
  color: white;
  padding: 40px;
  max-width: 600px;
}

.login-left h1 {
  font-size: 2.5rem;
  margin-bottom: 20px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.login-left p {
  font-size: 1.1rem;
  line-height: 1.8;
  margin-bottom: 30px;
  max-width: 500px;
}

.features {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-top: 30px;
}

.feature {
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  padding: 20px;
  width: calc(50% - 10px);
  transition: all 0.3s ease;
}

.feature:hover {
  transform: translateY(-5px);
  background: rgba(255, 255, 255, 0.25);
}

.feature h3 {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1.1rem;
}

.feature h3 i {
  color: #00ffcc;
}

.feature p {
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.9);
}

.login-body {
  background: white;
  border-radius: 20px;
  box-shadow: 0 15px 50px rgba(0, 0, 0, 0.2);
  padding: 40px;
  width: 450px;
  position: relative;
  overflow: hidden;
}

.login-body::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 5px;
  background: linear-gradient(to right, #00b3b3, #009999, #006666);
}

.login-title {
  text-align: center;
  font-size: 28px;
  font-weight: bold;
  color: #008080;
  margin-bottom: 30px;
  position: relative;
}

.login-title::after {
  content: '';
  display: block;
  width: 80px;
  height: 4px;
  background: linear-gradient(to right, #00cccc, #009999);
  border-radius: 2px;
  margin: 10px auto;
}

.role-selector {
  display: flex;
  justify-content: space-between;
  margin-bottom: 25px;
}

.role-btn {
  flex: 1;
  margin: 0 5px;
  border-radius: 10px;
  padding: 12px 5px;
  text-align: center;
  background: #f5f7fa;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid #e4e7ed;
  font-weight: 500;
}

.role-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.role-btn.active {
  background: linear-gradient(to bottom, #e6f7ff, #b3e6ff);
  border-color: #80dfff;
  color: #008080;
  box-shadow: 0 5px 15px rgba(0, 153, 153, 0.2);
}

.role-btn i {
  display: block;
  font-size: 24px;
  margin-bottom: 8px;
}

.login-form {
  margin-top: 20px;
}

.footer-links {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  z-index: 1;
}

.footer-links a {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
}

.footer-links a:hover {
  color: white;
  text-decoration: underline;
}

.copyright {
  text-align: center;
  color: rgba(255, 255, 255, 0.7);
  margin-top: 10px;
  font-size: 13px;
  z-index: 1;
}

@media (max-width: 992px) {
  .login-left {
    display: none;
  }

  .login-body {
    width: 90%;
    max-width: 450px;
  }
}

/* 覆盖Element UI样式 */
.el-form-item {
  margin-bottom: 25px;
}

.el-input__inner {
  height: 46px;
  border-radius: 10px;
  padding-left: 15px;
}

.el-button {
  height: 46px;
  border-radius: 10px;
  font-size: 16px;
  font-weight: bold;
  letter-spacing: 1px;
  transition: all 0.3s ease;
  background: linear-gradient(to right, #00b3b3, #008080);
  border: none;
}

.el-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 7px 15px rgba(0, 153, 153, 0.4);
}

.el-link {
  display: block;
  text-align: center;
  margin-top: 15px;
  color: #008080 !important;
  font-weight: 500;
}

.el-link:hover {
  color: #00cccc !important;
}

/* 对话框样式 */
.el-dialog {
  border-radius: 15px;
  overflow: hidden;
}

.el-dialog__header {
  background: linear-gradient(to right, #00b3b3, #008080);
  padding: 15px 20px;
}

.el-dialog__title {
  color: white !important;
  font-weight: bold;
}
</style>