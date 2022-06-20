package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
public class UserController {
	
	//필드
	@Autowired
	private UserService userService;
	
	
	//회원가입폼
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {
		System.out.println("UserController.joinForm()");

		return "user/joinForm";
	}

	//회원가입
	@RequestMapping(value = "/joinCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinOk(@ModelAttribute UserVo userVo) {
		System.out.println("UserController.joinCheck()");

		userService.join(userVo);

		return "user/joinOk";
	}
	
	//로그인
	@RequestMapping(value = "/user/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("UserController.login()");
		
		UserVo authUser = userService.login(userVo);
		
		/* 세션에 저장 (수명이길다)*/
		if(authUser !=null) { //로그인 성공
			System.out.println("로그인 성공");
			session.setAttribute("authUser", authUser);
			return "redirect:/main";
		}else {
			System.out.println("로그인 실패");
			return "redirect:/user/loginForm?result=fail";
		}
		
		
		
	}

	//로그인폼
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("UserController.loginForm()");

		return "user/loginForm";
	}
	
	
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm() {
		System.out.println("UserController.modifyForm()");

		return "user/modifyForm";
	}

}