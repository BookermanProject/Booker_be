package com.sparta.booker.domain.search.querydsl.repository;

import java.util.List;

import com.sparta.booker.domain.search.querydsl.entity.Book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	List<Book> findByBookNameContainingOrAuthorContainingOrderByInsertionTimeAsc(Pageable pageable,  String bookname, String author);

	List<Book> findByBookNameContainingOrAuthorContainingOrderByLikeCountDesc(Pageable pageable,  String bookname, String author);

	List<Book> findByBookNameContainingOrAuthorContainingOrderByLikeCountAsc(Pageable pageable,  String bookname, String author);

	List<Book> findByBookNameContainingOrAuthorContainingOrderByStarDesc(Pageable pageable, String bookname, String author);

	List<Book> findByBookNameContainingOrAuthorContainingOrderByStarAsc(Pageable pageable, String bookname, String author);

	List<Book> findTop10ByOrderByLikeCountDesc();
}
