package com.james.P20241018.config;

import com.james.P20241018.filter.AuthFilter;
import com.james.P20241018.filter.CSPFilter;
import com.james.P20241018.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private MemberService memberService;

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter(memberService));
        registrationBean.addUrlPatterns("/*"); // 過濾所有路徑
        registrationBean.setOrder(1); // 設定執行順序，數字越小優先級越高
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CSPFilter> cspFilter() {
        FilterRegistrationBean<CSPFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CSPFilter());
        registrationBean.addUrlPatterns("/*"); // 套用於所有頁面
        registrationBean.setOrder(2); // 確保 CSPFilter 在 AuthFilter 之後執行
        return registrationBean;
    }

}
