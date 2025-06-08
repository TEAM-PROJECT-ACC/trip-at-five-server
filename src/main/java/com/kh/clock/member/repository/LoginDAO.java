package com.kh.clock.member.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.clock.member.domain.MemberVO;

@Repository
public class LoginDAO {

	@Autowired
	private final SqlSession sqlsession;

	public LoginDAO (SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	public int snsRegisterSelect(LoginDTO loginDTO) {
		
		int result = sqlsession.selectOne("mamberMapper.emailDuplication", loginDTO);

		return result;
	}
	
	public int snsRegister(LoginDTO kakaoLoginDTO) {
		
		int result = sqlsession.insert("mamberMapper.snsRegister", kakaoLoginDTO);

		return result;
	}

	public MemberVO loginInfo(LoginDTO userInfo) {

		MemberVO result = sqlsession.selectOne("mamberMapper.loginInfo", userInfo);
		return result;
	}
	

}
