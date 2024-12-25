<template>
  <div class="test2">
    <el-upload
      class="avatar-uploader el-upload--text"
      :drag="Plus"
      :action="`${$serverUrl}/api/uploadVoice3`"
      multiple
      :show-file-list="false"
      :on-success="handlevoiceSuccess"
      :before-upload="beforeUploadvoice"
      :on-progress="uploadvoiceProcess"
    >
      <i v-if="Plus" class="el-icon-upload"></i>
      <div v-if="Plus" class="el-upload__text">
        将语音文件拖到此处，或<em>点击上传</em>
      </div>
      <el-progress
        v-if="voiceFlag == true"
        type="circle"
        :percentage="voiceUploadPercent"
        style="margin-top: 30px"
      ></el-progress>
      <div class="el-upload__tip" slot="tip">只能上传mp3，且不超过30M</div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "uploadvoice",
  data() {
    return {
      voiceForm: {
        voiceName: "",
      },
      voiceFlag: false,
      Plus: true,
      voiceUploadPercent: 0,
    };
  },
  mounted: function () {},
  methods: {
    // 语音上传前执行
    beforeUploadvoice(file) {
        console.log(file);
      let isLt300M = file.size / 1024 / 1024 < 300;
      if (["audio/mpeg"].indexOf(file.type) === -1) {
        this.$message.error("请上传正确的语音格式");
        return false;
      }
      if (!isLt300M) {
        this.$message.error("上传语音文件大小不能超过30MB哦!");
        return false;
      }
    },
    // 语音上传过程中执行
    uploadvoiceProcess(event, file, fileList) {
      this.Plus = false;
      this.voiceFlag = true;
      this.voiceUploadPercent = parseFloat(file.percentage.toFixed(0));
    },
    // 语音上传成功是执行
    handlevoiceSuccess(res, file) {
      this.Plus = false;
      this.voiceUploadPercent = 100;
      console.log(res);
      // 如果为200代表语音保存成功
      if (res.resCode === "200") {
        // 接收语音传回来的名称和保存地址
        // 至于怎么使用看你啦~
        this.voiceForm.voiceName = res.newVoiceName;
        // 触发父组件的事件，传递语音名称
        this.$emit("upload-success", res.newVoiceName); // 发送上传的语音文件名
        this.Plus = true;
        this.voiceFlag = false;
        this.voiceUploadPercent = 0;
        this.$message.success("语音文件上传成功！");
        
      } else {
        this.$message.error("语音上传失败，请重新上传！");
      }
    },
  },
};
</script>
