package com.test.code.service.impl;

import com.test.code.dto.CommentDto;
import com.test.code.entity.Book;
import com.test.code.entity.Comment;
import com.test.code.exception.BookException;
import com.test.code.repo.BookRepo;
import com.test.code.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final BookRepo bookRepo;

    public CommentServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Transactional
    @Override
    public void createComment(Integer bookId, CommentDto commentDto) {
        Optional<Book> book = bookRepo.findById(bookId);

        if (book.isEmpty()) {
            throw new BookException("Book not found");
        }
        Comment comment = new Comment(commentDto);
        comment.setBook(book.get());
        book.get().addComment(comment);

        bookRepo.save(book.get());
    }
}
