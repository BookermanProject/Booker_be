package com.sparta.booker.domain.book.service;

import com.sparta.booker.global.exception.SuccessCode;
import com.sparta.booker.domain.book.dto.BookFilterDto;
import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.dto.BookListDto;
import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.book.repository.BookElasticOperation;
import com.sparta.booker.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.sparta.booker.global.dto.Message;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final BookElasticOperation bookElasticOperation;

    @Transactional
    public ResponseEntity<Message> importExcel(MultipartFile excel) {
        try {
            InputStream inputStream = excel.getInputStream();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            int xssfSheet = xssfWorkbook.getNumberOfSheets();
            List<Book> queries = new ArrayList<>();

            for (int i = 0; i < xssfSheet; i++) {
                Sheet loopSheet = xssfWorkbook.getSheetAt(i);
                Iterator<Row> rowIterator = loopSheet.iterator();
                rowIterator.next();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    List<String> aLine = new ArrayList<>();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getCellType()) {
                            case BOOLEAN:
                                aLine.add(String.valueOf(cell.getBooleanCellValue()));
                                break;
                            case NUMERIC:
                                aLine.add(String.valueOf(cell.getNumericCellValue()));
                                break;
                            case STRING:
                                aLine.add(cell.getStringCellValue());
                                break;
                            case FORMULA:
                                break;
                            case BLANK:
                                aLine.add("");
                                break;
                        }// switch
                    }// while
                    if (aLine.size() == 0) continue;
                    Book book = new Book(aLine.get(1), aLine.get(2), aLine.get(3), aLine.get(4), aLine.get(5), aLine.get(6),
                            aLine.get(7), aLine.get(8), aLine.get(9), aLine.get(10), aLine.get(11), aLine.get(12));
                    bookRepository.save(book);
                }
            }
            inputStream.close();
        }
        catch (IOException | DirectoryIteratorException ex) {
            System.err.println(ex);
        }
        return Message.toResponseEntity(SuccessCode.IMPORT_SUCCESS, "엑셀 업로드 성공");
    }

    @Transactional(readOnly = true)
//    @CircuitBreaker(name = "ElasticError", fallbackMethod = "keywordSearchBySql")
    public ResponseEntity<Message> searchWordByElastic(BookFilterDto bookFilterDto) {
        //엘라스틱서치 검색결과를 해당클래스로 원하는 데이터타입으로 가져올 수 있음
        SearchHits<BookDto> searchHits = bookElasticOperation.keywordSearchByElastic(bookFilterDto);
        BookListDto bookListDto = resultToDto(searchHits, bookFilterDto);


        return Message.toResponseEntity(SuccessCode.SEARCH_SUCCESS, bookListDto);
    }

    public ResponseEntity<Message> searchFilterByElastic(BookDto bookRequestDto) {

        return Message.toResponseEntity(SuccessCode.SEARCH_SUCCESS, "test" );
    }

    private BookListDto resultToDto(SearchHits<BookDto> search, BookFilterDto bookFilterDto) {
        List<SearchHit<BookDto>> searchHits = search.getSearchHits();
        System.out.println(search.getTotalHits());
        List<BookDto> bookDtoList = searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());

        List<Object> searchAfter = setSearchAfter(searchHits, bookFilterDto);
        String searchAfterSort = String.valueOf(searchAfter.get(0));
        Long searchAfterId = Long.parseLong(String.valueOf(searchAfter.get(1)));

        return new BookListDto(bookDtoList, searchAfterSort, searchAfterId, bookFilterDto.getPage(), true);
    }

    private List<Object> setSearchAfter(List<SearchHit<BookDto>> searchHits, BookFilterDto bookFilterDto) {
        List<Object> lists = new ArrayList<>();
        if (searchHits.size() == 0) {		// 검색 결과가 없는 경우
            lists.add("-1");
            lists.add("-1");
            return lists;
        } else if (searchHits.size() < bookFilterDto.getTotalRow()) {		// 검색 결과가 총 개수보다 작은 경우
            lists.add("0");
            lists.add("-1");
            return lists;
        }
        return searchHits.get(searchHits.size() - 1).getSortValues();
    }

//    @Transactional(readOnly = true)
//    public void keywordSearchBySql(BookFilterDto filter, Throwable t) {
//        log.warn("keyword Elastic Down : " + t.getMessage());
//    }
}
