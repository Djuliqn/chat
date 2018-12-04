package com.chat.server.controller;

import com.chat.server.view.MessageView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private SimpMessagingTemplate webSocket;

    @Autowired
    public ChatController(SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }
    
    @MessageMapping("/chat")
    public void sendMessage(Message<MessageView> message, @Payload MessageView messageView) {
    	webSocket.convertAndSend("/topic/messages",messageView);
    }
    
    @MessageMapping("/createChat")
    public void sendGroupRequest(Message<MessageView> message, @Payload MessageView messageView) {
    	webSocket.convertAndSend("/topic/newChats",messageView);
    }
}