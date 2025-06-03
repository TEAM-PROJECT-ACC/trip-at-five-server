package com.kh.clock.member.service;

import org.springframework.stereotype.Service;
import com.kh.clock.member.repository.MemberDAO;
import com.kh.clock.member.repository.RegisterDTO;

import lombok.RequiredArgsConstructor;

@Service
public class RegisterServiceImpl implements RegisterService {

	private  final MemberDAO memberDAO;
	
	public RegisterServiceImpl(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
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
