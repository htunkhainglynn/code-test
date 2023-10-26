package com.test.code.entity;

import com.test.code.dto.BookDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;

    private String description;

    @Column(unique = true)
    private String isbn;

    private String language;

    private int pages;

    private String publisher;

    private LocalDate publishedDate;

//    @NotNull
    private String coverPhotoURL;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Author> authors;

    @OneToMany(mappedBy = "book",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "book",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToMany
    private List<Tag> tags;

    @ManyToMany
    private List<Genre> genres;

    public Book(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.description = bookDto.getDescription();
        this.isbn = bookDto.getIsbn();
        this.language = bookDto.getLanguage();
        this.pages = bookDto.getPages();
        this.publisher = bookDto.getPublisher();
        this.publishedDate = bookDto.getPublishedDate();

        // if the book authors already don't exist in the database, then add them to the book
        if (!bookDto.getAuthors().isEmpty()) {
            this.authors = bookDto.getAuthors().stream().map(Author::new).toList();
        }
    }

}
