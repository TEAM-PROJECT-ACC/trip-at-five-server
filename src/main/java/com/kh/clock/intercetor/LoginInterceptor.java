package com.kh.clock.intercetor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.clock.member.domain.AdminVO;
import com.kh.clock.member.domain.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 *  세션에 로그인 정보가 있을 경우, 컨트롤러 동작.
 *    				 없을 경우, 응답헤더에 401 코드를 담아서 컨트롤러를 동작시키지 않음
 * 
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 세션에 로그인 정보가 있는 지 확인
		HttpSession session = request.getSession();
		MemberVO user = (MemberVO) session.getAttribute("loginUser");
		AdminVO admin = (AdminVO) session.getAttribute("adminUser");

		if (admin == null) {
			if (user == null) {

				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				return false;
			} else {
				return true;
			}

		} else {
			return true;
		}

	}
}
