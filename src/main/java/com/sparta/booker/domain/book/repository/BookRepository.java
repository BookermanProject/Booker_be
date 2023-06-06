package com.sparta.booker.domain.book.repository;

import java.util.List;

import com.sparta.booker.domain.book.entity.Book;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookSearch {

	List<Book> findByBookNameOrAuthorOrderByLikeCountDesc(Pageable pageable,  String bookname, String author);

	List<Book> findByBookNameOrAuthorOrderByLikeCountAsc(Pageable pageable,  String bookname, String author);

	List<Book> findByBookNameOrAuthorOrderByStarDesc(Pageable pageable, String bookname, String author);

	List<Book> findByBookNameOrAuthorOrderByStarAsc(Pageable pageable, String bookname, String author);

}
