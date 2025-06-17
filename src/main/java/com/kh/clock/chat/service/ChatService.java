package com.kh.clock.chat.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.kh.clock.chat.domain.ChatAdminVO;
import com.kh.clock.chat.domain.ChatMessage;
import com.kh.clock.chat.domain.ChatRoom;
import com.kh.clock.member.domain.AdminVO;

@Service
public interface ChatService {

  /**
   * [GET] 채팅 방 조회
   * @param memSq 로그인 회원 정보
   * @return ChatRoom
   */
  public ChatRoom selectChatRoom(Object loginInfoObj);
  public ChatRoom selectChatRoom(int roomNo);
  
  
  /**
   * [PUT] 채팅 방 생성
   * @param newChatRoom
   * @return int result 0 또는 1
   */
  public int insertChatRoom(ChatRoom newChatRoom);


  /**
   * [GET] 채팅 방 메시지 목록 조회
   * @param chatRoomSq
   * @return ArrayList<ChatMessage>
   */
  public ArrayList<ChatMessage> selectChatMessageList(int chatRoomSq);


  /**
   * [PUT] 채팅 생성
   * @param chatMessage
   * @return int result 0 또는 1
   */
  public int insertChatMessage(ChatMessage chatMessage);


  /**
   * [GET] 채팅 조회
   * @param senderEmail
   * @return ChatMessage
   */
  public ChatMessage selectChatMessage(String senderEmail);


  /**
   * [GET] 채팅 관리자 목록 조회
   * @param inqCtgCd
   * @return ArrayList<ChatAdminVO>
   */
  public ArrayList<ChatAdminVO> selectChatAdminList(String inqCtgCd);


  /**
   * [GET] 채팅 관리자 채팅 방 목록 조회
   * @param adminSq
   * @return int
   */
  public int selectAdminChatRoomCount(int adminSq);


  /**
   * [GET] 채팅 관리자 조회
   * @param adminSq
   * @return AdminVO
   */
  public AdminVO selectChatAdmin(int adminSq);


  /**
   * @param chatRoomSq
   * @return int
   */
  public int updateChatRoomInactive(int chatRoomSq);
}
