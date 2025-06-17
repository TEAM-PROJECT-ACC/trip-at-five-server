package com.kh.clock.member.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	private String email;			// 회원 이메일
	private String pwd;  			// 회원 비밀번호
	private String nickName;        // 회원 닉네임
	private String snsUid;          // 소셜 회원 UID
	private String platformName;    // 소셜 회원 플랫폼 

}
