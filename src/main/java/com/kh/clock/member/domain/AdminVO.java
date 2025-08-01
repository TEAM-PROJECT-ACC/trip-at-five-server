package com.kh.clock.member.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminVO {

	private int adminSq; // 관리자번호
	private String adminEmailId; // 관리자이메일
	private String adminPwd; // 관리자비밀번호
	private Date adminRegDt; // 등록일
	private String inqCtgCd; // 담당문의유형
}
