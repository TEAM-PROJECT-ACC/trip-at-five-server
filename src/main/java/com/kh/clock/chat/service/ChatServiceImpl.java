package com.kh.clock.chat.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.kh.clock.chat.domain.ChatAdminVO;
import com.kh.clock.chat.domain.ChatMessage;
import com.kh.clock.chat.domain.ChatRoom;
import com.kh.clock.chat.repository.ChatDAO;
import com.kh.clock.member.domain.AdminVO;

@Service
public class ChatServiceImpl implements ChatService {
  private final ChatDAO chatDAO;
  
  public ChatServiceImpl(ChatDAO chatDAO) {
    this.chatDAO = chatDAO;
  }
  
  /**
   * [GET] 채팅 방 조회
   * @param memNo 로그인 회원 정보
   * @return ChatRoom
   */
  @Override
  public ChatRoom selectChatRoom(Object loginInfoObj) {
    ChatRoom chatRoom = chatDAO.selectChatRoom(loginInfoObj);
    
    return chatRoom;
  }
  
  /**
   * [PUT] 채팅 방 생성
   * @param newChatRoom
   * @return int result 0 또는 1
   */
  @Override
  public int insertChatRoom(ChatRoom chatRoom) {
    int result = chatDAO.insertChatRoom(chatRoom);
    return result;
  }
  
  /**
   * [GET] 채팅 방 메시지 목록 조회
   * @param chatRoomSq
   * @return ArrayList<ChatMessage>
   */
  @Override
  public ArrayList<ChatMessage> selectChatMessageList(int chatRoomSq) {
    ArrayList<ChatMessage> chatMessageList = (ArrayList<ChatMessage>)chatDAO.selectChatMessageList(chatRoomSq);
    return chatMessageList;
  }
  
  /**
   * [PUT] 채팅 생성
   * @param chatMessage
   * @return int result 0 또는 1
   */
  @Override
  public int insertChatMessage(ChatMessage chatMessage) {
    int result = chatDAO.insertChatMessage(chatMessage);
    return result;
  }
  
  /**
   * [GET] 채팅 조회
   * @param senderEmail
   * @return ChatMessage
   */
  @Override
  public ChatMessage selectChatMessage(String senderEmail) {
    ChatMessage chatMessage = chatDAO.selectChatMessage(senderEmail);
    return chatMessage;
  }

  /**
   * [GET] 채팅 관리자 조회 
   * @param inqCtgCd
   * @return ArrayList<AdminVO>
   */
  @Override
  public ArrayList<ChatAdminVO> selectChatAdminList(String inqCtgCd) {
    ArrayList<ChatAdminVO> chatAdminList = (ArrayList<ChatAdminVO>)chatDAO.selectChatAdminList(inqCtgCd);
    return chatAdminList;
  }

  /**
   * [GET] 채팅 관리자 채팅 방 목록 조회
   * @param adminSq
   * @return int
   */
  @Override
  public int selectAdminChatRoomCount(int adminSq) {
    int result = chatDAO.selectAdminChatRoomCount(adminSq); 
    return result;
  }
  
  /**
   * [GET] 채팅 관리자 조회
   * @param adminNo
   * @return AdminVO
   */
  @Override
  public AdminVO selectChatAdmin(int adminNo) {
    AdminVO admin = chatDAO.selectChatAdmin(adminNo);
    return admin;
  }
}
