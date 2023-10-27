package com.test.code.vo;

import com.test.code.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookVo {
    private int id;
    private String isbn;
    private String title;
    private String imageUrl;
    private int rating;
    private int ratingCount;
    private List<String> authors;
    private List<String> genres;
    private List<String> tags;

    public BookVo(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.imageUrl = book.getCoverPhotoURL();
        if (!book.getRatings().isEmpty()) {
            this.rating = book.getRatings().stream().mapToInt(Rating::getRating).sum() / book.getRatings().size();
        } else {
            this.rating = 0;
        }
        this.ratingCount = book.getRatings().size();
        this.authors = book.getAuthors().stream().map(Author::getName).toList();
        this.genres = book.getGenres().stream().map(Genre::getName).toList();
        this.tags = book.getTags().stream().map(Tag::getName).toList();
    }
}
