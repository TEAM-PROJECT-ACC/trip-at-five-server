package com.kh.clock.member.repository;

import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeHistoryCreateDTO {

	private int memNo; 			  // 회원 번호 전달
	private int chalTotalCountNo; // 챌린지 테이블에서 총 조회되는 개수
}
