<template>
  <div class="user">
    <!--搜索框-->
    <el-row>
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        label-width="68px"
        style="font-weight: bold"
      >
        <el-row style="margin-bottom: 0px">
          <el-form-item label="图片名称" prop="headline">
            <el-input
              v-model="queryParams.headline"
              placeholder="请输入图片名称关键字"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="时间" prop="saveTime">
            <el-date-picker
              v-model="timespan"
              @change="getTime"
              type="daterange"
              unlink-panels
              clearable
              value-format="yyyy-MM-dd HH:mm:ss"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions"
            >
            </el-date-picker>
          </el-form-item>
        </el-row>
        <el-row style="margin-bottom: 0px">
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="handleRefresh"
            >重置</el-button
          >
        </el-row>
      </el-form>
    </el-row>

    <!--按钮区域-->
    <el-row style="margin-bottom: 10px" :gutter="10">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-popconfirm title="您确定删除吗？" @confirm="handleDelete">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            slot="reference"
            >删除
          </el-button>
        </el-popconfirm>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleCheck"
          >检测
        </el-button>
      </el-col>

      <el-col :span="3" style="float: right">
        <RightToolbar
          :showSearch="true"
          @queryTable="getList"
          @searchTable="handleQuery"
          :columns="columns"
        ></RightToolbar>
      </el-col>
    </el-row>

    <!--新增表单内容-->
    <el-dialog
      v-dialog-drag
      :title="addFormTitle"
      class="presonDialog"
      :visible.sync="addFormVisible"
      :width="addFormTitle === '修改信息' ? '600px' : '600px'"
      :before-close="cancleDialog"
    >
      <div>
        <el-card
          style="padding-top: 20px"
          shadow="hover"
          :style="{ width: isEdit ? '600px' : '600px' }"
        >
          <el-form
            :model="addFormData"
            class="clearfix"
            label-width="110px"
            size="small"
            ref="form"
            style="position: relative"
            :rules="FormRules"
          >
            <el-form-item
              label="图片标题"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="headline"
            >
              <el-input
                clearable
                v-model="addFormData.headline"
                autocomplete="off"
                style="width: 400px"
              ></el-input>
            </el-form-item>

            <el-form-item
              label="保存时间"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="saveTime"
            >
              <el-date-picker
                clearable
                v-model="addFormData.saveTime"
                type="datetime"
                placeholder="选择保存时间"
                align="right"
                :picker-options="pickerOptions"
                style="width: 400px"
                autocomplete="off"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-form-item>

            <el-form-item
              label="识别大模型"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="recognitionPercentage"
            >
              <el-input
                v-if="
                  addFormData.recognitionPercentage &&
                  addFormData.recognitionPercentage !== ''
                "
                clearable
                v-model="addFormData.recognitionPercentage"
                :disabled="isEdit"
                autocomplete="off"
                style="width: 400px"
              />
              <el-input
                v-else
                clearable
                v-model="addFormData.recognitionPercentage"
                :disabled="true"
                autocomplete="off"
                style="width: 400px"
                :placeholder="'待检测'"
              />
            </el-form-item>
            <el-form-item
              v-if="
                isEdit ||
                (this.addFormData.newUrl != null &&
                  this.addFormData.newUrl != '')
              "
              label="原图片文件"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="oldurl"
              :key="addFormData.newUrl"
            >
              <el-image
                style="width: 360px; vertical-align: middle"
                :src="`${$serverUrl}/photo/${this.addFormData.newUrl}`"
                fit="fill"
                :preview-src-list="[
                  `${$serverUrl}/photo/${this.addFormData.newUrl}`,
                ]"
              >
                <div slot="error" class="image-slot">
                  <i
                    class="el-icon-picture-outline"
                    style="font-size: 100px"
                  ></i>
                </div>
              </el-image>
            </el-form-item>

            <el-form-item
              label="上传图片文件"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="path"
            >
              <photo-upload
                @upload-success="handleUploadSuccess"
              ></photo-upload>
            </el-form-item>

            <el-form-item :style="{ 'margin-left': isEdit ? '30px' : '50px' }">
              <el-button
                type="primary"
                @click="isEdit ? updatePhoto() : addPhoto()"
                >保 存</el-button
              >
              <el-button @click="cancleDialog">取消</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </el-dialog>

    <el-table
      v-loading="loading"
      header-cell-class-name="cell-class-name"
      highlight-current-row
      @selection-change="handleSelectionChange"
      :data="tableData"
      :expand-row-keys="expands"
      row-key="id"
      border
      style="width: 100%"
    >
      <template slot="empty">
        <el-empty :image-size="100" description="暂无数据"></el-empty>
      </template>

      <!-- 扩展图片列 -->
      <el-table-column width="0" type="expand">
        <template slot-scope="scope">
          <div class="content-expand">
            <ul>
              <el-row>
                <li class="header">
                  <el-col :span="8" type="flex" justify="center">
                    <span
                      align="center"
                      style="margin-left: 20px; display: block"
                      class="pro-name"
                      ><el-tag style="font-size: 16px">图片描述</el-tag
                      ><span style="margin-left: 20px">{{
                        scope.row.headline
                      }}</span></span
                    >
                  </el-col>
                  <el-col :span="3">
                    <div class="pro-name" align="center">
                      <el-tag style="font-size: 16px">保存时间</el-tag>
                    </div>
                  </el-col>
                  <el-col :span="3">
                    <div class="pro-name">
                      <i class="el-icon-time"></i>
                      <span>{{ scope.row.saveTime }}</span
                      >:
                    </div>
                  </el-col>

                  <el-col :span="6">
                    <div class="pro-name" align="center">
                      <el-tag style="font-size: 16px">识别大模型</el-tag>
                      <span style="margin-left: 5px">
                        <el-tag
                          v-if="scope.row.recognitionPercentage === null"
                          type="danger"
                          >待检测</el-tag
                        >
                        <el-tag v-else type="success">
                          {{ scope.row.recognitionPercentage }}</el-tag
                        >
                      </span>
                    </div>
                  </el-col>
                </li>
              </el-row>

              <el-divider>
                <div
                  v-if="scope.row.path != null"
                  style="color: #67c23a; font-size: 16px"
                >
                  <i class="el-icon-caret-bottom">图片展示</i>
                </div>
                <div
                  v-if="scope.row.path === null"
                  style="color: #f56c6c; font-size: 16px"
                >
                  <i class="el-icon-error" style="font-color: #f56c6c"
                    >图片加载失败，请检查图片文件是否存在!</i
                  >
                </div>
              </el-divider>
            </ul>
            <div style="text-align: center">
              <el-row style="width: 80%; display: inline-block">
                <el-col :span="4"
                  ><div style="width: 10px; height: 10px"></div
                ></el-col>
                <el-col :span="16">
                  <div class="pro-name">
                    <el-image
                      style="width: 100%; vertical-align: middle"
                      :src="`${$serverUrl}/photo/${scope.row.path}`"
                      fit="fill"
                      :preview-src-list="[
                        `${$serverUrl}/photo/${scope.row.path}`,
                      ]"
                      @error="handlePhotoError(scope.row)"
                    >
                      <div slot="error" class="image-slot">
                        <i
                          class="el-icon-picture-outline"
                          style="font-size: 100px"
                        ></i>
                      </div>
                    </el-image>
                  </div>
                </el-col>
                <el-col :span="4"
                  ><div style="width: 10px; height: 10px"></div
                ></el-col>
              </el-row>
            </div>
          </div>
        </template>
      </el-table-column>

      <!--    表格各列-->
      <el-table-column type="selection" width="55"> </el-table-column>

      <el-table-column
        label="图片标题"
        key="headline"
        prop="headline"
        v-if="columns[0].visible"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <i class="el-icon-user"></i>
          <span style="margin-left: 5px">{{ scope.row.headline }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="保存时间"
        key="saveTime"
        prop="saveTime"
        v-if="columns[1].visible"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 5px">{{ scope.row.saveTime }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="识别大模型"
        key="recognitionPercentage"
        prop="recognitionPercentage"
        v-if="columns[2].visible"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <i class="el-icon-s-flag"></i>
          <span style="margin-left: 5px">
            <el-tag
              v-if="scope.row.recognitionPercentage === null"
              type="danger"
              >待检测</el-tag
            >
            <el-tag v-else type="success">
              {{ scope.row.recognitionPercentage }}</el-tag
            >
          </span>
        </template>
      </el-table-column>

      <!--操作方法-->
      <el-table-column width="370px" fixed="right" label="操作">
        <!--      行尾菜单栏-->
        <template slot-scope="scope">
          <el-button
            slot="reference"
            size="mini"
            type="success"
            icon="el-icon-caret-bottom"
            style="margin-left: 0px"
            @click="expandRow(scope.row)"
            >展开
          </el-button>

          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="handleEdit(scope.$index, scope.row)"
            >编辑
          </el-button>
          <!--删除提示框-->
          <el-popconfirm
            title="您确定删除吗？"
            @confirm="handleDelete(scope.$index, scope.row)"
          >
            <el-button
              slot="reference"
              size="mini"
              type="danger"
              icon="el-icon-delete"
              >删除
            </el-button>
          </el-popconfirm>

          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleCheck"
            >检测
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--    分页-->
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentPageChange"
        :current-page="queryParams.pageNow"
        :page-sizes="[2, 5, 10, 15, 20]"
        :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalRow"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import RightToolbar from "../../components/RightToolbar.vue";
//导入上传组件
import UploadExcel from "../../components/UploadExcel.vue";

//导入图片上传组件
import PhotoUpload from "../../components/UploadPhoto.vue";

export default {
  components: {
    RightToolbar,
    UploadExcel,
    PhotoUpload,
  },
  name: "vidio",
  data() {
    return {
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      //多选区域内容
      multipleSelection: [],
      loading: false, //是否加载遮罩
      addFormTitle: "",

      addFormVisible: false, //新增表单是否可见
      timespan: "", //时间跨度，数组含两个变量，为yyyy-mm-dd格式
      //表格数据
      tableData: [],
      //新增表单
      addFormData: {
        id: "",
        headline: "", //headline
        saveTime: "", //saveTime
        recognitionPercentage: "", //识别大模型
        path: "",
        newUrl: "", //上传的图片临时地址
      },
      isEdit: false,
      // 列信息
      columns: [
        { key: 0, label: `文本内容`, visible: true },
        { key: 1, label: `保存时间`, visible: true },
        { key: 2, label: `识别大模型`, visible: true },
      ],
      //  查询信息
      queryParams: {
        pageNow: 1, //当前页
        pageSize: 10, //页面含量
        headline: "", //地址
        startTime: "", //上限时间
        endTime: "", //下限时间
      },
      //总记录
      totalRow: 0,
      //expands表格扩展
      expands: [],
      pickerOptions: {
        //时间栏选择附加函数
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      //  表单验证
      FormRules: {
        headline: [
          { required: true, message: "请输入语音标题", trigger: "blur" },
        ],
        saveTime: [
          { required: true, message: "请填写保存时间", trigger: "blur" },
        ],
        path: [{ required: true, message: "请上传图片文件", trigger: "blur" }],
      },
    };
  },
  methods: {
    // 扩展table行
    expandRow(row) {
      if (this.expands.indexOf(row.id) < 0) {
        // 如果当前没有该扩展列，expands添加该列，扩展
        this.expands = [];
        this.expands.push(row.id);
      } else {
        // 如果当前已经有该扩展列，expands清空，收回
        this.expands = [];
      }
    },

    //取消新增数据
    cancleDialog(done) {
      console.log(this.addFormData);
      this.$confirm("关闭后不会保存刚才的数据,确定离开吗？")
        .then((_) => {
          if (
            this.addFormData.newUrl != "" &&
            this.addFormData.newUrl != null &&
            this.addFormData.newUrl !== this.addFormData.path
          ) {
            this.$http
              .delete("/photo/delete", {
                params: { fileName: this.addFormData.newUrl },
              })
              .then((res) => {
                console.log(res);
              });
          }

          this.addFormVisible = false;

          this.$refs.form.resetFields();
          this.addFormData = {
            id: "",
            headline: "", //headline
            saveTime: "", //saveTime
            recognitionPercentage: "", //识别大模型
            path: "",
          };
          done();
        })
        .catch((_) => {});
    },
    //新增数据
    handleAdd() {
      this.addFormData = {
        id: "",
        headline: "", //headline
        saveTime: "", //saveTime
        recognitionPercentage: "", //识别大模型
        path: "",
      };
      this.addFormTitle = "新增图片信息";
      this.addFormVisible = true;
      this.isEdit = false;
    },
    //修改信息
    handleUpdate() {
      this.addFormTitle = "修改图片信息";
      this.addFormData = Object.assign({}, this.multipleSelection[0]);
      this.addFormData.newUrl = this.addFormData.path;
      this.isEdit = true;
      this.addFormVisible = true;
    },
    //编辑功能
    handleEdit(index, row) {
      this.addFormTitle = "修改信息";
      this.addFormData = Object.assign({}, row);
      this.addFormData.newUrl = this.addFormData.path;
      this.addFormVisible = true;
      this.isEdit = true;
    },
    //获得数据
    async getList() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 500);
      // 假设 this.queryParams 包含 content, path, startTime, endTime, pageNow, pageSize
      let params = this.queryParams;

      // 过滤掉空参数，只传递有值的参数
      let filteredParams = {};
      for (let key in params) {
        if (params[key]) {
          // 仅传递有值的参数
          filteredParams[key] = params[key];
        }
      }
      // 将参数拼接到 URL 中
      let query = Object.keys(filteredParams)
        .map(
          (key) =>
            encodeURIComponent(key) +
            "=" +
            encodeURIComponent(filteredParams[key])
        )
        .join("&");

      await this.$http
        .get(`/pictures/search?${query}`)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.tableData = res.data.data.records;
            this.totalRow = res.data.data.total; // 获取总条数
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((err) => {
          console.log(err);
          this.$router.push("/error");
        });
    },

    //搜索
    handleQuery() {
      this.getList();
    },

    //多选的时候使得图标可见
    handleSelectionChange(val) {
      this.multipleSelection = val;
      let size = this.multipleSelection.length;
      this.single = size !== 1;
      this.multiple = size === 0;
    },

    //每页条数改变时触发 选择一页显示多少行
    handleSizeChange(val) {
      this.queryParams.pageNow = 1;
      this.queryParams.pageSize = val;
      this.getList();
    },
    //当前页改变时触发 跳转其他页
    handleCurrentPageChange(val) {
      this.queryParams.pageNow = val;
      this.getList();
    },

    //重置筛选条件
    handleRefresh() {
      this.$refs.queryForm.resetFields();
      this.timespan = "";

      this.queryParams.startTime = null;
      this.queryParams.endTime = null;
      this.getList();
    },

    //具体提交方法，去访问后端
    async addPhoto() {
      console.log("新增");
      console.log(this.addFormData);
      if (
        this.addFormData.newUrl != undefined &&
        this.addFormData.newUrl != ""
      ) {
        this.addFormData.path = this.addFormData.newUrl;
      }

      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过，进行保存操作
          console.log("提交的数据：", this.addFormData);

          const pictureData = {
            headline: this.addFormData.headline,
            saveTime: this.addFormData.saveTime,
            recognitionPercentage: this.addFormData.recognitionPercentage,
            path: this.addFormData.path,
          };

          // 执行保存逻辑
          this.$http
            .post("/pictures/add", pictureData)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success(res.data.message);
                this.addFormVisible = false;
                this.getList();
              } else {
                this.$message.error(
                  this.addFormData.role.username + "" + res.data.message
                );
              }
            })
            .catch((res) => {
              console.log(res);
              this.$router.push("/error");
            });
        } else {
          console.log("表单验证失败");
          return false;
        }
      });
    },

    //具体修改，去访问后端
    async updatePhoto() {
      console.log("修改");
      console.log(this.addFormData);

      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过，进行保存操作
          console.log("提交的数据：", this.addFormData);
          //删除旧的
          this.$http
            .delete("/photo/delete", {
              params: { fileName: this.addFormData.path },
            })
            .then((res) => {
              console.log(res);
            });

          this.addFormData.path = this.addFormData.newUrl;

          // 执行保存逻辑
          this.$http
            .put("pictures/update/" + this.addFormData.id, this.addFormData)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success(res.data.message);
                this.addFormVisible = false;
                this.getList();
              } else {
                this.$message.error(res.data.message);
              }
            })
            .catch((res) => {
              console.log(res);
              this.$router.push("/error");
            });
        } else {
          console.log("表单验证失败");
          return false;
        }
      });
    },

    //删除某一行
    handleDelete(index, row) {
      //调用后端删除接口
      //刷新
      if (row === undefined) {
        for (let item of this.multipleSelection) {
          this.Delete(item);
        }
      } else {
        this.Delete(row);
      }
    },
    //删除某一行
    async Delete( row) {
      console.log(row);

      //删除旧的
          this.$http
            .delete("/photo/delete", {
              params: { fileName: this.addFormData.path },
            })
            .then((res) => {
              console.log(res);
            });
            
      await this.$http
        .delete("/pictures/delete/"+row.id)
        .then((res) => {
          if (res.data.code === 200) {
            this.$message.success(res.data.message);
            this.getList();
          } else {
            this.$message.error(row.role.username + "" + res.data.message);
          }
        })
        .catch((res) => {
          console.log(res);
          this.$router.push("/error");
        });
    },

    //赋值时间
    getTime() {
      this.queryParams.endTime = this.timespan[1];
      this.queryParams.startTime = this.timespan[0];
    },
    handlePhotoError(row) {
      // 更新 path 为 null，触发视图重新渲染
      this.$set(row, "path", null);
    },

    //上传成功修改原本函数
    // 处理上传成功的事件
    handleUploadSuccess(uploadedUrl) {
      this.addFormData.newUrl = uploadedUrl; // 设置上传完成后的图片地址
      this.$nextTick(() => {
        this.$forceUpdate(); // 强制 Vue 更新视图
      });
    },
    //进行检测
    handleCheck() {
      this.$message.info("待完成！");
    },
  },
  mounted() {
    this.getList();
  },
};
</script>

<style scoped>
.user /deep/ .el-table .el-table__cell {
  padding: 15px 0;
}

.user.el-table thead {
  color: #909399;
}

.el-table td.el-table__cell,
.el-table th.el-table__cell.is-leaf {
  border-bottom: 1px solid #dfe6ec;
}

/*设置表头*/
.user /deep/ .cell-class-name {
  font-weight: bold;
  color: #515a6e;
  background-color: #f8f8f9;
  height: 40px;
  font-size: 13px;
}

/*设置分页*/
.block {
  margin-top: 20px;
  text-align: center;
}

.user .el-row {
  margin-bottom: 20px;
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

.presonDialog /deep/ .el-dialog .el-dialog__body {
  padding: 0px;
}

/*搜索表格设置*/
.searchForm .div-inline {
  display: inline-block;
}

.searchForm .button-search {
  display: flex;
  justify-content: space-between;
}
/************************* */

.content-expand {
  background-color: #f0f8ff; /* 淡蓝色背景 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加轻微阴影效果 */
  transition: transform 3s ease, box-shadow 3s ease; /* 动画过渡效果 */
}

.pro-name {
  display: block;
  font-size: 16px;
  line-height: 32px;
}
</style>
