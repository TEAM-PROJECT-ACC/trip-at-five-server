package com.kh.clock.member.service;

import org.springframework.stereotype.Service;

import com.kh.clock.member.repository.SnsLoginDAO;
import com.kh.clock.member.repository.SnsLoginDTO;

@Service
public class LoginServiceImpl implements LoginService {

	private  final SnsLoginDAO snsLoginDAO;
	
	public LoginServiceImpl(SnsLoginDAO snsLoginDAO) {
		this.snsLoginDAO = snsLoginDAO;
	}
	
	
	@Override
	public int kakaoRegisterSelect(SnsLoginDTO kakaoLoginDTO) {
		
		int result = snsLoginDAO.kakaoRegisterSelect(kakaoLoginDTO);
		
		return result;
	}


	@Override
	public int kakaoRegister(SnsLoginDTO kakaoLoginDTO) {
		
		int result = snsLoginDAO.kakaoRegister(kakaoLoginDTO);
		
		return result;
	}




	
	
}
