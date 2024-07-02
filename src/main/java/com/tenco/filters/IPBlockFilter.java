package com.tenco.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
/*
 *  1. Filter 구현
 *  2. URL 패턴 설정 (web.xml 파일에서 설정할 예정)
 */
public class IPBlockFilter implements Filter{
	//192.168.0.48
	
	// 차단할 IP 대역의 접두사
	private static final String BLOCKED_IP_PREFIX = "192.168.0.";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("IPBlockFilter 초기화");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 전처리 - 요청자의 IP를 확인
		String remoteIP =  request.getRemoteAddr();
		System.out.println("Request from IP : " + remoteIP);
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURL = httpRequest.getRequestURL().toString();
		// 차단시킬 코드 작성
		if(remoteIP.startsWith(BLOCKED_IP_PREFIX)) {
			System.out.println("부러쓰부러쓰 차단시켜부러쓰");
			response.setContentType("text/plain; charset=UTF-8");
			response.getWriter().println("여기 들어온 사람들 다 파란색");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
	
	
}
