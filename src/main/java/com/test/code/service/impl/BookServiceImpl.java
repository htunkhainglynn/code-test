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
import com.test.code.vo.BookDetailVo;
import com.test.code.vo.BookVo;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final GenreRepo genreRepo;
    private final TagRepo tagRepo;
    private final AuthorRepo authorRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, GenreRepo genreRepo, TagRepo tagRepo, AuthorRepo authorRepo) {
        this.bookRepo = bookRepo;
        this.genreRepo = genreRepo;
        this.tagRepo = tagRepo;
        this.authorRepo = authorRepo;
    }

    @Transactional
    @Override
    public void saveBook(BookDto bookDto) {
        Book book = new Book(bookDto);

        // update case
        if (bookDto.getId() != null ) {
            book.setId(bookDto.getId());
        }

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
            Genre genre = genreRepo.getReferenceById(genreId);
            genres.add(genre);
        });

        bookDto.getTagIds().forEach(tagId -> {
            Tag tag = tagRepo.getReferenceById(tagId);
            tags.add(tag);
        });

        // if the book authors already don't exist in the database, add them to the database too by using cascade
        if (!authors.isEmpty()) {
            authors.forEach(book::addAuthor);
        }

        genres.forEach(book::addGenre);
        tags.forEach(book::addTag);

        bookRepo.save(book);
    }

    @Override
    public Page<BookVo> getAllBooks(String keyword, Optional<Integer> page, Optional<Integer> size) {

        PageRequest pageRequest = PageRequest.of(page.orElse(0), size.orElse(10));

        Specification<Book> specification = (root, query, cb) -> cb.conjunction();

        if (StringUtils.hasLength(keyword)) {
            specification = (root, query, cb) -> {
                Subquery<Book> subquery = query.subquery(Book.class);
                Root<Book> subRoot = subquery.from(Book.class);

                String keywordLowerCase = keyword.toLowerCase().trim();

                subquery.select(subRoot)
                        .where(cb.or(
                                cb.like(cb.lower(subRoot.get("title")), "%" + keywordLowerCase + "%"),
                                cb.like(cb.lower(subRoot.get("isbn")), "%" + keywordLowerCase + "%"),
                                cb.like(cb.lower(subRoot.get("publisher")), "%" + keywordLowerCase + "%"),
                                cb.like(cb.lower(subRoot.get("authors").get("name")), "%" + keywordLowerCase + "%"),
                                cb.like(cb.lower(subRoot.get("tags").get("name")), "%" + keywordLowerCase + "%"),
                                cb.like(cb.lower(subRoot.get("genres").get("name")), "%" + keywordLowerCase + "%")
                        ))
                        .distinct(true);

                return cb.in(root).value(subquery);
            };
        }

        return bookRepo.findAll(specification, pageRequest).map(BookVo::new);
    }

    @Override
    public BookDetailVo getBookDetailById(int id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isEmpty()) {
            throw new RuntimeException("Book not found");
        }

        BookDetailVo bookDetailVo = new BookDetailVo(book.get());

        calculateRelatedBooks(bookDetailVo);

        return bookDetailVo;
    }

    @Transactional
    @Override
    public void deleteBookById(int id) {
        bookRepo.deleteById(id);
    }

    @Override
    public Optional<String> getBookCoverPhotoUrlById(int id) {
        return bookRepo.findById(id).map(Book::getCoverPhotoURL);
    }

    @Override
    public Optional<Book> getBookById(int id) {
        return Optional.of(bookRepo.getReferenceById(id));
    }

    private void calculateRelatedBooks(BookDetailVo bookDetailVo) {
        String genre = bookDetailVo.getGenres().get(0);
        String tag = bookDetailVo.getTags().get(0);
        String author = bookDetailVo.getAuthors().get(0);
        double rating = bookDetailVo.getRating();

        List<BookVo> booksByGenre = bookRepo.findByGenres(genre);
        List<BookVo> booksByTag = bookRepo.findByTags(tag);
        List<BookVo> booksByAuthor = bookRepo.findByAuthors(author);
        List<BookVo> booksByRating = bookRepo.findByRating(rating);

        List<BookVo> relatedBooks = Stream.of(booksByGenre, booksByTag, booksByAuthor, booksByRating)
                .flatMap(List::stream)
                .filter(book -> book.getId() != bookDetailVo.getId())
                .distinct()
                .limit(7)
                .toList();

        bookDetailVo.setRelatedBooks(relatedBooks);
    }
}
