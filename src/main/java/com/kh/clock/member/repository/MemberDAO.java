package com.kh.clock.member.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
public class MemberDAO {

	
	@Autowired
	private final SqlSession sqlsession;
	
	public MemberDAO (SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	public int emailDuplicationCheck(String email) {
		//

		int result = sqlsession.selectOne("mamberMapper.emailDuplication", email);

		return result;
	}

	public int nickNameDuplicationCheck(String nickName) {
		int result = sqlsession.selectOne("mamberMapper.nickNameDuplicationCheck", nickName);

		return result;
	}

	public int registerSend(RegisterDTO register) {

		int result = sqlsession.insert("mamberMapper.registerSend", register);
		
		return result;
	}

}
