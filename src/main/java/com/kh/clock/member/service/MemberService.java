package com.kh.clock.member.service;

import com.kh.clock.member.repository.RegisterDTO;

public interface MemberService {

	/* 이메일 중복 체크 */
	public int emailDuplicationCheck(String email);

	/* 닉네임 중복 체크 */
	public int nickNameDuplicationCheck(String nickNmae);

	/* 회원 가입 */
	public int registerSend(RegisterDTO register);
}
