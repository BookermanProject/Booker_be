package com.sparta.booker.domain.user.service;

import com.sparta.booker.domain.user.dto.LoginRequestDto;
import com.sparta.booker.domain.user.dto.ResponseDto;
import com.sparta.booker.domain.user.dto.SignupRequestDto;
import com.sparta.booker.domain.user.entity.User;
import com.sparta.booker.domain.user.entity.UserRole;
import com.sparta.booker.domain.user.repository.UserRepository;
import com.sparta.booker.global.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${admin.secret.key}")
    private String ADMIN_TOKEN;

    // 회원가입
    public ResponseEntity<ResponseDto> signup(SignupRequestDto signupRequestDto){

        // 회원 중복 확인
        if(userRepository.findByUserId(signupRequestDto.getUserId()).isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        userRepository.save(new User(signupRequestDto, role));
        return ResponseEntity.ok().body(new ResponseDto("회원가입 성공"));
    }

    // 로그인
    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response){

        // 입력한 ID를 기반으로 회원 존재 유무 체크
        User user = userRepository.findByUserId(loginRequestDto.getUserId()).orElseThrow(
                () -> new NullPointerException("회원이 존재하지 않습니다.")
        );

        // 비밀번호 일치여부 체크
        if(!user.getPassword().equals(loginRequestDto.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //Token 발급
        String token = jwtUtil.createToken(user.getUserId(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseEntity.ok().body(new ResponseDto(token));
    }
}
