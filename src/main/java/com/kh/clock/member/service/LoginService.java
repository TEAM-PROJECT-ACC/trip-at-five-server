package com.kh.clock.member.service;

import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.LoginDTO;

public interface LoginService {

	/* 카카오 유저 가입 여부 확인  */
	int snsRegisterSelect(LoginDTO loginDTO);

	
	int snsRegister(LoginDTO kakaoLoginDTO);


	MemberVO loginInfo(LoginDTO userInfo);

	
}
