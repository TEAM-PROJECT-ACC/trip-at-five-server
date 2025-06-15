package com.kh.clock.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.ChallengeVO;
import com.kh.clock.member.domain.MemberVO;
import com.kh.clock.member.repository.ChallengeHistoryCreateDTO;
import com.kh.clock.member.repository.LoginDTO;
import com.kh.clock.member.repository.RegisterDTO;
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

	@PostMapping("/normal")
	public ResponseEntity<HashMap<String, Object>> nomalLogin(@RequestBody LoginDTO userInfo,
			HttpServletRequest request, HttpSession session) throws Exception {

		MemberVO loginUser = mService.userInfo(userInfo);
		HashMap<String, Object> hashMap = new HashMap<>();

		if (loginUser == null) {

			hashMap.put("IdFail", "IdFail");

			return ResponseEntity.ok(hashMap);

		} else if (loginUser.getCkMemSt().equals("INACTIVE")) {

			hashMap.put("INACTIVE", "INACTIVE");

			return ResponseEntity.ok(hashMap);
		}

		else if (!bCryptPasswordEncoder.matches(userInfo.getPwd(), loginUser.getMemPwd())) {

			hashMap.put("pwdFail", "pwdFail");
			return ResponseEntity.ok(hashMap);
		} else {
			session = request.getSession();
			session.setAttribute("loginUser", loginUser);

			hashMap.put("memSq", loginUser.getMemSq());
			hashMap.put("memEmailId", loginUser.getMemEmailId());
			hashMap.put("memNick", loginUser.getMemNick());
			hashMap.put("memType", "user");
			hashMap.put("memLvl", loginUser.getMemLvl());
			return ResponseEntity.ok(hashMap);
		}

	}

	@PostMapping("/admin")
	public ResponseEntity<HashMap<String, Object>> adminLogin(@RequestBody LoginDTO userInfo,
			HttpServletRequest request, HttpSession session) throws Exception {

		AdminVO loginUser = mService.adminInfo(userInfo);
		HashMap<String, Object> hashMap = new HashMap<>();

		if (loginUser == null) {

			hashMap.put("IdFail", "IdFail");

			return ResponseEntity.ok(hashMap);

		} else if (!userInfo.getPwd().equals(loginUser.getAdminPwd())) {

			hashMap.put("pwdFail", "pwdFail");
			return ResponseEntity.ok(hashMap);
		} else {
			session = request.getSession();
			session.setAttribute("adminUser", loginUser);

			hashMap.put("adminSq", loginUser.getAdminSq());
			hashMap.put("adminEmailId", loginUser.getAdminEmailId());
			hashMap.put("inqCtgCd", loginUser.getInqCtgCd());
			hashMap.put("memType", "admin");
			return ResponseEntity.ok(hashMap);
		}

	}

	@PostMapping("/kakao")
	public ResponseEntity<HashMap<String, Object>> kakaoLogin(@RequestBody String kakaoInfo, HttpServletRequest request,
			HttpSession session) {

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

		MemberVO loginUser = mService.userInfo(kakaoLoginDTO);
		HashMap<String, Object> hashMap = new HashMap<>();

		if (loginUser == null) {

		}

		if (loginUser == null) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			kakaoLoginDTO.setPwd(encPwd);
			kakaoLoginDTO.setNickName(nickName);
			kakaoLoginDTO.setSnsUid(kakaoUid);

			mService.snsRegister(kakaoLoginDTO);
			loginUser = mService.userInfo(kakaoLoginDTO);

			challAndMemLevelCreate(kakaoLoginDTO.getEmail());

		} else if (loginUser.getCkMemSt().equals("INACTIVE")) {
			hashMap.put("INACTIVE", "INACTIVE");

			return ResponseEntity.ok(hashMap);
		}

		else if (!loginUser.getCkSocPlt().equals("KAKAO")) {

			hashMap.put("ckSocPlt", loginUser.getCkSocPlt());

			return ResponseEntity.ok(hashMap);
		} else {

			hashMap.put("memSq", loginUser.getMemSq());
			hashMap.put("memEmailId", loginUser.getMemEmailId());
			hashMap.put("memNick", loginUser.getMemNick());
			hashMap.put("memType", "kakaoUser");
			hashMap.put("memLvl", loginUser.getMemLvl());

			session = request.getSession();
			session.setAttribute("loginUser", loginUser);
		}

		return ResponseEntity.ok(hashMap);
	}

	@PostMapping("/naver")
	public ResponseEntity<HashMap<String, Object>> naverLogin(@RequestBody String naverInfo, HttpServletRequest request,
			HttpSession session) {

		JsonObject naverObj = JsonParser.parseString(naverInfo).getAsJsonObject();

		JsonObject naverodata = naverObj.getAsJsonObject("data");
		JsonObject naverResponse = naverodata.getAsJsonObject("response");

		String naverUid = naverResponse.get("id").getAsString();
		String email = naverResponse.get("email").getAsString();
		String nickName = naverResponse.get("nickname").getAsString();

		LoginDTO naverLoginDTO = new LoginDTO();
		naverLoginDTO.setEmail(email);
		naverLoginDTO.setPlatformName("NAVER");

		MemberVO loginUser = mService.userInfo(naverLoginDTO);
		HashMap<String, Object> hashMap = new HashMap<>();

		if (loginUser == null) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			naverLoginDTO.setPwd(encPwd);
			naverLoginDTO.setNickName(nickName);
			naverLoginDTO.setSnsUid(naverUid);

			mService.snsRegister(naverLoginDTO);
			loginUser = mService.userInfo(naverLoginDTO);
			challAndMemLevelCreate(naverLoginDTO.getEmail());

		} else if (loginUser.getCkMemSt().equals("INACTIVE")) {
			hashMap.put("INACTIVE", "INACTIVE");

			return ResponseEntity.ok(hashMap);
		} else if (!loginUser.getCkSocPlt().equals("NAVER")) {

			hashMap.put("ckSocPlt", loginUser.getCkSocPlt());

			return ResponseEntity.ok(hashMap);
		} else {

			hashMap.put("memSq", loginUser.getMemSq());
			hashMap.put("memEmailId", loginUser.getMemEmailId());
			hashMap.put("memNick", loginUser.getMemNick());
			hashMap.put("memType", "naverUser");
			hashMap.put("memLvl", loginUser.getMemLvl());

			session = request.getSession();
			session.setAttribute("loginUser", loginUser);
		}

		return ResponseEntity.ok(hashMap);
	}

	@PostMapping("/google")
	public ResponseEntity<HashMap<String, Object>> googleLogin(@RequestBody String googleInfo,
			HttpServletRequest request, HttpSession session) {

		JsonObject googleObj = JsonParser.parseString(googleInfo).getAsJsonObject();

		JsonObject naverodata = googleObj.getAsJsonObject("data");

		String naverUid = naverodata.get("sub").getAsString();
		String email = naverodata.get("email").getAsString();
		String name = naverodata.get("given_name").getAsString();

		LoginDTO googleLoginDTO = new LoginDTO();
		googleLoginDTO.setEmail(email);
		googleLoginDTO.setPlatformName("GOOGLE");

		MemberVO loginUser = mService.userInfo(googleLoginDTO);
		HashMap<String, Object> hashMap = new HashMap<>();

		if (loginUser == null) {
			/* 회원가입처리 */
			String tempPassword = "kakao_" + System.currentTimeMillis();
			String encPwd = bCryptPasswordEncoder.encode(tempPassword);

			googleLoginDTO.setPwd(encPwd);
			googleLoginDTO.setNickName(name);
			googleLoginDTO.setSnsUid(naverUid);

			mService.snsRegister(googleLoginDTO);
			challAndMemLevelCreate(googleLoginDTO.getEmail());

		} else if (loginUser.getCkMemSt().equals("INACTIVE")) {
			hashMap.put("INACTIVE", "INACTIVE");

			return ResponseEntity.ok(hashMap);
		} else if (!loginUser.getCkSocPlt().equals("GOOGLE")) {

			hashMap.put("ckSocPlt", loginUser.getCkSocPlt());

			return ResponseEntity.ok(hashMap);
		} else {

			hashMap.put("memSq", loginUser.getMemSq());
			hashMap.put("memEmailId", loginUser.getMemEmailId());
			hashMap.put("memNick", loginUser.getMemNick());
			hashMap.put("memType", "googleUser");
			hashMap.put("memLvl", loginUser.getMemLvl());

			session = request.getSession();
			session.setAttribute("loginUser", loginUser);
		}

		return ResponseEntity.ok(hashMap);

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 세션영역 비우기
		session.invalidate();

		return "ok";
	}

	public int challAndMemLevelCreate(String email) {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmail(email);

		MemberVO loginUser = mService.userInfo(loginDTO); // 회원 번호 확인
		List<Object> numlist = mService.getChallengeCountNo(); // 챌린지 각 번호 확인

		ChallengeVO chVo = new ChallengeVO();

		List<ChallengeHistoryCreateDTO> list = new ArrayList<>();

		for (int i = 0; i < numlist.size(); i++) {

			list.add(new ChallengeHistoryCreateDTO((int) numlist.get(i), loginUser.getMemSq()));
		}

		int memLevelResult = mService.memberLevelSetting(loginUser.getMemSq());
		int chcN = mService.insertUserChallengeList(list);
		System.out.println(chcN);

		if (chcN > 1) {
			return 1;
		}

		return 0;

	}

}
