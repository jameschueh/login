package com.james.P20241018.filter;

import com.james.P20241018.service.MemberService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    private final MemberService memberService;

    // 需要一個接收 MemberService 的建構子
    public AuthFilter(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化邏輯（可選）
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = httpRequest.getContextPath() + "/login";
        
        // 如果用戶未登入且請求非公開資源，則重定向到登入頁面
        if (!loggedIn && !requestURI.equals(loginURI)) {
            httpResponse.sendRedirect(loginURI);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 過濾器銷毀時的清理邏輯（可選）
    }
}
