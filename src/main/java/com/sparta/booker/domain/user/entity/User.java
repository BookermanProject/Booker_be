package com.sparta.booker.domain.user.entity;

import com.sparta.booker.domain.user.dto.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    public User(SignupRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.password = requestDto.getPassword();
        this.address = requestDto.getAddress();
    }
}
