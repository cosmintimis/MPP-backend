package org.example.serverapp.socket;

import org.example.serverapp.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final UserService userService;

    public WebSocketConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketBroadcaster(userService), "/websocket-broadcaster")
                .setAllowedOrigins("*");
    }
}
