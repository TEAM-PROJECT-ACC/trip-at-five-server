package com.kh.clock.member.controller;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.clock.member.repository.RegisterDTO;
import com.kh.clock.member.service.RegisterServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class MemberRegisterController {

	private final RegisterServiceImpl mService;
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

		String nick = (String) requestBody.get("nick");

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

		System.out.println(registerdto);

		String encPwd = bCryptPasswordEncoder.encode(registerdto.getPwd());
		System.out.println("encPwd : " + encPwd);

		registerdto.setPwd(encPwd);

		int result = mService.registerSend(registerdto);

		if (result > 0) {
			registerdto.setNickCount(result);
		}

		return result;

	}

}
