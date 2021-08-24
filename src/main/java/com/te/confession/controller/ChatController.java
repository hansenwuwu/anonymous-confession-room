package com.te.confession.controller;

import java.util.Map;

import com.te.confession.storage.UserStorage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.confession.model.UserModel;

@RestController
public class ChatController {

    @GetMapping("/fetchAllUsers")
    public Map<String, UserModel> fetchAll() {
        System.out.println("get all map: " + UserStorage.getInstance().getUsers());
        return UserStorage.getInstance().getUsers();
    }

    // @MessageMapping("/join")
    // @SendTo("/topic/public")
    // public ChatMessage addUser(@Payload ChatMessage chatMessage,
    // SimpMessageHeaderAccessor headerAccessor) {
    // // 把username加入WebSocket的Session
    // headerAccessor.getSessionAttributes().put("username",
    // chatMessage.getSender());
    // return chatMessage; // 返回時會將訊息送至/topic/public
    // }

    // /**
    // * 處理前端送來的聊天訊息，並把訊息推送給前端
    // */
    // @MessageMapping("/chat")
    // @SendTo("/topic/public")
    // public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    // return chatMessage; // 返回時會將訊息送至/topic/public
    // }

}