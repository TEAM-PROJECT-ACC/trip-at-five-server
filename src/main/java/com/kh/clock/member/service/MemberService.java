package com.kh.clock.member.service;

import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.RegisterDTO;

public interface MemberService {

	int snsRegister(LoginDTO loginDTO);

	MemberVO loginInfo(LoginDTO userInfo);

	/* 이메일 중복 체크 */
	public int emailDuplicationCheck(String email);

	/* 닉네임 중복 체크 */
	public int nickNameDuplicationCheck(String nickNmae);

	/* 회원 가입 */
	public int registerSend(RegisterDTO register);

	/* sns 로그인 */

	/* 카카오 유저 가입 여부 확인 */
	int snsRegisterSelect(LoginDTO loginDTO);

}
