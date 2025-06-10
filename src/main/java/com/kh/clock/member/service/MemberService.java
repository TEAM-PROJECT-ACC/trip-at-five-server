package com.kh.clock.member.service;

import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.RegisterDTO;

public interface MemberService {

	/* 이메일 중복 체크 */
	public int emailDuplicationCheck(String email);

	/* 닉네임 중복 체크 */
	public int nickNameDuplicationCheck(String nickNmae);

	/* 회원 가입 */
	public int registerSend(RegisterDTO register);

	/* sns 회원 가입 유무 */
	int snsRegister(LoginDTO loginDTO);

	/* 회원 유무 확인 */
	MemberVO userInfo(LoginDTO userInfo);

	/* 관리자 유무 확인 */
	AdminVO adminInfo(LoginDTO userInfo);

	/* sns 로그인 */

	/* sns 유저 가입 여부 확인 */
	int snsRegisterSelect(LoginDTO loginDTO);

	/* 유저 비밀번호 변경 */
	public int updatePwd(LoginDTO userInfo);

}
