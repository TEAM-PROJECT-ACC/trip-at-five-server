package com.kh.clock.nonMember.domain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonMemberReserveItemVO {
  private String resCd;         //  RES_CD VARCHAR2(100 BYTE) 예약코드
  private String resEmailId;    //  RES_EMAIL_ID VARCHAR2(100 BYTE) 사용자이메일
  private String resName;       //  RES_NAME VARCHAR2(20 BYTE) 예약자명
  private String resPhone;      //  RES_PHONE VARCHAR2(11 BYTE) 예약자 전화번호
  private int resNumOfPeo;      //  RES_NUM_OF_PEO NUMBER 인원수
  private String checkInDt;     //  CHECK_IN_DT VARCHAR2(20 BYTE) 체크인 날짜
  private String checkOutDt;    //  CHECK_OUT_DT VARCHAR2(20 BYTE) 체크아웃 날짜
  private String ckResSt;       //  CK_RES_ST VARCHAR2(10 BYTE) 예약상태
  private Date resRegDt;        //  RES_REG_DT DATE 예약 생성일
  private int roomNo;           //  ROOM_NO NUMBER 객실번호
  private int memNo;            //  MEM_NO NUMBER 회원번호
  
}
