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
          <el-form-item label="手机号码" prop="phone">
            <el-input
              v-model="queryParams.phone"
              placeholder="请输入手机号码关键字"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="地址" prop="address">
            <el-input
              v-model="queryParams.address"
              placeholder="请输入地址关键字"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input
              v-model="queryParams.email"
              placeholder="请输入邮箱关键字"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="姓名" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入姓名关键字"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="时间" prop="time">
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
          icon="el-icon-upload2"
          size="mini"
          @click="dialogUploadEXcelVisable = true"
          >导入
        </el-button>
      </el-col>
      <el-dialog
        v-dialog-drag
        class="dialogUploadEXcel"
        title="导入文件"
        :visible.sync="dialogUploadEXcelVisable"
        :close-on-click-modal="false"
      >
        <UploadExcel :on-success="success" />
      </el-dialog>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          >导出
        </el-button>
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
            label-width="100px"
            size="small"
            ref="form"
            style="position: relative"
            :rules="FormRules"
          >
            <el-form-item
              label="文本内容"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="text"
            >
              <el-input
                clearable
                v-model="addFormData.text"
                autocomplete="off"
                style="width: 400px"
                type="textarea"
                ref="textareaInput"
                @input="adjustHeight"
              ></el-input>
            </el-form-item>

            <el-form-item
              label="保存时间"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="time"
            >
              <el-date-picker
                clearable
                v-model="addFormData.time"
                type="datetime"
                placeholder="选择保存时间"
                align="right"
                :picker-options="pickerOptions"
                style="width: 400px"
                autocomplete="off"
              >
              </el-date-picker>
            </el-form-item>

            <el-form-item
              label="识别大模型"
              :style="{ 'margin-left': isEdit ? '30px' : '30px' }"
              prop="identify"
            >
              <el-input
                v-if="addFormData.identify && addFormData.identify !== ''"
                clearable
                v-model="addFormData.identify"
                :disabled="isEdit"
                autocomplete="off"
                style="width: 400px"
              />
              <el-input
                v-else
                clearable
                v-model="addFormData.identify"
                :disabled="true"
                autocomplete="off"
                style="width: 400px"
                :placeholder="'待检测'"
              />
            </el-form-item>

            <el-form-item :style="{ 'margin-left': isEdit ? '30px' : '50px' }">
              <el-button
                type="primary"
                @click="isEdit ? updateText() : addText()"
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

      <!-- 扩展商品列 -->
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
                      ><el-tag style="font-size: 16px">文本内容</el-tag></span
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
                      <span>{{ scope.row.time }}</span
                      >:
                    </div>
                  </el-col>

                  <el-col :span="6">
                    <div class="pro-name" align="center">
                      <el-tag style="font-size: 16px">识别大模型</el-tag>
                      <span style="margin-left: 5px">
                        <el-tag v-if="scope.row.identify === null" type="danger"
                          >待检测</el-tag
                        >
                        <el-tag v-else type="success">
                          {{ scope.row.identify }}</el-tag
                        >
                      </span>
                    </div>
                  </el-col>
                </li>
              </el-row>
              <!-- 我的订单表头END -->
              <!-- 订单列表 -->
              <el-divider
                ><i class="el-icon-caret-bottom">文本内容</i></el-divider
              >
              <el-row>
                <li>
                  <el-col :span="2"
                    ><div style="width: 10px; height: 10px"></div
                  ></el-col>
                  <el-col :span="20">
                    <div
                      class="pro-name"
                      style="word-spacing: 2ch; /* 设置单词间的间距为2个字符 */"
                    >
                      <span>{{ scope.row.text }}</span>
                    </div>
                  </el-col>
                  <el-col :span="2"
                    ><div style="width: 10px; height: 10px"></div
                  ></el-col>
                </li>
              </el-row>
            </ul>
          </div>
        </template>
      </el-table-column>

      <!--    表格各列-->
      <el-table-column type="selection" width="55"> </el-table-column>

      <el-table-column
        label="文本内容"
        key="text"
        prop="text"
        v-if="columns[0].visible"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <i class="el-icon-user"></i>
          <span style="margin-left: 5px">{{ scope.row.text }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="保存时间"
        key="time"
        prop="time"
        v-if="columns[1].visible"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 5px">{{ scope.row.time }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="识别大模型"
        key="identify"
        prop="identify"
        v-if="columns[2].visible"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <i class="el-icon-s-flag"></i>
          <span style="margin-left: 5px">
            <el-tag v-if="scope.row.identify === null" type="danger"
              >待检测</el-tag
            >
            <el-tag v-else type="success"> {{ scope.row.identify }}</el-tag>
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

export default {
  components: { RightToolbar, UploadExcel },
  name: "textContext",
  data() {
    return {
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //是否显示导入框
      dialogUploadEXcelVisable: false,

      //多选区域内容
      multipleSelection: [],

      loading: false, //是否加载遮罩
      addFormTitle: "",
      isEdit: false,
      addFormVisible: false, //新增表单是否可见
      timespan: "", //时间跨度，数组含两个变量，为yyyy-mm-dd格式
      //表格数据
      tableData: [],
      //新增表单
      addFormData: {
        text: "",
        time: "",
        identify: "",
      },

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
        address: "", //地址
        email: "", //email
        phone: "", //电话
        name: "", //姓名
        idCard: "", //身份证
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
        text: [{ required: true, message: "请输入视频标题", trigger: "blur" }],
        time: [{ required: true, message: "请填写保存时间", trigger: "blur" }],
        url: [{ required: true, message: "请上传视频", trigger: "blur" }],
      },
    };
  },
  methods: {
    // 扩展table行
    expandRow(row) {
      console.log(row);
      if (this.expands.indexOf(row.id) < 0) {
        // 如果当前没有该扩展列，expands添加该列，扩展
        this.expands = [];
        this.expands.push(row.id);
      } else {
        // 如果当前已经有该扩展列，expands清空，收回
        this.expands = [];
      }
    },

    /** 导出按钮操作 */
    async handleExport() {
      await this.$http
        .post("/support/AllCustomerByPage", {
          pageNow: 1,
          pageSize: this.totalRow,
        })
        .then((res) => {
          if (res.data.code === 200) {
            this.exportData(res.data.data.rowData);
          } else {
            this.$message.error(res.data.message);
          }
        })
        .catch((res) => {
          this.$router.push("/error");
        });
    },
    //取消新增数据
    cancleDialog(done) {
      console.log(this.addFormData);
      this.$confirm("关闭后不会保存刚才的数据,确定离开吗？")
        .then((_) => {
          this.addFormVisible = false;
          this.$refs.form.resetFields();
          this.addFormData = {
            text: "",
            time: "",
            identify: "",
          };
          done();
        })
        .catch((_) => {});
    },
    //新增数据
    handleAdd() {
      this.addFormData = {
        text: "",
        time: "",
        identify: "",
      };
      this.addFormTitle = "新增文本内容";
      this.addFormVisible = true;
    },
    //修改信息
    handleUpdate() {
      this.addFormTitle = "修改信息";
      this.addFormData = Object.assign({}, this.multipleSelection[0]);

      this.addFormVisible = true;
    },
    //编辑功能
    handleEdit(index, row) {
      this.addFormTitle = "修改信息";
      this.addFormData = Object.assign({}, row);

      this.addFormVisible = true;
    },

    //获得数据
    async getList() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
      }, 500);

      this.tableData = this.$store.state.textList;

      //   await this.$http.post(
      //     "/support/AllCustomerByPage",
      //     this.queryParams
      //   ).then(res => {
      // console.log(res)
      //     if (res.data.code === 200) {
      //       console.log(res.data.data)
      //       this.tableData = res.data.data.rowData
      //       this.totalRow = res.data.data.totalRows
      //     } else {
      //       this.$message.error(res.data.message);
      //     }
      //   }).catch(res => {
      //     console.log(res);
      //     this.$router.push("/error")
      //   })
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
    async addText() {
      console.log("新增");
      console.log(this.addFormData);
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过，进行保存操作
          console.log("提交的数据：", this.addFormData);
          // 执行保存逻辑
        } else {
          console.log("表单验证失败");
          return false;
        }
      });

      // await this.$http
      //   .post("/support/insertCustomer", this.addFormData)
      //   .then((res) => {
      //     if (res.data.code === 200) {
      //       this.$message.success(res.data.message);
      //       this.addFormVisible = false;
      //       // this.$refs.person.beforeRemove();
      //       this.getList();
      //     } else {
      //       this.$message.error(
      //         this.addFormData.role.username + "" + res.data.message
      //       );
      //     }
      //   })
      //   .catch((res) => {
      //     console.log(res);
      //     this.$router.push("/error");
      //   });
    },

    //具体修改，去访问后端
    async updateText() {
      console.log("修改");
      console.log(this.addFormData);

      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过，进行保存操作
          console.log("提交的数据：", this.addFormData);
          // 执行保存逻辑
        } else {
          console.log("表单验证失败");
          return false;
        }
      });

      // await this.$http
      //   .post("/support/updateCustomer", this.addFormData)
      //   .then((res) => {
      //     if (res.data.code === 200) {
      //       this.$message.success(res.data.message);
      //       this.addFormVisible = false;
      //       // this.$refs.person.beforeRemove();
      //       this.getList();
      //     } else {
      //       this.$message.error(res.data.message);
      //     }
      //   })
      //   .catch((res) => {
      //     console.log(res);
      //     this.$router.push("/error");
      //   });
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
    async Delete(index, row) {
      await this.$http
        .post("/support/deleteCustomer", row)
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
    //进行检测
    handleCheck() {
      this.$message.info("待完成！");
    },
    //------------------------------------------------------------------------------------------------------------------
    // 要使用这个组件，可以传入beforeUpload和onSuccess这两个函数
    // beforeUpload要注意要return一个布尔值
    // onSuccess这里面可以拿到表格的header(表头)和results(表格内容)
    // 在我们自己的onSuccess函数里，可以拼接出我们需要的结构的数据
    async success({ header, results }) {
      // 拿到的数据是如下这种，我们要将它转化成我们请求接口中所需要的数据
      console.log(header);
      // ["手机号", "姓名", "入职日期", "转正日期", "工号", ...]
      console.log(results);
      // [{入职日期: 'xxx', 姓名: 'xxx', 工号: 'xxx', ...}]
      // 1.定义我们需要的表头的映射关系（表头的字段其实就是我们接口中所需要的参数）
      //导出文件使用
      let requireFileds = ["用户名", "地址", "邮箱", "电话", "姓名", "身份证"];
      let userRelations = {
        用户名: "username",
        地址: "address",
        邮箱: "email",
        电话: "phone",
        姓名: "name",
        身份证: "idCard",
      };
      //定义需要的数据

      for (let i of requireFileds) {
        if (!header.includes(i)) {
          this.$message.error("上传的数据少" + i + "列数据");
          return;
        }
      }
      try {
        // 2.进行数据转换，实现业务需求
        let newArr = results.map((item, index) => {
          let number = 0;
          const userInfo = {
            role: {},
          };
          Object.keys(item).forEach((k) => {
            number++;

            if (userRelations[k] === "username") {
              userInfo.role[userRelations[k]] = item[k];
            } else {
              // key是当前的中文名 找到对应的英文名
              userInfo[userRelations[k]] = item[k];
            }
          });
          //判断是否有空数据，抛出异常
          if (number < requireFileds.length) {
            throw "上传的数据少第" + (index + 1) + "行数据不完整";
          }
          return userInfo;
        });
        let that = this;
        new Promise((resolve, reject) => {
          for (let obj of newArr) {
            that.addFormData = obj;
            that.addUser();
          }
          resolve();
        })
          .then((res) => {
            that.$message.success("导入成功");
          })
          .catch((res) => {
            console.log(res);
            that.$message.error("导入失败，数据不完整");
          });
      } catch (res) {
        console.log(res);
        this.$message.error(res);
      }
      this.dialogUploadEXcelVisable = false;
    },
    //  -------------------------------------------------------------
    // 导出数据
    exportData(rows) {
      // 1.定义表头对应关系
      const headers = {
        用户名: "username",
        地址: "address",
        邮箱: "email",
        电话: "phone",
        姓名: "name",
        身份证: "idCard",
        头像: "url",
      };
      // 懒加载
      import("../../assets/js/Export2Excel").then(async (excel) => {
        // excel是引入文件
        // 获取所有的数据-----------应该是后端方法
        // const {rows} = await getEmployeeList({page: 1, size: this.page.total})

        let data = rows.map((item) => {
          return Object.keys(headers).map((k) => {
            if (headers[k] === "username") {
              return item.role[headers[k]];
            }
            return item[headers[k]];
          });
        });
        // 返回的data就是我们转化后的数据
        excel.export_json_to_excel({
          // 要求转出的表头是中文
          header: Object.keys(headers), // 表头 必填
          data, // //具体数据 必填
          filename: `user_${new Date().getTime()}`, // 非必填
          autoWidth: true, // 非必填
          bookType: "xlsx", // 非必填
        });
        // 拿到的数据是这种
        //  [{ username: 'xxx', mobile: 'xxx', ... }, { username: 'xxx', mobile: 'xxx', ... }]
        // 要转化的是如下这种
        // header: ['姓名', '手机号', '入职日期', ...]
        // data: [['张三', '138xxxxxxxx', '1992-08-04', ...], ['李四', '135xxxxxxxx', '1992-08-04', ...]]
      });
    },
    // 动态调整textarea的高度
    adjustHeight() {
      this.$nextTick(() => {
        const textarea = this.$refs.textareaInput.$el.querySelector("textarea");

        if (!textarea) return;

        // 设置文本框的高度为内容的scrollHeight，限制最大最小高度
        textarea.style.height = "auto"; // 先重置高度
        const scrollHeight = textarea.scrollHeight;

        // 最大高度为300px，最小高度为60px
        textarea.style.height =
          Math.min(Math.max(scrollHeight, 60), 300) + "px";
      });
    },
  },
  watch: {
    // 监听文本框内容的变化，确保初次加载时正确调整高度
    "addFormData.text": "adjustHeight",
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
