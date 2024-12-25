package com.example.webback.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class testVoice {
    @Resource
    NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    // 获取项目根目录路径
    String projectRootPath = System.getProperty("user.dir");
    String SavePath = projectRootPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "voice";

    /**
     * @description: 返回视频播放
     * @author: curry
     * @date: 2024/12/7 16:41
     * @param:
     * @return:
     **/
    @GetMapping("/voice/{voiceName}")
    public void videoPreview(@PathVariable("voiceName") String voiceName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(voiceName);
        //我把音频1.mp4放在了static下的voice文件夹里面
        String realPath = SavePath + "/" + voiceName;  // 根据传入的音频名称构建路径

        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            // 视频不存在时，返回 JSON 错误响应
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            // 构建 JSON 响应，提示视频文件不存在
            PrintWriter writer = response.getWriter();
            writer.write("{\"error\": \"Video not found: " + voiceName + "\"}");
            writer.flush();
        }
    }

    /**
     * @description: 上传视频的接口
     * @author: curry
     * @date: 2024/12/7 16:42
     * @param:
     * @return:
     **/
    @PostMapping(value = "/api/uploadVoice3")
    @ResponseBody
    public Map<String, String> savaVideotest(@RequestParam("file") MultipartFile file)
            throws IllegalStateException {
        Map<String, String> resultMap = new HashMap<>();
        try {
            //获取文件后缀，因此此后端代码可接收一切文件，上传格式前端限定
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            // 重构文件名称
            // 根据传入的视频名称构建路径
            System.out.println("前端传递的保存路径：" + SavePath);

            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String newVoiceName = pikId + "." + fileExt;

            System.out.println("重构文件名防止上传同名文件：" + newVoiceName);
            // 确保文件夹存在
            File directory = new File(SavePath);
            if (!directory.exists()) {
                directory.mkdirs();// 创建文件夹
            }

            //保存视频
            File fileSave = new File(SavePath, newVoiceName);
            file.transferTo(fileSave);
            //构造Map将视频信息返回给前端
            //视频名称重构后的名称
            resultMap.put("newVoiceName", newVoiceName);
            //正确保存视频则设置返回码为200
            resultMap.put("resCode", "200");
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            //保存视频错误则设置返回码为400
            resultMap.put("resCode", "400");
            return resultMap;

        }
    }

    @RequestMapping(value = "/voice/delete", method = RequestMethod.DELETE)
    public String deleteFile(@RequestParam String fileName) {
        System.out.println(fileName);
        try {

            fileName = SavePath + "/" + fileName;  // 根据传入的视频名称构建路径
            boolean flag = false;
            //根据路径创建文件对象
            File file = new File(fileName);
            //路径是个文件且不为空时删除文件
            if (file.isFile() && file.exists()) {
                flag = file.delete();
            }
            //删除失败时，返回false
            if (flag) {
                System.out.println("success!!");
                return "success!!";
            } else {
                System.out.println("error!!");
                return "error!!";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}

