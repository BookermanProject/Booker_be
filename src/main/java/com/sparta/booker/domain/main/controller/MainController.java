package com.sparta.booker.domain.main.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;
import com.sparta.booker.domain.search.elastic.service.ElasticSearchBookService;
import com.sparta.booker.domain.search.querydsl.dto.BookDto;
import com.sparta.booker.domain.search.querydsl.service.MySQLBookService;
import com.sparta.booker.domain.search.querydsl.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MySQLBookService mySQLBookService;

	private final RedisUtil redisUtil;

	@GetMapping("/login")
	public String login(){

		return "login";

	}

	@GetMapping("/main")
	public String main(Model model, Pageable pageable) {

		model.addAttribute("bookList", mySQLBookService.getBookList(pageable));
		model.addAttribute("topSearchList", redisUtil.SearchRankList());
		model.addAttribute("topLikeList", redisUtil.SearchList());

		return "main";

	}
}
