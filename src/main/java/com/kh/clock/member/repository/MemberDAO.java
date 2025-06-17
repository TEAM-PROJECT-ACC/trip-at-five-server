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

	/* 이메일 중복 체크 */
	public int emailDuplicationCheck(String email) {

		return sqlsession.selectOne("mamberMapper.emailDuplication", email);

	}

	/* 닉네임 중복 체크 */
	public int nickNameDuplicationCheck(String nickName) {
		return sqlsession.selectOne("mamberMapper.nickNameDuplicationCheck", nickName);

	}

	/* 회원 가입 */
	public int registerSend(RegisterDTO register) {

		return sqlsession.insert("mamberMapper.registerSend", register);

	}

	/* sns 유저 가입 여부 확인 */
	public int snsRegisterSelect(LoginDTO loginDTO) {

		int result = sqlsession.selectOne("mamberMapper.emailDuplication", loginDTO);

		return result;
	}

	/* sns 회원 가입 유무 */
	public int snsRegister(LoginDTO kakaoLoginDTO) {

		return sqlsession.insert("mamberMapper.snsRegister", kakaoLoginDTO);
	}

	/* 회원 유무 확인 */
	public MemberVO loginInfo(LoginDTO userInfo) {

		return sqlsession.selectOne("mamberMapper.loginInfo", userInfo);
	}

	/* 관리자 유무 확인 */
	public AdminVO adminInfo(LoginDTO userInfo) {
		return sqlsession.selectOne("mamberMapper.adminInfo", userInfo);
	}

	/* 유저 비밀번호 변경 */
	public int updatePwd(LoginDTO userInfo) {
		return sqlsession.update("mamberMapper.updatePwd", userInfo);

	}

	/* 회원 정보 수정 */
	public int infoUpdate(LoginDTO userInfo) {
		return sqlsession.update("mamberMapper.infoUpdate", userInfo);

	}

	/* 회원 비활성화(탈퇴) */
	public int userInactive(MemberVO loginUser) {
		return sqlsession.update("mamberMapper.userInactive", loginUser);

	}

	/* 챌린지 리스트 조회 */
	public List<ChallengeVO> getChallengeList() {

		return sqlsession.selectList("mamberMapper.getChallengeList");
	}

	/* 회원 가입시 챌린지 추가 */
	public int insertUserChallengeList(List<ChallengeHistoryCreateDTO> list) {

		return sqlsession.insert("mamberMapper.insertUserChallengeList", list);
	}

	/* 챌린지 테이블 리스트 총 숫자 조회 */
	public List<Object> getChallengeCountNo() {
		return sqlsession.selectList("mamberMapper.getChallengeCountNo");
	}

	/* 유저 챌린지 진형 내역 조회 */
	public List<ChallengHistoryDTO> getChallengeUserList(String memNo) {
		return sqlsession.selectList("mamberMapper.getChallengeUserList", memNo);

	}

	/* 유저 레벨 및 경험치 추가 */
	public int memberLevelSetting(int memSq) {
		return sqlsession.insert("mamberMapper.memberLevelSetting", memSq);
	}

	/* 챌린지 완료 처리 */
	public int challengeSuccess(HashMap chalSuccessInfo) {

		return sqlsession.insert("mamberMapper.challengeSuccess", chalSuccessInfo);
	}

	/* 쿠폰 정보 조회 */
	public List<CouponDTO> couponSelect(String userMemSq) {
		return sqlsession.selectList("mamberMapper.couponSelect", userMemSq);
	}

	/* 예약 조회 */
	public List<ReservationSelectDTO> reservationSelect(String userMemSq) {
		return sqlsession.selectList("mamberMapper.reservationSelect", userMemSq);
	}

	/* 예약 취소 수정 */
	public int reservationCancelUpdate(ReservationSelectDTO cancelInfo) {
		return sqlsession.update("mamberMapper.reservationCancelUpdate", cancelInfo);
	}

	public List<ChallengHistoryDTO> getChallengeCompletion(List<ChallengHistoryDTO> list) {
		return sqlsession.selectList("mamberMapper.getChallengeCompletion", list);
	}

	public int levelSearch(String userMemSq) {
		return sqlsession.selectOne("mamberMapper.levelSearch", userMemSq);
	}

	public int reservationCancellationUpdate(ReservationSelectDTO cancelInfo) {
		return sqlsession.update("mamberMapper.reservationCancellationUpdate", cancelInfo);
	}

	public int reviewCount(String userMemSq) {
		return sqlsession.selectOne("mamberMapper.reviewCount", userMemSq);
	}

	public int accommodationCount(String userMemSq) {
		return sqlsession.selectOne("mamberMapper.accommodationCount", userMemSq);
	}

	public int locCount(String userMemSq) {
		return sqlsession.selectOne("mamberMapper.locCount", userMemSq);
	}


}
