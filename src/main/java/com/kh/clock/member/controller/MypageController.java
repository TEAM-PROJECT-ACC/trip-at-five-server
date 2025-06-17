package com.kh.clock.member.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.ChallengHistoryDTO;
import com.kh.clock.member.repository.CouponDTO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.ReservationSelectDTO;
import com.kh.clock.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MypageController {

	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final MemberService mService;

	@PutMapping("/updatePwd")
	public int pwdupdate(@RequestBody LoginDTO userInfo) {

		MemberVO loginUser = mService.userInfo(userInfo);

		if (loginUser == null) {

			return 0;
		} else {

			String encPwd = bCryptPasswordEncoder.encode(userInfo.getPwd());
			userInfo.setPwd(encPwd);

			int result = mService.updatePwd(userInfo);
			return result;
		}

	}

	@PutMapping("/infoUpdate")
	public int infoUpdate(@RequestBody LoginDTO userInfo) {

		MemberVO loginUser = mService.userInfo(userInfo);

		if (loginUser == null) {

			return 0;
		} else if (userInfo.getPwd().length() == 0) {
			int result = mService.infoUpdate(userInfo);

			return result;
		} else {
			String encPwd = bCryptPasswordEncoder.encode(userInfo.getPwd());
			userInfo.setPwd(encPwd);
			int result = mService.infoUpdate(userInfo);
			return result;

		}

	}

	@PutMapping("/userInactive")
	public int userInactive(@RequestBody LoginDTO userInfo) {

		MemberVO loginUser = mService.userInfo(userInfo);

		if (loginUser == null) {
			return 0;
		}

		if (!bCryptPasswordEncoder.matches(loginUser.getMemPwd(), userInfo.getPwd())) {

			int result = mService.userInactive(loginUser);

			System.out.println(result);

			if (result > 0) {
				return result;
			}
			return 0;
		} else {

			return 0;
		}

	}

	@GetMapping("/challenge")
	public ResponseEntity<List<ChallengHistoryDTO>> getChallengeList(@RequestParam String userMemSq,
			@RequestParam String memLvl ) {

		List<ChallengHistoryDTO> list = mService.getChallengeUserList(userMemSq);
		List<ChallengHistoryDTO> list2 = mService.getChallengeUserList(memLvl);

		List<ChallengHistoryDTO> ckCouponStlist = mService.getChallengeCompletion(list);

		for (int i = 0; i < ckCouponStlist.size(); i++) {
			if (list.get(i).getChalHistoryNo() == ckCouponStlist.get(i).getChalHistoryNo()) {
				list.get(i).setCkCouponSt(ckCouponStlist.get(i).getCkCouponSt());
			} else {
				break;
			}
		}

		return ResponseEntity.ok(list);

	}

	@PutMapping("/challengeSuccess")
	public int challengeSucces(@RequestBody HashMap chalSuccessInfo) {

		int result = mService.challengeSuccess(chalSuccessInfo);

		if (result > 0) {
			/* 챌린지 성공 */
			return result;
		} else {
			/* 챌린지 실패 */
			return result;
		}
	}

	@GetMapping("/couponSelect")
	public ResponseEntity<List<CouponDTO>> couponSelect(@RequestParam String userMemSq) {

		List<CouponDTO> list = mService.couponSelect(userMemSq);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/reservationSelect")
	public ResponseEntity<List<ReservationSelectDTO>> reservationSelect(@RequestParam String userMemSq) {

		List<ReservationSelectDTO> list = mService.reservationSelect(userMemSq);
		return ResponseEntity.ok(list);
	}

	@PutMapping("/reservationCancelUpdate")
	public int reservationCancelUpdate(@RequestBody ReservationSelectDTO cancelInfo) {

		int result = mService.reservationCancelUpdate(cancelInfo);
		return result;
	}

}
