package com.sparta.booker.domain.book.scheduling;

import com.sparta.booker.domain.book.dto.LikeDto;
import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.book.redisUtil.RedisUtil;
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
//	@Scheduled(cron = "0 0 20 20 * ?")
    @Scheduled(cron = "0 35 20 * * ?")
    public List<LikeDto> likeList(){
        System.out.println("스케줄러 시작합니다");
        List<Book> booklikecount = bookRepository.OrderByLikeCount();
        for(Book book : booklikecount){
            System.out.println("좋아요카운트 순위");
            System.out.println(book.toString());
            System.out.println("================");
        }
        return null;
    }
}
