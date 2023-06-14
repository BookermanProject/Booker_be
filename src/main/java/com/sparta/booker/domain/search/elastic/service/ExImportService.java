package com.sparta.booker.domain.search.elastic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExImportService {

    // @Transactional
    // public ResponseEntity<Message> importExcel(MultipartFile excel) {
    //     try {
    //         InputStream inputStream = excel.getInputStream();
    //         XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
    //         int xssfSheet = xssfWorkbook.getNumberOfSheets();
    //         List<Book> queries = new ArrayList<>();
    //
    //         for (int i = 0; i < xssfSheet; i++) {
    //             Sheet loopSheet = xssfWorkbook.getSheetAt(i);
    //             Iterator<Row> rowIterator = loopSheet.iterator();
    //             rowIterator.next();
    //             while (rowIterator.hasNext()) {
    //                 Row row = rowIterator.next();
    //                 List<String> aLine = new ArrayList<>();
    //                 Iterator<Cell> cellIterator = row.cellIterator();
    //                 while (cellIterator.hasNext()) {
    //                     Cell cell = cellIterator.next();
    //                     switch (cell.getCellType()) {
    //                         case BOOLEAN:
    //                             aLine.add(String.valueOf(cell.getBooleanCellValue()));
    //                             break;
    //                         case NUMERIC:
    //                             aLine.add(String.valueOf(cell.getNumericCellValue()));
    //                             break;
    //                         case STRING:
    //                             aLine.add(cell.getStringCellValue());
    //                             break;
    //                         case FORMULA:
    //                             break;
    //                         case BLANK:
    //                             aLine.add("");
    //                             break;
    //                     }// switch
    //                 }// while
    //                 if (aLine.size() == 0) continue;
    //                 Book book = new Book(aLine.get(1), aLine.get(2), aLine.get(3), aLine.get(4), aLine.get(5), aLine.get(6),
    //                         aLine.get(7), aLine.get(8), aLine.get(9), aLine.get(10), aLine.get(11), aLine.get(12));
    //                 bookRepository.save(book);
    //             }
    //         }
    //         inputStream.close();
    //     }
    //     catch (IOException | DirectoryIteratorException ex) {
    //         System.err.println(ex);
    //     }
    //     return Message.toResponseEntity(SuccessCode.IMPORT_SUCCESS, "엑셀 업로드 성공");
    // }


}
