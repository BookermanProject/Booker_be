package com.sparta.booker.service;

import com.sparta.booker.entity.Book;
import com.sparta.booker.exception.Message;
import com.sparta.booker.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.sparta.booker.exception.StatusEnum;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

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
        Message message = Message.setSuccess(StatusEnum.OK, "엑셀 업로드 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
