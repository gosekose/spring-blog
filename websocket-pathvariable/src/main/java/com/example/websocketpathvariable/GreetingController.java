package com.example.websocketpathvariable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/hello/{gameId}")
    public void greeting(@Payload HelloMessage message,
                             @DestinationVariable String gameId,
                             StompHeaderAccessor headerAccessor) throws Exception {

        Greeting greeting = new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");

        template.convertAndSend("/topic/greetings/" + gameId, greeting);
    }


    public String getUserIdByHeaders(SimpMessageHeaderAccessor headerAccessor) {

        Map<String, Object> headers = headerAccessor.getMessageHeaders();

        Map<String, Object> nativeHeaders;
        if ((nativeHeaders = (Map<String, Object>) headers.get("nativeHeaders")) != null) {

            if (!((List<String>) nativeHeaders.get("userId")).isEmpty()) {
                return ((List<String>) nativeHeaders.get("userId")).get(0);
            } else {
                throw new NotAcceptableStatusException("error");
            }
        }

        throw new NotAcceptableStatusException("error");
    }
}