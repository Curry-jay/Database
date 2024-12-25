<template>
  <div class="top-right-btn" :style="style">
    <el-row>
      <el-tooltip class="item" effect="dark" content="搜索" placement="top" v-if="search">
        <el-button size="medium" circle icon="el-icon-search" @click="toggleSearch()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <el-button size="medium" circle icon="el-icon-refresh" @click="refresh()" />
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="显隐列" placement="top" v-if="columns">
        <el-button size="medium" circle icon="el-icon-menu" @click="showColumn()" />
      </el-tooltip>
    </el-row>
    <el-dialog v-dialog-drag :title="title" :visible.sync="open" append-to-body width="700px">
      <el-transfer
        :titles="['显示', '隐藏']"
        v-model="value"
        :data="columns"
        :button-texts="['显示', '隐藏']"
        filterable
        @change="dataChange"
      ></el-transfer>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "RightToolbar" ,
  data() {
    return {
      // 显隐数据
      value: [],
      // 弹出层标题
      title: "显示/隐藏",
      // 是否显示弹出层
      open: false,
    };
  },
  props: {
    showSearch: {
      type: Boolean,
      default: true,
    },
    columns: {
      type: Array,
    },
    search: {
      type: Boolean,
      default: true,
    },
    gutter: {
      type: Number,
      default: 10,
    },
  },
  computed: {
    style() {
      const ret = {};
      if (this.gutter) {
        ret.marginRight = `${this.gutter / 2}px`;
      }
      return ret;
    }
  },
  created() {
    // 显隐列初始默认隐藏列
    for (let item in this.columns) {

      if (this.columns[item].visible === false) {
        this.value.push(parseInt(item));
      }
    }
  },
  methods: {
    // 搜索
    toggleSearch() {
      this.$emit("searchTable");
    },
    // 刷新
    refresh() {

      this.$emit("queryTable");
    },
    // 右侧列表元素变化
    dataChange(data) {
      for (let item in this.columns) {
        const key = this.columns[item].key;
        this.columns[item].visible = !data.includes(key);
      }
    },
    // 打开显隐列dialog
    showColumn() {
      this.open = true;
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-transfer__button {
  border-radius: 50%;
  padding: 12px;
  display: block;
  margin-left: 0px;
}
::v-deep .el-transfer__button:first-child {
  margin-bottom: 10px;
}

/*****************************************************************/
/*对弹出框容器设置*/
 /deep/ .el-dialog__header {
  background-color: #424244;
  font-size: 18px;
  color: #f8c883;
  padding: 0 16px 0 32px;
  line-height: 50px;
  height: 50px;
}

/*对弹出框标题设置*/
 /deep/ .el-dialog__title {
  color: #f8c883;
}


/deep/ .el-dialog .el-dialog__body {
  padding-top: 20px;
  padding-bottom: 20px;
}

</style>
