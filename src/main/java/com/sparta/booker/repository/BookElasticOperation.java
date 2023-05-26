package com.sparta.booker.repository;

import com.sparta.booker.dto.BookDto;
import com.sparta.booker.dto.BookFilterDto;
import com.sparta.booker.dto.autoMakerDto;
import com.sparta.booker.elastic.custom.CustomBoolQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.sparta.booker.elastic.custom.CustomQueryBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Repository
@RequiredArgsConstructor
@Getter
public class BookElasticOperation {

    private final ElasticsearchOperations operations;

    // keyword 검색
    public SearchHits<BookDto> keywordSearchByElastic(BookFilterDto bookFilterDto) {
        //네이티브 검색쿼리 인스턴스 생성
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                //엘라스틱 서치는 BM25 알고리즘을 이용한 도큐먼트 점수계산을 함
                //검색결과의 최소점수를 50으로 설정함
                .withMinScore(50f)
                .withQuery(new CustomBoolQueryBuilder()		//검색쿼리를 설정하는 부분 CustomBoolQueryBuilder 을 생성함으로써 커스텀한다는 뜻
                        //must -> title, quthor 필드에 매칭되는 키워드가 필수로 있어야 함
                        .must(multiMatchQuery(bookFilterDto.getBookCnt(), "bookName", "authors"))
//                        .filter(CustomQueryBuilders.matchQuery("category.keyword", bookFilterDto.getQuery()))
//                        .filter(CustomQueryBuilders.matchQuery("baby_category.keyword", bookFilterDto.getQuery()))
//                        .should(new RangeQueryBuilder("inventory").gte(1).boost(40F))
                        .should(matchPhraseQuery("bookName", bookFilterDto.getQuery()))
                )
                .withSorts(sortQuery(bookFilterDto.getSort()))
                .build();

        return operations.search(build, BookDto.class);
    }

    // filter 검색
    public SearchHits<BookDto> filterSearchByElastic(BookFilterDto bookFilterDto, List<Object> searchAfter) {
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withMinScore(50f)
                .withQuery(new CustomBoolQueryBuilder()
                        .must(multiMatchQuery(bookFilterDto.getQuery(), "bookName", "authors"))
//                        .should(new RangeQueryBuilder("inventory").gte(1).boost(40F))
//                        .mustNot(inventoryQuery(bookFilterDto.getSort()))
//                        .filter(matchQuery("category.keyword", bookFilterDto.getSort()))
//                        .filter(matchQuery("baby_category.keyword", bookFilterDto.getBabyCategory()))
//                        .filter(priceQuery(bookFilterDto.getMinPrice(), bookFilterDto.getMaxPrice()))
//                        .filter(starQuery(bookFilterDto.getStar()))
//                        .filter(yearQuery(bookFilterDto.getPublicationYear()))
                        .should(matchPhraseQuery("bookName", bookFilterDto.getQuery()))
                        .must(matchQuery("authors", bookFilterDto.getAuthors()))
                        .must(matchQuery("publisher", bookFilterDto.getPublisher())))
                .withSearchAfter(searchAfter)
                .withSorts(sortQuery(bookFilterDto.getSort()))
                .build();

        return operations.search(build, BookDto.class);
    }

    // 자동 완성
    public SearchHits<autoMakerDto> autoMaker(String query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder()
                        .should(new MatchPhraseQueryBuilder("bookName", query))
                        .should(new PrefixQueryBuilder("bookName.keyword", query))
                )
                .withCollapseField("bookName.keyword")
                .build();

        return operations.search(searchQuery, autoMakerDto.class);
    }
}
