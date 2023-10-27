package com.test.code.api;

import com.test.code.dto.BookDto;
import com.test.code.entity.Book;
import com.test.code.exception.BookException;
import com.test.code.service.BookService;
import com.test.code.service.CloudinaryService;
import com.test.code.util.PagerResult;
import com.test.code.vo.BookDetailVo;
import com.test.code.vo.BookVo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

        // upload image to cloudinary
        uploadImage(bookDto, request);

        bookService.saveBook(bookDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Need to login")
    public ResponseEntity<PagerResult<BookVo>> getAllBooks(@RequestParam(required = false) String keyword,
                                                  @RequestParam Optional<Integer> page,
                                                  @RequestParam Optional<Integer> size) {

        Page<BookVo> bookResult = bookService.getAllBooks(keyword, page, size);
        PagerResult<BookVo> pagerResult = PagerResult.of(bookResult);
        return ResponseEntity.ok().body(pagerResult);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by id", description = "Need to login")
    public ResponseEntity<BookDetailVo> getBookDetailById(@PathVariable int id) {
        BookDetailVo bookDetailVo = bookService.getBookDetailById(id);
        return ResponseEntity.ok().body(bookDetailVo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book by id", description = "Need to login")
    public ResponseEntity<Void> updateBookById(@PathVariable int id, @Validated BookDto bookDto, HttpServletRequest request) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isEmpty()) {
            throw new BookException("Book not found");
        }

        // if new photo is uploaded
        updateCoverPhoto(bookDto, request, book.get());

        bookDto.setId(book.get().getId());

        bookService.saveBook(bookDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by id", description = "Need to login")
    public ResponseEntity<Void> deleteBookById(@PathVariable int id) {
        bookService.getBookCoverPhotoUrlById(id).ifPresent(cloudinaryService::deleteImage);

        bookService.deleteBookById(id);
        return ResponseEntity.status(204).build();
    }

    private void updateCoverPhoto(BookDto bookDto, HttpServletRequest request, Book book) {
        if (!bookDto.getImage().isEmpty()) {
            // delete old image
            cloudinaryService.deleteImage(book.getCoverPhotoURL());

            // update new image
            uploadImage(bookDto, request);
        }
    }

    private void uploadImage(BookDto bookDto, HttpServletRequest request) {
        try {
            String url = cloudinaryService.uploadImageAndGetUrl(request);
            log.info("Image url: {}", url);
            bookDto.setImageUrl(url);
        } catch (Exception e) {
            throw new BookException("Error while uploading image");
        }
    }
}
