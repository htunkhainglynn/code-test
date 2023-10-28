package com.test.code.service;

import com.test.code.dto.BookDto;
import com.test.code.entity.Book;
import com.test.code.vo.BookDetailVo;
import com.test.code.vo.BookVo;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface BookService {
    void saveBook(BookDto bookDto);
    
    Page<BookVo> getAllBooks(String keyword, Optional<Integer> page, Optional<Integer> size);

    BookDetailVo getBookDetailById(int id);

    void deleteBookById(int id);

    Optional<String> getBookCoverPhotoUrlById(int id);

    Optional<Book> getBookById(int id);
}
