package com.example.webback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.webback.common.CommonResult;
import com.example.webback.pojo.Text;
import com.example.webback.service.TextService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/texts")
public class TextController {

    @Autowired
    private TextService textService;

    /**
     * 获取所有文本记录
     *
     * @return 文本记录列表
     */
    @GetMapping("/list")
    public CommonResult<List<Text>> listTexts() {
        List<Text> texts = textService.list();
        return CommonResult.success(texts);
    }

    /**
     * 获取单个文本记录
     *
     * @param id 文本ID
     * @return 单个文本记录
     */
    @GetMapping("/{id}")
    public CommonResult<Text> getText(@PathVariable("id") Long id) {
        Text text = textService.getById(id);
        if (text == null) {
            return CommonResult.failed("文本记录未找到");
        }
        return CommonResult.success(text);
    }

    /**
     * 新增文本记录
     *
     * @param text 文本对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public CommonResult<Text> addText(@RequestBody Text text) {
        boolean result = textService.save(text);
        if (result) {
            return CommonResult.success(text, "新增成功");
        } else {
            return CommonResult.failed("新增失败");
        }
    }

    /**
     * 更新文本记录
     *
     * @param id   文本ID
     * @param text 更新后的文本对象
     * @return 更新结果
     */
    @PutMapping("/update/{id}")
    public CommonResult<Text> updateText(@PathVariable("id") Integer id, @RequestBody Text text) {
        text.setId(Math.toIntExact(id));  // 设置ID为传入的ID
        boolean result = textService.updateById(text);
        if (result) {
            return CommonResult.success(text, "更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    /**
     * 删除文本记录
     *
     * @param id 文本ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult<Void> deleteText(@PathVariable("id") Integer id) {
        boolean result = textService.removeById(id);
        if (result) {
            return CommonResult.success(null, "删除成功");
        } else {
            return CommonResult.failed("删除失败");
        }
    }

    @GetMapping("/search")
    public CommonResult<Page<Text>> searchTexts(@RequestParam(required = false) String content,
                                                @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date startTime,  // 开始时间
                                                @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date endTime,    // 结束时间
                                                @RequestParam(defaultValue = "1") int pageNow,    // 当前页码，默认值为1
                                                @RequestParam(defaultValue = "10") int pageSize) {  // 每页记录数，默认值为10

        // 创建分页对象，传入当前页码和每页记录数
        Page<Text> page = new Page<>(pageNow, pageSize);

        // 构建查询条件
        QueryWrapper<Text> queryWrapper = new QueryWrapper<>();

        if (content != null && !content.isEmpty()) {
            queryWrapper.like("content", content);  // 根据文本内容进行模糊查询
        }

        // 时间范围查询：如果开始时间和结束时间都存在
        if (startTime != null) {
            queryWrapper.ge("save_time", startTime);  // save_time >= startTime
        }
        if (endTime != null) {
            queryWrapper.le("save_time", endTime);  // save_time <= endTime
        }

        // 执行分页查询
        Page<Text> textPage = textService.page(page, queryWrapper);

        // 返回分页查询结果
        return CommonResult.success(textPage);
    }


}