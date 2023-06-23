package com.sparta.booker.domain.search.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.booker.domain.search.querydsl.entity.BookLike;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {

	BookLike findByUserIdAndBookId(String userId, Long bookId);

}
