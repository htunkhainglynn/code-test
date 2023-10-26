package com.test.code.api;

import com.test.code.dto.BookDto;
import com.test.code.exception.BookException;
import com.test.code.service.BookService;
import com.test.code.service.CloudinaryService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@Api(value = "Book Management")
@Slf4j
public class BookApi {

    private final BookService bookService;

    private final CloudinaryService cloudinaryService;

    @Autowired
    public BookApi(BookService bookService, CloudinaryService cloudinaryService) {
        this.bookService = bookService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new book")
    public ResponseEntity<Void> createBook(@Validated BookDto bookDto, HttpServletRequest request) {

        try {
            String url = cloudinaryService.uploadImageAndGetUrl(bookDto, request);
            log.info("Image url: {}", url);
            bookDto.setImageUrl(url);
        } catch (Exception e) {
            throw new BookException("Error while uploading image");
        }

        bookService.saveBook(bookDto);
        return ResponseEntity.status(201).build();
    }
}
