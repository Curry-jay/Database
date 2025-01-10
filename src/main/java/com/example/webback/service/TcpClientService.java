package com.example.webback.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.net.*;

@Service
public class TcpClientService {
    private static final Logger logger = LoggerFactory.getLogger(TcpClientService.class);

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private String serverAddress = "10.184.17.53";  // 服务器的 IP 地址
    private int port = 12345;  // 服务器端口

    // 连接到服务器并启动监听
    @PostConstruct
    public void startTcpClient() {
        try {
            // 连接到服务器
            socket = new Socket(serverAddress, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            logger.info("TCP Client started, connected to {}:{}", serverAddress, port);
        } catch (IOException e) {
            logger.error("Failed to start TCP Client:", e);
        }
    }

    // 发送数据并获取响应
    public String sendMessage(String message) {
        try {
            // 发送数据给服务器
            outputStream.write(message.getBytes());
            logger.info("Sent message: {}", message);

            // 读取服务器响应的数据
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, bytesRead);
            logger.info("Received from server: {}", receivedMessage);

            return receivedMessage;
        } catch (IOException e) {
            logger.error("Error in TCP client communication:", e);
            return "Error: " + e.getMessage();
        }
    }

    // 在应用关闭时停止客户端连接
    @PreDestroy
    public void stopTcpClient() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                logger.info("TCP Client connection closed.");
            }
        } catch (IOException e) {
            logger.error("Error while closing TCP client connection:", e);
        }
    }
}
