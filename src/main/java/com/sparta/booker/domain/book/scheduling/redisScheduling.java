package com.sparta.booker.domain.book.scheduling;

import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.book.util.RedisUtil;
import com.sparta.booker.domain.book.repository.BookRepository;
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
    // @Scheduled(cron = "0/10 * * * * ?")
    public void likeList(){
        List<Book> booklikelist = bookRepository.findTop10ByOrderByLikeCountDesc();
        for(int i = 0; i<booklikelist.size(); i++){
            redisUtil.set(String.valueOf((i+1)), booklikelist.get(i).getBookName());
        }
    }
}
