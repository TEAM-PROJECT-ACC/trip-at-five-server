package com.kh.clock.member.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeVO {

	private int chalSQ; 	  // 챌린지번호
	private String chalName;  // 챌린지명
	private String chalCond;  // 챌린지조건값
	private Date chalRegDt;   // 등록일
	private int couponNo;     // 쿠폰번호 
	private int adminNol;	  // 관리자 번호
}
