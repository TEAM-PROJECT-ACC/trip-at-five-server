package com.kh.clock.common.websocket;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
  private List<WebSocketSession> sessionList = new ArrayList<>(); 
  
  // 연결 시 동작
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("Connected to Client : " + session.getId());
    // NOTI: 첫 연결 시 클라이언트에서 {type: AUTH}를 수신 받고 인증 과정이 필요한 경우 구현해야 함
    
    // TODO: 
    // 웹 소켓이 연결되었을 때 ChatController에 회원 정보를 전달하고 
    // Controller -> Service -> DAO 
    // Chatroom을 조회하여 사용자의 활성화 되어 있는 Chatroom이 있으면
    // Chatroom의 ChatMessage 조회하여 전달 
    // TODO: 비활성 Chatroom 만 존재하는 경우 인사말 메시지 전송
    TextMessage message = new TextMessage("WebSocket Connected");
    
    // TODO: 새로 수신 받은 데이터가 있으면 알림 전달
    // 주기적으로 DB 조회 요청
    
    session.sendMessage(message);
  }
  
  // 수신 시 동작
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    // 메시지 전송
    String msg = message.getPayload();  // 수신된 메시지 추출
    System.out.println("session attribute");
    System.out.println("handleTextMessage handshake: " + session.getHandshakeHeaders());
    System.out.println(msg); // 메시지 확인
    
    // TODO: msg json parsing 후 Message 인스턴스로 생성
    
    // Message 인스턴스 ChatController 로 전달
    
    session.sendMessage(new TextMessage(msg));  // 메시지 전송
  }
  
  // 종료 시 동작 
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
      System.out.println("클라이언트 종료 :: " + session.getId());
      System.out.println("종료 상태 : " + status);
      // TODO: 종료 세션 조회 후 close 처리
      // session.close();
  }
}
