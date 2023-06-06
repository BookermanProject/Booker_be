package com.sparta.booker.domain.book.entity;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.event.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "booker")
@Getter
@NoArgsConstructor
@ToString
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
    private String pubDate;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private double star;

    @Column(nullable = false)
    private String imgUrl;

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
        this.pubDate = bookDto.getPub_date();
        this.category= bookDto.getCategory();
        this.introduction = bookDto.getIntroduction();
        this.star= bookDto.getStar();
        this.imgUrl= bookDto.getImg_url();
    }


    public void upLikeCount(){
        this.likeCount = likeCount + 1;
    }
}
