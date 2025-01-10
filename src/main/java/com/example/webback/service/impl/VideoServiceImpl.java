package com.example.webback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webback.pojo.Video;
import com.example.webback.service.VideoService;
import com.example.webback.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【video(视频表)】的数据库操作Service实现
* @createDate 2025-01-08 22:36:01
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

}




