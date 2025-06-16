package com.kh.clock.nonMember.domain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonMemberRoomInfo {
  private int roomSq;           //  ROOM_SQ   NUMBER  객실번호
  private String roomName;      //  ROOM_NAME   VARCHAR2(50 BYTE)   객실명
  private int roomPrice;        //  ROOM_PRICE  NUMBER  객실가격
  private String roomChkIn;     //  ROOM_CHK_IN VARCHAR2(10 BYTE)   체크인 시간
  private String roomChkOut;    //  ROOM_CHK_OUT    VARCHAR2(10 BYTE)   체크아웃 시간
  private int roomStdPpl;       //  ROOM_STD_PPL    NUMBER  기준인원
  private int roomMaxPpl;       //  ROOM_MAX_PPL    NUMBER  최대인원
  private int roomCnt;          //  ROOM_CNT    NUMBER  객실 수
  private Date roomRegDt;       //  ROOM_REG_DT DATE    등록일
  private int accomNo;          //  ACCOM_NO    NUMBER  숙박업소번호
}
