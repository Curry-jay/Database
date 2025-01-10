package com.example.webback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.webback.pojo.Text;
import com.example.webback.service.TextService;
import com.example.webback.mapper.TextMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【text(文字表)】的数据库操作Service实现
* @createDate 2025-01-08 22:35:54
*/
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text>
    implements TextService{

}




