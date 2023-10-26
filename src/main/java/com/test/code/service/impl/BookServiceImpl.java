package com.test.code.service.impl;

import com.test.code.dto.BookDto;
import com.test.code.entity.Author;
import com.test.code.entity.Book;
import com.test.code.entity.Genre;
import com.test.code.entity.Tag;
import com.test.code.repo.AuthorRepo;
import com.test.code.repo.BookRepo;
import com.test.code.repo.GenreRepo;
import com.test.code.repo.TagRepo;
import com.test.code.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final GenreRepo genreRepo;
    private final TagRepo tagRepo;
    private final AuthorRepo authorRepo;

    public BookServiceImpl(BookRepo bookRepo, GenreRepo genreRepo, TagRepo tagRepo, AuthorRepo authorRepo) {
        this.bookRepo = bookRepo;
        this.genreRepo = genreRepo;
        this.tagRepo = tagRepo;
        this.authorRepo = authorRepo;
    }

    @Override
    public void saveBook(BookDto bookDto) {
        Book book = new Book(bookDto);
        List<Author> authors = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        // if the book authors already exist in the database, just add them to the book
        if (bookDto.getAuthorIds() != null) {
            bookDto.getAuthorIds().forEach(authorId -> {
                Optional<Author> author = authorRepo.findById(authorId);
                author.ifPresent(authors::add);
            });
        }

        bookDto.getGenreIds().forEach(genreId -> {
            Optional<Genre> genre = genreRepo.findById(genreId);
            genre.ifPresent(genres::add);
        });

        bookDto.getTagIds().forEach(tagId -> {
            Optional<Tag> tag = tagRepo.findById(tagId);
            tag.ifPresent(tags::add);
        });

        // if the book authors already don't exist in the database, add them to the database too by using cascade
        if (!authors.isEmpty()) {
            book.setAuthors(authors);
        }

        book.setGenres(genres);
        book.setTags(tags);

        bookRepo.save(book);
    }
}
