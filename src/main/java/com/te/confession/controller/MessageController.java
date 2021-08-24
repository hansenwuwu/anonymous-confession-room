package com.te.confession.controller;

import com.te.confession.model.MessageModel;
import com.te.confession.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message) {
        System.out.println("handling send message: " + message + " to: " + to);
        boolean isExists = UserStorage.getInstance().getUsers().containsKey(to);
        if (isExists) {
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }
    }

    @MessageMapping("/registration")
    @SendToUser("/topic")
    public MessageModel addUser(MessageModel messageMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("handling registration message: " + messageMessage);
        // generate id
        // headerAccessor.getSessionAttributes().put("username",
        // messageMessage.getSender());
        return messageMessage;
    }
}
