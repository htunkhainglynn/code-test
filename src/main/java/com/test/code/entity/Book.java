package com.test.code.entity;

import com.test.code.dto.BookDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;

    private String summary;

    @Column(unique = true)
    private String isbn;

    private String language;

    private int pages;

    private String publisher;

    private LocalDate publishedDate;

    @NotNull
    private String coverPhotoURL;

    private String pdfURL;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "book",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    @ManyToMany
    private List<Genre> genres = new ArrayList<>();

    public Book(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.summary = bookDto.getSummary();
        this.isbn = bookDto.getIsbn();
        this.coverPhotoURL = bookDto.getImageUrl();
        this.language = bookDto.getLanguage();
        this.pages = bookDto.getPages();
        this.publisher = bookDto.getPublisher();
        this.publishedDate = bookDto.getPublishedDate();
        this.pdfURL = bookDto.getPdfUrl();

        // if the book authors already don't exist in the database, then add them to the book
        if (!bookDto.getAuthors().isEmpty()) {
            bookDto.getAuthors().forEach(author -> {
                Author author1 = new Author(author);
                this.authors.add(author1);
            });
        }
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

}
