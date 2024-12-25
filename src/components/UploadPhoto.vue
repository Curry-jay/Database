<template>
  <div class="test2">
    <el-upload
      class="avatar-uploader el-upload--text"
      :drag="Plus"
      :action="`${$serverUrl}/api/uploadPhoto3`"
      multiple
      :show-file-list="false"
      :on-success="handlePhotoSuccess"
      :before-upload="beforeUploadPhoto"
      :on-progress="uploadPhotoProcess"
    >
      <i v-if="Plus" class="el-icon-upload"></i>
      <div v-if="Plus" class="el-upload__text">
        将图片文件拖到此处，或<em>点击上传</em>
      </div>
      <el-progress
        v-if="PhotoFlag == true"
        type="circle"
        :percentage="PhotoUploadPercent"
        style="margin-top: 30px"
      ></el-progress>
      <div class="el-upload__tip" slot="tip">只能上传mp3，且不超过30M</div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "uploadPhoto",
  data() {
    return {
      PhotoForm: {
        PhotoName: "",
      },
      PhotoFlag: false,
      Plus: true,
      PhotoUploadPercent: 0,
    };
  },
  mounted: function () {},
  methods: {
    // 图片上传前执行
    beforeUploadPhoto(file) {
      console.log(file);
      let isLt300M = file.size / 1024 / 1024 < 300;
      if (
        [
          "image/jpeg",
          "image/png",
          "image/gif",
          "image/bmp",
          "image/webp",
        ].indexOf(file.type) === -1
      ) {
        this.$message.error("请上传正确的图片格式");
        return false;
      }
      if (!isLt300M) {
        this.$message.error("上传图片文件大小不能超过30MB哦!");
        return false;
      }
    },
    // 图片上传过程中执行
    uploadPhotoProcess(event, file, fileList) {
      this.Plus = false;
      this.PhotoFlag = true;
      this.PhotoUploadPercent = parseFloat(file.percentage.toFixed(0));
    },
    // 图片上传成功是执行
    handlePhotoSuccess(res, file) {
      this.Plus = false;
      this.PhotoUploadPercent = 100;
      console.log(res);
      // 如果为200代表图片保存成功
      if (res.resCode === "200") {
        // 接收图片传回来的名称和保存地址
        // 至于怎么使用看你啦~
        this.PhotoForm.PhotoName = res.newPhotoName;
        // 触发父组件的事件，传递图片名称
        this.$emit("upload-success", res.newPhotoName); // 发送上传的图片文件名
        this.Plus = true;
        this.PhotoFlag = false;
        this.PhotoUploadPercent = 0;
        this.$message.success("图片文件上传成功！");
      } else {
        this.$message.error("图片上传失败，请重新上传！");
      }
    },
  },
};
</script>
