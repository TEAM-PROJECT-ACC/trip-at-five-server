package com.kh.clock.member.repository;

import lombok.Data;

@Data
public class RegisterDTO {
	private String email;    // 회원 이메일
	private int emailCount;  // 회원 이메일 중복여부 카운트
	private String pwd;      // 회원 비밀번호
	private String nickName; // 회원 닉네임
	private int nickCount;   // 회원 닉네임 중복여부 카운트
	private String tel;      // 회원 연락처
	private String address;  // 회원 주소

}
