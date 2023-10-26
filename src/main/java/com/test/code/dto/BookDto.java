package com.test.code.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDto {
    private String title;
    private String description;
    private String isbn;
    private String language;
    private int pages;
    private String publisher;
    private LocalDate publishedDate;
    private List<String> authors;
    private List<Integer> authorIds;
    private List<Integer> genreIds;
    private List<Integer> tagIds;
}
