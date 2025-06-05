package com.kh.clock.member.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.clock.member.repository.SnsLoginDTO;
import com.kh.clock.member.service.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
class LoginController {
	
	


	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final LoginService lService;

	@PostMapping("/kakao")
	public String kakaoLogin(@RequestBody String kakaoInfo) {
	
		JsonObject kakaoObj = JsonParser.parseString(kakaoInfo).getAsJsonObject();

		JsonObject kakaodata = kakaoObj.getAsJsonObject("data");
		JsonObject kakaoAccount = kakaodata.getAsJsonObject("kakao_account");
		JsonObject kakaoProfile = kakaoAccount.getAsJsonObject("profile");

		String kakaoUid = kakaodata.get("id").getAsString();
		String email = kakaoAccount.get("email").getAsString();
		String nickName = kakaoProfile.get("nickname").getAsString();
		

		SnsLoginDTO kakaoLoginDTO = new SnsLoginDTO();
		kakaoLoginDTO.setEmail(email);
		
		System.out.println(kakaoLoginDTO);
		
		int result = lService.kakaoRegisterSelect(kakaoLoginDTO);		
		System.out.println(result);
		
		if (result == 0 ) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);
			
			kakaoLoginDTO.setPwd(encPwd);
			kakaoLoginDTO.setNickName(nickName);
			kakaoLoginDTO.setSnsUid(kakaoUid);
			kakaoLoginDTO.setPlatformName("KAKAO");
			
			System.out.println("회원가입 쪽 : "+kakaoLoginDTO);
			int test = lService.kakaoRegister(kakaoLoginDTO);
			
			return "ok";
			
		} else {
			return "login";
		}
		

//		return "kakao";
	}
	
	@Value("${jwt.secret.key}")
	private  String testkey;
	

	@PostMapping("test")
	public String JwtTest() {
		

		
		return "key";
	}



}
