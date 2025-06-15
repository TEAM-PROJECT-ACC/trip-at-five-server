package com.kh.clock.member.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.ChallengeVO;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.ChallengHistoryDTO;
import com.kh.clock.member.repository.ChallengeHistoryCreateDTO;
import com.kh.clock.member.repository.CouponDTO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.MemberDAO;
import com.kh.clock.member.repository.RegisterDTO;
import com.kh.clock.member.repository.ReservationSelectDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	@Autowired
	private final MemberDAO memberDAO;

	/* 이메일 중복 체크 */
	@Override
	public int emailDuplicationCheck(String email) {

		int result = memberDAO.emailDuplicationCheck(email);

		return result;
	}

	/* 닉네임 중복 체크 */
	@Override
	public int nickNameDuplicationCheck(String nickName) {
		int result = memberDAO.nickNameDuplicationCheck(nickName);

		return result;
	}

	/* sns 회원 가입 유무 */
	@Override
	public int registerSend(RegisterDTO register) {

		int result = memberDAO.registerSend(register);
		return result;
	}

	/* 회원 가입 유무 확인 */
	@Override
	public MemberVO userInfo(LoginDTO userInfo) {

		MemberVO result = memberDAO.loginInfo(userInfo);

		return result;
	}

	/* 관리자 유무 확인 */
	@Override
	public AdminVO adminInfo(LoginDTO userInfo) {

		AdminVO result = memberDAO.adminInfo(userInfo);
		return result;
	}

	/* sns 유저 가입 여부 확인 */
	@Override
	public int snsRegisterSelect(LoginDTO loginDTO) {

		int result = memberDAO.snsRegisterSelect(loginDTO);

		return result;
	}

	/* sns 회원 가입 유무 */
	@Override
	public int snsRegister(LoginDTO kakaoLoginDTO) {

		int result = memberDAO.snsRegister(kakaoLoginDTO);

		return result;
	}

	/* 비밀번호 재설정 */
	@Override
	public int updatePwd(LoginDTO userInfo) {
		int result = memberDAO.updatePwd(userInfo);

		return result;
	}

	/* 회원 정보 수정 */
	@Override
	public int infoUpdate(LoginDTO userInfo) {
		int result = memberDAO.infoUpdate(userInfo);

		return result;
	}

	/* 회원 비활성화(탈퇴) */
	@Override
	public int userInactive(MemberVO loginUser) {

		int result = memberDAO.userInactive(loginUser);

		return result;
	}

	/* 챌린지 리스트 조회 */
	@Override
	public List<ChallengeVO> getChallengeList() {

		return memberDAO.getChallengeList();

	}

	/* 회원 가입시 챌린지 추가 */
	@Override
	public int insertUserChallengeList(List<ChallengeHistoryCreateDTO> list) {
		return memberDAO.insertUserChallengeList(list);
	}

	/* 챌린지 테이블 리스트 총 숫자 조회 */
	@Override
	public List<Object> getChallengeCountNo() {
		return memberDAO.getChallengeCountNo();
	}

	/* 회원 가입시 챌린지 추가 */
	@Override
	public List<ChallengHistoryDTO> getChallengeUserList(String memNo) {
		return memberDAO.getChallengeUserList(memNo);
	}

	/* 유저 레벨 및 경험치 추가 */
	@Override
	public int memberLevelSetting(int memSq) {
		return memberDAO.memberLevelSetting(memSq);

	}

	/* 챌린지 완료 처리 */
	@Override
	public int challengeSuccess(HashMap chalSuccessInfo) {
		return memberDAO.challengeSuccess(chalSuccessInfo);
	}

	/* 쿠폰 정보 조회 */
	@Override
	public List<CouponDTO> couponSelect(String userMemSq) {
		return memberDAO.couponSelect(userMemSq);
	}

	/* 예약 조회 */
	@Override
	public List<ReservationSelectDTO> reservationSelect(String userMemSq) {
		return memberDAO.reservationSelect(userMemSq);
	}

	/* 예약 취소 수정 */
	@Override
	public int reservationCancelUpdate(ReservationSelectDTO cancelInfo) {
		return memberDAO.reservationCancelUpdate(cancelInfo);
	}

	@Override
	public List<ChallengHistoryDTO> getChallengeCompletion(String userMemSq) {
		return memberDAO.getChallengeCompletion(userMemSq);
	}

	
}