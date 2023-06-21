package com.sparta.booker.domain.main.controller;

import com.sparta.booker.domain.search.elastic.service.ElasticSearchBookService;
import com.sparta.booker.domain.search.querydsl.dto.LikeBookDto;
import com.sparta.booker.domain.search.querydsl.service.MySQLBookService;
import com.sparta.booker.domain.search.querydsl.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final ElasticSearchBookService elasticSearchBookService;

	private final MySQLBookService mySQLBookService;

	private final RedisUtil redisUtil;

	@GetMapping("/login")
	public String login(){
		return "login";
	}

	@GetMapping("/main")
	public String main(Model model, Pageable pageable) {
		model.addAttribute("bookList", mySQLBookService.getBookList(pageable));
		model.addAttribute("topLikeList", redisUtil.SearchList());
		return "main";
	}

	@PostMapping("/realtime")
	@ResponseBody
	public List<String> realtime() {
		return redisUtil.SearchRankList();
	}

	@PostMapping("/liketop")
	@ResponseBody
	public List<LikeBookDto> liketop() {
		return redisUtil.SearchList();
	}

	@PostMapping("/evnet")
	@ResponseBody
	public String evnet() {
		return "main";
	}

}
