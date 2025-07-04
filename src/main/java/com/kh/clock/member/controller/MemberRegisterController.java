package com.kh.clock.member.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.clock.member.domain.ChallengeVO;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.ChallengeHistoryCreateDTO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.RegisterDTO;
import com.kh.clock.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class MemberRegisterController {

	@Autowired
	private final MemberService mService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	/* 이메일 중복 체크 */
	@PostMapping("/emailDuplication")
	public int emailDuplicationCheck(@RequestBody Map<String, Object> requestBody) {

		String email = (String) requestBody.get("email");
		RegisterDTO register = new RegisterDTO();

		int result = mService.emailDuplicationCheck(email);

		System.out.println(result);
		if (result > 0) {
			register.setEmailCount(result);
		}

		
		return register.getEmailCount();
	}

	/* 닉네임 중복 체크 */
	@PostMapping("/nickNameDuplicationCheck")
	public int nickNameDuplicationCheck(@RequestBody Map<String, Object> requestBody) {

		String nick = (String) requestBody.get("nickName");

		RegisterDTO register = new RegisterDTO();

		int result = mService.nickNameDuplicationCheck(nick);

		if (result > 0) {
			register.setNickCount(result);
		}

		return register.getNickCount();

	}

	/* 회원가입 */
	@PostMapping("/send")
	public int registerSend(@RequestBody RegisterDTO registerdto) throws Exception {

		String encPwd = bCryptPasswordEncoder.encode(registerdto.getPwd());

		registerdto.setPwd(encPwd);

		int result = mService.registerSend(registerdto);

		if (result > 0) {

			/* 가입 완료 */

			return challAndMemLevelCreate(registerdto);

		} else {
			/* 가입 실패 */
			return result;
		}

	}

	public int challAndMemLevelCreate(RegisterDTO registerdto) {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmail(registerdto.getEmail());

		MemberVO loginUser = mService.userInfo(loginDTO); // 회원 번호 확인
		List<Object> numlist = mService.getChallengeCountNo(); // 챌린지 각 번호 확인
		List<ChallengeHistoryCreateDTO> list = new ArrayList<>();
		int memLevelResult = mService.memberLevelSetting(loginUser.getMemSq());
		
		
		
		for (int i = 0; i < numlist.size(); i++) {

			list.add(new ChallengeHistoryCreateDTO( loginUser.getMemSq(),(int) numlist.get(i)));
		}

		int chcN = mService.insertUserChallengeList(list);

		if (chcN > 1) {
			return 1;
		}

		return 0;

	}

}
