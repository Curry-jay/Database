<template>
  <div class="charts">
    <el-backtop :bottom="100">
      <div
        style="
           {
            height: 100%;
            width: 100%;
            background-color: #f2f5f6;
            box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
            text-align: center;
            line-height: 40px;
            color: #1989fa;
          }
        "
      ></div>
    </el-backtop>
    <br />

    <div style="margin-left: 30px; margin-right: 30px">
      <el-row style="width: 100%" type="flex" justify="center">
        <el-card shadow="hover" style="width: 100%">
          <span style="font-size: 30px; margin-left: 5%; margin-top: 40px">
            {{ this.$store.state.admin.userName }}
          </span>
          <br />
          <span style="font-size: 30px; margin-left: 15%; margin-top: 40px">
            欢迎使用多模态内容识别系统！
          </span>
        </el-card>
      </el-row>

      <el-row style="width: 100%" type="flex" justify="center">
        <el-card shadow="hover" style="width: 100%">
          <el-carousel :interval="4000" type="card" height="200px">
            <el-carousel-item v-for="(item, index) in imgList" :key="index">
              <el-image :src="item.image" fit="fill"></el-image>
            </el-carousel-item>
          </el-carousel>
        </el-card>
      </el-row>
    </div>
    <!-- ******************************************************************************************* -->
    <div style="margin-left: 30px; margin-right: 30px">
      <el-row style="width: 100%; background: white" justify="center">
        <el-divider content-position="center">
          <i class="iconfont icon-statistic"></i>
          <span style="font-size: 20px; margin-left: 10px">统计数据</span>
        </el-divider>
      </el-row>
    </div>
    <!-- ******************************************************************************************* -->
    <div style="margin-left: 30px; margin-right: 30px">
      <el-row style="width: 100%" type="flex" justify="center">
        <el-col>
          <el-card shadow="hover">
            <el-statistic group-separator="," :value="5" title="文本数量">
              <template slot="prefix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
              <template slot="suffix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
            </el-statistic>
          </el-card>
        </el-col>

        <el-col>
          <el-card shadow="hover">
            <el-statistic group-separator="," :value="4" title="图片数量">
              <template slot="prefix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
              <template slot="suffix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
            </el-statistic>
          </el-card>
        </el-col>

        <el-col>
          <el-card shadow="hover">
            <el-statistic group-separator="," :value="5" title="语音数量">
              <template slot="prefix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
              <template slot="suffix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
            </el-statistic>
          </el-card>
        </el-col>

        <el-col>
          <el-card shadow="hover">
            <el-statistic group-separator="," :value="4" title="视频数量">
              <template slot="prefix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
              <template slot="suffix">
                <i class="el-icon-s-flag" style="color: green"></i>
              </template>
            </el-statistic>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <!-- ****************************************下面详细统计******************************** -->
    <div style="margin-left: 30px; margin-right: 30px">
      <el-row style="width: 100%" type="flex" justify="center">
        <el-col>
          <el-card shadow="hover">
            <el-divider content-position="center"
              ><i class="iconfont icon-desktop-rank"></i>
              <span style="font-size: 20px; margin-left: 10px"
                >数量排行榜</span
              ></el-divider
            >
              <EChartBar :chartData="chartData" :categories="categories" />
          </el-card>
        </el-col>
        <!-- ************************************************************************ -->
        <el-col>
          <el-card shadow="hover">
            <el-divider content-position="center"
              ><i class="iconfont icon-desktop-rank"></i>
              <span style="font-size: 20px; margin-left: 10px"
                >数量排行榜</span
              ></el-divider
            >
            <EChartBar2  :chartData="chartData" :categories="categories"/>
          </el-card>
        </el-col>
      </el-row>
      
    </div>
  </div>
</template>

<script>
import EChartBar from "./Charts/EChartBar.vue";
import EChartBar2 from "./Charts/EChartBar2.vue";
export default {
  components: {
    EChartBar,EChartBar2
  },
  data() {
    return {
      testurl:
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      imgList: [
        { image: require("../assets/image/1.jpg") },
        { image: require("../assets/image/2.jpg") },
        { image: require("../assets/image/3.jpg") },
        { image: require("../assets/image/4.jpg") },
        { image: require("../assets/image/5.jpg") },
      ],
      firstNum: 123123123,
      firstTitle: "第一个统计数据",

      // 第一个表格卡片
      categories: ["文本数据", "图片数据", "语音数据", "视频数据"],
      chartData: [
        {
          name: "检测的",
          type: "bar",
          stack: "total",
          label: { show: true },
          emphasis: { focus: "series" },
          data: [3, 3, 4, 4],
        },
        {
          name: "未检测的",
          type: "bar",
          stack: "total",
          label: { show: true },
          emphasis: { focus: "series" },
          data: [2, 1, 1, 1],
        },
      ],
      // 第二个表格卡片

      // 第三个表格卡片

      // 第四个表格卡片
    };
  },
};
</script>

<style scoped>
.charts {
  background: #f6f7f9;
  margin-top: -30px;
  margin-left: -40px;
}
.desktop-icon {
  width: 100px;
  height: 100px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
