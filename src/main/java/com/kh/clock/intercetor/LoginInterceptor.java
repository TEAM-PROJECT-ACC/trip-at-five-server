package com.kh.clock.intercetor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
public class LoginInterceptor implements HandlerInterceptor  {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 세션에 로그인 정보가 있는 지 확인
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		System.out.println("로그인 인터셉터");
		System.out.println(loginUser);
		
		if (loginUser == null ) { // 로그인 정보가 없는 경우
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().print("로그인 후 이용 가능합니다.");
			
			return false;
		} else {
		
			return true;
			
		}
	
	}
}
