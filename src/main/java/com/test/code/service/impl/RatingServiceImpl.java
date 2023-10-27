package com.test.code.service.impl;

import com.test.code.entity.Book;
import com.test.code.entity.Rating;
import com.test.code.repo.BookRepo;
import com.test.code.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingServiceImpl implements RatingService {

    private final BookRepo bookRepo;

    public RatingServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Transactional
    @Override
    public void createRating(Integer bookId, Integer star) {
        Book book = bookRepo.getReferenceById(bookId);
        Rating rating = new Rating(star);
        rating.setBook(book);
        book.addRating(rating);
    }
}
