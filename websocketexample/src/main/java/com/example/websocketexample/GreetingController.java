package com.example.websocketexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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

//    private final GreetingService greetingService;


    @MessageMapping("/hello/{gameId}")
    @SendTo("/topic/greetings/{gameId}")
    public Greeting greeting(HelloMessage message,
                             @DestinationVariable String gameId,
                             SimpMessageHeaderAccessor headerAccessor) throws Exception {

        if (!(gameId.equals("testGameId") || gameId.equals("test"))) {
            throw new Exception();
        }

        String userId = getUserIdByHeaders(headerAccessor);
        log.info("userId = {}", userId);


        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
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