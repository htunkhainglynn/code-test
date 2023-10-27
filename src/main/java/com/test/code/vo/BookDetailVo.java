package com.test.code.vo;

import com.test.code.entity.Book;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookDetailVo extends BookVo {
    private String summary;
    private String pdfUrl;
    private List<CommentVo> comments;
    List<BookVo> relatedBooks;

    public BookDetailVo(Book book) {
        super(book);
        this.summary = book.getSummary();
        this.pdfUrl = book.getPdfURL();
        book.getComments().forEach(comment -> System.out.println(comment.getContent()));
        this.comments = book.getComments().stream().map(CommentVo::new).toList();

    }

}
