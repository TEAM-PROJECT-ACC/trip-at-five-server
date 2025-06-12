package com.kh.clock.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.clock.member.domain.MemberVO;
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
	

}
