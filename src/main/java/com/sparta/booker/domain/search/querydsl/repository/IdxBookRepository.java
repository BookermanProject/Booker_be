package com.sparta.booker.domain.search.querydsl.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.booker.domain.search.querydsl.entity.Book;


public interface IdxBookRepository extends JpaRepository<Book, Long> {
	@Query(nativeQuery = true, value =
		"SELECT * FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "order by like_count desc "
		,countQuery =
		"SELECT count(*) FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) " )
	public List<Book> findByBookListbyFullTextLikedesc(@Param("keyword") String keyword, @Param("pageable")Pageable pageable);


	@Query(nativeQuery = true, value =
		"SELECT * FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "order by like_count asc "
		,countQuery =
		"SELECT count(*) FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) ")
	public List<Book> findByBookListbyFullTextLikeasc(@Param("keyword") String keyword, @Param("pageable")Pageable pageable);



	@Query(nativeQuery = true, value =
		"SELECT * FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "order by star desc "
		,countQuery =
		"SELECT count(*) FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) " )
	public List<Book> findByBookListbyFullTextStardesc(@Param("keyword") String keyword, @Param("pageable")Pageable pageable);


	@Query(nativeQuery = true, value =
		"SELECT * FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "order by star asc "
		,countQuery =
		"SELECT count(*) FROM booker "
			+ "where MATCH(book_name) AGAINST(:keyword'*' IN BOOLEAN MODE) "
			+ "union all "
			+ "SELECT * FROM booker "
			+ "WHERE MATCH(author) AGAINST(:keyword'*' IN BOOLEAN MODE) " )
	public List<Book> findByBookListbyFullTextStarasc(@Param("keyword") String keyword, @Param("pageable")Pageable pageable);

}