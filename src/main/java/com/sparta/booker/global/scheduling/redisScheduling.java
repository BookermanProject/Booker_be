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

    //좋아요 TOP10 리스트
    @Scheduled(cron = "0/1 * * * * ?")
    public void likeList(){
        List<Book> booklikelist = bookRepository.findTop10ByOrderByLikeCountDesc();
        for(int i = 0; i<booklikelist.size(); i++){
            redisUtil.set(String.valueOf((i+1)), booklikelist.get(i).getBookName());
        }
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void initializeTopkeywords(){
        redisUtil.delete("ranking");
    }
}
