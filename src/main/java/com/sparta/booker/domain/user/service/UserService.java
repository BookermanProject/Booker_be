package com.sparta.booker.domain.user.service;

import com.sparta.booker.domain.search.querydsl.util.RedisUtil;
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

    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;

    @Value("${admin.secret.key}")
    private String ADMIN_TOKEN;

    // 회원가입
    public ResponseEntity<ResponseDto> signup(SignupRequestDto signupRequestDto) {

        // 회원 중복 확인
        if (userRepository.findByUserId(signupRequestDto.getUserId()).isPresent()) {
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
    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {


        //신규로그인
        if (loginRequestDto.getJwt() == null) {
            User user = userRepository.findByUserId(loginRequestDto.getUserId()).orElseThrow(
                    () -> new NullPointerException("회원이 존재하지 않습니다.")
            );
            if (!user.getPassword().equals(loginRequestDto.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            String token = jwtUtil.createToken(user.getUserId(), user.getRole());
            response.addHeader("Authorization", token);
            return ResponseEntity.ok().body(new ResponseDto(token));
        }

        //로그아웃된 토큰값가지고 로그인 시도
        if(redisUtil.get(loginRequestDto.getJwt()) != null){
            User user = userRepository.findByUserId(loginRequestDto.getUserId()).orElseThrow(
                    () -> new NullPointerException("회원이 존재하지 않습니다.")
            );
            if (!user.getPassword().equals(loginRequestDto.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
            String token = jwtUtil.createToken(user.getUserId(), user.getRole());
            response.addHeader("Authorization", token);
            return ResponseEntity.ok().body(new ResponseDto(token));
        }
        return null;
    }
    public void logout(String token) {
        redisUtil.settoken(token);
    }
}
