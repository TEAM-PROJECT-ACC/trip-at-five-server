package com.kh.clock.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
class MemLoginController {

	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final LoginService lService;

	@PostMapping("/nomal")
	public int nomalLogin(@RequestBody LoginDTO userInfo, HttpServletRequest request, HttpSession session)
			throws Exception {

		MemberVO loginUser = lService.loginInfo(userInfo);

		if (loginUser != null) {

			return loginUser.getMemSq();
		}

		return 0;

	}

	@PostMapping("/kakao")
	public int kakaoLogin(@RequestBody String kakaoInfo, HttpServletRequest request, HttpSession session) {

		JsonObject kakaoObj = JsonParser.parseString(kakaoInfo).getAsJsonObject();

		JsonObject kakaodata = kakaoObj.getAsJsonObject("data");
		JsonObject kakaoAccount = kakaodata.getAsJsonObject("kakao_account");
		JsonObject kakaoProfile = kakaoAccount.getAsJsonObject("profile");

		String kakaoUid = kakaodata.get("id").getAsString();
		String email = kakaoAccount.get("email").getAsString();
		String nickName = kakaoProfile.get("nickname").getAsString();

		LoginDTO kakaoLoginDTO = new LoginDTO();
		kakaoLoginDTO.setEmail(email);
		kakaoLoginDTO.setPlatformName("KAKAO");
		System.out.println(kakaoLoginDTO);

		int result = lService.snsRegisterSelect(kakaoLoginDTO);
		System.out.println(result);

		if (result == 0) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			kakaoLoginDTO.setPwd(encPwd);
			kakaoLoginDTO.setNickName(nickName);
			kakaoLoginDTO.setSnsUid(kakaoUid);

			System.out.println("회원가입 쪽 : " + kakaoLoginDTO);
			lService.snsRegister(kakaoLoginDTO);

		}

		MemberVO loginUser = lService.loginInfo(kakaoLoginDTO);
		return loginUser.getMemSq();

	}

	@PostMapping("/naver")
	public int naverLogin(@RequestBody String naverInfo) {

		JsonObject naverObj = JsonParser.parseString(naverInfo).getAsJsonObject();

		System.out.println(naverObj);

		JsonObject naverodata = naverObj.getAsJsonObject("data");
		JsonObject naverResponse = naverodata.getAsJsonObject("response");

		System.out.println(naverResponse);
		String naverUid = naverResponse.get("id").getAsString();
		String email = naverResponse.get("email").getAsString();
		String nickName = naverResponse.get("nickname").getAsString();

		System.out.println("naverUid " + naverUid);
		System.out.println("email " + email);
		System.out.println("nickName " + nickName);

		LoginDTO naverLoginDTO = new LoginDTO();
		naverLoginDTO.setEmail(email);
		naverLoginDTO.setPlatformName("NAVER");

		System.out.println(naverLoginDTO);

		int result = lService.snsRegisterSelect(naverLoginDTO);
		System.out.println(result);

		if (result == 0) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			naverLoginDTO.setPwd(encPwd);
			naverLoginDTO.setNickName(nickName);
			naverLoginDTO.setSnsUid(naverUid);

			lService.snsRegister(naverLoginDTO);

		}
		
		MemberVO loginUser = lService.loginInfo(naverLoginDTO);
		return loginUser.getMemSq();

	}

	@PostMapping("/google")
	public int googleLogin(@RequestBody String googleInfo) {

		JsonObject googleObj = JsonParser.parseString(googleInfo).getAsJsonObject();

		JsonObject naverodata = googleObj.getAsJsonObject("data");

		System.out.println(naverodata);
		String naverUid = naverodata.get("sub").getAsString();
		String email = naverodata.get("email").getAsString();
		String name = naverodata.get("given_name").getAsString();

		System.out.println("googleUid " + naverUid);
		System.out.println("email " + email);
		System.out.println("nickName " + name);

		LoginDTO googleLoginDTO = new LoginDTO();
		googleLoginDTO.setEmail(email);
		googleLoginDTO.setPlatformName("GOOGLE");

		int result = lService.snsRegisterSelect(googleLoginDTO);
		System.out.println(result);

		if (result == 0) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			googleLoginDTO.setPwd(encPwd);
			googleLoginDTO.setNickName(name);
			googleLoginDTO.setSnsUid(naverUid);

			lService.snsRegister(googleLoginDTO);
			MemberVO loginUser = lService.loginInfo(googleLoginDTO);
			System.out.println("회원가입 쪽 : " + googleLoginDTO);

			return loginUser.getMemSq();

		} else {
			return 0;
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		return "ok";
	}

}
