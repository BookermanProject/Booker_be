package com.sparta.booker.domain.search.querydsl.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.sparta.booker.domain.search.querydsl.dto.LikeBookDto;
import com.sparta.booker.domain.search.querydsl.dto.RankBookDto;

@Component
@RequiredArgsConstructor
public class RedisUtil{

    private final RedisTemplate<String, Object> redisTemplate;
    public void upKeywordCount(String keyword){
        try {
            redisTemplate.opsForZSet().incrementScore("ranking", keyword,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //인기검색어 리스트 1위~10위까지
    public List<RankBookDto> SearchRankList() {
        String key = "ranking";
        ZSetOperations<String, Object> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);  //score순으로 10개 보여줌
        return typedTuples.stream()
            .map(typedTuple -> new RankBookDto(typedTuple.getValue().toString(),(int)Math.round(typedTuple.getScore())))
            .collect(Collectors.toList());
    }

    // 좋아요 검색어 리스트 1-10위까지
    public List<LikeBookDto> SearchList() {
        String key = "Like";
        ZSetOperations<String, Object> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);
        //score순으로 10개 보여줌
        return typedTuples.stream()
            .map(typedTuple -> new LikeBookDto(typedTuple.getValue().toString(),(int)Math.round(typedTuple.getScore())))
            .collect(Collectors.toList());
    }

    // 인기검색어 전체 삭제하기

    public void Deletekey(String keyname){
        redisTemplate.opsForHash().delete(keyname);
    }

    public void likeSet(String bookname, double score){
        redisTemplate.opsForZSet().add("Like", bookname, score);
    }

    public void setLong(String key, String value) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value);
        //        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        //        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setBlackList(String key, Object o, Long minutes) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object getBlackList(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}