package com.kh.clock.member.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SnsLoginDAO {

	@Autowired
	private final SqlSession sqlsession;

	public SnsLoginDAO (SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	public int kakaoRegisterSelect(SnsLoginDTO kakaoLoginDTO) {
		
		int result = sqlsession.selectOne("mamberMapper.emailDuplication", kakaoLoginDTO);

		return result;
	}
	
	public int kakaoRegister(SnsLoginDTO kakaoLoginDTO) {
		
		int result = sqlsession.insert("mamberMapper.kakaoRegister", kakaoLoginDTO);

		return result;
	}
	

}
