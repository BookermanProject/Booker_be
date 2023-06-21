package com.sparta.booker.global.scheduling;

import com.sparta.booker.domain.search.querydsl.entity.Book;
import com.sparta.booker.domain.search.querydsl.repository.BookRepository;
import com.sparta.booker.domain.search.querydsl.util.RedisUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class redisScheduling {

    private final RedisUtil redisUtil;
    private final BookRepository bookRepository;


    // @Scheduled(cron = "0 0 0/1 * * *")
    // @Scheduled(cron = "0/1 * * * * ?")
    // @Scheduled(cron = "0/1 * * * * ?")
    public void likeList(){
        List<Book> booklikelist = bookRepository.findTop10ByOrderByLikeCountDesc();
        for(int i = 0; i<booklikelist.size(); i++){
            redisUtil.likeSet(booklikelist.get(i).getBookName(), Double.valueOf(booklikelist.get(i).getLikeCount())) ;
        }
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void initializeTopkeywords(){
        redisUtil.delete("ranking");
    }
}
