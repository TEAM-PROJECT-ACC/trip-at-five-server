package com.kh.clock.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public int snsRegisterSelect(LoginDTO loginDTO) {

		int result = memberDAO.snsRegisterSelect(loginDTO);

		return result;
	}

	@Override
	public int snsRegister(LoginDTO kakaoLoginDTO) {

		int result = memberDAO.snsRegister(kakaoLoginDTO);

		return result;
	}

	@Override
	public MemberVO loginInfo(LoginDTO userInfo) {

		MemberVO result = memberDAO.loginInfo(userInfo);

		return result;
	}

	@Override
	public int emailDuplicationCheck(String email) {

		int result = memberDAO.emailDuplicationCheck(email);

		return result;
	}

	@Override
	public int nickNameDuplicationCheck(String nickName) {
		int result = memberDAO.nickNameDuplicationCheck(nickName);

		return result;
	}

	@Override
	public int registerSend(RegisterDTO register) {

		int result = memberDAO.registerSend(register);
		return result;
	}

}