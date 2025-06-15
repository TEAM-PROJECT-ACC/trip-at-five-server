package com.kh.clock.member.service;

import java.util.HashMap;
import java.util.List;

import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.ChallengeVO;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.ChallengHistoryDTO;
import com.kh.clock.member.repository.ChallengeHistoryCreateDTO;
import com.kh.clock.member.repository.CouponDTO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.RegisterDTO;
import com.kh.clock.member.repository.ReservationSelectDTO;

public interface MemberService {

	/* 이메일 중복 체크 */
	public int emailDuplicationCheck(String email);

	/* 닉네임 중복 체크 */
	public int nickNameDuplicationCheck(String nickNmae);

	/* 회원 가입 */
	public int registerSend(RegisterDTO register);

	/* sns 회원 가입 유무 */
	int snsRegister(LoginDTO loginDTO);

	/* 회원 유무 확인 */
	MemberVO userInfo(LoginDTO userInfo);

	/* 관리자 유무 확인 */
	AdminVO adminInfo(LoginDTO userInfo);

	/* sns 로그인 */

	/* sns 유저 가입 여부 확인 */
	int snsRegisterSelect(LoginDTO loginDTO);

	/* 유저 비밀번호 변경 */
	public int updatePwd(LoginDTO userInfo);

	/* 회원 정보 수정 */
	public int infoUpdate(LoginDTO userInfo);

	/* 회원 비활성화(탈퇴) */
	public int userInactive(MemberVO loginUser);

	/* 챌린지 리스트 조회 */
	public List<ChallengeVO> getChallengeList();

	/* 회원 가입시 챌린지 추가 */
	public int insertUserChallengeList(List<ChallengeHistoryCreateDTO> list);

	/* 챌린지 테이블 리스트 총 숫자 조회 */
	public List<Object> getChallengeCountNo();

	/* 유저 챌린지 진형 내역 조회 */
	public List<ChallengHistoryDTO> getChallengeUserList(String memNo);

	/* 유저 레벨 및 경험치 추가 */
	public int memberLevelSetting(int memSq);

	/* 챌린지 완료 처리 */
	public int challengeSuccess(HashMap chalSuccessInfo);

	/* 완료된 챌린지 조회 */
	public List<ChallengHistoryDTO> getChallengeCompletion(String userMemSq);
	
	/* 쿠폰 정보 조회 */
	public List<CouponDTO> couponSelect(String userMemSq);

	/* 예약 조회 */
	public List<ReservationSelectDTO> reservationSelect(String userMemSq);

	/* 예약 취소 수정 */
	public int reservationCancelUpdate(ReservationSelectDTO cancelInfo);



}
