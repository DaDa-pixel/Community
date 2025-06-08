<template>
  <el-header class="fater-header">
    <div class="fater-header-logo">
      <i class="el-icon-s-platform"></i>
      <span>校园社团管理系统</span>
    </div>
    <el-dropdown @command="handleUser" class="fater-header-user">
			<span class="el-dropdown-link">
        <el-avatar :size="36" :src="avatarUrl"></el-avatar>
        <span class="username">{{ userName }}</span>
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="info" icon="el-icon-user">个人信息</el-dropdown-item>
        <el-dropdown-item command="pwd" icon="el-icon-lock">修改密码</el-dropdown-item>
        <el-dropdown-item divided command="exit" icon="el-icon-switch-button">退出系统</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <el-dialog title="修改信息" width="700px" :append-to-body="true" :visible.sync="showUpdInfoFlag" custom-class="custom-dialog">
      <el-form label-width="100px" :model="userForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户账号">
              <el-input v-model="userForm.userName"
                        placeholder="请输入用户账号" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户密码">
              <el-input v-model="userForm.passWord" type="password"
                        placeholder="请输入用户密码" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户姓名">
              <el-input v-model="userForm.name"
                        placeholder="请输入用户姓名" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户年龄">
              <el-input v-model="userForm.age"
                        placeholder="请输入用户年龄" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-radio-group v-model="userForm.gender">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input v-model="userForm.phone"
                        placeholder="请输入联系电话" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="联系地址">
          <el-input rows="3" type="textarea" v-model="userForm.address"
                    placeholder="请输入联系地址" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showUpdInfoFlag = false">取 消</el-button>
        <el-button type="primary" @click="updInfo()">保 存</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改密码" width="500px" :append-to-body="true" :visible.sync="showUpdPwdFlag" custom-class="custom-dialog">
      <el-form label-width="100px" ref="userPwd" :model="userPwd" :rules="userPwdFormRules">
        <el-form-item label="原始密码" prop="oldPwd">
          <el-input type="password" v-model="userPwd.oldPwd" placeholder="请输入原始密码" autocomplete="off">
            <i slot="prefix" class="el-icon-lock"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPwd">
          <el-input type="password" v-model="userPwd.newPwd" placeholder="请输入新密码" autocomplete="off">
            <i slot="prefix" class="el-icon-key"></i>
          </el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="rePwd">
          <el-input type="password" v-model="userPwd.rePwd" placeholder="请再次输入新密码" autocomplete="off">
            <i slot="prefix" class="el-icon-key"></i>
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showUpdPwdFlag = false">取 消</el-button>
        <el-button type="primary" @click="updPwd('userPwd')">确 定</el-button>
      </div>
    </el-dialog>
  </el-header>
</template>

<script>
import {
  exit,
  getLoginUser,
  checkUserPwd,
  updLoginUserInfo,
  updLoginUserPwd,
} from "../api";

export default {
  data() {
    var checkOldPwd = async (rule, value, callback) => {
      if (value) {
        await checkUserPwd(this.$store.state.token, value).then((resp) => {
          if (resp.code != 0) {
            callback(new Error("原始密码输入错误"));
          }
        });
      } else {
        callback(new Error("原始密码必须输入"));
      }
      callback();
    };
    var checkNewPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error("修改密码必须输入"));
      }

      callback();
    };
    var checkRePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error("确认密码必须输入"));
      }

      if (value != this.userPwd.newPwd) {
        callback(new Error("两次输入密码不一致"));
      }

      callback();
    };
    return {
      showUpdInfoFlag: false,
      showUpdPwdFlag: false,
      userPwd: {
        oldPwd: "",
        newPwd: "",
        rePwd: "",
      },
      userPwdFormRules: {
        oldPwd: [{
          validator: checkOldPwd,
          trigger: "blur",
        }, ],
        newPwd: [{
          validator: checkNewPwd,
          trigger: "blur",
        }, ],
        rePwd: [{
          validator: checkRePwd,
          trigger: "blur",
        }, ],
      },
      userForm: {
        id: "",
        userName: "",
        passWord: "",
        name: "",
        gender: "",
        age: "",
        phone: "",
        address: "",
      },
      avatarUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      userName: "用户"
    };
  },
  methods: {
    handleUser(comm) {
      if (comm == "info") {
        this.showUpdInfoWin();
      }

      if (comm == "pwd") {
        this.showUpdPwdWin();
      }

      if (comm == "exit") {
        this.$confirm("确认要退出系统吗？", "系统提示", {
          confirmButtonText: "确认",
          cancelButtonText: "取消",
          type: "warning",
          customClass: "custom-confirm"
        }).then(() => {
          exit(this.$store.state.token).then(() => {
            this.$store.commit("clearToken");
            this.$store.commit("clearMenus");
            sessionStorage.clear();
            this.$router.push("/");
          });
        });
      }
    },
    initUserPwd() {
      this.userPwd = {
        oldPwd: "",
        newPwd: "",
        rePwd: "",
      };
    },
    initUserForm() {
      this.userForm = {
        id: "",
        userName: "",
      };
    },
    showUpdInfoWin() {
      getLoginUser(this.$store.state.token).then((resp) => {
        this.initUserForm();
        this.userForm = resp.data;
        this.userName = resp.data.name || "用户";
        this.showUpdInfoFlag = true;
      });
    },
    showUpdPwdWin() {
      this.initUserPwd();
      this.showUpdPwdFlag = true;
    },
    updInfo() {
      this.userForm.token = this.$store.state.token;
      updLoginUserInfo(this.userForm).then((resp) => {
        this.$message({
          message: resp.msg,
          type: "success",
          customClass: "custom-message"
        });

        this.showUpdInfoFlag = false;
        this.initUserForm();
      });
    },
    updPwd(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updLoginUserPwd(this.$store.state.token, this.userPwd.newPwd).then((resp) => {
            this.$message({
              message: resp.msg,
              type: "success",
              customClass: "custom-message"
            });

            this.showUpdPwdFlag = false;
            this.initUserPwd();
          });
        } else {
          return false;
        }
      });
    },
  },
  mounted() {
    // 初始化时获取用户信息
    getLoginUser(this.$store.state.token).then((resp) => {
      this.userName = resp.data.name || "用户";
    });
  }
};
</script>

<style scoped>
.fater-header {
  background: linear-gradient(135deg, #0ba237, #0066cc);
  color: white;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.fater-header-logo {
  font-size: 20px;
  font-weight: bold;
  display: flex;
  align-items: center;
}

.fater-header-logo i {
  font-size: 24px;
  margin-right: 10px;
}

.fater-header-user {
  cursor: pointer;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  color: white;
}

.username {
  margin: 0 10px;
  font-size: 14px;
}

.el-icon-arrow-down {
  font-size: 12px;
}

/* 头像悬停效果 */
.el-avatar {
  transition: all 0.3s;
}

.el-avatar:hover {
  transform: scale(1.1);
}

/* 下拉菜单样式 */
/deep/ .el-dropdown-menu {
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border: none;
}

/deep/ .el-dropdown-menu__item {
  padding: 0 20px;
  height: 36px;
  line-height: 36px;
  font-size: 13px;
}

/deep/ .el-dropdown-menu__item i {
  margin-right: 8px;
}

/* 对话框样式 */
.custom-dialog {
  border-radius: 8px;
}

.custom-dialog /deep/ .el-dialog__header {
  background: linear-gradient(135deg, #1a8cff, #0066cc);
  padding: 15px 20px;
  border-radius: 8px 8px 0 0;
}

.custom-dialog /deep/ .el-dialog__title {
  color: white;
  font-size: 16px;
}

.custom-dialog /deep/ .el-dialog__headerbtn .el-dialog__close {
  color: white;
}

.custom-dialog /deep/ .el-dialog__body {
  padding: 20px 30px;
}

.custom-dialog /deep/ .el-form-item__label {
  color: #606266;
  font-weight: normal;
}

.custom-dialog /deep/ .el-input__inner {
  border-radius: 4px;
}

.custom-dialog /deep/ .el-textarea__inner {
  border-radius: 4px;
  min-height: 80px !important;
}

/* 消息提示样式 */
.custom-message {
  min-width: 180px;
  padding: 15px;
  border-radius: 4px;
}

/* 确认框样式 */
.custom-confirm {
  border-radius: 8px;
}

.custom-confirm /deep/ .el-message-box__header {
  background: linear-gradient(135deg, #1a8cff, #0066cc);
  padding: 15px 20px;
  border-radius: 8px 8px 0 0;
}

.custom-confirm /deep/ .el-message-box__title {
  color: white;
}

.custom-confirm /deep/ .el-message-box__headerbtn .el-message-box__close {
  color: white;
}

.custom-confirm /deep/ .el-message-box__content {
  padding: 20px;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .fater-header {
    padding: 0 15px;
  }

  .fater-header-logo span {
    display: none;
  }

  .username {
    display: none;
  }

  .el-icon-arrow-down {
    display: none;
  }
}
</style>