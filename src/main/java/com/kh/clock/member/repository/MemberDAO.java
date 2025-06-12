package com.kh.clock.member.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.ChallengeVO;
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

		return sqlsession.selectOne("mamberMapper.emailDuplication", email);

	}

	public int nickNameDuplicationCheck(String nickName) {
		return sqlsession.selectOne("mamberMapper.nickNameDuplicationCheck", nickName);

	}

	public int registerSend(RegisterDTO register) {

		return sqlsession.insert("mamberMapper.registerSend", register);

	}

	public int snsRegisterSelect(LoginDTO loginDTO) {

		int result = sqlsession.selectOne("mamberMapper.emailDuplication", loginDTO);

		return result;
	}

	public int snsRegister(LoginDTO kakaoLoginDTO) {

		return sqlsession.insert("mamberMapper.snsRegister", kakaoLoginDTO);
	}

	public MemberVO loginInfo(LoginDTO userInfo) {

		return sqlsession.selectOne("mamberMapper.loginInfo", userInfo);
	}

	public AdminVO adminInfo(LoginDTO userInfo) {
		return sqlsession.selectOne("mamberMapper.adminInfo", userInfo);
	}

	public int updatePwd(LoginDTO userInfo) {
		return sqlsession.update("mamberMapper.updatePwd", userInfo);

	}

	public int infoUpdate(LoginDTO userInfo) {
		return sqlsession.update("mamberMapper.infoUpdate", userInfo);

	}

	public int userInactive(MemberVO loginUser) {
		return sqlsession.update("mamberMapper.userInactive", loginUser);

	}

	public List<ChallengeVO> getChallengeList() {

		return sqlsession.selectList("mamberMapper.getChallengeList");
	}

	public int insertUserChallengeList(List<ChallengeHistoryCreateDTO> list) {

		return sqlsession.insert("mamberMapper.insertUserChallengeList", list);
	}

	public List<Object> getChallengeCountNo() {
		return sqlsession.selectList("mamberMapper.getChallengeCountNo");
	}

	public List<ChallengHistoryDTO> getChallengeUserList(String memNo) {
		return sqlsession.selectList("mamberMapper.getChallengeUserList",memNo);

	}

	public int memberLevelSetting(int memSq) {
		return sqlsession.insert("mamberMapper.memberLevelSetting",memSq);
	}

	public int challengeSucces(HashMap chalSuccessInfo) {

		return sqlsession.insert("mamberMapper.challengeSucces",chalSuccessInfo);
	}

}
