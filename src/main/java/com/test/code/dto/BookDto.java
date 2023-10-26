package com.test.code.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Language is required")
    private String language;

    @NotNull(message = "Pages is required")
    private int pages;

    @NotNull(message = "Publisher is required")
    private String publisher;

    @NotNull(message = "Published Date is required")
    private LocalDate publishedDate;

    private MultipartFile image;

    private String imageUrl;

    private String pdfUrl;

    private List<String> authors;
    private List<Integer> authorIds;

    @NotNull(message = "Genre is required")
    private List<Integer> genreIds;

    @NotNull(message = "Tag is required")
    private List<Integer> tagIds;
}
