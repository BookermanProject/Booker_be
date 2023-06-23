package com.sparta.booker.domain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {


	@GetMapping("/login")
	public String login(){
		return "login";
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}

	@PostMapping("/evnet")
	@ResponseBody
	public String evnet() {
		return "main";
	}

}
