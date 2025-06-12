package com.kh.clock.member.controller;

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

		if (result > 0) {
			register.setEmailCount(result);
		}

		return register.getEmailCount();
	}

	/* 닉네임 중복 체크 */
	@PostMapping("/nickNameDuplicationCheck")
	public int nickNameDuplicationCheck(@RequestBody Map<String, Object> requestBody) {

		String nick = (String) requestBody.get("nickName");
		System.out.println(nick);

		RegisterDTO register = new RegisterDTO();

		int result = mService.nickNameDuplicationCheck(nick);
		
		System.out.println(result);

		if (result > 0) {
			register.setNickCount(result);
		}

		return register.getNickCount();

	}

	/* 회원가입 */
	@PostMapping("/send")
	public int registerSend(@RequestBody RegisterDTO registerdto) throws Exception {

		System.out.println(registerdto);

		String encPwd = bCryptPasswordEncoder.encode(registerdto.getPwd());

		registerdto.setPwd(encPwd);

		int result = mService.registerSend(registerdto);


		if (result > 0) {
			/* 가입 완료 */
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setEmail(registerdto.getEmail());
			
			MemberVO loginUser = mService.userInfo(loginDTO);
			List<ChallengeVO> items = mService.getChallengeList();
			int test = mService.insetUserChallengeList(items);
			
			
			return result;
		} else {
			/* 가입 실패 */
			return result;
		}

	}

}
