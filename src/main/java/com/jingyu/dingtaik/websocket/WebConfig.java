package com.jingyu.dingtaik.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 20252
 */
@Configuration
public class WebConfig {

    @Bean
    public HandlerMapping handlerMapping(){
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/path",new MyWebSocketHandler());
        //在带注释控制器之前
        int order = -1;

        return new SimpleUrlHandlerMapping(map,order);
    }

}
