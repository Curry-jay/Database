<template>
  <div class="Login">
    <div class="connect-box">
      <div class="connect clearfix">
        <!--Transition 进入动画 left指从左边进入-->
        <Transition
          appear
          enter-active-class="animate__animated animate__bounceInLeft"
        >
          <el-col :span="12">
            <!--  左边栏   -->
            <div class="connect-left">
              <!--  logo及标题-->
              <el-row>
                <el-col :span="3">
                  <div
                    style="width: 143px; height: 80px"
                  >
                  </div>
                </el-col>
              </el-row>
              <!--显示中间的大部分-->
              <el-row>
                <div class="connect-left-main">
                  <el-image
                    style="width: 300px; height: 300px"
                    :src="require('../assets/svg/login-box-bg.svg')"
                    fit="fill"
                  >
                  </el-image>
                </div>
              </el-row>
              <!--显示下面的标题语句-->
              <el-row>
                <div class="title">
                  <!--            登录页面宣传语-->
                  <span>生成式人工智能多模态内容识别系统</span>
                </div>
              </el-row>
            </div>
          </el-col>
        </Transition>
        <!--  进入动画      -->
        <Transition
          appear
          enter-active-class="animate__animated animate__bounceInRight"
        >
          <el-col :span="12">
            <!--  右边栏   -->
            <div class="connect-right clearfix">
              <el-container>
                <el-header style="height: 116px">
                  <div class="title">
                    <span> 登录 </span>
                  </div>
                  <div class="welcome">
                    <span>欢迎来到生成式人工智能多模态内容识别系统</span>
                  </div>
                </el-header>

                <el-main>
                  <!--      登录表单-->
                  <el-row class="from">
                    <el-form :model="loginForm" :rules="rules" ref="loginForm">
                      <!-- 用户名框-->
                      <el-row>
                        <el-form-item prop="username">
                          <el-input
                            prefix-icon="el-icon-user-solid"
                            type="text"
                            style="height: 30px; width: 400px; font-size: 16px"
                            v-model="loginForm.username"
                            placeholder="账号"
                            clearable
                          >
                          </el-input>
                        </el-form-item>
                      </el-row>

                      <el-row>
                        <!--密码框-->
                        <el-form-item prop="password">
                          <el-input
                            prefix-icon="el-icon-lock"
                            type="text"
                            style="height: 30px; width: 400px; font-size: 16px"
                            v-model="loginForm.password"
                            placeholder="密码"
                            show-password
                          >
                          </el-input>
                        </el-form-item>
                      </el-row>
                    </el-form>
                  </el-row>
                </el-main>

                <el-footer>
                  <!--按钮组-->

                  <el-row>
                    <el-button
                      @click="submitForm('ruleForm')"
                      size="medium"
                      type="primary"
                      style="min-width: 210px"
                      round
                    >
                      登录
                    </el-button>
                    <el-button
                      @click="resetForm('ruleForm')"
                      size="medium"
                      type="primary"
                      style="min-width: 210px; background: #909399"
                      round
                      >重置
                    </el-button>
                  </el-row>
                </el-footer>
              </el-container>
            </div>
          </el-col>
        </Transition>
      </div>
    </div>
  </div>
</template>

<script>
//去除原本样式
import "../assets/css/reset.css";
//导入基本样式
import "../assets/css/base.css";


export default {
  name: "Login",
  data() {
    return {
      //验证码是否有效
      getValidateCode: false,
      //登录表单
      loginForm: {
        username: "name",
        password: "password",
        role:1
      },
      //表单验证规则
      rules: {
        //用户名验证规则
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 1,
            max: 10,
            message: "长度在 1 到 10 个字符",
            trigger: "blur",
          },
        ],
        //密码验证规则
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  methods: {
    //改变验证变量
    setValidateCode(val) {
      this.$refs.isValidate = val;
    },
    //提交表单
  async  submitForm() {
    await this.$refs.loginForm.validate((valid) => {
        if (valid) {
        
            //  this.$http(
            //   {
            //     method:'post',
            //     url: "/sso/api/auth/login",
            //     data:this.loginForm,
            //     headers:{'Content-Type':"application/x-www-form-urlencoded"}
            //   }
            // ).then(res => {
            //   console.log(res.data);
            //   if (res.data.code === 200) {
            //     localStorage.setItem("token",res.data.data.token);
                this.$message.success("登陆成功！");
            //     this.$store.state.isLogin = true;
                this.$router.push("/mainPage");
            //   } else {
            //     this.$message.error(res.data.message);
            //   }
            // }).catch(res => {
            //   console.log(res)
            //   // this.$router.push("/error")
            // })
         
        } else {
          this.$message.error("请先确保所有信息都填写！");
          return false;
        }
      });
    },
    //重置表单
    resetForm() {
      this.$refs.loginForm.resetFields();
    },
  },
  mounted() {},
  // components: { Recha },
};
</script>

<style scoped>
/* 外围容器进行设置底色 */
.Login .connect-box {
  width: 100%;
  height: 100vh;
  background: url("../assets/image/login_background.jpg") center center fixed
    no-repeat;
  background-size: cover;
  text-align: center;
  vertical-align: middle;
  position: relative;
}

/* 设置外围容器 */
.Login .connect {
  width: 50%;
  min-width: 1000px;
  height: 50%;
  min-height: 500px;
  border-radius: 20px;
  margin: auto;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

/* ******************************************* */
/* 左边栏 */
.Login .connect-left {
  border-radius: 20px;
  background: #409eff;
  min-height: 500px;
}

/* 去除每一行下面的外边框 */
.Login .connect-left .el-row {
  margin-bottom: 0px;
}

/* 将中间部分居中 */
.Login .connect-left .connect-left-main {
  margin: 0 auto;
  width: 100%;
}

/* 对于下面的标语设置 */
.Login .connect-left .title {
  font-weight: bold;
  color: #ffffff;
  font-size: 25px;
  font-family: "PingFang SC Semibold",serif;
}

/* ******************************************* */
/* 右边栏 */
.Login .connect-right {
  border-radius: 20px;
  background: #f2f6fc;
  min-height: 500px;
}

/* 右边栏上面的登录标题 */
.Login .connect-right .title {
  margin-top: 40px;
  font-weight: 500;
  font-size: 24px;
  color: rgb(24, 24, 24);
}

/* 下面的欢迎样式 */
.Login .connect-right .welcome {
  font-size: 16px;
  color: rgb(179, 179, 179);
  padding-top: 20px;
}

/* 对表格里面图标设置 */
.Login .connect-right .from /deep/ span i {
  font-size: 20px;
}

.Login .connect-right .from /deep/ .el-form-item__error {
  font-size: 14px;
  margin-left: 30px;
}

/* --------------------------------------------------- */
.row {
  height: 15%;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  border-radius: 4px;
}
</style>
