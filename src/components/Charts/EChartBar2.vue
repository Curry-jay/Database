<template>
  <div ref="chartContainer" style="width: 100%; height: 400px"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "EChartBar2",
  props: {
    chartData: {
      type: Array,
      required: true,
    },
    categories: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      myChart: null,
      option: null,
    };
  },
  methods: {
    initChart() {
      // 初始化图表实例
      const chartDom = this.$refs.chartContainer;
      this.myChart = echarts.init(chartDom);

      this.option = {
        tooltip: {
          trigger: "item",
        },
        legend: {
          top: "5%",
          left: "center",
        },
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: "#fff",
              borderWidth: 2,
            },
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: [
              { value: 1, name: "AICG" },
              { value: 1, name: "人工写的" },
            ],
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表
      this.myChart.setOption(this.option);
    },
  },
  watch: {
    chartData: "initChart",
    categories: "initChart",
  },
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    if (this.myChart) {
      this.myChart.dispose(); // 销毁图表实例
    }
  },
};
</script>

<style scoped>
/* 可以根据需要添加样式 */
</style>
