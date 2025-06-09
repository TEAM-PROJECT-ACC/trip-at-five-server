package com.kh.clock.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
class MemLoginController {

	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final MemberService mService;

	@PostMapping("/nomal")
	public String nomalLogin(@RequestBody LoginDTO userInfo, HttpServletRequest request, HttpSession session)
			throws Exception {

		MemberVO loginUser = mService.loginInfo(userInfo);

		System.out.println(loginUser);
		if (loginUser == null) {

			return "IdFail";
		} else if (!bCryptPasswordEncoder.matches(userInfo.getPwd(), loginUser.getMemPwd())) {

			return "pwdFail";
		} else {
			session = request.getSession();
			session.setAttribute("loginUser", loginUser);

			String result = Integer.toString(loginUser.getMemSq());
			return result;
		}

	}

	@PostMapping("/kakao")
	public String kakaoLogin(@RequestBody String kakaoInfo, HttpServletRequest request, HttpSession session) {

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

		int register = mService.snsRegisterSelect(kakaoLoginDTO);
		MemberVO loginUser = mService.loginInfo(kakaoLoginDTO);

		System.out.println(register);

		if (loginUser == null) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			kakaoLoginDTO.setPwd(encPwd);
			kakaoLoginDTO.setNickName(nickName);
			kakaoLoginDTO.setSnsUid(kakaoUid);

			System.out.println("회원가입 쪽 : " + kakaoLoginDTO);
			mService.snsRegister(kakaoLoginDTO);

		} else if (!loginUser.getCkSocPlt().equals("KAKAO")) {

			return loginUser.getCkSocPlt();
		}

		session = request.getSession();
		session.setAttribute("loginUser", loginUser);
		return Integer.toString(loginUser.getMemSq());

	}

	@PostMapping("/naver")
	public String naverLogin(@RequestBody String naverInfo, HttpServletRequest request, HttpSession session) {

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

		int register = mService.snsRegisterSelect(naverLoginDTO);
		MemberVO loginUser = mService.loginInfo(naverLoginDTO);

		System.out.println(register);
		System.out.println(loginUser);

		if (loginUser == null) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			naverLoginDTO.setPwd(encPwd);
			naverLoginDTO.setNickName(nickName);
			naverLoginDTO.setSnsUid(naverUid);

			System.out.println("회원가입 쪽 : " + naverLoginDTO);
			mService.snsRegister(naverLoginDTO);

		} else if (!loginUser.getCkSocPlt().equals("NAVER")) {

			return loginUser.getCkSocPlt();
		}

		session = request.getSession();
		session.setAttribute("loginUser", loginUser);
		return Integer.toString(loginUser.getMemSq());

	}

	@PostMapping("/google")
	public String googleLogin(@RequestBody String googleInfo, HttpServletRequest request, HttpSession session) {

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

		int register = mService.snsRegisterSelect(googleLoginDTO);
		MemberVO loginUser = mService.loginInfo(googleLoginDTO);

		System.out.println(register);
		System.out.println(loginUser);

		if (loginUser == null) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			googleLoginDTO.setPwd(encPwd);
			googleLoginDTO.setNickName(name);
			googleLoginDTO.setSnsUid(naverUid);

			System.out.println("회원가입 쪽 : " + googleLoginDTO);
			mService.snsRegister(googleLoginDTO);

		} else if (!loginUser.getCkSocPlt().equals("GOOGLE")) {

			return loginUser.getCkSocPlt();
		}

		session = request.getSession();
		session.setAttribute("loginUser", loginUser);
		return Integer.toString(loginUser.getMemSq());

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 세션영역 비우기
		session.invalidate();

		return "ok";
	}

}
