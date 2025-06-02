package com.kh.clock.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.clock.member.repository.RegisterDTO;
import com.kh.clock.member.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class MemberController {

	private final MemberServiceImpl mService;

	@PostMapping("/emailDuplication")
	public int emailDuplicationCheck(@RequestBody Map<String, Object> requestBody) {

		String email = (String) requestBody.get("email");
		RegisterDTO r = new RegisterDTO();

		int result = mService.emailDuplicationCheck(email);

		if (result > 0) {
			r.setEmailCount(result);
		}

		return r.getEmailCount();
	}

	@PostMapping("/nickNameDuplicationCheck")
	public int nickNameDuplicationCheck(@RequestBody Map<String, Object> requestBody) {

		String nick = (String) requestBody.get("nick");

		System.out.println(nick);

		RegisterDTO r = new RegisterDTO();

		int result = mService.nickNameDuplicationCheck(nick);

		System.out.println(result);

		if (result > 0) {
			r.setNickCount(result);
		}

		return r.getNickCount();

	}

	@PostMapping("/send")
	public int registerSend(@RequestBody RegisterDTO registerdto) {

		System.out.println(registerdto);

		RegisterDTO register = registerdto;

		int result = mService.registerSend(register);

		System.out.println(result);

//		if (result > 0) {
//			r.setNickCount(result);
//		}

		return 2;

	}
}
