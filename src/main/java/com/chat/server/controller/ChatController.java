package com.chat.server.controller;

import com.chat.server.view.OutputMessageView;
import com.chat.server.view.MessageRecipient;
import com.chat.server.view.MessageView;

import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatController {
    
    @Autowired
    private SimpMessagingTemplate webSocket;
    
    @MessageMapping("/chat")
    public void sendMessage(Message<MessageView> message, @Payload MessageView messageView) throws Exception {
    	
        Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
        if (principal == null) {
            log.error("Principal is null");
            return;
        }

        List<MessageRecipient> recipients = messageView.getRecipients();
        for (MessageRecipient recipient : recipients) {
        	if (MessageRecipient.RecipientType.USER.equals(recipient.getRecipientType())) {
		        webSocket.convertAndSendToUser(recipient.getRecipientName(), "/queue/messages",
		        		OutputMessageView.builder().sender(messageView.getUsername()).text(messageView.getText()).date(messageView.getDate()).build());
        	} else {
        		webSocket.convertAndSend("/queue/messages/group/"+recipient.getRecipientName(),
        				OutputMessageView.builder().sender(messageView.getUsername()).text(messageView.getText()).date(messageView.getDate()).build());
        	}
        }
    }
}