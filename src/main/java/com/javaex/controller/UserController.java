package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	// 필드
	@Autowired
	private UserService userService;

	// 로그인폼
	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("UserController>loginForm()");

		return "/user/loginForm";
	}

	// 로그인
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
		public String login(@ModelAttribute UserVo userVo, HttpSession session) {
			System.out.println("UserController>login()");
			
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
	
	//로그아웃			
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
		public String logout(HttpSession session) {
			System.out.println("UserController > logout");
				
			session.removeAttribute("/logout");
			session.invalidate();
				
			return "redirect:/main";
			}

	// 회원가입폼
	@RequestMapping(value = "/joinForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {
		System.out.println("UserController>joinForm()");

		return "/user/joinForm";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("UserController>joinCheck()");

		// userService.userInsert(userVo);

		return "redirect:/user/joinOk";
	}

	// 등록성공
	@RequestMapping(value = "/joinOk", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinOk() {
		System.out.println("UserController>joinOk");

		return "user/joinOk";
	}

	// 정보수정폼
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(HttpSession session, Model model) {
		System.out.println("UserController>modifyForm");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		// model.addAllAttributes("userVo", userVo);

		return "/user/modifyForm";
	}

	// 정보수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(HttpSession session, Model model) {
		System.out.println("UserController>modify");

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		// userService.userUpdate(userVo);
		// authUser = userService.maintain(userVo.getNo());
		session.setAttribute("authUser", authUser);

		return "redirect:/main";
	}
}