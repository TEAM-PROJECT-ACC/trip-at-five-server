package com.kh.clock.chat.domain;

import java.sql.Date;
import lombok.Data;

@Data
public class ChatMessage {
  private int chatMsgSq;            //  CHAT_MSG_SQ   메시지번호
  private String chatMsgCont;       //  CHAT_MSG_CONT   채팅메시지
  private Date sendDt;              //  SEND_DT 보낸날짜
  private String ckSenderType;      //  CK_SENDER_TYPE  회원유형
  private String senderEmail;       //  SENDER_EMAIL    메시지 보낸 사용자 이메일
  private int chatRoomNo;           //  CHAT_ROOM_NO    채팅방번호
}
