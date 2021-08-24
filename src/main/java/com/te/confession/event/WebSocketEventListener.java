package com.te.confession.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;

import com.te.confession.storage.UserStorage;

@Component
public class WebSocketEventListener {

    /** STOMP 訊息發送器 */
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * 連線時的處理
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        String sessionId = SimpAttributesContextHolder.currentAttributes().getSessionId();
        System.out.println("收到一個新的WebSocket連線 " + sessionId);

        try {
            UserStorage.getInstance().setUser(sessionId);
        } catch (Exception e) {
            System.out.println("create user storage for " + sessionId + " fail!");
            e.printStackTrace();
        }
    }

    /**
     * 離線時的處理
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = SimpAttributesContextHolder.currentAttributes().getSessionId();
        System.out.println("使用者已離線 " + sessionId);

        UserStorage.getInstance().removeUser(sessionId);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // 從WebSocket Session中取得使用者名稱
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            System.out.println("使用者" + username + "已離線");

            // // 建立一個離線訊息送給前端
            // ChatMessage chatMessage = new ChatMessage();
            // chatMessage.setType(ChatMessage.ChatType.LEAVE);
            // chatMessage.setSender(username);

            // messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }

        // 清除所有有關此使用者的資料

    }
}