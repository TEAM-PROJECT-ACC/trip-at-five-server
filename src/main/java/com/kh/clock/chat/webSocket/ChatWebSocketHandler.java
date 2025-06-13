package com.kh.clock.chat.webSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.clock.chat.domain.ChatMessage;
import com.kh.clock.chat.domain.ChatRoom;
import com.kh.clock.chat.repository.ChatDTO;
import com.kh.clock.chat.service.ChatService;
import com.kh.clock.common.gson.CommonGson;
import com.kh.clock.member.domain.AdminVO;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
  private Map<String, WebSocketSession> sessionList = new HashMap<>();
  private final ChatService chatService;

  public ChatWebSocketHandler(ChatService chatService) {
      this.chatService = chatService;
  } 
  
  // 연결 시 동작
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessionList.put(session.getId(), session);
    System.out.println(sessionList);
  }
  
  // 수신 시 동작
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    Map<String, Object> sessionAttributes = session.getAttributes();
    String sessionId = session.getId();
    JsonObject payload = JsonParser.parseString(message.getPayload()).getAsJsonObject();
    System.out.println("request data : " + payload);
    
    String type = CommonGson.getJsonString(payload, "type");
    
    JsonObject data = payload.getAsJsonObject("data");
    
    JsonObject chatRoomObj = data.getAsJsonObject("chatRoom");

    int chatRoomSq = CommonGson.getJsonInt(chatRoomObj, "chatRoomSq");
    String ckChatSt = CommonGson.getJsonString(chatRoomObj, "ckChatSt");
    int memNo = CommonGson.getJsonInt(chatRoomObj, "memNo");
    String userEmail = CommonGson.getJsonString(chatRoomObj, "userEmail");
    int adminNo = CommonGson.getJsonInt(chatRoomObj, "adminNo");
    String inqCtgCd = CommonGson.getJsonString(chatRoomObj, "inqCtgCd");
    
    ChatRoom chatRoom = new ChatRoom(chatRoomSq, ckChatSt, memNo, userEmail, adminNo, inqCtgCd);      
    sessionAttributes.put("chatRoom", chatRoom);
    ChatDTO chatDTO = null;
    ArrayList<ChatMessage> messages = null;
    
    Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
    
    // 채팅 로그 존재 여부 판별
    if(type.equals("INIT")) {
      // 새로 생성 된 채팅 방 정보로 welcome 메시지 db 저장
      String newMessage = "채팅이 시작되었습니다. 무엇을 도와드릴까요?";
      ChatMessage tempChatMessage = new ChatMessage();
      
      tempChatMessage.setChatMsgCont(newMessage);
      tempChatMessage.setCkSenderType("ADMIN");
      
      // NOTI: 임시 관리자 계정 이메일 사용 추후 변경해야 함
      // TODO: chatroom으로 전달 받은 관리자 번호로 관리자 DB 조회
      AdminVO admin = chatService.selectChatAdmin(adminNo);
      System.out.println("chat init admin data : " + admin);
      // 조회한 관리자 데이터의 이메일 할당
      tempChatMessage.setSenderEmail(admin.getAdminEmailId());
      tempChatMessage.setChatRoomNo(chatRoomSq);
      int result = chatService.insertChatMessage(tempChatMessage);
      
      if(result > 0) {
        messages = chatService.selectChatMessageList(chatRoomSq);
        chatDTO = new ChatDTO(chatRoom, messages);
      }

      String responseMessage = gson.toJson(chatDTO);
      
      session.sendMessage(new TextMessage(responseMessage));
    } else {
      if(type.equals("NEW")) {
        JsonObject newMessage = data.getAsJsonObject("messageData");
        String chatMsgCont = CommonGson.getJsonString(newMessage, "chatMsgCont");
        String ckSenderType = CommonGson.getJsonString(newMessage, "ckSenderType");
        String senderEmail = CommonGson.getJsonString(newMessage, "senderEmail");
        // chatMsgCont, ckSenderType, senderEmail, chatRoomNo
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatMsgCont(chatMsgCont);
        chatMessage.setCkSenderType(ckSenderType);
        chatMessage.setSenderEmail(senderEmail);
        chatMessage.setChatRoomNo(chatRoomSq);
        
        // message DB 저장
        int result = chatService.insertChatMessage(chatMessage);
        if(result > 0) {
          // sessionList 조회
          for(String key : sessionList.keySet()) {
            if(!sessionId.equals(key)) {
              WebSocketSession receiver = sessionList.get(key);
              ChatRoom receiverChatRoom = (ChatRoom)receiver.getAttributes().get("chatRoom");
              int receiverChatRoomSq = receiverChatRoom.getChatRoomSq();
              if(chatRoomSq == receiverChatRoomSq) {
                // session.getId()가 현재 세션 id가 아닌 값 중 chatRoomSq가 같은 값
                // ChatDTO chatDTO = new ChatDTO(receiverChatRoom, chatMessage);
                messages = new ArrayList<ChatMessage>();
                messages.add(chatMessage);
                
                chatDTO = new ChatDTO(chatRoom, messages);
                String chatMessageJson = gson.toJson(chatDTO);
                
                // 해당 세션에 send
                receiver.sendMessage(new TextMessage(chatMessageJson));
              }
            }
          }
        }
      }
    }
  }
  
  // 종료 시 동작 
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    System.out.println("클라이언트 종료 :: " + session.getId());
    System.out.println("종료 상태 : " + status);
    String sessionId = session.getId();
    sessionList.remove(sessionId);
  }
}
