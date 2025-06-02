package com.kh.clock.common.mailAuth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kh.clock.common.mailAuth.domain.AuthDataVo;
import com.kh.clock.config.SecurityConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    private final SecurityConfig securityConfig;

	private final JavaMailSender sender;

	public MailService(JavaMailSender sender, SecurityConfig securityConfig) {
		this.sender = sender;

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> clearData(), 0, 1, TimeUnit.MINUTES);
		this.securityConfig = securityConfig;

	}

	// 이메일, 인증 정보(인증코드, 유효시간)를 관리하기 위한 Map 추가
	private Map<String, AuthDataVo> authInfo = new HashMap<>();

	public void sendMail(String email) throws MessagingException {

		// 인증 정보 생성 --> AuthData
		AuthDataVo authData = new AuthDataVo();
		String code = authData.getCode();	

	    System.out.println(code);
		// 메일 내용 작성
		String subject = "[여행다섯시] 인증 코드";
		String[] to = { email };
		String content = "<p>아래의 인증코드를 입력해주세요.</p>" + "<h3>" + code + "</h3>" + "<p>인증코드는 3분간 유효합니다..</p>";

		// 메일 전송
		MimeMessage mm = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm);

		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(content, true);

		sender.send(mm);

		// 이메일, 인증 정보를 Map에 추가
		authInfo.put(email, authData);
	}

	public boolean verifyCode(String email, String code) {
		// Map에서 email에 해당하는 이증 저옵가 있는 지 체크

		AuthDataVo authData = authInfo.get(email);
		
		System.out.println("email : "+email+" code: "+code);
		System.out.println("authData: " + authData);

		if (authData == null)
			return false;

		long currTime = System.currentTimeMillis();
		if (currTime > authData.getTime()) {
			authInfo.remove(email);
			return false;
		}
		
		
		if (!authData.getCode().equals(code)) {
			authInfo.remove(email);
			return false;
		}
		

//			 if (code.equals(authData.getCode())) {
//				 authInfo.remove(email);
//				 return true;
//			 } else { 
//				 return false;
//			 }

		if (code.equals(authData.getCode())) {
			authInfo.remove(email);
		}
		return true;

	}

	private void clearData() {
		long currTime = System.currentTimeMillis();

		authInfo.entrySet().removeIf(entry -> currTime > entry.getValue().getTime());
	}
}
