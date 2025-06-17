package com.kh.clock.admin.domain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminContactVO {
  private int no;               //  CHAT_ROOM_SQ  NUMBER  채팅방번호  
  private String userEmail;     //  USER_EMAIL  VARCHAR2(100 BYTE)  사용자 이메일
  private String userName;
  private Date chatRoomRegDt;   //  CHAT_ROOM_REG_DT    DATE    등록일
}
