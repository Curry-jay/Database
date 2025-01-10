package com.example.webback.controller;

import com.example.webback.service.TcpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tcp")
public class TextTcp {

    @Autowired
    private TcpClientService tcpClientService;

    // 使用 POST 请求来发送消息到服务器
    @PostMapping("/send")
    public String sendMessageToServer(@RequestBody String message) {
        // 调用 TcpClientService 发送消息
        return tcpClientService.sendMessage(message);
    }

    // 使用 GET 请求来获取与服务器的连接状态或其他信息
    @GetMapping("/status")
    public String getStatus() {
        // 返回一些连接状态信息
        return "TCP Client is running and connected.";
    }


}
