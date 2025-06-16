package com.kh.clock.member.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengHistoryDTO {

	private String rewardCouponName; // 쿠폰이름
	private String chalName; 		 // 챌린지명
	private int chalCond; 			 // 챌린지 조건
	private int currentStep; 		 // 챌린지 진행도
	private int chalHistoryNo; 		 // 유저 챌린지 내역 번호
	private String ckCouponSt;		 // 쿠폰 사용 여부 

}
