package com.kh.clock.member.service;

import com.kh.clock.member.repository.SnsLoginDTO;

public interface LoginService {

	/* 카카오 유저 가입 여부 확인  */
	int kakaoRegisterSelect(SnsLoginDTO kakaoLoginDTO);

	
	int kakaoRegister(SnsLoginDTO kakaoLoginDTO);

	
}
