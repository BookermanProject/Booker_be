package com.sparta.booker.domain.book.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.event.entity.Event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "booker")
@Getter
@NoArgsConstructor
@Table(indexes = {@Index(name = "idxLikeCount", columnList = "likeCount"), @Index(name = "idxStar", columnList = "star")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String pub_date;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private double star;

    @Column(nullable = false)
    private String img_url;

    @Column(nullable = false)
    private LocalDateTime modificationTime;

    @Column(nullable = false)
    private LocalDateTime insertionTime;

    @Column(nullable = true)
    private int likeCount;



    public Book(BookDto bookDto) {
        this.bookName = bookDto.getBookName();
        this.author = bookDto.getAuthor();
        this.publisher = bookDto.getPublisher();
        this.pub_date = bookDto.getPub_date();
        this.category= bookDto.getCategory();
        this.introduction = bookDto.getIntroduction();
        this.star= bookDto.getStar();
        this.img_url= bookDto.getImg_url();
    }


    public void upLikeCount(){
        this.likeCount = likeCount + 1;
    }
}
