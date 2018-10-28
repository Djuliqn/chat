package com.chat.server.controller;

import com.chat.server.view.MessageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.management.Notification;
import java.security.Principal;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/message")
    public void handleShout(String message) {
        logger.info("Received message: " + message);
    }

    @MessageMapping("/spittle")
    @SendToUser("/queue/notifications")
    public Notification handleSpittle(Principal principal, MessageView messageView) {
        return null;
    }
}