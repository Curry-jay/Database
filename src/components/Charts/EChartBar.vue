<template>
  <div ref="chartContainer" style="width: 100%; height: 400px;"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'EChartBar',
  props: {
    chartData: {
      type: Array,
      required: true
    },
    categories: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      myChart: null,
      option: null
    };
  },
  methods: {
    initChart() {
      // 初始化图表实例
      const chartDom = this.$refs.chartContainer;
      this.myChart = echarts.init(chartDom);

      // 设置图表的配置项
      this.option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: this.categories // 从父组件传递进来的类别
        },
        series: this.chartData // 从父组件传递进来的数据
      };

      // 使用刚指定的配置项和数据显示图表
      this.myChart.setOption(this.option);
    }
  },
  watch: {
    chartData: 'initChart',
    categories: 'initChart'
  },
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    if (this.myChart) {
      this.myChart.dispose(); // 销毁图表实例
    }
  }
};
</script>

<style scoped>
/* 可以根据需要添加样式 */
</style>
