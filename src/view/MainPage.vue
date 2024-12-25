<template>
  <div class="MainPage">
    <!--整体容器样式-->
    <el-container class="container-box" v-bind:class="{ allgrey: isGrey }">
      <!--    顶部通知栏-->
      <el-aside
        :width="isCollapse ? '92px' : '200px'"
        :background-color="color"
        id="menu-box"
        class="menu-box clearfix"
        ><!--    左侧导航栏-->
        <div class="clearfix">
          <!--logo-->
          <div class="logo">
            <el-image
              style="width: 59px; height: 32px; vertical-align: middle"
              :src="require('../assets/image/ai.jpg')"
              fit="fill"
            >
            </el-image>
            <!-- 系统名-->
            <div class="title" id="title" v-if="!isCollapse">
              <h2>多模态内容识别</h2>
            </div>
          </div>
        </div>

        <div>
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            :background-color="color"
            :unique-opened="true"
            :text-color="textColor"
            active-text-color="#60abf6"
            :collapse="isCollapse"
          >
            <template v-for="item in menuList">
              <el-submenu
                v-if="item.children"
                :index="item.path"
                :key="item.path"
              >
                <template slot="title">
                  <i :class="item.icon"></i>
                  <span slot="title">{{ item.name }}</span>
                </template>
                <router-link
                  v-for="(citem, cindex) in item.children"
                  :to="citem.path"
                  :key="cindex"
                >
                  <el-menu-item :index="citem.path">
                    <i :class="citem.icon"></i>
                    <span slot="title">{{ citem.name }}</span>
                  </el-menu-item>
                </router-link>
              </el-submenu>
            </template>
          </el-menu>
        </div>
      </el-aside>

      <!--分区域中间-->
      <el-container class="content-box" style="width: 100%">
        <el-header class="head-box-bar clearfix" style="height: 80px">
          <div class="head-box clearfix">
            <!--显示展开按钮和缩小按钮-->
            <div class="toggle-button" @click="isCollapse = !isCollapse">
              <i
                :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"
                style="font-size: 25px"
              >
              </i>
            </div>
            <div class="breadcrumb" style="height: 50px">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/mainPage' }"
                  >首页</el-breadcrumb-item
                >
                <el-breadcrumb-item
                  v-if="nowViews.length > 0"
                  :to="{ path: nowViews[0].path }"
                  >{{ nowViews[0].title }}
                </el-breadcrumb-item>
                <el-breadcrumb-item v-if="nowViews.length > 0">{{
                  nowViews[0].title
                }}</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <!-- 右侧信息展示-->
            <div class="info">
              <ul>
                <li style="margin-right: -10px">
                  <Calendar style="margin-top: 2px"></Calendar>
                </li>

                <li>
                  <div
                    style="
                      margin-left: 10px;
                      margin-top: 5px;
                      position: relative;
                      z-index: 999;
                    "
                  >
                    <!-- <Weather></Weather> -->
                  </div>
                </li>

                <li>
                  <el-tooltip
                    content="选主题颜色"
                    effect="dark"
                    placement="bottom"
                  >
                    <el-color-picker
                      style="vertical-align: middle"
                      size="mini"
                      v-model="color"
                      @change="changeColor"
                    ></el-color-picker>
                  </el-tooltip>
                </li>
                <li>
                  <el-tooltip content="全屏" effect="dark" placement="bottom">
                    <screenFull id="screenfull" />
                  </el-tooltip>
                </li>
                <li>
                  <el-avatar
                    style="background-color: #ffffff; vertical-align: middle"
                    :size="40"
                    :src="
                      admin.url === ''
                        ? require('../assets/image/avatar.jpg')
                        : admin.url
                    "
                  ></el-avatar>
                </li>
                <!--显示用户信息-->
                <li>
                  <div class="adminUsername">
                    <h2>{{ admin.userName }}</h2>
                  </div>
                </li>
                <!-- 下拉箭头-->
                <li>
                  <el-tooltip
                    content="其他内容"
                    effect="dark"
                    placement="bottom"
                  >
                    <el-dropdown
                      @command="handleCommand"
                      placement="bottom"
                      trigger="click"
                      width="50"
                      popper-class="info-person"
                      style="
                        display: block;
                        height: 50px;
                        width: 15px;
                        line-height: 50px;
                      "
                    >
                      <span
                        class="el-dropdown-link"
                        style="display: block; width: 15px; height: 50px"
                      >
                        <i
                          class="el-icon-caret-bottom"
                          style="cursor: pointer"
                          slot="reference"
                        ></i>
                      </span>

                      <el-dropdown-menu
                        slot="dropdown"
                        style="margin: 0px; padding: 0px"
                      >
                        <el-popover
                          placement="bottom"
                          trigger="click"
                          width="50"
                          popper-class="info-person"
                          ><!--头像下拉框-->
                        </el-popover>
                        <el-dropdown-item
                          command="a"
                          style="margin-bottom: 10px"
                        >
                          <el-button type="information" size="small" round
                            >个人信息
                          </el-button>
                        </el-dropdown-item>
                        <el-dropdown-item command="b">
                          <el-button type="danger" size="small" round
                            >退出登录
                          </el-button>
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </el-tooltip>
                </li>
                <li>
                  <el-tooltip
                    content="界面设置"
                    effect="dark"
                    placement="bottom"
                  >
                    <i class="el-icon-setting" @click="drawer = !drawer"></i>
                  </el-tooltip>
                </li>
              </ul>
            </div>
          </div>
          <TagsView></TagsView>
        </el-header>

        <!--主要显示区域-->
        <el-scrollbar style="height: 100%; overflow-x: hidden">
          <el-main style="width: 100%; height: 100%; overflow-x: hidden">
            <div class="main-box">
              <keep-alive>
                <router-view :key="key" />
              </keep-alive>
              <router-link to="/charts"></router-link>
            </div>
          </el-main>
        </el-scrollbar>
      </el-container>
    </el-container>

    <el-drawer title="界面设置" :visible.sync="drawer" direction="rtl">
      <el-row>
     
        <el-col :span="12">
          灰色模式:
        </el-col>
        <el-col :span="11">  <el-switch v-model="isGrey"> </el-switch></el-col>
      </el-row>
    </el-drawer>
  </div>
</template>

<script>
//去除原本样式
import "../assets/css/reset.css";
//导入基本样式
import "../assets/css/base.css";
//导入全屏工具
import screenFull from "../components/ScreenFull";
//导入预览
import TagsView from "./TagsView/TagsView";
//导入天气
// import Weather from "../components/Weather";
//导入日历
import Calendar from "../components/Calendar";

export default {
  name: "MainPage",
  components: {
    Calendar,
    TagsView,
    screenFull,
  },

  data() {
    return {
      drawer: false,
      isGrey: false,
      //背景颜色以及字体颜色
      color: "#304156",
      textColor: "#fff",
      admin: {
        userName: "admin",
        passWord: "",
        url: "",
      },
      //是否展开或者缩小
      isCollapse: false,
      //  保存菜单List
      menuList: [],
      //  保存已经访问的地址，相当于面包屑
      nowViews: [],
    };
  },
  methods: {
    //改变颜色
    changeColor(val) {
      console.log(val);
      document
        .getElementById("menu-box")
        .setAttribute("style", "background-color:" + val);
      let textColor = this.contrastTextColor(val);
      console.log(textColor);
      document
        .getElementById("title")
        .setAttribute("style", "color:" + textColor);
      this.textColor = textColor;
    },

    contrastTextColor(backgroundHexColor) {
      let hex = backgroundHexColor;
      // 如果当前传入的参数以 # 开头,去除当前的
      if (hex.startsWith("#")) {
        hex = hex.substring(1);
      }
      // 如果当前传入的是 3 位小数值，直接转换为 6 位进行处理
      if (hex.length === 3) {
        hex = [hex[0], hex[0], hex[1], hex[1], hex[2], hex[2]].join("");
      }

      if (hex.length !== 6) {
        throw new Error("Invalid background color." + backgroundHexColor);
      }
      const r = parseInt(hex.slice(0, 2), 16);
      const g = parseInt(hex.slice(2, 4), 16);
      const b = parseInt(hex.slice(4, 6), 16);
      if ([r, g, b].some((x) => Number.isNaN(x))) {
        throw new Error("Invalid background color." + backgroundHexColor);
      }
      const textColor =
        r * 0.299 + g * 0.587 + b * 0.114 > 186 ? "#000" : "#FFF";
      return textColor;
    },

    handleCommand(command) {
      if (command == "a") {
        this.$message.error("个人信息待完成");
      }
      if (command == "b") {
        this.$message.error("退出登录待完成");
      }
    },
  },
  //加载时
  mounted() {
    this.admin = this.$store.state.admin;
    this.nowViews = this.$store.state.tagsView.nowViews;
    this.menuList = this.$store.state.menuList.AllMenuList;
  },
  computed: {
    key() {
      return this.$route.path;
    },
    cachedViews() {
      return this.$store.state.tagsView.cachedViews;
    },
  },
};
</script>

<style scoped>
.MainPage {
  height: 100%;
}

/* 设置整体高度 */
.MainPage .container-box {
  height: 100%;
}

/* ************************************************** */
/* 左侧导航栏 */
.MainPage .container-box .menu-box {
  background: #304156;
  height: 100%;
  transition: width 0.3s linear;
  overflow: auto;
}
/*导航栏*/
.MainPage .container-box .menu-box .el-menu {
  height: 100%;
  border-right: none;
}

.MainPage
  .container-box
  .menu-box
  .el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  height: 100%;
}

/* ******************************************************** */
/* 对Logo设置 */
.MainPage .menu-box .logo {
  margin-left: 20px;
  float: left;
  vertical-align: middle;
  margin-right: 12px;
  line-height: 50px;
  max-height: 50px;
  overflow: hidden;
}

/* 对标题设置 */
.MainPage .menu-box .title {
  /* display: inline-block; */
  color: #fff;
  font-weight: 600;
  line-height: 50px;
  font-size: 14px;
  font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
  vertical-align: middle;
  margin: 0 0 0 10px;
  float: right;
  transition: width 0.3s linear;
}

/* ************************************************** */

/* 顶部最外层设置 */
.MainPage .head-box-bar {
  height: 80px;
  padding: 0;
}

/* 顶部容器设置 */
/*    <!--      阴影凸显--> */
.MainPage .head-box {
  border-bottom: 1px solid #ece9e9;
  min-height: 50px;
  background: #fff;
}

/* ************************************************** */
/* 对面包屑整体设置 */
.MainPage .head-box .breadcrumb {
  float: left;
  text-align: center;
}

.MainPage .head-box .breadcrumb .el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;
  color: #606266;
}

/* ************************************************** */
/* 对右侧信息设置 */
.MainPage .head-box .info {
  float: right;
}

/* 右侧信息的每一个li进行浮动 */
.MainPage .head-box .info li {
  float: left;
  line-height: 50px;
  margin-right: 20px;
  height: 50px;
}

/* 用户名称显示 */
.MainPage .head-box .adminUsername {
  font-weight: bold;
  line-height: 50px;
  font-size: 17px;
}

/* 主要区域 */
/* ************************************************** */
.MainPage .content-box .el-main {
  padding: 0;
}

.MainPage .content-box .main-box {
  height: 100%;
  margin-top: 30px;
  margin-left: 20px;
  position: relative;
}
</style>


// 对侧边栏折叠设置
<style lang="scss">
.el-aside {
  // 去除底部滚动条
  overflow-x: hidden;
  min-height: calc(100vh - 60px);
  z-index: 999;
  // 去除侧边栏滚动条
  scrollbar-width: none;

  &::-webkit-scrollbar {
    display: none;
  }
}

.toggle-button {
  float: left;
  font-size: 20px;
  cursor: pointer;
  text-align: center;
  line-height: 50px;
  padding: 0px 15px;
}

//
.info-person {
  min-width: 80px;
}
.allgrey {
  //filter:progid:DXImageTransform.Microsoft.BasicImage(graysale=1);
  -webkit-filter: grayscale(100%);
}
</style>
