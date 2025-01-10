package com.example.webback.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.webback.common.CommonResult;
import com.example.webback.pojo.Picture;
import com.example.webback.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pictures")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    /**
     * 获取所有图片
     * @return 图片列表
     */
    @GetMapping("/list")
    public CommonResult<List<Picture>> listPictures() {
        List<Picture> pictures = pictureService.list();
        return CommonResult.success(pictures);
    }

    /**
     * 获取单张图片
     * @param id 图片ID
     * @return 单张图片信息
     */
    @GetMapping("/{id}")
    public CommonResult<Picture> getPicture(@PathVariable("id") Long id) {
        Picture picture = pictureService.getById(id);
        if (picture == null) {
            return CommonResult.failed("图片未找到");
        }
        return CommonResult.success(picture);
    }

    /**
     * 新增图片
     * @param picture 图片对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public CommonResult<Picture> addPicture(@RequestBody Picture picture) {
        boolean result = pictureService.save(picture);
        if (result) {
            return CommonResult.success(picture, "新增成功");
        } else {
            return CommonResult.failed("新增失败");
        }
    }

    /**
     * 更新图片信息
     * @param id 图片ID
     * @param picture 更新后的图片对象
     * @return 更新结果
     */
    @PutMapping("/update/{id}")
    public CommonResult<Picture> updatePicture(@PathVariable("id") Integer id, @RequestBody Picture picture) {
        picture.setId(Math.toIntExact(id));  // 设置ID为传入的ID
        boolean result = pictureService.updateById(picture);
        if (result) {
            return CommonResult.success(picture, "更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    /**
     * 删除图片
     * @param id 图片ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult<Void> deletePicture(@PathVariable("id") Long id) {
        boolean result = pictureService.removeById(id);
        if (result) {
            return CommonResult.success(null, "删除成功");
        } else {
            return CommonResult.failed("删除失败");
        }
    }

    @GetMapping("/search")
    public CommonResult<Page<Picture>> searchPictures(@RequestParam(required = false) String headline,
                                                      @RequestParam(required = false) String path,
                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date startTime,  // 开始时间
                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date endTime,    // 结束时间
                                                      @RequestParam(required = false) BigDecimal recognitionPercentage,
                                                      @RequestParam(defaultValue = "1") int pageNow,  // 当前页码，默认值为1
                                                      @RequestParam(defaultValue = "10") int pageSize) {  // 每页记录数，默认值为10
        // 创建分页对象，传入当前页码和每页记录数
        Page<Picture> page = new Page<>(pageNow, pageSize);

        // 构建查询条件
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();

        if (headline != null && !headline.isEmpty()) {
            queryWrapper.like("headline", headline);  // 根据图片标题进行模糊查询
        }
        if (path != null && !path.isEmpty()) {
            queryWrapper.eq("path", path);  // 根据图片路径进行精确查询
        }
        if (recognitionPercentage != null) {
            queryWrapper.eq("recognition_percentage", recognitionPercentage);  // 根据识别率进行精确查询
        }

        // 时间范围查询：如果开始时间和结束时间都存在
        if (startTime != null) {
            queryWrapper.ge("save_time", startTime);  // save_time >= startTime
        }
        if (endTime != null) {
            queryWrapper.le("save_time", endTime);  // save_time <= endTime
        }

        // 执行分页查询
        Page<Picture> picturePage = pictureService.page(page, queryWrapper);

        // 返回分页查询结果
        return CommonResult.success(picturePage);
    }



}