package com.sparta.booker.domain.search.querydsl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "book_like")
@Getter
@Setter
@NoArgsConstructor
public class BookLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private Long bookId;

	@Builder
	public BookLike(String userId, Long bookId) {
		this.userId = userId;
		this.bookId = bookId;
	}
}
