package com.example.webback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.webback.common.CommonResult;
import com.example.webback.pojo.Video;
import com.example.webback.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 获取所有视频记录
     * @return 视频记录列表
     */
    @GetMapping("/list")
    public CommonResult<List<Video>> listVideos() {
        List<Video> videos = videoService.list();
        return CommonResult.success(videos);
    }

    /**
     * 获取单个视频记录
     * @param id 视频ID
     * @return 单个视频记录
     */
    @GetMapping("/{id}")
    public CommonResult<Video> getVideo(@PathVariable("id") Long id) {
        Video video = videoService.getById(id);
        if (video == null) {
            return CommonResult.failed("视频记录未找到");
        }
        return CommonResult.success(video);
    }

    /**
     * 新增视频记录
     * @param video 视频对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public CommonResult<Video> addVideo(@RequestBody Video video) {
        boolean result = videoService.save(video);
        if (result) {
            return CommonResult.success(video, "新增成功");
        } else {
            return CommonResult.failed("新增失败");
        }
    }

    /**
     * 更新视频记录
     * @param id 视频ID
     * @param video 更新后的视频对象
     * @return 更新结果
     */
    @PutMapping("/update/{id}")
    public CommonResult<Video> updateVideo(@PathVariable("id") Long id, @RequestBody Video video) {
        video.setId(Math.toIntExact(id));  // 设置ID为传入的ID
        boolean result = videoService.updateById(video);
        if (result) {
            return CommonResult.success(video, "更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    /**
     * 删除视频记录
     * @param id 视频ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult<Void> deleteVideo(@PathVariable("id") Long id) {
        boolean result = videoService.removeById(id);
        if (result) {
            return CommonResult.success(null, "删除成功");
        } else {
            return CommonResult.failed("删除失败");
        }
    }

    @GetMapping("/search")
    public CommonResult<Page<Video>> searchVideos(@RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String path,
                                                  @RequestParam(required = false) BigDecimal recognitionPercentage,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date startTime,  // 开始时间
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date endTime,    // 结束时间
                                                  @RequestParam(defaultValue = "1") int pageNow,    // 当前页码，默认值为1
                                                  @RequestParam(defaultValue = "10") int pageSize) {  // 每页记录数，默认值为10

        // 创建分页对象，传入当前页码和每页记录数
        Page<Video> page = new Page<>(pageNow, pageSize);

        // 构建查询条件
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();

        // 根据 title 进行模糊查询
        if (title != null && !title.isEmpty()) {
            queryWrapper.like("title", title);
        }

        // 根据 path 进行精确匹配
        if (path != null && !path.isEmpty()) {
            queryWrapper.eq("path", path);
        }

        // 根据 recognitionPercentage 进行精确匹配
        if (recognitionPercentage != null) {
            queryWrapper.eq("recognition_percentage", recognitionPercentage); // 假设字段名为 recognition_percentage
        }

        // 时间范围查询：根据开始时间和结束时间进行过滤
        if (startTime != null) {
            queryWrapper.ge("save_time", startTime);  // save_time >= startTime
        }
        if (endTime != null) {
            queryWrapper.le("save_time", endTime);  // save_time <= endTime
        }

        // 执行分页查询
        Page<Video> videoPage = videoService.page(page, queryWrapper);

        // 返回分页查询结果
        return CommonResult.success(videoPage);
    }


}
