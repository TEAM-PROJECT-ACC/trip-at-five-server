package com.kh.clock.member.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.MemberVO;

import lombok.AllArgsConstructor;

@Repository
public class MemberDAO {

	@Autowired
	private final SqlSession sqlsession;

	public MemberDAO(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	public int emailDuplicationCheck(String email) {

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

	public AdminVO adminInfo(LoginDTO userInfo) {
		AdminVO result = sqlsession.selectOne("mamberMapper.adminInfo", userInfo);
		return result;
	}

	public int updatePwd(LoginDTO userInfo) {
		int result = sqlsession.update("mamberMapper.updatePwd", userInfo);

		return result;
	}

}
