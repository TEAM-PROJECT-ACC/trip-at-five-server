package com.kh.clock.member.service;

import org.springframework.stereotype.Service;

import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.LoginDAO;
import com.kh.clock.member.repository.LoginDTO;

@Service
public class LoginServiceImpl implements LoginService {

	private final LoginDAO snsLoginDAO;

	public LoginServiceImpl(LoginDAO snsLoginDAO) {
		this.snsLoginDAO = snsLoginDAO;
	}

	@Override
	public int snsRegisterSelect(LoginDTO loginDTO) {

		int result = snsLoginDAO.snsRegisterSelect(loginDTO);

		return result;
	}

	@Override
	public int snsRegister(LoginDTO kakaoLoginDTO) {

		int result = snsLoginDAO.snsRegister(kakaoLoginDTO);

		return result;
	}

	@Override
	public MemberVO loginInfo(LoginDTO userInfo) {

		MemberVO result = snsLoginDAO.loginInfo(userInfo);

		return result;
	}

}