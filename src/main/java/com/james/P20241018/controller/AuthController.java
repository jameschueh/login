package com.james.P20241018.controller;

import com.james.P20241018.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.P20241018.model.ApiResponse;
import com.james.P20241018.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedList;

@Controller
public class AuthController {

    private final MemberService memberService;

    @Autowired
    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 顯示登入頁面
    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        String successMessage = (String) session.getAttribute("successMessage");
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }

        return "auth/login";
    }

    // 處理傳統登入
    @PostMapping("/login")
    public String processLogin(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Member member = memberService.login(account, password);
        if (member != null) {
            session.setAttribute("user", member);
            return "redirect:/index";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "帳號或密碼錯誤！");
            return "redirect:/login";
        }
    }

    // 處理 AJAX 登入
    @PostMapping("/ajaxLogin")
    public void processAjaxLogin(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            HttpSession session,
            HttpServletResponse response) throws IOException {

        Member member = memberService.login(account, password);
        ApiResponse<Member> apiResponse;

        if (member != null) {
            session.setAttribute("user", member);
            apiResponse = new ApiResponse<>("success", "登入成功", member);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            apiResponse = new ApiResponse<>("fail", "帳號或密碼錯誤", null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 清除 session
        return "redirect:/login"; // 登出後重定向到登入頁面
    }
}
