package com.kh.clock.config.webSocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.kh.clock.common.websocket.WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  @Value("${client.origins}")
  private String origins;
  private final WebSocketHandler socketHandler;
  
  public WebSocketConfig(WebSocketHandler socketHandler) {
    this.socketHandler = socketHandler;
  }
  
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(socketHandler, "/chat", "/alert")
            .setAllowedOrigins(origins);
  }
}
