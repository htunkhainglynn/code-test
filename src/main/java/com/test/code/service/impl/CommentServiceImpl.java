package com.test.code.service.impl;

import com.test.code.dto.CommentDto;
import com.test.code.entity.Book;
import com.test.code.entity.Comment;
import com.test.code.repo.BookRepo;
import com.test.code.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final BookRepo bookRepo;

    public CommentServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Transactional
    @Override
    public void createComment(Integer bookId, CommentDto commentDto) {
        Book book = bookRepo.getReferenceById(bookId);
        Comment comment = new Comment(commentDto);
        comment.setBook(book);
        book.addComment(comment);
    }
}
