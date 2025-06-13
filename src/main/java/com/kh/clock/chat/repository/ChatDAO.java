package com.kh.clock.chat.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.kh.clock.chat.domain.ChatAdminVO;
import com.kh.clock.chat.domain.ChatMessage;
import com.kh.clock.chat.domain.ChatRoom;
import com.kh.clock.member.domain.AdminVO;

@Repository
public class ChatDAO {
  private final SqlSession sqlSession;
  
  public ChatDAO(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
  
  /**
   * @param memNo
   * @return ChatRoom
   */
  public ChatRoom selectChatRoom(Object loginInfoObj) {
    return sqlSession.selectOne("chatMapper.selectChatRoom", loginInfoObj);
  }

  /**
   * @param chatRoom
   * @return int 0 또는 1
   */
  public int insertChatRoom(ChatRoom chatRoom) {
    return sqlSession.insert("chatMapper.insertChatRoom", chatRoom);
  }

  /**
   * @param chatRoomSq
   * @return ArrayList<ChatMessage>
   */
  public List<ChatMessage> selectChatMessageList(int chatRoomSq) {
    return sqlSession.selectList("chatMapper.selectChatMessageList", chatRoomSq);
  }

  /**
   * @param chatMessage
   * @return int 0 또는 1
   */
  public int insertChatMessage(ChatMessage chatMessage) {
    return sqlSession.insert("chatMapper.insertChatMessage", chatMessage);
  }

  /**
   * @param senderEmail
   * @return ChatMessage
   */
  public ChatMessage selectChatMessage(String senderEmail) {
    return sqlSession.selectOne("chatMapper.selectChatMessage", senderEmail);
  }

  /**
   * @param inqCtgCd 
   * @return List<ChatAdminVO>
   */
  public List<ChatAdminVO> selectChatAdminList(String inqCtgCd) {
    return sqlSession.selectList("chatMapper.selectChatAdminList", inqCtgCd);
  }

  /**
   * @param adminSq
   * @return int
   */
  public int selectAdminChatRoomCount(int adminSq) {
    return sqlSession.selectOne("chatMapper.selectAdminChatRoomCount", adminSq);
  }

  /**
   * @param adminNo
   * @return AdminVO
   */
  public AdminVO selectChatAdmin(int adminNo) {
    return sqlSession.selectOne("chatMapper.selectChatAdmin", adminNo);
  }
}
