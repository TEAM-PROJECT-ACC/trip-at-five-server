//package com.kh.clock.common.websocket;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Component
//public class WebSocketHandler extends TextWebSocketHandler {
//  private List<WebSocketSession> sessionList = new ArrayList<>(); 
//  
//  // 연결 시 동작
//  @Override
//  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//    System.out.println("Connected to Client : " + session.toString());
//    TextMessage message = new TextMessage("WebSocket Connected");
//    session.sendMessage(message);
//  }
//  
//  // 수신 시 동작
//  @Override
//  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//    // 메시지 전송
//    String msg = message.getPayload();  // 수신된 메시지 추출
//    System.out.println("session attribute");
//    System.out.println("handleTextMessage handshake: " + session.getHandshakeHeaders());
//    System.out.println(msg); // 메시지 확인
//    session.sendMessage(new TextMessage(msg));  // 메시지 전송
//  }
//  
//  // 종료 시 동작 
//  @Override
//  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//      System.out.println("클라이언트 종료 :: " + session.getId());
//      System.out.println("종료 상태 : " + status);
//  }
//}
