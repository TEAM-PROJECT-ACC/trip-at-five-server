package com.kh.clock.common.mailAuth.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.clock.common.mailAuth.service.MailService;
import com.kh.clock.member.repository.RegisterDTO;
import com.kh.clock.member.service.MemberService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailAuthContorller {

	private final MailService mailService;
	private final MemberService mService;

	@PostMapping("/emailDuplication")
	public int emailDuplicationCheck(@RequestBody Map<String, Object> requestBody) {

		String email = (String) requestBody.get("email");
		RegisterDTO register = new RegisterDTO();

		int result = mService.emailDuplicationCheck(email);

		/* 중복 확인 1: 중복 0: 미중복 */
		if (result > 0) {
			register.setEmailCount(result);
		}

		return register.getEmailCount();
	}

	@PostMapping("/send")
	public String sendEmailCode(@RequestBody Map<String, Object> requestBody) {

		String email = (String) requestBody.get("email");
		System.out.println(email);
		try {
			mailService.sendMail(email);
		} catch (MessagingException e) {
			return "no"; //
		}

		return "ok"; //
	}

	@PostMapping("/verify")
	public String verifyEmailCode(@RequestBody Map<String, Object> requestBody) {
		String email = (String) requestBody.get("email");
		String code = (String) requestBody.get("code");

		System.out.println(email + " " + code);

		boolean result = mailService.verifyCode(email, code);
		System.out.println(result);

		return result ? "sussess" : "failed";
	}

}
