package com.kh.clock.chat.controller;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.clock.chat.domain.AdminInfo;
import com.kh.clock.chat.domain.ChatAdminVO;
import com.kh.clock.chat.domain.ChatMessage;
import com.kh.clock.chat.domain.ChatRoom;
import com.kh.clock.chat.domain.MemberInfo;
import com.kh.clock.chat.repository.ChatDTO;
import com.kh.clock.chat.service.ChatService;
import com.kh.clock.common.gson.CommonGson;

@RestController
@RequestMapping("/chat")
public class ChatController {
  private final ChatService chatService;
  
  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }
  
  /**
   * [GET] 채팅 메시지 목록 조회
   * @param chatRoomSq
   * @return ArrayList<ChatMessage>
   */
  @GetMapping("/room/{chatRoomSq}")
  public ArrayList<ChatMessage> selectChatMessages(@PathVariable int chatRoomSq) {
    ArrayList<ChatMessage> chatMessageList = chatService.selectChatMessageList(chatRoomSq);
    return chatMessageList;
  }
  
  /**
   * [GET] 채팅방 조회
   * @param memType
   * @param memNo
   * @param loginInfo
   * @param userEmail
   * @param inqCtgCd
   * @return String
   */
  @GetMapping("{memType}/{memNo}")
  public String selectInitChat( @PathVariable String memType, @PathVariable int memNo, @RequestParam String loginInfo, @RequestParam String userEmail, @RequestParam String inqCtgCd) {
    JsonObject loginInfoJson = JsonParser.parseString(loginInfo).getAsJsonObject();
    
    Object loginInfoObj = null;
    
    if(memType == "admin") {
      int adminSq = CommonGson.getJsonInt(loginInfoJson, "adminEmailId");
      String adminEmailId = CommonGson.getJsonString(loginInfoJson, "adminEmailId");
      
      loginInfoObj = new AdminInfo(adminEmailId, adminSq, inqCtgCd, memType);
    } else if(memType == "user") {
      int memSq = CommonGson.getJsonInt(loginInfoJson, "memSq");
      String memEmailId = CommonGson.getJsonString(loginInfoJson, "memEmailId");
      String memNick = CommonGson.getJsonString(loginInfoJson, "memNick");
      
      loginInfoObj = new MemberInfo(memEmailId, memNick, memSq, memType);
    } else {
      // TODO: 비회원 vo 생성
    }
    
    // memNo로 채팅 방 조회
    ChatRoom chatRoom = chatService.selectChatRoom(loginInfoObj);
    
    if(chatRoom == null) {
      // inqCtgCd로 관리자 계정 조회 후 
      ArrayList<ChatAdminVO> adminList = chatService.selectChatAdminList(inqCtgCd);
      int tempCount = 0;
      ChatAdminVO admin = null;
      // 관리자 계정의 방 목록 조회 
      for(int i = 0; i < adminList.size(); i++) {
        ChatAdminVO currAdmin = adminList.get(i);
        int count = currAdmin.getRoomCount();
        
        if(i == 0) {
          tempCount = count;
          admin = currAdmin;
          continue;
        }
        if(count > tempCount) {
          // 방 목록 개수 비교 후 
          // 상대적으로 적은 수의 관리자 배정
          tempCount = count;
          admin = currAdmin;
          continue;
        }
        if(count == tempCount) {
          // 개수가 같을 경우 
          // 앞 순서부터 배정
          continue;
        }
      }
      
      int adminNo = admin.getAdminSq();
      ChatRoom newChatRoom = new ChatRoom(memNo, adminNo, userEmail, inqCtgCd);
      
      int result = chatService.insertChatRoom(newChatRoom);
      if(result > 0) {
        ArrayList<ChatMessage> chatMessageList = new ArrayList<>();
        ChatDTO chatDTO = new ChatDTO(newChatRoom, chatMessageList);
        Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
        String responseData = gson.toJson(chatDTO);
        
        return responseData;
      } else {
        return "";
      }
    } else {
      // 방이 있으면 방에 대한 정보와 해당 방의 메시지 목록을 조회
      ArrayList<ChatMessage> chatMessageList = selectChatMessages(chatRoom.getChatRoomSq());
      ChatDTO chatDTO = new ChatDTO(chatRoom, chatMessageList);
      
      Gson gson = CommonGson.getDateFormattedGson("yyyy-MM-dd");
      String responseData = gson.toJson(chatDTO);
      
      return responseData;
    }
  }
}
