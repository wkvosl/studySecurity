package com.study.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.security.model.User;
import com.study.security.service.UserService;

@Controller
public class myController {

	@Autowired
	private UserService userService;

	@RequestMapping("/home")
	public String home() {
		return "home";
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody User user) {
		
		if(userService.login(user.getId(), 
				user.getPassword()).equals("Success")) {
		return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	// -----------------------------
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	// 폼에서 가져온 이름(payload)으로 User에 담기게
	@PostMapping("/register")
	public String register(User user) {
		// 사용자권한 및 패스워드 암호화
		userService.save(user);
		return "redirect:/";
	}
}
