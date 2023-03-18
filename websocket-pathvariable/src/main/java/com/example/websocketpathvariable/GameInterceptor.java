package com.example.websocketpathvariable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
public class GameInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            isMatchUserIdAndRequestMessageUserId(headerAccessor);
            log.info("T userId = {}", headerAccessor.getFirstNativeHeader("userId"));
        }


        return message;
    }


    public boolean isMatchUserIdAndRequestMessageUserId(StompHeaderAccessor headerAccessor) {
        String userId = headerAccessor.getFirstNativeHeader("userId");
        log.info("userId = {}", userId);
        return true;
    }
}
