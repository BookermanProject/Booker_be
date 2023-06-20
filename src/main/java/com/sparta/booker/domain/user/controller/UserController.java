package com.sparta.booker.domain.user.controller;

import com.sparta.booker.domain.user.dto.LoginRequestDto;
import com.sparta.booker.domain.user.dto.ResponseDto;
import com.sparta.booker.domain.user.dto.SignupRequestDto;
import com.sparta.booker.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    //@Operation(summary = "회원가입 API", description = "회원가입")
    //@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "회원 가입 성공")})
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }

    // 로그인
    //@Operation(summary = "로그인 API", description = "로그인")
    //@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "로그인 성공")})
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }
}
