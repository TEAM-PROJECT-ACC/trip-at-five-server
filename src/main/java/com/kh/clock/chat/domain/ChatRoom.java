package com.kh.clock.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
  private int chatRoomSq;       //  chatRoomSq : 채팅 로그 조회를 위한 채팅 방 번호
  private String ckChatSt;      //  ckChatSt : active, 문의 종료 판별 어떻게 할 건지 결정
  private int memNo;            //  memNo : 처음 웹 소켓 연결 시 회원 번호 전달
  private String userEmail;    //  memEmailId: 회원 이메일 (클라이언트에서 전달)
  private int adminNo;          //  adminNo : 관리자 임의 배정 (관리자 계정 개수 파악)
  private String inqCtgCd;      //  inqCtgCd : 클라이언트에서 문의 유형(카테고리) 전달
  
  public ChatRoom(int memNo, int adminNo, String userEmail, String inqCtgCd) {
    this.memNo = memNo;
    this.adminNo = adminNo;
    this.userEmail = userEmail;
    this.inqCtgCd = inqCtgCd;
  }
}
