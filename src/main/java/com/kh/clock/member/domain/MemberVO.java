package com.kh.clock.member.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

	private int memSq;			// 유저 번호
	private String memEmailId;  // 유저 이메일
	private String memPwd;      // 유저 비밀번호
	private String memNick;     // 유저 닉네임
	private String ckMemSt;     // 유저 상태 ("ACTIVE","INACTIVE")
	private String memPhone;    // 유저 연락처
	private String memAddr;     // 유저 주소
	private Date memRegDt;      // 가입 날짜
	private String memSocUid;   // 소셜회원 식별 값
	private String ckSocPlt;    // 소셜회원 플랫폼 구분
	private String socRefTkn;   // 리프레쉬 토큰값
	private Date memUpdatedDt;  // 정보수정 날짜
	private Date memInactiveDt; // 탈퇴 날짜 
	
	/* 경험치 부분 */
	private int memLvl;         // 회원 레벨

}
