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
import com.kh.clock.member.repository.LoginDTO;
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
		System.out.println(userInfo);
		System.out.println(loginUser);

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
	public ResponseEntity<List<ChallengHistoryDTO>> getChallengeList(@RequestParam String userMemSq) {

		List<ChallengHistoryDTO> list = mService.getChallengeUserList(userMemSq);

		return ResponseEntity.ok(list);
	}

	@PutMapping("/challengeSucces")
	public int challengeSucces(@RequestBody HashMap chalSuccessInfo) {

		System.out.println(chalSuccessInfo);

		int result = mService.challengeSucces(chalSuccessInfo);

		if (result > 0) {
			/* 챌린지 성공 */
			return result;
		} else {
			/* 챌린지 실패 */
			return result;
		}
	}

}
