package com.kh.clock.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.MemberDAO;
import com.kh.clock.member.repository.RegisterDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	@Autowired
	private final MemberDAO memberDAO;

	/* 이메일 중복 체크 */
	@Override
	public int emailDuplicationCheck(String email) {

		int result = memberDAO.emailDuplicationCheck(email);

		return result;
	}

	/* 닉네임 중복 체크 */
	@Override
	public int nickNameDuplicationCheck(String nickName) {
		int result = memberDAO.nickNameDuplicationCheck(nickName);

		return result;
	}

	/* sns 회원 가입 유무 */
	@Override
	public int registerSend(RegisterDTO register) {

		int result = memberDAO.registerSend(register);
		return result;
	}

	/* 회원 가입 유무 확인 */
	@Override
	public MemberVO userInfo(LoginDTO userInfo) {

		MemberVO result = memberDAO.loginInfo(userInfo);

		return result;
	}

	/* 관리자 유무 확인 */
	@Override
	public AdminVO adminInfo(LoginDTO userInfo) {

		AdminVO result = memberDAO.adminInfo(userInfo);
		return result;
	}

	/* sns 유저 가입 여부 확인 */
	@Override
	public int snsRegisterSelect(LoginDTO loginDTO) {

		int result = memberDAO.snsRegisterSelect(loginDTO);

		return result;
	}

	/* sns 회원 가입 유무 */
	@Override
	public int snsRegister(LoginDTO kakaoLoginDTO) {

		int result = memberDAO.snsRegister(kakaoLoginDTO);

		return result;
	}

	@Override
	public int updatePwd(LoginDTO userInfo) {
		int result = memberDAO.updatePwd(userInfo);

		return result;
	}

}