package com.sparta.booker.domain.main.controller;

import com.sparta.booker.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final UserService userService;

	@GetMapping("/login")
	public String login(){
		return "login";
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}

	@GetMapping("/event")
	public String event() {
		return "event";
	}

	@GetMapping("/mypage")
	public String mypage(){return "mypage";}
}
