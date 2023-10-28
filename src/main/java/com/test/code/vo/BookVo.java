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
    private double rating;
    private int ratingCount;
    private List<Author> authors;
    private List<String> genres;
    private List<String> tags;

    public BookVo(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.imageUrl = book.getCoverPhotoURL();
        if (!book.getRatings().isEmpty()) {
            double sum = book.getRatings().stream().mapToDouble(Rating::getRating).sum();
            double average = sum / book.getRatings().size();

            this.rating = Double.parseDouble(String.format("%.1f", average));
        } else {
            this.rating = 0;
        }
        this.ratingCount = book.getRatings().size();
        this.authors = book.getAuthors();
        this.genres = book.getGenres().stream().map(Genre::getName).toList();
        this.tags = book.getTags().stream().map(Tag::getName).toList();
    }
}
