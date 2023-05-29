package com.sparta.booker.domain.book.repository;

import com.sparta.booker.domain.book.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookSearch {
}
